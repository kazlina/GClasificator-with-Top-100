package models;

import java.util.*;
import javax.persistence.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
 
public class Parser {

    public static TempProfile GetProfile(String id) throws IOException {
    	TempProfile profile = new TempProfile();
        Document doc = Jsoup.connect("https://plus.google.com/u/0/"+id).get();
        
        Elements name = doc.getElementsByClass("fn"); //displayName
        profile.displayName = name.text();
        
        Elements tag = doc.getElementsByClass("rPciVd"); //tagline
        profile.tagline = tag.text();
        
        Elements aboutMe = doc.select("div.kM5Oeb-uoq5sb.Sd8iK.KtnyId.IzbGp");//aboutMe
        Elements about = aboutMe.select("div.aYm0te.c-wa-Da.note");//aboutMe
        profile.aboutMe = about.text();
        
        Elements urls = doc.select("a.Qc0zVe.url");//url
                
        profile.amounturls = urls.size(); 
          
        for(Element ur : urls) {
       	 String link = ur.attr("href");
         profile.urls.add(link);
        }
        
        Elements genders = doc.select("div.kM5Oeb-fYiPJe.KtnyId.IzbGp");//gender
        Elements gender = genders.select("div.aYm0te.c-wa-Da");//gender
        profile.gender = gender.text();    
         
        Elements statuses = doc.select("div.kM5Oeb-jcNnAf.KtnyId.IzbGp");//relationshipStatus
        Elements status = statuses.select("div.aYm0te.c-wa-Da");//relationshipStatus
        profile.relationshipStatus = status.text();
         
        Elements imag = doc.select("div.k-Qf-pc-N.CyVWVb");//image
        Elements im = imag.select("div.k-Va-pc-N-A");
        Elements imga = im.select("[src]");

        for (Element src : imga) 
        if (src.tagName().equals("img")){
        profile.image = src.attr("abs:src");
        }
        
        return profile;
}

    public static Vector <Post> GetPosts(String id) throws IOException 
    {
        Document doc = Jsoup.connect("https://plus.google.com/u/0/"+id).get();
        // íóæíà ïðîâåðêà ÷òî íå áûëî îøèáîê
        
        Elements posts = doc.select("div.CPLjOe.Ye"); //ïîñòû ëåíòû
        // âñòàâèòü ïðîâåðêó íà êîëè÷åñòâî ïîñòîâ
        Vector <Post> list_of_posts = new Vector(posts.size());
        for(Element post : posts) 
        {
        	Post elem = new Post();
        	
        	String date_of_post = post.getElementsByClass("fD7nue").text().replace("Post date: ", "");
        	//ïðîâåðêà ÷òî ïðàâèëüíî îáðàòèëèñü
        	
        	// çàïîìíèëè äàòó ïóáëèêàöèè ïîñòà
        	elem.published = date_of_post;
        	
        	// çàïîìíèì àâòîðà ïîñòà è òåêñò
        	Elements repost = post.getElementsByClass("Xt");
        	if (repost.isEmpty())	 
        	{
        		//åñëè ýòî ïîñò
        		elem.kind_post = true;
        		elem.actor_id = "";
        		elem.original_content = "";
        		
        		Elements post_text = post.getElementsByClass("rXnUBd");
        		elem.content = post_text.text();
        	}
        	else	//åñëè ýòî ðåïîñò
        	{
        		elem.kind_post = false;
        		String actor = post.getElementsByClass("Yt").html();
        		int start_cut = actor.indexOf("href=\"./") + 8;
        		actor = actor.substring(start_cut, start_cut + 21);
        		elem.actor_id = actor;
        		
        		Elements post_text = post.getElementsByClass("oX401d");
        		Elements repost_text = post.getElementsByClass("rXnUBd");
        		elem.content = post_text.text();
        		elem.original_content = repost_text.text();
        	}
        	
        	// îïðåäåëèì òèï êîíòåíòà
        	Elements content = post.getElementsByClass("Zbbru");
        	if (content.isEmpty())	//åñëè ýòî ñàéò/êðóã/èëè åùž ôèãíÿ êàêàÿ-òî
        	{
        		//elem.kind_content = "circle/page";
        		continue;
        	}
        	else
        	{
        		String content_in_html = content.html();
        		if (content_in_html.isEmpty())
        		{
        			// êîíòåíò - òåêñò
        			elem.kind_content = "text";
        		}
        		else if (!content_in_html.contains("data-content-type=\""))
        		{
        			elem.kind_content = "link";
        		}
        		else if(content_in_html.contains("data-content-type=\"image/jpeg"))
        		{
        			if (content_in_html.contains("data-content-url=\"https://plus.google.com/"))
        			{
        				elem.kind_content = "image";
        			}
        			else
        			{
        				elem.kind_content = "link";
        			}
        		}
        		else if(content_in_html.contains("data-content-type=\"application/x-shockwave-flash"))
        		{
        			elem.kind_content = "video";
        		}
         		
        	}
        	
        	String plus_one = post.getElementsByClass("yVKsMe").text();
        	String comments = post.getElementsByClass("aISsjb").text();
        	String shared = post.getElementsByClass("FdmHNd").text();
        	
        	// ïîëó÷àåì êîëè÷åñòâî +1
        	if (plus_one.isEmpty())
        	{
        		elem.total_plusoners = 0;
        	}
        	else
        	{
        		plus_one = plus_one.replace("+", "");
        		try
        		{
        			elem.total_plusoners = Short.parseShort(plus_one);
        		}
        		catch (NumberFormatException exeption)
        		{
        			//exeption.printStackTrace();
        			elem.total_plusoners = 0;
        		}
        	}
        	
        	// ïîëó÷àåì êîëè÷åñòâî êîììåíòàðèåâ
        	if (comments.isEmpty())
        	{
        		elem.total_replies = 0;
        	}
        	else
        	{
        		try
        		{
        			elem.total_replies = Short.parseShort(comments);
        		}
        		catch (NumberFormatException exeption)
        		{
        			//exeption.printStackTrace();
        			elem.total_replies = 0;
        		}
        	}
        		
        	//ïîëó÷àåì êîëè÷åñòâî ðåïîñòîâ
        	if (shared.isEmpty())
        	{
        		elem.total_resharers = 0;
        	}
        	else
        	{
        		int length_string_with_nemb = shared.indexOf(" ");
        		shared = shared.substring(0, length_string_with_nemb);
        		try
        		{
        			elem.total_resharers = Short.parseShort(shared);
        		}
        		catch (NumberFormatException exeption)
        		{
        			//exeption.printStackTrace();
        			elem.total_resharers = 0;
        		}
        	}
        	list_of_posts.add(elem);
        }
        /*int i = 1;
        for (Post p : list_of_posts)
        {
        	System.out.println("    Post ¹" + i);
        	System.out.println(p.published);
        	System.out.println(p.actor_id);
        	System.out.println(p.kind_content);
        	System.out.println(" --- *** original content *** ---");
        	System.out.println(p.original_content);
        	System.out.println(" --- *** content *** ---");
        	System.out.println(p.content);
        	System.out.println(" --- *** statistic *** ---");
        	System.out.println("+1 = " + p.total_plusoners);
        	System.out.println("comments " + p.total_replies);
        	System.out.println("reshared " + p.total_resharers);
        	i ++;
        }*/
        return list_of_posts;
    }
}
