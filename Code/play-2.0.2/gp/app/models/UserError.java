package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Ebean;
import play.db.ebean.*;

@Entity
@Table(name = "UserError")
public class UserError extends Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Column(name = "idGpm", length = 21, unique = true, nullable = false)
	public String idGpm;

	@Column(name = "dateOfAddition", nullable = false)
    public Date dateOfAddition;

    @Column(name = "reasonOfAddition")
    public String reasonOfAddition;

	public String toString() {
		return this.idGpm;
	}

	private UserError(String gpmId, String reasonOfAddition) {
		this.idGpm = gpmId;
		this.reasonOfAddition = reasonOfAddition;
    }

	private static Model.Finder<Long, UserError> find = new Model.Finder<Long, UserError>(Long.class, UserError.class);

	public static List<UserError> all() {
		return find.all();
	}

	public static UserError findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}
	
	public static UserError findByIdGpm(String Id) {
		return find.where().eq("idGpm", Id).findUnique();
	}

	public static void add(String idGpm, String reasonOfAddition) {
		Ebean.beginTransaction();
		try {
			GPM findGpm = GPM.findByIdGpm(idGpm);
			if (findGpm != null)
				GPM.delete(findGpm.id);
			else {
				NewGPM findNewGpm = NewGPM.findByIdGpm(idGpm);
				if (findNewGpm != null)
					NewGPM.delete(findNewGpm.id);
			}
		
			UserError element = new UserError(idGpm, reasonOfAddition);
			element.dateOfAddition = Calendar.getInstance().getTime();
			element.save();
			
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
