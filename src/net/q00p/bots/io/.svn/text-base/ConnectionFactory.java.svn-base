package net.q00p.bots.io;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;

public class ConnectionFactory {
	private static ConnectionManager cm = new ConnectionManager();
	
	private static final String DEFAULT_SERVER = "talk.google.com";
	private static final String DEFAULT_DOMAIN = "gmail.com";
	
	public static void init() {
	    // Don't want offline messages
	    ProviderManager.getInstance().
		removeExtensionProvider("x", "jabber:x:delay");	
	}
	
	public static Connection getConnection(String username, String password) {
	  String server = DEFAULT_SERVER;
	  String domain = DEFAULT_DOMAIN;
	  
	  int atIndex = username.indexOf("@");
	  if (atIndex != -1) {
	    domain = username.substring(atIndex + 1);
	    // google hosted domains talk to talk.google.com servers. most other domains talk to their
	    // own server.
	    if (!domain.equals("gmail.com")) {
		server = domain;
	    }
	    username = username.substring(0, atIndex);
	  }
	  // Create the configuration for the connection
	  ConnectionConfiguration config = new ConnectionConfiguration(server, 5222, domain);
	  //	  config.setCompressionEnabled(true);
	  config.setSASLAuthenticationEnabled(false);
	  
	  XMPPConnection connection;
	  try {
	      connection = new XMPPConnection(config);
	      //	    new ConnectionConfiguration(server, 5222, domain));
	      connection.connect();
	      connection.login(username, password);
	  } catch (XMPPException e) {
	      throw new GoogleTalkException(e);
	  }
	  Roster roster = connection.getRoster();
	  roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
	  Connection conn = new Connection(connection);
	  cm.addConnection(username + "@" + domain, conn);
	  
	  return conn;
	}
	
	public static ConnectionManager getConnectionManager() {
		return cm;
	}
	
	@SuppressWarnings("serial")
	public static class GoogleTalkException extends RuntimeException {
		public GoogleTalkException() { super(); }
		public GoogleTalkException(Exception e) { super(e); }
	}
}
