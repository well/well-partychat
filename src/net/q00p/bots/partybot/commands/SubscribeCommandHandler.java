package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class SubscribeCommandHandler implements CommandHandler {

  private static final String SUB_JOINED = "%s joined the chat";

  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {
    String announcement = String.format(SUB_JOINED, subscriber.getUser()
        .getName());
    String userResponse = lineManager.subscribe(subscriber, commandMatcher
        .group(2), commandMatcher.group(3));
    PartyLine partyLine = lineManager.getPartyLine(subscriber);
    partyBot.announce(partyLine, announcement);
    return userResponse;
  }

}
