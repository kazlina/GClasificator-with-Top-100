package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Link")
public class Link extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@Column(name = "link", nullable = false, unique = true)
	public String link;

	@OneToMany(mappedBy = "link")
	public List<GroupLink> groupLink;

	@OneToMany(mappedBy = "link")
	public List<PostLink> postLinks;

	@OneToMany(mappedBy = "link")
	public List<ProfileLink> profileLinks;
/*
	public String toString(){
		return this.link;
	}

	public Link(String link){
		this.link = link;
	}
*/
	public static Model.Finder<Long, Link> find = new Model.Finder<Long, Link>(Long.class, Link.class);

	public static List<Link> all() {
		return find.all();
	}

	public static Link linkById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Link element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
