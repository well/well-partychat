package net.q00p.bots.partybot.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultiset;

import net.q00p.bots.io.Connection;
import net.q00p.bots.io.ConnectionFactory;
import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;
import net.q00p.bots.util.DateUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Displays usage stats about the whole partychat service (across all
 * installations)
 *
 * @author <a href="mailto:mihai.parparita@gmail.com">Mihai Parparita</a>
 */
public class StatsCommandHandler implements CommandHandler {

  private static final long DAY_IN_MS = 24 * 60 * 60 * 1000L;

  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {
    StringBuilder sb = new StringBuilder();
    
    long now = System.currentTimeMillis();
    long oneDayCutoff = now - DAY_IN_MS;
    long sevenDayCutoff = now - 7 * DAY_IN_MS;
    long thirtyDayCutoff = now - 30 * DAY_IN_MS;
    
    Collection<PartyLine> partyLines = lineManager.getPartyLines();
    
    // Total number of rooms
    sb.append("Party chats:\n");
    
    sb.append("  Total: ")
      .append(partyLines.size())
      .append("\n");
    
    // Subscriber and room stats
    int maxSubscribers = 0;
    double totalSubscribers = 0;
    Set<String> oneDayActiveLines = Sets.newHashSet();
    Set<String> sevenDayActiveLines = Sets.newHashSet();
    Set<String> thirtyDayActiveLines = Sets.newHashSet();
    int oneDayActiveSubscribers = 0;
    int sevenDayActiveSubscribers = 0;
    int thirtyDayActiveSubscribers = 0;
        
    
    for (PartyLine line : partyLines) {
      int subscribers = line.getSubscribers().size();
      
      totalSubscribers += subscribers;
      if (subscribers > maxSubscribers) {
        maxSubscribers = subscribers;
      }
      
      for (Subscriber s : line.getSubscribers()) {
        long lastSeen = s.getLastActivityTime();
        
        if (lastSeen >= oneDayCutoff) {
          oneDayActiveSubscribers++;
          oneDayActiveLines.add(line.getName());
        }
        if (lastSeen >= sevenDayCutoff) {
          sevenDayActiveSubscribers++;
          sevenDayActiveLines.add(line.getName());
        }
        if (lastSeen >= thirtyDayCutoff) {
          thirtyDayActiveSubscribers++;
          thirtyDayActiveLines.add(line.getName());
        }
      }      
    }
    double averageSubscribers = totalSubscribers/partyLines.size();
    sb.append("  Active in the past day: ")
      .append(oneDayActiveLines.size())
      .append("\n");
    sb.append("  Active in the past week: ")
      .append(sevenDayActiveLines.size())
      .append("\n");
    sb.append("  Active in the past 30 days: ")
      .append(thirtyDayActiveLines.size())
      .append("\n");    
    sb.append("  Average subscribers: ")
      .append(String.format("%.3g", averageSubscribers))
      .append("\n");
    sb.append("  Most subscribers: ")
      .append(maxSubscribers)
      .append("\n");
    
    // Activity stats

    sb.append("\nSubscribers:\n");
    sb.append("  Total: ")
      .append((int)totalSubscribers)
      .append("\n");
    sb.append("  Active in the past day: ")
      .append(oneDayActiveSubscribers)
      .append("\n");
    sb.append("  Active in the past week: ")
      .append(sevenDayActiveSubscribers)
      .append("\n");
    sb.append("  Active in the past 30 days: ")
      .append(thirtyDayActiveSubscribers)
      .append("\n");
    
    // Bot stats
    Multiset<String> botCounts = TreeMultiset.create();
    Map<String, Long> botLastActivityTimes = Maps.newHashMap();
    for (PartyLine line : partyLines) {
      for (Subscriber s : line.getSubscribers()) {
        botCounts.add(s.getBotScreenName().toLowerCase());
      }
    }
    
    for (Connection connection :
      ConnectionFactory.getConnectionManager().getConnections()) {
      String connectionUser = connection.getSendingUser();
      botLastActivityTimes.put(
          connectionUser.split("/")[0],
          connection.getLastActivityTime());
    }
    
    sb.append("\nBot stats:\n");
    for (String botScreenName : botCounts.elementSet()) {
      sb.append("  ")
        .append(botScreenName)
        .append(":\n    ")
        .append(botCounts.count(botScreenName))
        .append(" subscribers");
      
      if (botLastActivityTimes.containsKey(botScreenName) &&
          botLastActivityTimes.get(botScreenName) > 0) {
        sb.append("\n    last message received ")
          .append(DateUtil.timeSince(botLastActivityTimes.get(botScreenName)))
          .append(" ago");
      }
      
        sb.append("\n");
    }
    
    return sb.toString();
  }

}
