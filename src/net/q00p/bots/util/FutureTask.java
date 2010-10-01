//Copyright 2005 Google Inc. All Rights Reserved

package net.q00p.bots.util;

import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * A utility class to run shit in the future. Provide a runnable and a time
 * in the future to run it and it does. 
 * 
 * Internally we store a sorted mapping of whens to whats. We then make a real
 * TimerTask to run the next item in the list.
 * 
 * We foolishly pretend that this isn't chock full of potential synchronization
 * issues.
 * 
 * We can't guarantee any execution smaller than RESOLUTION.
 *
 * Created on Feb 25, 2007
 * @author dolapo
 */
public class FutureTask {
  
  final SortedMap<Long, Runnable> tasks = new TreeMap<Long, Runnable>();
  Object taskLock = new Integer(0);
  
  private Timer timer = new Timer(true);

  private final static int RESOLUTION = 30000;
  
  
  public FutureTask() {
    timer.schedule(new MyTask(), RESOLUTION, RESOLUTION);
  }
  
  
  public void addTask(long when, Runnable what) {
    // Is it earlier than our next to run? If so make it next and reset the timer
    // task. Otherwise, just stick it in the map.
    synchronized(taskLock) {
      tasks.put(when, what);
    }
  }
  
  public boolean removeTask(long when) {
    synchronized(taskLock) {
      return tasks.remove(when) != null;
    }
  }
  
  public boolean hasMoreTasks() {
    synchronized(taskLock) {
      return tasks.size() > 0;
    }
  }
  
  public int numTasks() {
    synchronized(taskLock) {
      return tasks.size();
    }
  }
  
  private final class MyTask extends TimerTask {
    @Override
    public void run() {
      
      synchronized(taskLock) {
        
        // Cleanup and process any tasks that were supposed to have occured 
        // before now.
        while (tasks.size() > 0 && System.currentTimeMillis() >= tasks.firstKey()) {
          Runnable what = tasks.remove(tasks.firstKey());
          
          try {
            what.run();
          } catch (RuntimeException e) {
            e.printStackTrace();
          }
        }
      }
    }
    
  }
  
  
  // Some tests.
  public static void main(String[] args) throws Exception {
    FutureTask ft = new FutureTask();
    
    long now = System.currentTimeMillis();
    
    final boolean[] seen = new boolean[100];
    for (int i = 0; i < 100; ++i) {
      final int index = i;
      ft.addTask(now + i*1000, new Runnable() {
        public void run() {
          System.out.println("Running: " + index);
          seen[index] = true;
        }});
    }
    
    // Ugh, we want to spin until all the tasks are done...
    // Ugh!
    while (ft.hasMoreTasks()) {}
    Thread.sleep(2000);
    
    boolean passed = true;
    for (int i = 0; i < 100; ++i) {
      if (!seen[i]) {
        System.out.printf("%d failed.\n", i);
        passed = false;
      }
    }
    
    System.out.println("Passed? " + passed);
  }
}
