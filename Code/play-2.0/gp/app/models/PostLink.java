package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "PostLink", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"post", "link"
		})
	})
public class PostLink extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@JoinColumn(name = "post", nullable = false)
    @ManyToOne
    public Post post;

    @Constraints.Required
	@JoinColumn(name="link", nullable = false)
    @ManyToOne
    public Link link;

    @Constraints.Required
	@Column(name="amount", nullable = false)
    public int amount;

   private PostLink(Post post, Link link, int amount){
        this.post = post;
        this.link = link;
        this.amount = amount;
        }

    private static Model.Finder<Long, PostLink> find = new Model.Finder<Long, PostLink>(Long.class, PostLink.class);

	public static PostLink findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static List<PostLink> findByPost(Long id) {
		return Post.findById(id).links;
	}
	
	public static List<PostLink> findByLink(Long id) {
		return Link.findById(id).postLinks;
	}

	public static void add(Post post, String link, int amount) {
		Ebean.beginTransaction();
		
		Link findLink = Link.findByLink(link);
		if (findLink == null) {
			Ebean.endTransaction();
			return;
		}
		
		PostLink findPostLink = find.where().eq("post", post).eq("link", findLink).findUnique();
		if (findPostLink != null) {
			Ebean.endTransaction();
			return;
		}

		
		PostLink element = new PostLink(post, findLink, amount);
		element.save();
		
		Ebean.commitTransaction();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
