package net.q00p.bots.chatbot;

public class ChatContext {
	/** May be null if sending user is not in a {@link ChatLine} */
	public ChatLine chatLine;
	public Subscriber sender;
	
	public ChatContext(ChatLine chatLine, Subscriber sender) {
		this.chatLine = chatLine;
		this.sender = sender;
	}
}
