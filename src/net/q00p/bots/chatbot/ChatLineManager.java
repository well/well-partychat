package net.q00p.bots.chatbot;

import java.util.Set;

/**
 * We really need a database backing. short of that, this shoule be moved to 
 * an instance sitting in a service registrar.
 * 
 * @author ak
 */
public class ChatLineManager {
	Set<ChatLine> chatLines;
	
	/**
	 * Convenience method that retrieves a chat line with the specified name.
	 */
	public ChatLine getChatLine(String name) {
		for(ChatLine line : chatLines) {
			if (line.getName().equals(name)) return line;
		}
		return null;
	}
	
	/**
	 * Convenience method that retrieves a chat line with the specified subscriber
	 * in it. If multiple chat lines have the same subscriber 
	 * (which should not happen), the line returned will be arbitrary.
	 */
	public ChatLine getChatLine(Subscriber subscriber) {
		if (subscriber == null) return null;
		for (ChatLine line : chatLines) {
			if (line.getSubscribers().contains(subscriber)) return line;
		}
		return null;
	}

	/**
	 * @return Returns the lines.
	 */
	public Set<ChatLine> getChatLines() {
		return chatLines;
	}

	/**
	 * @param lines The lines to set.
	 */
	public void setChatLines(Set<ChatLine> lines) {
		chatLines = lines;
	}
	
	
}
