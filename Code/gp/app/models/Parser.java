package models;

import java.util.*;
import javax.persistence.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
 
public class Parser {
    public static TempProfile getProfile(String id) throws IOException {
    	TempProfile profile = new TempProfile();
        Document doc = Jsoup.connect("https://plus.google.com/u/0/"+id).get();
        
        profile.id = id;
        
        String type = doc.select("div.GDsXwf.Qm.lwy5pf.c-wa-Da.Om").text();
        if (type == "+страница")
        	profile.type = false;
        else
        	profile.type = true;
        
        Elements name = doc.getElementsByClass("fn"); //displayName
        profile.displayName = name.text();
        
        Element tag = doc.select("div.aYm0te.c-wa-Da").first(); //tagline
        profile.tagline = tag.text();
        
        Elements aboutMe = doc.select("div.kM5Oeb-uoq5sb.Sd8iK.KtnyId.IzbGp");//aboutMe
        Elements about = aboutMe.select("div.aYm0te.c-wa-Da.note");//aboutMe
        profile.aboutMe = about.text();
        
        Elements urls = doc.select("a.Qc0zVe.url");//url        
           
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
        //profile.print();
        return profile;
}

    public static List <TempPost> getActivity(String id) throws IOException 
    {
        Document doc = Jsoup.connect("https://plus.google.com/u/0/"+id).get();
        // íóæíà ïðîâåðêà ÷òî íå áûëî îøèáîê
        
        Elements posts = doc.select("div.CPLjOe.Ye"); //ïîñòû ëåíòû
        // âñòàâèòü ïðîâåðêó íà êîëè÷åñòâî ïîñòîâ
        List <TempPost> list_of_posts = new ArrayList <TempPost>();
        for(Element post : posts) 
        {
        	TempPost elem = new TempPost();
        	
        	String date_of_post = post.getElementsByClass("fD7nue").text().replace("Post date: ", "");
          	//ïðîâåðêà ÷òî ïðàâèëüíî îáðàòèëèñü
        	
        	// çàïîìíèëè äàòó ïóáëèêàöèè ïîñòà
        	//elem.published_data = date_of_post;

        	// çàïîìíèì àâòîðà ïîñòà è òåêñò
        	Elements repost = post.getElementsByClass("Xt");
        	if (repost.isEmpty())	 
        	{
        		//åñëè ýòî ïîñò
        		elem.isRepost = false;
        		elem.actorId = "";
        		elem.annotation = "";
        		
        		Elements post_text = post.getElementsByClass("rXnUBd");
        		elem.content = post_text.text();
        	}
        	else	//åñëè ýòî ðåïîñò
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
        	
        	// ïîëó÷àåì êîëè÷åñòâî +1
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
        	
        	// ïîëó÷àåì êîëè÷åñòâî êîììåíòàðèåâ
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
        		
        	//ïîëó÷àåì êîëè÷åñòâî ðåïîñòîâ
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
