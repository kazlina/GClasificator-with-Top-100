package models;

import java.util.*;
import javax.persistence.*;
import java.io.*;

import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusRequest;
import com.google.api.services.plus.model.*;

import java.io.IOException;
//import java.util.logging.Logger;


public class GAPI {
  //private static final Logger log = Logger.getLogger(Sample.class.getName());

  private static Plus plus;

  public static void main(String[] args) throws IOException {

    try {
      setupTransport();

      getProfile("110056094106588217220");
      //listActivities("107332580040286178426");
    } catch (HttpResponseException e) {
     // log.severe(e.getResponse().parseAsString());
      throw e;
    }
  }
  
  private static String stripTags(String xmlStr){
	    xmlStr = xmlStr.replaceAll("<(.)+?>", ""); 

	    xmlStr = xmlStr.replaceAll("<(\n)+?>", "");
	        
	    return xmlStr;
	  }
  
  private static void setupTransport() throws IOException {
	  plus = Plus.builder(Util.TRANSPORT, Util.JSON_FACTORY)
         .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {
          @Override
          public void initialize(JsonHttpRequest jsonHttpRequest) throws IOException {
            PlusRequest plusRequest = (PlusRequest) jsonHttpRequest;
            plusRequest.setKey(Auth.GOOGLE_API_KEY);            
          }
        }).build();      
    }

  private static void listActivities(String id) throws IOException {
    Plus.Activities.List listActivities = plus.activities().list(id,"public");
    listActivities.setMaxResults(100L);

    ActivityFeed feed;
    try {
      feed = listActivities.execute();
    } catch (HttpResponseException e) {
      throw e;
    }
      for (Activity activity : feed.getItems()) {

        show(activity);
        System.out.println();
        System.out.println("------------------------------------------------------");
        System.out.println();
      }
   }
  private static void getProfile(String id) throws IOException {
    try {
      Person profile = plus.people().get(id).execute();
      show(profile);
     // System.out.println(profile);
    } catch (HttpResponseException e) {
      //log.severe(Util.extractError(e));
      throw e;
    }
  }

  private static void show(Person person) {
    System.out.println("id: " + person.getId());
    System.out.println("name: " + person.getDisplayName());
    System.out.println("image url: " + person.getImage().getUrl());
    System.out.println("about me: " + stripTags(person.getAboutMe()));
    System.out.println("gender: " + person.getGender());
    System.out.println("tagline: " + person.getTagline());
    System.out.println("Urls: " +  person.getUrls());
    System.out.println("Status: " + person.getRelationshipStatus());
    System.out.println("Type: " + person.getObjectType());
   }

  private static void show(Activity activity) {
     System.out.println("content: " + activity.getObject().getContent());
     System.out.println("type: " + activity.getObject().getObjectType());
     System.out.println("original content: " + activity.getObject().getOriginalContent());
     if(activity.getObject().getActor() != null)
     {
    	 System.out.println("actor: " + activity.getObject().getActor().getId());
     }
     System.out.println("plusoners: " + activity.getObject().getPlusoners().getTotalItems());
     System.out.println("replies: " + activity.getObject().getReplies().getTotalItems());
     System.out.println("reshares: " + activity.getObject().getResharers().getTotalItems());
  }
}