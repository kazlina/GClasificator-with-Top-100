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

      //getProfile("107332580040286178426");
      GetActivity("107332580040286178426", 30L);
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
  
  private static boolean getGender(String gender){
	if(gender != null)
	{
		if (gender.equals("female"))
	    	return true;
	else
    	return false;
	}
	return false;
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

  public static List <Post> GetActivity(String id, Long numberOf) throws IOException {
    Plus.Activities.List listActivities = plus.activities().list(id,"public");
    
    listActivities.setMaxResults(numberOf);

    ActivityFeed feed;
    
    List <Post> posts = new ArrayList();
    
    try {
      feed = listActivities.execute();
      for (Activity activity : feed.getItems()) {
          Post post = new Post();
          post.published_data = activity.getPublished();
          post.content = stripTags(activity.getObject().getContent());
          post.annotation = stripTags(activity.getAnnotation());
          post.kind_post = true;
          if(activity.getObject().getActor() != null)
          {
          	post.actor_id = activity.getObject().getActor().getId();
          	post.kind_post = false;
          }
          post.total_plusoners = activity.getObject().getPlusoners().getTotalItems();
          post.total_replies = activity.getObject().getReplies().getTotalItems();
          post.total_resharers = activity.getObject().getResharers().getTotalItems();
          post.kind_content = getKindContent(activity.getObject().getAttachments());
          if(post.kind_content.equals("article"))
        	  post.url = getUrl(activity.getObject().getAttachments());       
          posts.add(post);
       }
      for ( Post p: posts){
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
      profile.gender = getGender(person.getGender());
      profile.tagline = person.getTagline();
      profile.urls = getUrls(person.getUrls());
      profile.relationshipStatus = person.getRelationshipStatus();
      profile.type = getType(person.getObjectType());
      //profile.print();
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
    System.out.println("Urls: " +  person.getUrls());
    System.out.println("Status: " + person.getRelationshipStatus());
    System.out.println("Type: " + person.getObjectType());
   }

  public static void print(ActivityFeed feed) {
	 for (Activity activity : feed.getItems()) {
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
     System.out.println();
     System.out.println("------------------------------------------------------");
     System.out.println();
  }
	 }
}
