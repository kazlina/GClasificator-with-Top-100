package models;

import java.util.*;

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

public class GAPI {
  private static Plus plus;
 
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
    		return "link";
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
		return "text";
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
  JsonFactory JSON_FACTORY = new GsonFactory();
  HttpTransport TRANSPORT = new NetHttpTransport();
	  plus = Plus.builder(TRANSPORT, JSON_FACTORY)
         .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {
          @Override
          public void initialize(JsonHttpRequest jsonHttpRequest) throws IOException {
            PlusRequest plusRequest = (PlusRequest) jsonHttpRequest;
            plusRequest.setKey("AIzaSyBYsU38f1LKp-Cv-Pgmzz0dKrHw0Zk-SAY"); //API key          
          }
        }).build();      
    }

  public List <TempPost> getActivity(String id, int numberOf) throws IOException {	  
	setupTransport();    
	Plus.Activities.List listActivities = plus.activities().list(id,"public");
    ActivityFeed feed;
    
    List <TempPost> posts = new ArrayList();
    
    int remainder = numberOf;
    while(remainder > 0){
    	if(remainder > 100){
			listActivities.setFields("items(actor/id,annotation,id,object(actor/id,attachments(content,displayName,objectType,url)," +
    		"content,id,originalContent,plusoners/totalItems,replies/totalItems,resharers/totalItems)," +
    		"published,updated,verb),nextPageToken"); 
			listActivities.setMaxResults(100L);
			remainder = remainder - 100;
    	}
    	else{
    		listActivities.setFields("items(actor/id,annotation,id,object(actor/id,attachments(content,displayName,objectType,url)," +
    	    		"content,id,originalContent,plusoners/totalItems,replies/totalItems,resharers/totalItems)," +
    	    		"published,updated,verb)"); 
    				listActivities.setMaxResults((long) remainder);
    				remainder = 0;
    	}
    try {
      feed = listActivities.execute(); 
      for (Activity activity : feed.getItems()) {
          TempPost post = new TempPost();
          post.postId = activity.getId();
          post.publishedData = new Date(activity.getPublished().getValue());
          post.content = stripTags(activity.getObject().getContent());
          post.annotation = stripTags(activity.getAnnotation());
          post.isRepost = false;
          if(activity.getObject().getActor() != null)
          {
          	post.actorId = activity.getObject().getActor().getId();
          	post.isRepost = true;
          }
          post.nPlusOne = activity.getObject().getPlusoners().getTotalItems().intValue();
          post.nComments = activity.getObject().getReplies().getTotalItems().intValue();
          post.nResharers = activity.getObject().getResharers().getTotalItems().intValue();
          post.kindContent = getKindContent(activity.getObject().getAttachments());
          if(post.kindContent.equals("link"))
        	  post.url = getUrl(activity.getObject().getAttachments());       
          posts.add(post);
          if(remainder > 100){
          	if (feed.getNextPageToken() == null) {
          	    remainder = 0;
          	}
          	else{
          		listActivities.setPageToken(feed.getNextPageToken());
          	}
          }
       }
  	}
    catch (HttpResponseException e) {
    	//System.out.println(e.getResponse().getStatusCode());
    	return posts;
    } 
 }
    int i = 0;
    for ( TempPost p: posts){
    	System.out.println("=====post number  "+ i++ +"===============");
      	p.print();
      }
	return posts;  
 }
  
  public static TempProfile getProfile(String id) throws IOException {
	  setupTransport();
	  TempProfile profile = new TempProfile();
    try {
      Person person = plus.people().get(id).execute();      
      profile.id = person.getId();
      profile.displayName = person.getDisplayName();
      profile.image = person.getImage().getUrl();
      profile.aboutMe = stripTags(person.getAboutMe());
      profile.gender = person.getGender();
      profile.tagline = person.getTagline();
      profile.urls = getUrls(person.getUrls());
      profile.relationshipStatus = person.getRelationshipStatus();
      profile.type = getType(person.getObjectType());
      //profile.print();
      return profile;
    } 
    catch (HttpResponseException e) {
      return profile;
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
	 System.out.println("postId: " + activity.getId());
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