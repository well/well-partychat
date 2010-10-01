//Copyright 2005 Google Inc. All Rights Reserved

package net.q00p.bots.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * Utilities for date stuff.
 *
 * Created on Feb 24, 2007
 * @author dolapo
 */
public final class DateUtil {
  private DateUtil() {}
  
  enum TimeUnit {
    SECOND("s", 1000),
    MINUTE("m", 60 * SECOND.getMs()),
    HOUR("h", 60 * MINUTE.getMs()),
    DAY("d", 24 * HOUR.getMs());
    
    private final String abbreviation;
    private final long ms;
    TimeUnit(String abbreviation, long ms) {
      this.abbreviation = abbreviation;
      this.ms = ms;
    }
    String getAbbreviation() {
      return abbreviation;
    }
    long getMs() {
      return ms;
    }
  }

  /** The {@link TimeUnit}s, but in reversed order (DAY -> SECOND) */
  final static TimeUnit[] REVERSED_TIME_UNITS = new TimeUnit[TimeUnit.values().length];
  static {
    int i = TimeUnit.values().length;
    for (TimeUnit unit : TimeUnit.values()) {
      REVERSED_TIME_UNITS[--i] = unit;
    }
  }
    
  
  /**
   * Tries to return milliseconds from a user entered string like 10m or 10s or
   * 10h or just 10. A bare number is interpreted as seconds.
   * 
   * @throws ParseException on several conditions - we try not to return a 
   *         number at all if the input is malformed.
   */
  public static long parseTime(String text) throws ParseException {
    NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
    
    ParsePosition position = new ParsePosition(0);
    Number number = nf.parse(text, position);
    
    if (position.getIndex() == 0) {
      // We didn't parse anything, so bye.
      throw new ParseException("Invalid input string", 0);
    }
    
    int unitIndex = position.getIndex();
    String unitString = text.substring(unitIndex);
    TimeUnit timeUnit;
    
    if (unitString == null || unitString.trim().equals("")) {
      // If no unit specified, default to minutes.
      timeUnit = TimeUnit.MINUTE;
    } else {
      // Try to parse the unit.
      try {
        timeUnit = getTimeUnit(unitString);
      } catch (IllegalArgumentException e) {
        throw new ParseException(e.getMessage(), unitIndex);
      }
    }
    
    long result = number.longValue() * timeUnit.getMs();
    if (result < 0) {
      // Oops overflow.
      throw new ParseException("Time period too large", 0); 
    }
    
    return result;
  }
  
  private static TimeUnit getTimeUnit(String abbreviation) {
    for (TimeUnit timeUnit : TimeUnit.values()) {
      if (timeUnit.getAbbreviation().equalsIgnoreCase(abbreviation))
        return timeUnit;
    }
    throw new IllegalArgumentException(
        abbreviation + " is not a valid time unit abbreviation");
  }
  
  // Throws an exception if the parse doesn't match expected.
  // expected = -1 if we expect an exception.
  private static void checkParse(String s, long expected) throws Exception {
    ParseException caughtException = null;
    long value = 0;
    
    try {
      value = parseTime(s);
    } catch (ParseException e) {
      caughtException = e;
    }
    
    if ((expected == -1 && caughtException == null) ||
        (expected != -1 && expected != value)) {
      throw new Exception("Parse broken for " + s + ", expected " + 
                          expected + " but got: " + value, caughtException);
    }
    
  }
  
  private static void checkPretty(long ms, String expected) throws Exception {
    String actual = prettyFormatTime(ms);
    if (!actual.equals(expected)) {
      throw new Exception(String.format(
          "Preffy format broken for " +
          "%d; expected: %s, actual: %s", ms, expected, actual) );
    }
  }
  
  /**
   * Return a string form of a ms.
   * Something like 10m or 10s or 10d with loss of precision.
   */
  public static String prettyFormatTime(long ms) {
    if (ms >= 0 && ms < 1000) {
      return "< 1s";
    }
    StringBuilder sb = new StringBuilder();
    if (ms < 0) {
      sb.append("negative ");
      ms = Math.abs(ms);
    }
    
    // Step through the time units, from largest to smallest.
    for (TimeUnit timeUnit : REVERSED_TIME_UNITS) {
      long numUnits = ms / timeUnit.getMs();
      if (numUnits != 0) {
        if (sb.length() > 0) sb.append(" ");
        sb.append(numUnits + timeUnit.getAbbreviation());
        // Get the remainder milliseconds
        ms %= timeUnit.getMs();
      }
    }
    return sb.toString();
  }
  
  // In lieu of unit tests...
  public static void main(String[] args) throws Exception {
    checkParse("10m", 10*60*1000);
    checkParse("20",  20*60*1000);
    checkParse("30h", 30*60*60*1000);
    checkParse("5s",  5*1000);
    checkParse("1d",  1*24*60*60*1000);
    checkParse("99d", 99L*24*60*60*1000);
    
    checkParse("",        -1);
    checkParse("crap",    -1);
    checkParse("10mmmm",  -1);
    checkParse("10hours", -1);
    
    checkParse("99999999999999999999d", -1);
    
    
    // Test prettyFormatTime
    checkPretty(5*1000, "5s");
    checkPretty(2*24*60*60*1000 + 5*1000, "2d 5s");
    checkPretty(2*24*60*60*1000 + 12*60*60*1000 + 5*1000, "2d 12h 5s");
    
    System.out.println("Tests passed");
  }

  /** Prints how long till the given time */
  public static String timeTill(long futureTimeMs) {
    return prettyFormatTime(futureTimeMs - System.currentTimeMillis());
  }

  public static String timeSince(long pastTimeMs) {
    return prettyFormatTime(System.currentTimeMillis() - pastTimeMs);
  }
}
