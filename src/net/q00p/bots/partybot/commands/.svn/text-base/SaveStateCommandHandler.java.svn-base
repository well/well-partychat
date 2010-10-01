package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class SaveStateCommandHandler implements CommandHandler {

  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {
    return partyBot.saveState(subscriber);
  }

}
