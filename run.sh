#!/bin/bash
echo starting a party chat with the following settings
echo name of party chat: $1
echo jabber id: $2
echo password: $3
java -classpath bin/:lib/smack.jar:lib/smackx-debug.jar:lib/smackx-jingle.jar:lib/smackx.jar:lib/google-collect-1.0-rc2.jar:lib/httpclient-4.0.jar:lib/httpcore-4.0.1.jar:lib/commons-logging-1.1.1.jar:lib/commons-lang-2.4.jar net.q00p.bots.partybot.PartyBot $1 $2 $3