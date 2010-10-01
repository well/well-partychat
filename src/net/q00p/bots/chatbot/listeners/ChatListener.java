package net.q00p.bots.chatbot.listeners;

import net.q00p.bots.Message;
import net.q00p.bots.chatbot.ChatContext;

public interface ChatListener {

	public void handleMessage(Message message, ChatContext chatContext);
	
	// private filtering stuff.
}
