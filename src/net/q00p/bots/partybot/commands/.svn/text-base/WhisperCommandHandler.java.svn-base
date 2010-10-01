package net.q00p.bots.partybot.commands;

import net.q00p.bots.Message;
import net.q00p.bots.User;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class WhisperCommandHandler extends PartyLineCommandHandler {

  private static final String PRIVATE_MESSAGE_FORMAT = "[%s to just you] %s";
  
  @Override
  public String doCommand(PartyBot partyBot, PartyLine partyLine,
      Subscriber subscriber, Matcher commandMatcher) {
    String alias = commandMatcher.group(2);
    String msg = commandMatcher.group(3);
    String formattedMsg = String.format(PRIVATE_MESSAGE_FORMAT, subscriber
        .getDisplayName(), msg);

    // It makes it easier for testing to not check if they're whispering to
    // themselves.
    Subscriber to = partyBot.findSubscriber(partyLine, alias);
    if (to == null) {
      return String.format(NO_SUBSCRIBER, alias);
    }

    Message whispermsg = new Message(User.get(to.getBotScreenName(),
        partyBot.botName()), to.getUser(), formattedMsg);
    PartyBot.getMessageSender().sendMessage(whispermsg);

    return null;
  }

}
