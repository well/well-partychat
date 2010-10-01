// Copyright 2009 Google Inc. All Rights Reserved.

package net.q00p.bots.partybot;

import net.q00p.bots.Message;

/**
 * Interface used to allow pluggable message handling in {@link PartyBot}.
 * Instances must be registered in the {@link PartyBot} constructor.
 * 
 * @author mihai.parparita@gmail.com (Mihai Parparita)
 */
public interface MessageHandler {
  /**
   * Checks if this message can be handled by this handler.
   * 
   * @param message current message
   * @return true if this message should be handled (and other handlers not
   * be invoked), false otherwise
   */
  boolean canHandle(Message message);
  
  /**
   * @return true if the original message should be broadcast to the party line,
   * in addition to whatever the {@link #handle} command may output. 
   */
  boolean shouldBroadcastOriginalMessage();
  
  /**
   * Makes this handler handle this message 
   * 
   * @param message current message
   * @param subscriber subscriber that sent this message
   * @param partyLine party line that the subscriber is in (may be null)
   */
  void handle(
      Message message,
      Subscriber subscriber,
      PartyLine partyLine,
      MessageResponder responder);
}
