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
	
	@Constraints.MinLength(21)
	@Constraints.MaxLength(21)
	@Constraints.Required
	@Constraints.Pattern("[0-9]+")
	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
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
		return find.orderBy("idGpm").findList();
	}

	public static List<String> allInString() {
		List <String> gpmsInString = new ArrayList<String>();
		for (GPM gpm: all()) {
			gpmsInString.add(gpm.idGpm);
		}
		return gpmsInString;
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
	
	public static GPM add(String idGpm) {
		GPM newGpm = null;
		
		Ebean.beginTransaction();
		try {
			GPM searchGpm = findByIdGpm(idGpm);
			if (searchGpm == null) {
				NewGPM searchNewGpm = NewGPM.findByIdGpm(idGpm);
				if (searchNewGpm != null)
					NewGPM.delete(searchNewGpm.id);
				
				newGpm = new GPM(idGpm);
				newGpm.save();
			}
			Ebean.commitTransaction();	
		} 
		finally {
			Ebean.endTransaction();
		}
		return newGpm;
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
		Date date = Calendar.getInstance().getTime();
		String dateToString = date.toString();
		String year = dateToString.substring(dateToString.length() - 4);
		int mount = date.getMonth() + 1;
		String parameter = year + "-" + mount + "-" + date.getDate();
		
		return Ebean.createSqlQuery("SELECT gpm"
				+ " FROM Profile"
				+ " WHERE dateUpdated < '" + parameter + "'"
				+ " GROUP BY gpm"
				+ " ORDER BY max(dateUpdated)"
				+ " LIMIT :count;").setParameter("count", count).findList();
	}
}
