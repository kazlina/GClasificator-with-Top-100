package models;

import java.util.*;
import java.lang.NumberFormatException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.google.api.client.http.HttpResponseException;

 
public class Parser {
    public static TempProfile getProfile(String id) throws IOException {
     TempProfile profile = new TempProfile();
    	try{
	        Document doc = Jsoup.connect("https://plus.google.com/u/0/"+id).get();
	        
	        profile.id = id;
	        
	        //followers
	        Elements links = doc.select("script");
	        String template = ",[0,[]\\n]\\n,[";
	        for ( Element e : links) {         	  
	        	  if (e.toString().indexOf("key: '77'") != -1){
		              String textWithFollowers = e.toString();
		              
		              int start = textWithFollowers.lastIndexOf(template);
		              if (start == -1)
		            	  profile.nfollowers = 0;
		              else {
			              start += template.length();
			              int end = textWithFollowers.indexOf(',', start);
			              profile.nfollowers = Integer.parseInt((String) textWithFollowers.subSequence(start, end));
	                  }
	        	  }
	        }
	        
	        // get displayName
	        Elements name = doc.getElementsByClass("fn"); 
	        profile.displayName = name.text();
	        
	        // get image
	        Elements image = doc.select("div.g-oa-Sa-R-N");	// first try
	        if (image.isEmpty())
	        	image = doc.select("div.h-va-Za-W-P");	// second try
	        image = image.select("[src]");
	        for (Element src : image) 
		        if (src.tagName().equals("img")){
		        	profile.image = src.attr("abs:src");
		        }
	        
	        // get type page
	        String type = doc.select("div.Rr.sU.rAa.a-f-e.qU").text();	// first try
	        if (type.contentEquals("+Страница"))
	        	profile.isPerson = false;
	        else {
	        	type = doc.select("div.GDsXwf.Qm.lwy5pf.c-wa-Da.Om").text();	// second try
		        if (type.contentEquals("+Page"))
		        	profile.isPerson = false;
		        else
		        	profile.isPerson = true;
	        }
	        
	        // get aboutMe 
	        Elements about = doc.select("div.l-Rg.IC.me.lc");	// first try
	        about = about.select("div.Ca.a-f-e.note");
	        if (about.isEmpty()) {
		        about = doc.select("div.l-Jg.Wx.ne.jc");	// second try
		        about = about.select("div.Ga.a-f-e.note");
	        }
	        profile.aboutMe = about.text();
	        
	        Elements tagline = doc.select("div.l-uq.ne.jc"); //tagline
	        Elements tag = tagline.select("div.Ga.a-f-e"); 
	        profile.tagline = tag.text();
	 
	        // get urls
	        Elements urls = doc.select("a.nX.url");	// first try
	        if (urls.isEmpty())
	        	urls = doc.select("a.Qc0zVe.url");	// second try   
	        for(Element ur : urls) {
		       	String link = ur.attr("href");
		        profile.urls.add(link);
	        }   
	        
	        if (!profile.isPerson) {
	        	profile.gender = profile.relationshipStatus = "";
	        }
	        else {
	        	// get gender
		        Elements gender = doc.select("div.l-Cha.me.lc");	// first try
		        gender = gender.select("div.Ca.a-f-e");
				if (gender.isEmpty()) {
					gender = doc.getElementsByClass("J90C7b");	// second try
					gender = gender.select("div.l-i9.ne.jc");
			        gender = gender.select("div.Ga.a-f-e");
				}
		        profile.gender = gender.text(); 
		        
		        // get relationshipStatus
		        Elements status = doc.select("div.l-Fla.me.lc");	// first try
		        status = status.select("div.Ca.a-f-e");
		        if (status.isEmpty()) {
			        Elements statuses = doc.select("div.l-Xea.ne.jc");	// second try
			        status = statuses.select("div.Ga.a-f-e");
		        }
		        profile.relationshipStatus = status.text();
	        }
	    }
    	catch(HttpResponseException e) {
    		System.out.println("ERROR in getting profile by id = " + id);
    		e.printStackTrace();
    		return null;
    	}
    	catch (SocketTimeoutException e) {
    		System.out.println("ERROR in getting profile by id = " + id);
    		e.printStackTrace();
    		return null;
    	}
    	
        return profile;
}

