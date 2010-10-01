package net.q00p.bots.partybot;

import net.q00p.bots.Message;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * {@link MessageHandler} implementation that handles special s/<regexp>/replace
 * commands to allow rephrasing of of recent messages (relies on {@link
 * Subscriber} to keep track of recently said messages).
 * 
 * @author mihai.parparita@gmail.com (Mihai Parparita)
 */
public class SearchReplaceMessageHandler implements MessageHandler {
  private static final Pattern SR_RX =
      Pattern.compile("^s/([^/]+)/([^/]*)/(g?)$");

  private static final String SR_OUTPUT = "%s meant _%s_";

  private static final String MALFORMED_PATTERN_REPLY = 
      "Malformed search/replace pattern, try s/old/new/";
  
  private static final String NO_MESSAGES_MATCH_REPLY = 
      "None of your recent messages match this search/replace.";
  
  public boolean canHandle(Message message) {
    return SR_RX.matcher(message.getPlainContent()).find();
  }

  public void handle(
      Message message,
      Subscriber subscriber,
      PartyLine partyLine,
      MessageResponder responder) {
    if (partyLine == null || subscriber == null) {
      responder.reply(message, LineManager.NOT_IN);
      return;
    }
    
    String content = message.getPlainContent();
    Matcher replaceMatcher = SR_RX.matcher(content);
    if (!replaceMatcher.matches()) {
      throw new IllegalStateException();
    }
    String search = replaceMatcher.group(1);
    String replace = replaceMatcher.group(2);
    boolean isGlobal = content.endsWith("g");
    
    Pattern searchPattern;

    try {
      searchPattern = Pattern.compile(search);
    } catch (PatternSyntaxException e) {
      responder.reply(message, MALFORMED_PATTERN_REPLY);
      return;
    }
    
    
    String replAnnouncement = attemptSearchReplace(
        subscriber, searchPattern, replace, isGlobal);
    if (replAnnouncement != null) {
      responder.announce(partyLine, replAnnouncement);
    } else {
      responder.reply(message, NO_MESSAGES_MATCH_REPLY);
    }
  }

  /**
   * Looks back 2 items in the user's history for any message containing
   * 'search'. If found replaces it with 'replace' and returns the new message.
   */
  private String attemptSearchReplace(
      Subscriber subscriber,
      Pattern searchPattern,
      String replace,
      boolean global) {

    List<SubscriberHistory.HistoryItem> history = subscriber.getHistoryItems();
    int historySize = history.size();

    String intent = null;
    // Loop through the history backwards, since presumably the user wants to
    // correct a more recent message
    for (int i = historySize - 1; i >= 0 && i > historySize - 3; --i) {
      String original = history.get(i).getMessage().getContent();
      Matcher searchMatcher = searchPattern.matcher(original);
      if (searchMatcher.find()) {
        // Handle /g correctly.
        if (global) {
          intent = searchMatcher.replaceAll(replace);
        } else {
          intent = searchMatcher.replaceFirst(replace);
        }
        
        // Stop looking for a match now that we've found one
        break;
      }
    }
    
    if (intent == null) {
      return null;
    }

    return String.format(SR_OUTPUT, subscriber.getDisplayName(), intent);
  }

  public boolean shouldBroadcastOriginalMessage() {
    return true;
  }

}
