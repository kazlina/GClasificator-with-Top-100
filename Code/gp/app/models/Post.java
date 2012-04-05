package models;

import java.util.*;
import javax.persistence.*;


public class Post {
	public String actor_id;
	public String kind_content;
	public String content;
	public String original_content;
	public String published;
	public Vector <String> urls;
	public boolean kind_post;
	public short amount_urls;
	public short total_replies;
	public short total_plusoners;
	public short total_resharers;
	public short total_images;

}
