package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

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
/*
   public PostLink(GPM Id, Posts post, Link link, int amount){
        //this.Id_GPM = Id;
        //this.date = new Date();
        //this.post = post;
        this.link = link;
        this.amount = amount;
        }
*/
	public static Model.Finder<Long, PostLink> find = new Model.Finder<Long, PostLink>(Long.class, PostLink.class);

	public static List<PostLink> all() {
		return find.all();
	}

	public static PostLink postLinkById(Long Id) {
		return find.ref(Id);
	}

	public static void create(PostLink element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
