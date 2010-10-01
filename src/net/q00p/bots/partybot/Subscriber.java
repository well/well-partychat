package net.q00p.bots.partybot;

import com.google.common.collect.Maps;

import net.q00p.bots.Message;
import net.q00p.bots.User;
import net.q00p.bots.util.Tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Captures a relationship between a user and a bot screen name.
 * 
 * @author ak
 */
public class Subscriber implements Serializable {
  private User user;
  private String botScreenName;
  private String alias = null;
  private long lastActivityTime;
  private long lineJoinTime;
  private long snoozeUntil;
  
  private static final int MAX_HISTORY = 10;
  private SubscriberHistory history = new SubscriberHistory(MAX_HISTORY);
  
  private static class UserBotKey extends Tuple<User, String> {
    public UserBotKey(User user, String botScreenName) {
      super(user, botScreenName);
    }
  }
  
  private static Map<UserBotKey, Subscriber> cache = Maps.newHashMap(); 
  private static final String ALIAS_TEMPLATE = "[%s]";

  public static Subscriber get(User user, String botScreenName) {
    UserBotKey key = new UserBotKey(user, botScreenName);
    Subscriber sub = cache.get(key);
    if (sub != null) return sub;
    sub = new Subscriber(user, botScreenName);
    cache.put(key, sub);
    return sub;
  }
  
  public static void forget(Subscriber sub) {
    cache.remove(new UserBotKey(sub.user, sub.botScreenName));
  }
  
  private Subscriber(User user, String sn) {
    this.user = user;
    this.botScreenName = sn;
    this.lastActivityTime = 0;
  }
  
  public User getUser() {
    return user;
  }
  
  public String getBotScreenName() {
    return botScreenName;
  }
  
  public String getAlias() {
    return alias;
  }
  
  public void setAlias(String a) {
    alias = a;
  }
  
  public void setLastActivityTime(long ms) {
    lastActivityTime = ms;
  }
  
  public long getLastActivityTime() {
    return lastActivityTime;
  }
  
  public void setLineJoinTime(long ms) {
    lineJoinTime = ms;
  }
  
  public long getLineJoinTime() {
    return lineJoinTime;
  }
  
  public void setSnoozeUntil(long ms) {
    snoozeUntil = ms;
  }
  
  public long getSnoozeUntil() {
    return snoozeUntil;
  }
  
  public boolean isSnoozing() {
    return System.currentTimeMillis() < snoozeUntil;
  }
  
  public String getDisplayName() {
    return getDisplayName(false);
  }
  
  public String getDisplayName(boolean noFormatting) {
    if (alias != null) {
      return noFormatting ? alias : String.format(ALIAS_TEMPLATE, alias);
    }
    else
    {
    	String n = user.getName();
    	n = (n.split("@"))[0];
    	return n;
    }
  }

  @Override
  public String toString() {
    return user + "|" + botScreenName + " \"" + alias + "\"";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof Subscriber) {
      Subscriber sub = (Subscriber)obj;
      return sub.getUser().equals(user) 
          && sub.getBotScreenName().equals(botScreenName);
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return user.hashCode() ^ botScreenName.hashCode();
  }

  /**
   * Pushes the message to the subscriber history.
   */
  public synchronized void addMessageToHistory(Message message) {
    history.addMessage(message);
  }
  
  /**
   * Returns the history of the user. 
   * Note this returns a copy of the history to dodge any synch issues.
   */
  public List<SubscriberHistory.HistoryItem> getHistoryItems() {
    return new ArrayList<SubscriberHistory.HistoryItem>(history.getItems());
  }
  
  public SubscriberHistory getHistory() {
    return history;
  }
  
  public void resetHistory(int totalWordCount, int totalMessageCount) {
    history = new SubscriberHistory(MAX_HISTORY, totalWordCount, totalMessageCount);
  }

}
