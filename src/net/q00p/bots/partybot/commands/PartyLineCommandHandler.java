package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

/**
 * {@link CommandHandler} abstract class for commands that require the user
 * to be in a room already (i.e. have a party line associated with them).
 * 
 * @author <a href="mailto:mihai.parparita@gmail.com">Mihai Parparita</a>
 */
public abstract class PartyLineCommandHandler implements CommandHandler {

  public String doCommand(PartyBot partyBot, LineManager lineManager,
    Subscriber subscriber, Matcher commandMatcher) {
    PartyLine partyLine = lineManager.getPartyLine(subscriber);
    if (partyLine == null) {
      return LineManager.NOT_IN;
    } else {
      return doCommand(partyBot, partyLine, subscriber, commandMatcher);
    }
  }
  
  protected abstract String doCommand(
      PartyBot partyBot, PartyLine partyLine, Subscriber subscriber,
      Matcher commandMatcher);
}
