package net.q00p.bots.util;

import net.q00p.bots.Bot;
import net.q00p.bots.Message;
import net.q00p.bots.MessageSender;
import net.q00p.bots.io.Connection;
import net.q00p.bots.io.ConnectionFactory;
import net.q00p.bots.io.Logger;
import net.q00p.bots.io.ConnectionFactory.GoogleTalkException;

import java.util.Iterator;
import java.util.List;

/**
 * Abstract implementation of the Bot interface.
 * 
 * TODO(ak): standardize a config file format and read that in as part of the
 * abstract bot.
 * 
 * @author ak
 *
 */
public abstract class AbstractBot implements Bot {

  private String botName;
  public String botName() {
    return botName;
  }
  
  public static MessageSender getMessageSender() {
    return ConnectionFactory.getConnectionManager();
  }
  
  /**
   * 
   * @param bot the bot 
   * @param usnPwds an even number of strings, representing username/password
   * pairs. the method logs in the as those users ans makes the bot a listener to
   * them
   */
  public static void run(Bot bot, List<String> usnPwds) {
    ConnectionFactory.init();

    assert usnPwds.size() % 2 == 0;   
    for (Iterator<String> it = usnPwds.iterator(); it.hasNext(); ) {
      try {
        Connection conn = ConnectionFactory.getConnection(it.next(), it.next());
        conn.addBot(bot);
      } catch (GoogleTalkException e) {
        Logger.log("Trouble connecting to Google Talk", true);
        Logger.log(e, true);
        System.exit(1);
      }
    }
    
    while (true) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException ex) {
        System.out.println("caught an exception");
      }
    }   
  }
  
  public AbstractBot() {}
  public AbstractBot(String string) {
    botName = string;
  }
  
  public abstract void handleMessage(Message message);
}
