package models;

import java.util.*;
import javax.persistence.*;

public class TempProfile {
	public String id;
	public String displayName;
	public String tagline;
	public String gender; //female, male, other or null
	public String relationshipStatus;
	public String image;
	public String aboutMe;
	List <String> urls = new ArrayList<String>();
	public boolean type;//if person - true, if page - false
	
	public TempProfile() {
	}	
	
    public TempProfile(String id, String displayName,String image, String aboutMe, String gender, String tagline,
    		              List <String> urls, String relationshipStatus, boolean type){
    	this.id = id;
    	this.displayName = displayName;
    	this.tagline = tagline;
    	this.gender = gender;
    	this.relationshipStatus = relationshipStatus;
    	this.image = image;
    	this.aboutMe = aboutMe;
    	this.type = type;
    	this.urls = urls;
        }
    
    public void print(){
    	System.out.println("id: " + this.id);
    	System.out.println("displayName: " + this.displayName);
    	System.out.println("tagline: " + this.tagline);
    	System.out.println("gender: " + this.gender);
    	System.out.println("relationshipStatus: " + this.relationshipStatus);
    	System.out.println("image: " + this.image);
    	System.out.println("aboutMe: " + this.aboutMe);
    	System.out.println("type: " + this.type);
    	for (String p : urls)
    	{
    		System.out.println("url " + p);    		
    	}
    }
	
}
