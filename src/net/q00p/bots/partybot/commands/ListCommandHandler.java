package net.q00p.bots.partybot.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;
import net.q00p.bots.util.DateUtil;

import java.util.Collection;
import java.util.regex.Matcher;

public class ListCommandHandler extends PartyLineCommandHandler {

  @Override
  public String doCommand(
      PartyBot partyBot,
      PartyLine partyLine,
      Subscriber subscriber,
      Matcher commandMatcher) {
    StringBuilder sb = new StringBuilder();
    
    sb.append("#" + partyLine.getName() + " members:\n");
    
    Multimap<String, Subscriber> subscribersByName = HashMultimap.create();
    
    for (Subscriber sub : partyLine.getSubscribers()) {
      subscribersByName.put(sub.getUser().getName(), sub);
    }
    
    for (String name : subscribersByName.keySet()) {
      sb.append(name).append(":");
      
      Collection<Subscriber> subscribers = subscribersByName.get(name);
      boolean isCompact = subscribers.size() == 1; 
      
      if (isCompact) {
        sb.append(" ");
      } else {
        sb.append("\n");
      }
      
      for (Subscriber sub : subscribersByName.get(name)) {
        if (!isCompact) {
          sb.append("  ");
        }
        
        sb.append("using ")
          .append(sub.getBotScreenName().toLowerCase());

        if (sub.getAlias() != null) {
          sb.append(" as ").append(sub.getAlias());
        }

        if (sub.isSnoozing()) {
          sb.append(" snoozing for ")
            .append(DateUtil.timeTill(sub.getSnoozeUntil()));
        }


        if (sub.getLastActivityTime() > 0) {
          sb.append(" last seen ")
            .append(DateUtil.timeSince(sub.getLastActivityTime()))
            .append(" ago");
        }
        
        sb.append("\n");
      }
    }
    
    return sb.toString();
  }
}
