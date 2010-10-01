package net.q00p.bots.partybot.commands;

import net.q00p.bots.partybot.MessageResponder;
import net.q00p.bots.partybot.PartyBot;
import net.q00p.bots.partybot.PartyLine;
import net.q00p.bots.partybot.Subscriber;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

/**
 * Commands that extracts information about a URL (currently just the title)
 * and then broadcasts it to the entire party chat.
 *
 * @author mihai.parparita@gmail.com (Mihai Parparita)
 */
public class ShareCommandHandler extends PartyLineCommandHandler {
  private static final String TITLE_START = "<title>";
  private static final String TITLE_END = "</title>";

  private final ExecutorService executor = Executors.newFixedThreadPool(10);
  private final HttpClient httpClient;
  
  public ShareCommandHandler() {
    HttpParams httpParams = new BasicHttpParams();
    httpParams.setParameter(
        CoreProtocolPNames.USER_AGENT, 
        "PartyChat/1.0 (+http://techwalla.googlepages.com/)");
    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(
        new Scheme(
            "http", 
            PlainSocketFactory.getSocketFactory(),
            80));
    ClientConnectionManager connectionManager =
        new ThreadSafeClientConnManager(httpParams, schemeRegistry);
    httpClient = new DefaultHttpClient(connectionManager, httpParams);
  }
  

  private final class UrlInfoRunnable implements Runnable {
    private final URI uri;
    private final String annotation;
    private final PartyLine partyLine;
    private final Subscriber subscriber;
    private final MessageResponder responder;
    
    public UrlInfoRunnable(
        URI uri,
        String annotation,
        PartyLine partyLine, 
        Subscriber subscriber,
        MessageResponder responder) {
      this.uri = uri;
      this.annotation = annotation;
      this.partyLine = partyLine;
      this.subscriber = subscriber;
      this.responder = responder;
    }
  
    public void run() {
      HttpGet infoGet = new HttpGet(uri);
      try {
        HttpResponse infoResponse = httpClient.execute(infoGet);
        int statusCode = infoResponse.getStatusLine().getStatusCode(); 
        if (statusCode >= 400) {
          finishShare(null);
          infoGet.abort();
          return;
        }
        
        HttpEntity infoEntity = infoResponse.getEntity();
        if (infoEntity != null) {
          String infoContent = EntityUtils.toString(infoEntity);
          infoGet.abort();
          String infoContentLower = infoContent.toLowerCase();
          int titleStart = infoContentLower.indexOf(TITLE_START);
          if (titleStart != -1) {
            int titleEnd = infoContentLower.indexOf(TITLE_END, titleStart);
            if (titleEnd != -1) {
              String title = infoContent.substring(
                  titleStart + TITLE_START.length(), titleEnd);
              finishShare(StringEscapeUtils.unescapeHtml(title));
              return;
            }
          }
          finishShare(null);
          return;
        }
      } catch (ClientProtocolException err) {
      
      } catch (IOException err) {
        
      } catch (IllegalStateException err) {
        
      }
    
      infoGet.abort();
      finishShare(null);
    }
    
    private void finishShare(String title) {
      StringBuilder shareAnnounce = new StringBuilder();
      shareAnnounce.append("_")
        .append(subscriber.getDisplayName(true))
        .append(" is sharing ")
        .append(uri);
      
      if (title != null && title.length() > 0) {
        shareAnnounce.append(" (").append(title).append(")");
      }
      
      if (annotation != null && annotation.length() > 0) {
        shareAnnounce.append(": ").append(annotation);
      }
      
      shareAnnounce.append("_");
      responder.announce(partyLine, shareAnnounce.toString());
    }
    
  }

  @Override
  protected String doCommand(
      PartyBot partyBot,
      PartyLine partyLine, 
      Subscriber subscriber,
      Matcher commandMatcher) {
    URI uri = null;
    String urlString = commandMatcher.group(1);
    if (urlString != null && urlString.length() > 0) {
      try {
        uri = new URI(urlString);
       } catch (URISyntaxException err) { 
         // Malformed URLs are reported below
       }
       if (uri == null || !isValidUri(uri)) {
         return urlString + " is not a valid URL.";         
       }
    } else {
      return "Must give a URL to share";
    }
    
    String annotation = commandMatcher.group(3);

    // Running info extraction in another thread since we don't want to block
    // the bot that's handling this request
    executor.execute(new UrlInfoRunnable(
        uri, annotation, partyLine, subscriber, partyBot));
    
    return "Sharing... ";
  }

  private static boolean isValidUri(URI uri) {
    return uri.isAbsolute()
        && "http".equals(uri.getScheme())
        && uri.getAuthority() != null
        && uri.getAuthority().length() > 0;    
  }
}
