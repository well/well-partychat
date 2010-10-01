package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.LineManager;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;
import java.util.Collection;

import java.util.regex.Matcher;

public class PartiesCommandHandler implements CommandHandler {


  public String doCommand(PartyBot partyBot, LineManager lineManager,
      Subscriber subscriber, Matcher commandMatcher) {

	  String s = new String();

	  Collection<PartyLine> partyLines = lineManager.getPartyLines();
	    for (PartyLine p : partyLines) {
	    	s += p.getName() + "\n";
	    }
	    
	    return "The current parties are: \n " + s;
	  
  }

}
