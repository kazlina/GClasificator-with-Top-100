package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "NewGPM")
public class NewGPM extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
	public String idGpm;

	@Constraints.Required
	@Column(name = "nMentiens", nullable = false)
	public int nMentiens;

	private NewGPM(String idGpm) {
        this.idGpm = idGpm;
	}
	
	private static Model.Finder<Long, NewGPM> find = new Model.Finder<Long, NewGPM>(Long.class, NewGPM.class);

	public static List<NewGPM> all() {
		return find.where().orderBy("nMentiens desc").findList();
	}
	
	public static NewGPM findById(Long Id) {
		return find.ref(Id);
	}
	
	public static NewGPM findByIdGpm(String Id) {
		return find.where().eq("idGpm", Id).findUnique();
	}

	public static void add(String idGpm) {
		Ebean.beginTransaction();
		
		NewGPM searchNewGpm = findByIdGpm(idGpm);
		if (searchNewGpm != null) {
			searchNewGpm.nMentiens ++;
			searchNewGpm.save();
		}
		else {
			GPM searchGpm = GPM.findByIdGpm(idGpm);
			if (searchGpm == null) {
				NewGPM newGpm = new NewGPM(idGpm);
				newGpm.nMentiens = 1;
				newGpm.save();
			}
		}
		
		Ebean.commitTransaction();
	}
	
	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
