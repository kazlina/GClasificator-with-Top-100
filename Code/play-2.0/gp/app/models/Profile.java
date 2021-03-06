package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

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

    @Constraints.MaxLength(100)
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

    @Column(name = "nFollowers")
    public Integer nFollowers;

    @OneToMany(mappedBy = "profile")
    public List<ProfileLink> links;

    @OneToMany(mappedBy = "profile")
    public List<ProfileWord> words;

    public Profile(GPM gpm, String name, String image, Gender gender, String tagline,
			Relationship relationshipStatus, int nFollowers) {
    	this.gpm = gpm;  
    	this.name = name;
    	this.image = image;
    	this.gender = gender;
    	this.tagline = (tagline.length() >= 255)? tagline.substring(0, 254): tagline;
    	this.relationshipStatus = relationshipStatus;
    	this.nFollowers = nFollowers;
    }

	private static Model.Finder<Long, Profile> find = new Model.Finder<Long, Profile>(Long.class, Profile.class);

	public static Profile findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static List<Profile> findByGpmId(Long Id) {
		return find.where().eq("gpm", GPM.findById(Id)).orderBy("date desc").findList();
	}
	
	public static Profile lastProfileByGpmId(Long Id) {
		List<Profile> profiles =  find.where().eq("gpm", GPM.findById(Id)).orderBy("date desc").findList();
		return (profiles.size() == 0)? null : profiles.get(0);
	}
	
	public static String getLastNotEmptyField(Long gpmId, String field) {
		SqlRow res = Ebean.createSqlQuery("SELECT " + field
				+ " FROM profile"
				+ " WHERE gpm = :gpmId"
					+ " AND NOT " + field + " = ''"
				+ " ORDER BY dateUpdated DESC"
				+ " LIMIT 1;").setParameter("gpmId", gpmId).findUnique();
    	return (res == null)? "" : res.getString("name");
	}
	
	public static void add(Profile element) throws PersistenceException {
		element.date = Calendar.getInstance().getTime();		
		element.save();
	}

	public static void delete(Long id) {
		Profile prof = findById(id);
		if (prof == null)
			return;
		
		// delete links for profile
		if (prof.links != null)
			for (ProfileLink pl: prof.links)
				ProfileLink.delete(pl.id);
		
		// delete words for profile
		if (prof.words != null)
			for (ProfileWord pw: prof.words)
				ProfileWord.delete(pw.id);
		
		prof.delete();
    }
	
	public static int size() {
		return find.findRowCount();
	}
}
