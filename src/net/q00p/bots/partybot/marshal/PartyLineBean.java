package net.q00p.bots.partybot.marshal;

import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PartyLineBean implements Serializable {
	private String password = null;
	private String name;
	private Set<SubscriberBean> subscribers;
	
	public String getPassword() { return password; }
	public String getName() { return name; }
	public Set<SubscriberBean> getSubscribers() { return subscribers; }
	public void setPassword(String in) { password = in; }
	public void setName(String in) { name = in; }
	public void setSubscribers(Set<SubscriberBean> in) { subscribers = in; }
	
	public PartyLine loadPartyLine() {
		PartyLine pl = new PartyLine(name, password);
		for (SubscriberBean sb : subscribers) {
			pl.addSubscriber(sb.loadSubscriber());
		}
		return pl;
	}
	
	public PartyLineBean() {}
	
	public PartyLineBean(PartyLine pl) {
		name = pl.getName();
		password = pl.getPassword();
		subscribers = new HashSet<SubscriberBean>();
		for(Subscriber sub : pl.getSubscribers()) {
			subscribers.add(new SubscriberBean(sub));
		}
	}
}
