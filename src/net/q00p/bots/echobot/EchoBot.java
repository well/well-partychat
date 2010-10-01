package net.q00p.bots.echobot;

import net.q00p.bots.Message;
import net.q00p.bots.io.Connection;
import net.q00p.bots.io.ConnectionFactory;
import net.q00p.bots.io.Logger;
import net.q00p.bots.util.AbstractBot;


public class EchoBot extends AbstractBot {
	
	EchoBot(String name) {super(name);}
	
	@Override
	public void handleMessage(Message message) {
		getMessageSender().sendMessage(
				message.reply("[" + message.getFrom().getName() + "] " 
						+ message.getContent()));
	}
	
	public static void main(String[] args) {
		assert args.length == 3 : "usage: java EchoBot botName usn pwd";
		Logger.log("Starting with parameters:" + args[0] + ", " + args[1] + ", " 
					+ args[2], true);
		Connection conn = ConnectionFactory.getConnection(args[1], args[2]);
		EchoBot bot = new EchoBot(args[0]);
		conn.addBot(bot);

		while (true) {
	        try {
	          Thread.sleep(10000);
	        } catch (InterruptedException ex) {
	          // do nothing, just waiting for shutdown
	        }
	      }
	}
}
