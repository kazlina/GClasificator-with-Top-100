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

	@Constraints.MaxLength(255)
	@Constraints.Required
	@Constraints.Pattern(value = "(http|https|HTTP|HTTPS)://(((\\?\\.)?[a-z]+(\\.){1}[a-z]{2,3}+)|(([a-z]+\\.)?\\?(\\.){1}[a-z]{2,3}+)|(([a-z]+\\.)?[a-z]+(\\.){1}\\?)|(([a-z]+\\.)?[a-z]+(\\.){1}[a-z]{2,3}+))(:\\d{1,4})?/?[/\\.a-zA-Z\\d\\?%=&_\\-#!;:@]*", message = "Incorrect link")
	@Column(name = "link", nullable = false, unique = true)
	public String link;

	@OneToMany(mappedBy = "link")
	public List<GroupLink> groupLink;

	@OneToMany(mappedBy = "link")
	public List<PostLink> postLinks;

	@OneToMany(mappedBy = "link")
	public List<ProfileLink> profileLinks;

	public String toString(){
		return this.link;
	}

	public Link(String link){
		this.link = link;
	}

	private static Model.Finder<Long, Link> find = new Model.Finder<Long, Link>(Long.class, Link.class);

	public static List<Link> all() {
		return find.all();
	}

	public static List<String> allInString() {
		List <String> linksInString = new ArrayList<String>();
		for (Link link: all())
			linksInString.add(link.link);
		return linksInString;
	}
	
	public static Integer size() {
		return find.findRowCount();
	}
	
	public static Link findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static Link findByLink(String link) {
		return find.where().eq("link", link).findUnique();
	}

	public static void add(Link element) {
		Link findLink = Link.findByLink(element.link);
		if (findLink == null)
			element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }

	public static void updateLink(Long idLink, Link link) {
	System.out.println(link.link);
	Link findLink = Link.findById(idLink);
	if (findLink == null)
		return;

	findLink.link = link.link;
	findLink.update();
	}

}
