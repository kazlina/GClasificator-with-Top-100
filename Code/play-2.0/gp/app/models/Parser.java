package models;

import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.google.api.client.http.HttpResponseException;

import java.io.*;
 
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
              int start = textWithFollowers.lastIndexOf(template)+template.length();
              int end = textWithFollowers.indexOf(',', start);
              profile.nfollowers = Integer.parseInt((String) textWithFollowers.subSequence(start, end));
              }  
        }
        
        String type = doc.select("div.GDsXwf.Qm.lwy5pf.c-wa-Da.Om").text();
        if (type == "+Page")
        	profile.isPerson = false;
        else
        	profile.isPerson = true;
        
        Elements name = doc.getElementsByClass("fn"); //displayName
        profile.displayName = name.text();
        
        Elements tagline = doc.select("div.l-uq.ne.jc"); //tagline
        Elements tag = tagline.select("div.Ga.a-f-e"); 
        profile.tagline = tag.text();
 
        Elements about = doc.select("div.l-Jg.Wx.ne.jc");//aboutMe 
        Elements aboutMe = about.select("div.Ga.a-f-e.note");
        profile.aboutMe = aboutMe.text();
        
        Elements urls = doc.select("a.Qc0zVe.url");//url        
           
        for(Element ur : urls) {
       	 String link = ur.attr("href");
         profile.urls.add(link);
        }
        
        Elements genders = doc.select("div.l-i9.ne.jc");//gender
        Elements gender = genders.select("div.Ga.a-f-e");
        profile.gender = gender.text();    
         
        Elements statuses = doc.select("div.l-Xea.ne.jc");//relationshipStatus
        Elements status = statuses.select("div.Ga.a-f-e");
        profile.relationshipStatus = status.text();
         
        Elements im = doc.select("div.h-va-Za-W-P");//image
        Elements imga = im.select("[src]");

        for (Element src : imga) 
        if (src.tagName().equals("img")){
        profile.image = src.attr("abs:src");
        }       
    	}
    	catch(HttpResponseException e){
    		
    	}
        //profile.print();
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
