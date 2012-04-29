package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Profile", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"gpm", "dateUpdated"
		})
	})
public class Profile extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
    @JoinColumn(name = "gpm", nullable = false)
    @ManyToOne
    public GPM gpm;

    @Constraints.Required
    @Column(name = "dateUpdated", nullable = false)
    public Date date;

    @Column(name = "name", length = 100)
    public String name;

    @Column(name = "image")
	public String image;

    @JoinColumn(name = "gender")
    @ManyToOne
    public Gender gender;

    @Column(name = "tagline")
    public String tagline;

    @JoinColumn(name = "relationshipStatus")
    @ManyToOne
    public Relationship relationshipStatus;

    @Column(name = "Followers")
    public Integer followers;

    @OneToMany(mappedBy = "profile")
    public List<ProfileLink> links;

    @OneToMany(mappedBy = "profile")
    public List<ProfileWord> words;
/*
    public Profile(GPM gpm, String name, String image, String gender, String tagline,
					String relationshipStatus, int followers) {
        this.gpm = gpm;
        this.date = Calendar.getInstance().getTime();
        this.name = name;
        this.image = image;
        this.gender = gender;
        this.tagline = tagline;
        this.relationshipStatus = relationshipStatus;
        this.followers = followers;
        }
*/
	public static Model.Finder<Long, Profile> find = new Model.Finder<Long, Profile>(Long.class, Profile.class);

	public static List<Profile> all() {
		return find.all();
	}

	public static Profile profileById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Profile element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
