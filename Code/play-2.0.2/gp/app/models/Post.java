package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Ebean;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Post")
public class Post extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.MaxLength(40)
    @Constraints.Required
    @Column(name = "postId", length = 40, nullable = false, unique = true)
    public String postId;
    
    @Constraints.Required
    @JoinColumn(name = "gpm", nullable = false)
    @ManyToOne
    public GPM gpm;

    @Constraints.Required
    @Column(name = "dateCreate", nullable = false)
    public Date date;

	@Constraints.Required
	@JoinColumn(name = "kindContent", nullable = false)
	@ManyToOne
	public Content kindContent;

	@Column(name = "nComment")
    public Integer nComment;

    @Column(name = "nPlusOne")
    public Integer nPlusOne;

    @Column(name = "nResharers")
    public Integer nResharers;

    @Constraints.Required
	@Column(name = "isRepost", nullable = false)
    public boolean isRepost;

    @OneToMany(mappedBy = "post")
    public List<PostLink> links;

    @OneToMany(mappedBy = "post")
    public List<PostWord> words;

    public Post(GPM gpm, String postId, Date publishedData, Content kindContent, int nComments, 
    		int nPlusOne, int nResharers, boolean isRepost) {
    	this.gpm = gpm;
    	this.postId = postId;
    	this.date = publishedData;
    	this.kindContent = kindContent;
    	this.nComment = nComments;
    	this.nPlusOne = nPlusOne;
    	this.nResharers = nResharers;
    	this.isRepost = isRepost;
		
    }

    private static Model.Finder<Long, Post> find = new Model.Finder<Long, Post>(Long.class, Post.class);

    public static Post findById(Long Id) {
    	return find.where().eq("id", Id).findUnique();
	}
    
    public static Post findByPostId(String postId) {
		return find.where().eq("postId", postId).findUnique();
	}
    
    public static List<Post> findByGpmId(Long Id) {
		return find.where().eq("gpm", GPM.findById(Id)).findList();
	}
    
    public static Date getDateLastPostForGpm(GPM gpm) {
    	List<Post> posts =  find.where().eq("gpm", gpm).orderBy("date desc").findList();
    	return (posts.size() == 0)? null : posts.get(0).date;
	}

	public static void add(Post element) {
		Ebean.beginTransaction();
		
		try {
			Post findPost = Post.findByPostId(element.postId);
			if (findPost == null)
				element.save();
			
			Ebean.commitTransaction();
		}
		finally {
			Ebean.endTransaction();
		}
	}

	public static void delete(Long id) {
		Post pos = findById(id);
		if (pos == null)
			return;
		
		// delete links for post
		for (PostLink pl: PostLink.findByPost(id))
			PostLink.delete(pl.id);
		
		// delete words for post
		for (PostWord pw: PostWord.findByPost(id))
			PostWord.delete(pw.id);
		
		pos.delete();
    }
	
	public static int size() {
		return find.findRowCount();
	}
}
