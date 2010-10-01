package net.q00p.bots.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * needs to be redone.
 * @author ak
 */
public class Logger {

	private static final BufferedWriter WRITER;

	private static final Timer timer;

	static {
		BufferedWriter writer = null;
		String logname = "botlog.txt";
		try {
		    logname = "botlog_"+ Long.toString(System.currentTimeMillis(), 36) 
		      + ".txt";
		    writer = new BufferedWriter(new FileWriter(new File(logname)));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		WRITER = writer;
		timer = new Timer();
		timer.schedule(new LogFlushingTask(), new Date(), 100);
		log("log file: " + logname);
	}

	public static void log(String message) {
		log(message, false);
	}

	public static void log(String message, boolean systemPrint) {
		if (systemPrint)
			System.out.println(message);
		try {
			synchronized (WRITER) {
				WRITER.write("(" + (new Date()) + "): " + message + "\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void log(Throwable t, boolean systemPrint) {
	  log(t.toString(), systemPrint);
	  for (StackTraceElement s : t.getStackTrace()) {
	    log("    " + s.toString(), systemPrint);
	  }
	  
	  if (t.getCause() != null) {
	    log("Caused by: ", systemPrint);
	    log(t.getCause(), systemPrint);
	  }
	}

	private static class LogFlushingTask extends TimerTask {
		@Override
		public void run() {
			try {
				synchronized (WRITER) {
					WRITER.flush();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
