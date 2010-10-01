package net.q00p.bots;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Effectively a message bean, with a few useful methods.
 * 
 * @author ak
 */
public class Message {
	private final User from;
	private final User to;
	private final String content;
	private final String plainContent;
	
	public Message(User from, User to, String content) {
		this.from = from;
		this.to = to;
		this.content = content.trim();
		this.plainContent = stripTags(content).trim();
	}
	
	/**
	 * Constructs a new Message for replying to an incoming message
	 * 
	 * @param origMessage message being replied to
	 * @param content content of the reply message
	 */
	public static Message reply (Message origMessage, String content) {
		return new Message(origMessage.to, origMessage.from, 
						  content);
	}
	
	/**
	 * Constructs a new Message in reply to this message
	 * 
	 */
	public Message reply(String content) {
		return new Message(to, from, content);
	}
	
	public String getContent() { return content; }
	public String getPlainContent() { return plainContent; }
	public User getFrom() { return from; }
	public User getTo() { return to; }
	
	private static final Pattern HTML_PATTERN = Pattern.compile("<[^>]*>");
	
	/** strips html tags out of messages; makes command parsing more resilient */
	private static String stripTags(String s) {
		Matcher matcher = HTML_PATTERN.matcher(s);
	    	return matcher.replaceAll("");
	}	
	
	@Override
    public String toString() {
		return from + " --> " + to + " : " + content;
	}
}
