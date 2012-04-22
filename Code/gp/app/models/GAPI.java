package models;

import java.util.*;
<<<<<<< HEAD
//import javax.persistence.*;
=======

import javax.persistence.*;
>>>>>>> f130c672f8c76409ead839d063d7ed09417a354c
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
import com.google.api.services.plus.model.Activity.PlusObject;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.Person.Urls;

import java.io.IOException;
//import java.util.logging.Logger;


public class GAPI {
  //private static final Logger log = Logger.getLogger(Sample.class.getName());

  private static Plus plus;

  public static void main(String[] args) throws IOException {

    try {
      setupTransport();

     // getProfile("100054563229729962810");
     GetActivity("100054563229729962810", 30L);
    } catch (HttpResponseException e) {
     // log.severe(e.getResponse().parseAsString());
      throw e;
    }
  }
  
  private static String stripTags(String xmlStr){
	    if (xmlStr != null){
	    xmlStr = xmlStr.replaceAll("<(.)+?>", " "); 

	    xmlStr = xmlStr.replaceAll("<(\n)+?>", " ");
	        
	    return xmlStr;
	    }
	    else 
	    	return null;
}
  
  private static List <String> getUrls(List <Urls> list){
	  if(list != null){
	  List <String> urls = new ArrayList<String>();
	  for (Person.Urls u : list){		 
		  urls.add(u.getValue());
	  }	  
	return urls;
	}
	  else 
		  return null;
  }
  
  private static boolean getType(String type){
	if(type != null)
	{
		if (type.equals("page"))
    		return true;
	    else
    	    return false;	
	}
	return false;
  }
  private static String getKindContent(List<Attachments> list){
	if(list != null){
        for (PlusObject.Attachments a: list){
      	  if(a.getObjectType().equals("article"))
    		return "article";
      	  if(a.getObjectType().equals("video"))
    		return "video";
      	  if(a.getObjectType().equals("photo"))
    		return "photo";
      	  if(a.getObjectType().equals("photo-album"))
    		return "photo";
      	if(a.getObjectType().equals("audio"))
    		return "audio";
      	  }
	}
	else
		return "note";
	return null;
}
private static String getUrl(List<Attachments> list){
		if(list != null){
	        for (PlusObject.Attachments a: list){
	        	if(a.getObjectType().equals("article")){
	      	       return a.getUrl();
	      	    }
	      	  }
		}
   		return null;
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

  public static List <TempPost> GetActivity(String id, Long numberOf) throws IOException {
    Plus.Activities.List listActivities = plus.activities().list(id,"public");
    
    listActivities.setMaxResults(numberOf);
    listActivities.setFields("items(actor/id,annotation,id,object(actor/id,attachments(content,displayName,objectType,url)," +
    		"content,id,originalContent,plusoners/totalItems,replies/totalItems,resharers/totalItems)," +
    		"published,updated,verb),nextLink,nextPageToken,selfLink");

    ActivityFeed feed;
    
    List <TempPost> posts = new ArrayList();
    
    try {
      feed = listActivities.execute();
      for (Activity activity : feed.getItems()) {
          TempPost post = new TempPost();
          post.publishedData = new Date(activity.getPublished().getValue());
          post.content = stripTags(activity.getObject().getContent());
          post.annotation = stripTags(activity.getAnnotation());
          post.isRepost = false;
          if(activity.getObject().getActor() != null)
          {
          	post.actorId = activity.getObject().getActor().getId();
          	post.isRepost = true;
          }
          post.nPlusOne = activity.getObject().getPlusoners().getTotalItems();
          post.nComments = activity.getObject().getReplies().getTotalItems();
          post.nResharers = activity.getObject().getResharers().getTotalItems();
          post.kindContent = getKindContent(activity.getObject().getAttachments());
          if(post.kindContent.equals("article"))
        	  post.url = getUrl(activity.getObject().getAttachments());       
          posts.add(post);
       }
      for ( TempPost p: posts){
      	p.print();
      }
  	return posts;     
    } 
    catch (HttpResponseException e) {
      return null;
    }    
   }
  
  public static TempProfile getProfile(String id) throws IOException {
    try {
      Person person = plus.people().get(id).execute();
      TempProfile profile = new TempProfile();
      profile.id = person.getId();
      profile.displayName = person.getDisplayName();
      profile.image = person.getImage().getUrl();
      profile.aboutMe = stripTags(person.getAboutMe());
      profile.gender = person.getGender();
      profile.tagline = person.getTagline();
      profile.urls = getUrls(person.getUrls());
      profile.relationshipStatus = person.getRelationshipStatus();
      profile.type = getType(person.getObjectType());
      profile.print();
      return profile;
    } 
    catch (HttpResponseException e) {
      return null;
    }
  }

  public static void print(Person person) {
    System.out.println("id: " + person.getId());
    System.out.println("name: " + person.getDisplayName());
    System.out.println("image url: " + person.getImage().getUrl());
    System.out.println("about me: " + person.getAboutMe());
    System.out.println("gender: " + person.getGender());
    System.out.println("tagline: " + person.getTagline());
    System.out.println("urls: " +  person.getUrls());
    System.out.println("relationshipStatus: " + person.getRelationshipStatus());
    System.out.println("type: " + person.getObjectType());
   }

  public static void print(ActivityFeed feed) {
	 for (Activity activity : feed.getItems()) {
     System.out.println("content: " + activity.getObject().getContent());
     System.out.println("type: " + activity.getObject().getObjectType());
     System.out.println("annotation: " + activity.getAnnotation());
     if(activity.getObject().getActor() != null)
     {
    	 System.out.println("actor: " + activity.getObject().getActor().getId());
     }
     System.out.println("plusoners: " + activity.getObject().getPlusoners().getTotalItems());
     System.out.println("replies: " + activity.getObject().getReplies().getTotalItems());
     System.out.println("reshares: " + activity.getObject().getResharers().getTotalItems());
     System.out.println();
     System.out.println("------------------------------------------------------");
     System.out.println();
  }
	 }
}
