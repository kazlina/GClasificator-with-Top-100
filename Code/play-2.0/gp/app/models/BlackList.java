package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

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

	@Constraints.Required
    @Column(name = "reasonOfAddition")
    public String reasonOfAddition;
/*
	public String toString(){
		return this.gpm.id;
	}

	public BlackList(GPM gpm) {
		this.gpm = gpm;
        this.dateOfAddition = Calendar.getInstance().getTime();;
    }
*/
	public static Model.Finder<Long, BlackList> find = new Model.Finder<Long, BlackList>(Long.class, BlackList.class);

	public static List<BlackList> all() {
		return find.all();
	}

	public static BlackList blackListById(Long Id) {
		return find.ref(Id);
	}

	public static void create(BlackList element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
