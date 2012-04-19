package models;

import java.util.*;

import javax.persistence.*;

import com.google.api.client.util.DateTime;


public class Post {
	public String actor_id;
	public String kind_content;// article, video, photo, note
	public String content;
	public String annotation;
	public DateTime published_data;
	public String url;
	public boolean kind_post;//post or repost
	public Long total_replies;
	public Long total_plusoners;
	public long total_resharers;
	public short total_images;
	
	public Post() {
	}	
	
    public Post(String actor_id, String kind_content, String content, String annotation, DateTime published_data,
	           String url, boolean kind_post, Long total_replies, Long total_plusoners,
	           Long total_resharers, short total_images){
    	this.actor_id = actor_id;
    	this.kind_content = kind_content;
    	this.content = content;
    	this.annotation = annotation;
    	this.published_data = published_data;
    	this.kind_post = kind_post;
        this.url = url;
    	this.total_replies = total_replies;
    	this.total_plusoners = total_plusoners;
    	this.total_resharers = total_resharers;
    	this.total_images = total_images;
        }
    
    public void print(){
    	System.out.println("published data: " + this.published_data);
    	System.out.println("kind post: " + this.kind_post);
    	System.out.println("actor_id: " + this.actor_id);
    	System.out.println("kind content: " + this.kind_content);
    	System.out.println("url " + this.url);
    	System.out.println("annotation: " + this.annotation); 
    	System.out.println("content: " + this.content);    	
    	System.out.println("total images: " + this.total_images);
    	System.out.println("total plusoners " + this.total_plusoners);
    	System.out.println("total replies " + this.total_replies);
    	System.out.println("total resharers " + this.total_resharers);		
       	System.out.println("=================================================");
    	}

}
