package net.q00p.bots;

/**
 * Bot interface.
 * @author ak
 *
 */
public interface Bot {
	
	public static String CLIENT = "BOT";

	/**
	 * Most important method; this is that the method that will be called
	 * every time an account the bot is listening to gets im'd
	 * @param message
	 */
	public void handleMessage(Message message);
	
	/** Name to identify this bot by. client should be {@link #CLIENT} */
	public String botName();
}
