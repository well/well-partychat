package net.q00p.bots.partybot;

import net.q00p.bots.Message;
import net.q00p.bots.partybot.commands.Command;

/**
 * Baseline {@link MessageHandler} which broadcasts all messages to the user's
 * current party line (if they are in one). 
 *
 * @author mihai.parparita@gmail.com (Mihai Parparita)
 */
public class BroadcastMessageHandler implements MessageHandler {
  private static final String NEED_HELP = "You are not in a party chat, for " +
      "help using PartyChat, type '" + Command.HELP.getShortName() + "' " +
      "for a list of parties, type '" + Command.PARTIES.getShortName() + "'";

  private static final String USER_NO_LONGER_SNOOZING =
      "%s is no longer snoozing";
  
  /**
   * We report that we can handle all messages, and rely on this being the last
   * {@link MessageHandler} instance to get triggered.
   */
  public boolean canHandle(Message message) {
    return true;
  }

  public void handle(Message message, Subscriber subscriber, PartyLine partyLine,
      MessageResponder responder) {
    if (partyLine == null) {
      responder.reply(message, NEED_HELP);
      return;
    }
    
    responder.broadcast(subscriber, partyLine, message);

    subscriber.setLastActivityTime(System.currentTimeMillis());
    subscriber.addMessageToHistory(message);

    if (subscriber.isSnoozing()) {
      subscriber.setSnoozeUntil(0);
      responder.announce(
          partyLine, 
          String.format(
              USER_NO_LONGER_SNOOZING, subscriber.getDisplayName()));
    }
  }

  /**
   * We handle broadcasting in {@link #handle} when the user is in 
   */
  public boolean shouldBroadcastOriginalMessage() {
    return false;
  }

}
