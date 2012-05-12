package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "BlackList")
public class BlackList extends Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@JoinColumn(name = "Id", unique = true, nullable = false)
    @OneToOne
    public GPM gpm;

	@Constraints.Required
    @Column(name = "dateOfAddition", nullable = false)
    public Date dateOfAddition;

    @Column(name = "reasonOfAddition")
    public String reasonOfAddition;

	public String toString(){
		return this.gpm.idGpm;
	}

	public BlackList(Long gpmId, String reasonOfAddition) {
		GPM gpm = GPM.findById(gpmId);
		if (gpm == null)
			return;
		
		this.gpm = gpm;
		this.reasonOfAddition = reasonOfAddition;
    }

	private static Model.Finder<Long, BlackList> find = new Model.Finder<Long, BlackList>(Long.class, BlackList.class);

	public static List<BlackList> all() {
		return find.all();
	}

	public static BlackList findById(Long Id) {
		return find.ref(Id);
	}
	
	public static BlackList findByGpmId(Long Id) {
		return find.where().eq("gpm", GPM.findById(Id)).findUnique();
	}

	public static void add(BlackList element) {
		element.dateOfAddition = Calendar.getInstance().getTime();
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
