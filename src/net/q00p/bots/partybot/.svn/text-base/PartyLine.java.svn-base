package net.q00p.bots.partybot;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PartyLine implements Serializable {
	public static int MAX_USERS = 50;
	private String password = null;
	private String name;
	private Set<Subscriber> subscribers;
	
	public PartyLine(String name) {
		this.name = name;
		subscribers = new HashSet<Subscriber>();
	}
	
	public PartyLine(String name, String password) {
		this(name);
		assert (! "".equals(password)) : "Blank password not allowed";
		this.password = password;
	}
	
	public String getName() { return name; }
	public String getPassword() { return password; }
	public Set<Subscriber> getSubscribers() { 
		return Collections.unmodifiableSet(subscribers); 
	}
	
	public boolean addSubscriber(Subscriber subscriber) {
		synchronized(subscribers) {
			if (subscribers.size() > MAX_USERS) return false;
			subscribers.add(subscriber);
		}
		return true;
	}
	
	public void removeSubscriber(Subscriber subscriber) {
		synchronized(subscribers) {
			subscribers.remove(subscriber);
		}
	}
	
	public boolean isEmpty() {
		return subscribers.size() == 0;
	}

}
