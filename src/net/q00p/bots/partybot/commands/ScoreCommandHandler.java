package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class ScoreCommandHandler extends PartyLineCommandHandler {

  @Override
  public String doCommand(PartyBot partyBot, PartyLine partyLine,
      Subscriber subscriber, Matcher commandMatcher) {
    String regex = commandMatcher.group(2);
    return partyBot.printScore(partyLine.getName(), regex, false);
  }

}