    public static List <TempPost> getActivity(String id) throws IOException 
    {
        Document doc = Jsoup.connect("https://plus.google.com/u/0/"+id).get();
               
        Elements posts = doc.select("div.CPLjOe.Ye");
        
        List <TempPost> list_of_posts = new ArrayList <TempPost>();
        for(Element post : posts) 
        {
        	TempPost elem = new TempPost();
        	
        	String date_of_post = post.getElementsByClass("fD7nue").text().replace("Post date: ", "");
        	//elem.published_data = date_of_post;

          	Elements repost = post.getElementsByClass("Xt");
        	if (repost.isEmpty())	 
        	{
        		elem.isRepost = false;
        		elem.actorId = "";
        		elem.annotation = "";
        		
        		Elements post_text = post.getElementsByClass("rXnUBd");
        		elem.content = post_text.text();
        	}
        	else	
        	{
        		elem.isRepost = true;
        		String actor = post.getElementsByClass("Yt").html();
        		int start_cut = actor.indexOf("href=\"./") + 8;
        		actor = actor.substring(start_cut, start_cut + 21);
        		elem.actorId = actor;
        		
        		Elements post_text = post.getElementsByClass("oX401d");
        		Elements repost_text = post.getElementsByClass("rXnUBd");
        		elem.content = post_text.text();
        		elem.annotation = repost_text.text();
        	}
        	
        	Elements content = post.getElementsByClass("Zbbru");
        	if (content.isEmpty())	
        	{
        		//elem.kind_content = "circle/page";
        		continue;
        	}
        	else
        	{
        		String content_in_html = content.html();
        		if (content_in_html.isEmpty())
        		{
        			elem.kindContent = "text";
        		}
        		else if (!content_in_html.contains("data-content-type=\""))
        		{
        			elem.kindContent = "link";
        		}
        		else if(content_in_html.contains("data-content-type=\"image/jpeg"))
        		{
        			if (content_in_html.contains("data-content-url=\"https://plus.google.com/"))
        			{
        				elem.kindContent = "image";
        			}
        			else
        			{
        				elem.kindContent = "link";
        			}
        		}
        		else if(content_in_html.contains("data-content-type=\"application/x-shockwave-flash"))
        		{
        			elem.kindContent = "video";
        		}
         		
        	}
        	
        	String plus_one = post.getElementsByClass("yVKsMe").text();
        	String comments = post.getElementsByClass("aISsjb").text();
        	String shared = post.getElementsByClass("FdmHNd").text();
        	
        	if (plus_one.isEmpty())
        	{
        		elem.nPlusOne = 0;
        	}
        	else
        	{
        		plus_one = plus_one.replace("+", "");
        		try
        		{
        			elem.nPlusOne = Short.parseShort(plus_one);
        		}
        		catch (NumberFormatException exeption)
        		{
        			//exeption.printStackTrace();
        			elem.nPlusOne = 0;
        		}
        	}
        	
        	if (comments.isEmpty())
        	{
        		elem.nComments = 0;
        	}
        	else
        	{
        		try
        		{
        			elem.nComments = Short.parseShort(comments);
        		}
        		catch (NumberFormatException exeption)
        		{
        			//exeption.printStackTrace();
        			elem.nComments = 0;
        		}
        	}
        		
        	if (shared.isEmpty())
        	{
        		elem.nResharers = 0;
        	}
        	else
        	{
        		int length_string_with_nemb = shared.indexOf(" ");
        		shared = shared.substring(0, length_string_with_nemb);
        		try
        		{
        			elem.nResharers = Short.parseShort(shared);
        		}
        		catch (NumberFormatException exeption)
        		{
        			//exeption.printStackTrace();
        			elem.nResharers = 0;
        		}
        	}
        	list_of_posts.add(elem);
        }
        int i = 1;
        for (TempPost p : list_of_posts)
        {
        	System.out.println("    Post  " + i);
        	System.out.println(p.publishedData);
        	System.out.println(p.actorId);
        	System.out.println(p.kindContent);
        	System.out.println(" --- *** original content *** ---");
        	System.out.println(p.annotation);
        	System.out.println(" --- *** content *** ---");
        	System.out.println(p.content);
        	System.out.println(" --- *** statistic *** ---");
        	System.out.println("+1 = " + p.nPlusOne);
        	System.out.println("comments " + p.nComments);
        	System.out.println("reshared " + p.nResharers);
        	i ++;
        }
        return list_of_posts;
    }
}
