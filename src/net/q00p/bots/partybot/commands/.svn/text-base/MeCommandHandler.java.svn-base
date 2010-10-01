package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class MeCommandHandler extends PartyLineCommandHandler {

  @Override
  public String doCommand(PartyBot partyBot, PartyLine partyLine,
      Subscriber subscriber, Matcher commandMatcher) {
    String actionCast = "_" + subscriber.getDisplayName(true) + " "
        + commandMatcher.group(2) + "_";
    partyBot.broadcast(subscriber, partyLine, actionCast, true);
    return actionCast;
  }

}
