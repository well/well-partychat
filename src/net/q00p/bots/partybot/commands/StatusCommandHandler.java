package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class StatusCommandHandler implements CommandHandler {

  private static final String SUB_STATUS_ONLINE = 
      "you are currently in party chat #%s as %s";
  private static final String SUB_STATUS_OFFLINE = 
      "you are not in a party chat";

  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {
    PartyLine partyLine = lineManager.getPartyLine(subscriber);
    if (partyLine == null) {
      return SUB_STATUS_OFFLINE;
    } else {
      return String.format(
          SUB_STATUS_ONLINE, partyLine.getName(), subscriber.getDisplayName());
    }
  }

}
