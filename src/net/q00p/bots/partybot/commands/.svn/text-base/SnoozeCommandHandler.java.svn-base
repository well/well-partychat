package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;
import net.q00p.bots.util.DateUtil;

import java.text.ParseException;
import java.util.regex.Matcher;

public class SnoozeCommandHandler extends PartyLineCommandHandler {

  private static final String NOT_CURRENTLY_SNOOZING = "You are not currently snoozing. To start snoozing, try /snooze 10m";
  private static final String SNOOZE_INCORRECT_TIME = "The snooze time you specified could not be parsed.";
  private static final String SNOOZE_SEE_YOU_IN = "ok, see you in %s";

  @Override
  public String doCommand(PartyBot partyBot, PartyLine partyLine,
      final Subscriber subscriber, Matcher commandMatcher) {
    String time = commandMatcher.group(2);

    if (time.length() == 0 && subscriber.isSnoozing()) {
      // They're no longer snoozing.
      subscriber.setSnoozeUntil(0);
      return null;
    } else if (time.length() == 0 && !subscriber.isSnoozing()) {
      return NOT_CURRENTLY_SNOOZING;
    }

    // They're not snoozing and they want to be.
    long snoozeTime;
    try {
      snoozeTime = DateUtil.parseTime(time);
    } catch (ParseException e) {
      return SNOOZE_INCORRECT_TIME;
    }

    long snoozeUntil = System.currentTimeMillis() + snoozeTime;
    long oldSnoozeUntil = subscriber.isSnoozing() ? subscriber.getSnoozeUntil()
        : 0;
    subscriber.setSnoozeUntil(snoozeUntil);

    // Cancel any existing snooze settings if they exist.
    if (oldSnoozeUntil > 0) {
      partyBot.getFutureTask().removeTask(oldSnoozeUntil);
    }

    // Set up the new snooze.
    partyBot.getFutureTask().addTask(snoozeUntil, new Runnable() {
      public void run() {
        subscriber.setSnoozeUntil(0);
      }
    });

    return String.format(SNOOZE_SEE_YOU_IN, time);
  }

}
