package net.q00p.bots.partybot;

import com.google.common.collect.Maps;

import net.q00p.bots.partybot.commands.Command;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Creates and maintains PartyLines. Returns Strings that indicate the action
 * taken.
 * 
 * @author ak
 */
public class LineManager implements Serializable {
  private final Map<String, PartyLine> linesByName = Maps.newHashMap();
  private final Map<Subscriber, PartyLine> linesBySubscriber = Maps.newHashMap();

  public static final String ALREADY_IN = "you are already in #%s";
  public static final String DOESNT_EXIST =
      "party chat #%s doesn't already " + "exist, to create it type " + "'"
          + Command.CREATE.getShortName() + " %s [optional_password]'";
  public static final String NOW_IN = "you are now in #%s";
  public static final String NOT_IN = "you are currently not in a party chat";
  public static final String WRONG_CHAT =
      "you are not in #%s, but you" + "are in party chat #%s.\nto leave, type: '"
          + Command.EXIT.getShortName() + "'";
  public static final String NOW_LEFT = "you have left #%s.";
  public static final String CHAT_CREATED = "you have created party chat #%s";
  public static final String CHAT_ALREADY_EXISTS = "someone else has already created party chat #%s";
  public static final String BAD_PASSWORD =
      "sorry, you must give the correct password to enter this party chat";


  LineManager() {
    
  }

  /**
   * Begins a new party line.
   * 
   * @param pwd optional parameter -- when specified, the party line is password
   *        protected.
   */
  public String startLine(String lineName, String pwd) {
    StringBuffer output = new StringBuffer();
    synchronized (linesByName) {
      PartyLine line = linesByName.get(lineName);
      if (line == null) {
        if (pwd == null || pwd.length() == 0)
          linesByName.put(lineName, new PartyLine(lineName));
        else
          linesByName.put(lineName, new PartyLine(lineName, pwd));

        output.append(String.format(CHAT_CREATED, lineName));
      } else {
        output.append(String.format(CHAT_ALREADY_EXISTS, lineName));
      }
      output.append("\n");
    }
    return output.toString();
  }

  /**
   * Adds the subscriber to the partyline. If the line has a password, the
   * password specified is checked against the line's password and the user is
   * only subscribed if it matches.
   */
  public String subscribe(Subscriber subscriber, String lineName, String pwd) {
    synchronized (linesBySubscriber) {
      PartyLine line = linesBySubscriber.get(subscriber);
      if (line != null) {
        return String.format(ALREADY_IN, line.getName());
      }
    }

    PartyLine line;
    synchronized (linesByName) {
      line = linesByName.get(lineName);
      if (line == null) {
        return String.format(DOESNT_EXIST, lineName, lineName);
      }
    }

    String linePwd = line.getPassword();
    if (!(linePwd == null || linePwd.equalsIgnoreCase(pwd))) return BAD_PASSWORD;
    line.addSubscriber(subscriber);
    synchronized (linesBySubscriber) {
      linesBySubscriber.put(subscriber, line);
    }

    // Note, subscribe can be called on a state reset so we only set line join
    // time if the current time is not 0.
    if (subscriber.getLineJoinTime() == 0) {
      subscriber.setLineJoinTime(System.currentTimeMillis());
    }
    return String.format(NOW_IN, lineName);
  }

  /** Unsubscribes */
  public String unsubscribe(Subscriber subscriber, String lineName) {
    synchronized (linesBySubscriber) {
      synchronized (linesByName) {
        PartyLine line = linesBySubscriber.get(subscriber);
        if (line == null) {
          return NOT_IN;
        } else if (lineName.length() != 0 && !line.getName().equals(lineName)) {
          return String.format(WRONG_CHAT, lineName, line.getName());
        } else {
          linesBySubscriber.remove(subscriber);
          line.removeSubscriber(subscriber);
          if (line.isEmpty()) linesByName.remove(line.getName());
          subscriber.setLineJoinTime(0);
          return String.format(NOW_LEFT, line.getName());
        }
      }
    }
  }
  
  public Collection<PartyLine> getPartyLines() {
    return linesByName.values();
  }

  /**
   * Returns the PartyLine object this subscriber is subscribed to.
   */
  public PartyLine getPartyLine(Subscriber subscriber) {
    return linesBySubscriber.get(subscriber);
  }
}
