package models;

import java.util.*;
import java.lang.Override;
import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "GPM")
public class GPM extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
	@Constraints.Required
	public String idGpm;

	@OneToMany(mappedBy = "gpm")
	public List<Profile> profile;

	@OneToMany(mappedBy = "gpm")
	public List<Post> post;

	@OneToMany(mappedBy = "gpm")
	public List<AddedByAdmin> addedByAdmin;

	public String toString(){
		return this.idGpm;
		/*Profile profile = Profile.lastProfileByGpmId(this.id);
		return (profile != null && profile.name != null)? profile.name : this.idGpm;*/
	}

	private GPM(String id) {
		this.idGpm = id;
	}

	private static Model.Finder<Long, GPM> find = new Model.Finder<Long, GPM>(Long.class, GPM.class);
	
	public static List<GPM> all() {
		return find.all();
	}

	public static GPM findById(Long Id) {
		return find.ref(Id);
	}
	
	public static GPM findByIdGpm(String idGpm) {
		return find.where().eq("idGpm", idGpm).findUnique();
	}
	
	public static int size() {
		return find.findRowCount();
	}
	
	public static void create(String idGpm) {
		Ebean.beginTransaction();
		
		GPM searchGpm = findByIdGpm(idGpm);
		if (searchGpm != null)
			return;

		NewGPM searchNewGpm = NewGPM.findByIdGpm(idGpm);
		if (searchNewGpm != null)
			NewGPM.delete(searchNewGpm.id);
		
		GPM newGpm = new GPM(idGpm);
		newGpm.save();
		
		Ebean.endTransaction();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
