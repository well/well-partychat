package net.q00p.bots.partybot;

import net.q00p.bots.Message;

/**
 * Interface used in conjunction with {@link MessageHandler} to allow message
 * handlers to respond to messages.
 * 
 * @author mihai.parparita@gmail.com (Mihai Parparita)
 */
public interface MessageResponder {
  /**
   * Sends a message from the system.
   */
  void announce(PartyLine partyLine, String message);

  /**
   * Broadcast the given message to all partyline subscribers as coming from
   * one subscriber
   */
  void broadcast(Subscriber subscriber, PartyLine partyLine, Message message);
  
  /**
   * Sends a message in reply to another (only to the sender of the original
   * message).
   */
  void reply(Message inReplyTo, String message);
}
