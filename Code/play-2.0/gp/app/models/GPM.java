package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

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
	}

	private GPM(String id) {
		this.idGpm = id;
	}

	private static Model.Finder<Long, GPM> find = new Model.Finder<Long, GPM>(Long.class, GPM.class);
	
	public static List<GPM> all() {
		return find.all();
	}

	public static GPM findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static GPM findByIdGpm(String idGpm) {
		return find.where().eq("idGpm", idGpm).findUnique();
	}
	
	public static int size() {
		return find.findRowCount();
	}
	
	public static void add(String idGpm) {
		Ebean.beginTransaction();
		
		GPM searchGpm = findByIdGpm(idGpm);
		if (searchGpm != null)
			return;

		NewGPM searchNewGpm = NewGPM.findByIdGpm(idGpm);
		if (searchNewGpm != null)
			NewGPM.delete(searchNewGpm.id);
		
		GPM newGpm = new GPM(idGpm);
		newGpm.save();
		
		Ebean.commitTransaction();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
	
	public static List<SqlRow> getIdGpmByLastPosts(int count) {
    	return Ebean.createSqlQuery("SELECT gpm"
				+ " FROM Post"
				+ " GROUP BY gpm"
				+ " ORDER BY max(dateCreate)"
				+ " LIMIT :count;").setParameter("count", count).findList();
	}
    
	public static List<SqlRow> getIdGpmByLastProfile(int count) {
		return Ebean.createSqlQuery("SELECT gpm"
				+ " FROM Profile"
				+ " GROUP BY gpm"
				+ " ORDER BY max(dateUpdated)"
				+ " LIMIT :count;").setParameter("count", count).findList();
	}
}
