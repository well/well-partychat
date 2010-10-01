package net.q00p.bots.partybot.commands;

import com.google.common.collect.Sets;

import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.Set;
import java.util.regex.Matcher;

public class MailCommandHandler extends PartyLineCommandHandler {

  @Override
  public String doCommand(
      PartyBot partyBot,
      PartyLine partyLine,
      Subscriber subscriber,
      Matcher commandMatcher) {
    Set<String> emailAddresses = Sets.newTreeSet();

    for (Subscriber sub : partyLine.getSubscribers()) {
      emailAddresses.add(sub.getUser().getName());
    }
      
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    
    for (String emailAddress : emailAddresses) {
      if (!isFirst) {
        sb.append(", ");
      } else {
        isFirst = false;
      }

      sb.append("<");
      sb.append(emailAddress);
      sb.append(">");
    }
    
    return sb.toString();
  }
  
}
