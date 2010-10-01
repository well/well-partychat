package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.util.regex.Matcher;

public class AliasCommandHandler extends PartyLineCommandHandler {
  private static final String ALIAS_SET = "alias set to %s";
  private static final String ALIAS_REMOVED = "alias removed";
  private static final String ALIAS_TAKEN = "alias %s is already taken, "
      + "try a different alias";
  private static final String SUB_ALIAS_CHANGE = "%s is now known as %s";
  private static final String SUB_ALIAS_CHANGE_HAD_PREVIOUS = "%s (%s) is now known as %s";

  @Override
  public String doCommand(
      PartyBot partyBot,
      PartyLine partyLine,
      Subscriber subscriber,
      Matcher commandMatcher) {
    // make sure they're in a party chat before aliasing them.
    String alias = commandMatcher.group(2);
    for (Subscriber sub : partyLine.getSubscribers()) {
      if (alias.equals(sub.getAlias()))
        return String.format(ALIAS_TAKEN, alias);
    }

    String aliasResponse;
    String oldAlias = subscriber.getAlias();
    if (alias.equals("")) {
      subscriber.setAlias(null);
      aliasResponse = ALIAS_REMOVED;
    } else {
      subscriber.setAlias(alias);
      aliasResponse = String.format(ALIAS_SET, alias);
    }

    String announcement;
    if (oldAlias == null) {
      announcement = String.format(SUB_ALIAS_CHANGE, subscriber.getUser()
          .getName(), alias);
    } else {
      announcement = String.format(SUB_ALIAS_CHANGE_HAD_PREVIOUS, subscriber
          .getUser().getName(), oldAlias, alias);
    }

    partyBot.announce(partyLine, announcement);

    return aliasResponse;
  }

}
