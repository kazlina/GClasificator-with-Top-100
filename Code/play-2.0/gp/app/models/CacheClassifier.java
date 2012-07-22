package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "CacheClassifier")
public class CacheClassifier extends Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@JoinColumn(name = "groupDescr", nullable = false)
	@ManyToOne
	public Group group;
	
	@Column(name = "gpm", nullable = false)
	GpmForOutput gpm;

	public CacheClassifier(Group group, GpmForOutput gpm) {
		this.group = group;
		this.gpm = gpm;
	}

	private static Model.Finder<Long, CacheClassifier> find = new Model.Finder<Long, CacheClassifier>(Long.class, CacheClassifier.class);

	public static List<GpmForOutput> getGPMs(Long id) {
		List<GpmForOutput> gpms = new ArrayList<GpmForOutput>();
		for (CacheClassifier gpm: findByGroup(id))
			gpms.add(gpm.gpm);
		
		return gpms;
	}
	
	private static List<CacheClassifier> findByGroup(Long id) {
		return find.where().eq("group", Group.findById(id)).orderBy("gpm.position").findList();
	}
	
	public static void update(Long idGroup, List<GpmForOutput> gpms) {
		Group group = Group.findById(idGroup);
		if (group == null)
			return;
		
		Ebean.beginTransaction();
		try {
			deleteForGroup(idGroup);
			
			for (GpmForOutput man: gpms) {
				CacheClassifier cc = new CacheClassifier(group, man);
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
			gpm.delete();
    }
}
