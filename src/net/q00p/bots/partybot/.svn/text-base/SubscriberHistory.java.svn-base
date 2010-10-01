package net.q00p.bots.partybot;

import net.q00p.bots.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * History tracking for a subscriber. Note: a Subscriber describes the
 * relationship between a user and a bot - effectively the relationship between
 * a user and a line. A SubscriberHistory instance is the history of that
 * user for that bot.
 * 
 * @author dolapo
 */
public class SubscriberHistory {

  private int maxSize;
  
  // The total number of words uttered by subscriber.
  private int totalWordCount;
  // The total number of messages sent by subscriber.
  private int totalMessageCount;
  
  // We really want a queue that allows us to iterate in reverse order but we
  // can't have that so we just use a queue implementation
  private LinkedList<HistoryItem> history = new LinkedList<HistoryItem>();

  /**
   * Create a new SubscriberHistory specifying the maximum number of interactions
   * to remember.
   * Worst case required memory across all bots/users/lines =
   * [num bots] * [num users] * [num lines] * [maxSize] * sizeof(item)
   * 
   * @param maxSize
   */
  public SubscriberHistory(int maxSize) {
    this(maxSize, 0, 0);
  }
  
  public SubscriberHistory(int maxSize, int previousWordCount, int previousMessageCount) {
    this.maxSize = maxSize;
    this.totalWordCount = previousWordCount;
    this.totalMessageCount = previousMessageCount;
  }
  
  /**
   * Push a message to the user history.
   * 
   * @param message
   * @return The current size of the user history after the push.
   */
  public int addMessage(Message message) {
    if (history.size() >= maxSize) {
      history.remove();
    }
    
    totalWordCount += message.getContent().split(" ").length;
    totalMessageCount++;
    
    history.offer(new HistoryItem(message, System.currentTimeMillis()));
    
    return history.size();
  }
  

  public List<? extends HistoryItem> getItems() {
    return history;
  }
  
  public int getTotalWordCount() {
    return totalWordCount;
  }
  
  public int getTotalMessageCount() {
    return totalMessageCount;
  }
  
  /**
   * Reset everything we know about the user.
   */
  public void reset() {
    history.clear();
    totalWordCount = 0;
    totalMessageCount = 0;
  }
  
  
  /**
   * The item saved in history. Basically the message and when.
   * @author dolapo
   */
  public static final class HistoryItem {
    private Message message;
    private long timestamp;

    public HistoryItem(Message message, long timestamp) {
      this.message = message;
      this.timestamp = timestamp;
    }

    public Message getMessage() {
      return message;
    }

    public long getTimestamp() {
      return timestamp;
    }
  }
}
