package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "ProfileLink", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"profile", "link"
		})
	})
public class ProfileLink extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@JoinColumn(name = "profile", nullable = false)
    @ManyToOne
    public Profile profile;

    @Constraints.Required
	@JoinColumn(name = "link", nullable = false)
    @ManyToOne
    public Link link;

    @Constraints.Required
	@Column(name = "amount", nullable = false)
    public Integer amount;

    private ProfileLink(Profile profile, Link link, int amount){
        this.profile = profile;
        this.link = link;
        this.amount = amount;
        }

    private static Model.Finder<Long, ProfileLink> find = new Model.Finder<Long, ProfileLink>(Long.class, ProfileLink.class);

    public static ProfileLink findById(Long Id) {
    	return find.where().eq("id", Id).findUnique();
	}
	
	public static List<ProfileLink> findByProfile(Long id) {
		return Profile.findById(id).links;
	}
	
	public static List<ProfileLink> findByLink(Long id) {
		return Link.findById(id).profileLinks;
	}

	public static void add(Profile profile, String link, int amount) {
		Ebean.beginTransaction();
		
		try {
			Link findLink = Link.findByLink(link);
			if (findLink != null) {
				ProfileLink findProfileLink = find.where().eq("profile", profile).eq("link", findLink).findUnique();
				if (findProfileLink == null) {
					ProfileLink element = new ProfileLink(profile, findLink, amount);
					element.save();
				}
			}
			Ebean.commitTransaction();
		}
		finally {
			Ebean.endTransaction();
		}
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
