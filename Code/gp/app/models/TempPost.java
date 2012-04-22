package models;

import java.util.*;

import javax.persistence.*;

public class TempPost {
	public String actorId;
	public String kindContent;// article, video, photo, note, audio
	public String content;
	public String annotation;
	public Date publishedData;
	public String url;
	public boolean isRepost;//post-false or repost -true
	public int nComments;
	public int nPlusOne;
	public int nResharers;
	
	public TempPost() {
	}	
	
    public TempPost(String actorId, String kindContent, String content, String annotation, 
    		  		Date publishedData, String url, boolean isRepost, int nComments,
    		  		int nPlusOne, int nResharers){
    	this.actorId = actorId;
    	this.kindContent = kindContent;
    	this.content = content;
    	this.annotation = annotation;
    	this.publishedData = publishedData;
    	this.isRepost = isRepost;
        this.url = url;
    	this.nComments = nComments;
    	this.nPlusOne = nPlusOne;
    	this.nResharers = nResharers;
        }
    
    public void print(){
    	System.out.println("published data: " + this.publishedData);
    	System.out.println("kind post: " + this.isRepost);
    	System.out.println("actor_id: " + this.actorId);
    	System.out.println("kind content: " + this.kindContent);
    	System.out.println("url " + this.url);
    	System.out.println("annotation: " + this.annotation); 
    	System.out.println("content: " + this.content);    	
    	System.out.println("plusoners " + this.nPlusOne);
    	System.out.println("comments " + this.nComments);
    	System.out.println("resharers " + this.nResharers);		
       	System.out.println("=================================================");
    	}

}
