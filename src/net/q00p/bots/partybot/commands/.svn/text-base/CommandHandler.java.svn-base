package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

interface CommandHandler {

  static final String NO_SUBSCRIBER = "No such alias or name: %s";

  String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher);

}
