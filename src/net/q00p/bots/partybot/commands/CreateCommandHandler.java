package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class CreateCommandHandler implements CommandHandler {

  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {
    String lineName = commandMatcher.group(2);
    String pwd = commandMatcher.group(3);
    String output = lineManager.startLine(lineName, pwd);
    return output + (lineManager.subscribe(subscriber, lineName, pwd));
  }

}
