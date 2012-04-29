package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name="NewGPM")
public class NewGPM extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
	@Column(name = "id_gpm", length = 21, unique = true, nullable = false)
	public String id_gpm;

	@Constraints.Required
	@Column(name = "nMentiens", nullable = false)
	public int nMentiens;
/*
	public NewGPM(String id, int severity) {
        this.id = id;
        this.nMentiens = severity;
        }
*/
	public static Model.Finder<Long, NewGPM> find = new Model.Finder<Long, NewGPM>(Long.class, NewGPM.class);

	public static List<NewGPM> all() {
		return find.all();
	}

	public static NewGPM newGPMById(Long Id) {
		return find.ref(Id);
	}

	public static void create(NewGPM element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
