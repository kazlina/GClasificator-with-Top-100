package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

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
/*
	public ProfileLink(Profile profile, Link link, int amount){
        this.profile = profile;
        this.link = link;
        this.amount = amount;
        }
*/
	public static Model.Finder<Long, ProfileLink> find = new Model.Finder<Long, ProfileLink>(Long.class, ProfileLink.class);

	public static List<ProfileLink> all() {
		return find.all();
	}

	public static ProfileLink profileLinkById(Long Id) {
		return find.ref(Id);
	}

	public static void create(ProfileLink element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
