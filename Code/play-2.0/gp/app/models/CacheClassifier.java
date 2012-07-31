package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.*;
import play.db.ebean.*;

@Entity
@Table(name = "CacheClassifier")
public class CacheClassifier extends Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@JoinColumn(name = "groupDescr", nullable = false)
	@ManyToOne
	public Group group;
	
	@Column(name = "position", nullable = false)
	public Integer position;
	
	@Column(name = "gpm", length = 21, nullable = false)
	public String gpm;

	@Column(name = "name", length = 100, nullable = false)
	public String name;

	@Column(name = "image", nullable = false)
	public String image;

	@Column(name = "gender", length = 10)
	public String gender;

	@Column(name = "relationshipStatus", length = 30)
	public String relationshipStatus;

	@Column(name = "nFollowers", nullable = false)
	public Integer nFollowers;

	public CacheClassifier(Group group, Integer position, String gpm, String name, String image, String gender, String relationshipStatus, Integer nFollowers) {
		this.group = group;
		this.position = position;
    	this.gpm = gpm;
    	this.name = name;
    	this.image = image;
    	this.gender = gender;
    	this.relationshipStatus = relationshipStatus;
    	this.nFollowers = nFollowers;
	}

	private static Model.Finder<Long, CacheClassifier> find = new Model.Finder<Long, CacheClassifier>(Long.class, CacheClassifier.class);

	public static List<CacheClassifier> findByGroup(Long id) {
		return find.where().eq("group", Group.findById(id)).orderBy("position").findList();
	}
	
	public static void update(Long idGroup, List<SqlRow> gpms) {
		Group group = Group.findById(idGroup);
		if (group == null)
			return;
		
		Ebean.beginTransaction();
		try {
			deleteForGroup(idGroup);
			
			Integer i = 1;
			for (SqlRow man: gpms) {
				Profile prof = Profile.lastProfileByGpmId(man.getLong("gpm"));
		    	if (prof == null)
		    		continue;
		    	
				CacheClassifier cc = new CacheClassifier(
						group, 
						i++, 
	    				prof.gpm.idGpm, 
	    				prof.name, 
	    				prof.image, 
	    				(prof.gender == null)? null : prof.gender.value,
	    				(prof.relationshipStatus == null)? null : prof.relationshipStatus.status,
	    				prof.nFollowers);
				cc.save();
			}
			
			Ebean.commitTransaction();	
		} 
		finally {
			Ebean.endTransaction();
		}
	}
	
	public static void deleteForGroup(Long id) {
		for (CacheClassifier gpm: findByGroup(id))
			//gpm.delete();
			find.ref(gpm.id).delete();
    }
}
