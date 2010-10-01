package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class ExitCommandHandler implements CommandHandler {

  private static final String SUB_LEFT = "%s left the chat";
  private static final String ONE_INSTANCE_LEFT = "%s instance remains";
  private static final String MANY_INSTANCE_LEFT = "%s instances remain";
  
  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {
    Subscriber.forget(subscriber);
    
    PartyLine partyLine = lineManager.getPartyLine(subscriber);

    if (partyLine != null) {
      String name = subscriber.getUser().getName();
      
      if (subscriber.getAlias() != null) {
        name += " (" + subscriber.getAlias() + ")";
      }

      String announcement = String.format(SUB_LEFT, name);
      
      // Count how many instances of this user remain, in case they were in
      // the room multiple times
      int instanceCount = 0;
      for (Subscriber sub : partyLine.getSubscribers()) {
        if (sub != subscriber &&
            sub.getUser().getName().equals(subscriber.getUser().getName())) {
          instanceCount++;
        }
      }
      
      if (instanceCount > 0) {
        announcement += ", " + String.format(
          instanceCount == 1 ? ONE_INSTANCE_LEFT : MANY_INSTANCE_LEFT, 
          instanceCount);
      }
      

      partyBot.announce(partyLine, announcement);
    }
    
    return lineManager.unsubscribe(subscriber, commandMatcher.group(2));
  }

}
