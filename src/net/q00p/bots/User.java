package net.q00p.bots;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user (username + client)
 * 
 * TODO: change the cache to use weak references so users get auto-garbage-collected
 * if the underlying bot doesn't hold onto the object.
 * 
 * @author ak
 */
public class User implements Comparable<User>, Serializable {
	private final String name;
	private final String client;
	private final String nameClient;
	
	private static Map<String, User> cache = new HashMap<String, User>();

	/**
	 * Static method for obtaining user instances
	 * 
	 * @param memberClient string of "username/client" form
	 */
	public static User get(String memberClient) {
		User user = cache.get(memberClient);
		if (user != null) 
			return user;
		
		String[] data = memberClient.split("/");
		if (data.length < 2 )
			user = new User(data[0], "");
		else if (data.length == 2)
			user = new User(data[0], data[1]);
		else
			throw new IllegalArgumentException("malformed memberClient string");

		cache.put(user.toString(), user);
		return user;
	}
	
	/**
	 * Static method for obtaining user instances
	 */
	public static User get(String name, String clientInfo) {
		return get(format(name, clientInfo));
	}
	
	private User(String name, String clientInfo) {
		assert (name != null && clientInfo != null): "null input to constructor";
		this.name = name;
		this.client = clientInfo;
		this.nameClient = format(name, clientInfo);
	}
		
	private static String format(String name, String client) {
		return client.length() > 0 ? name + "/" + client: name;
	}

	public String getName() {
		return name;
	}

	public String getClientInfo() {
		return client;
	}
		
	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof User && ((User)obj).name.equals(name));
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return nameClient;
	}
	
	public int compareTo(User user2) {
		return name.compareTo(user2.name);
	}
}
