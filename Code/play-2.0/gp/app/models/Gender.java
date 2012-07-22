package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Gender")
public class Gender extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.MaxLength(10)
    @Constraints.Required
    @Constraints.Pattern("[A-Za-zА-Яа-я]+")
    @Column(name = "value", length = 10, unique = true, nullable = false)
    public String value;

    public String toString(){
		return this.value;
	}

	private Gender(String value){
		this.value = value;
	}

    private static Model.Finder<Long, Gender> find = new Model.Finder<Long, Gender>(Long.class, Gender.class);

	public static List<Gender> all() {
		return find.all();
	}

	public static Gender findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}

	public static Gender findByValue(String element) {
		return find.where().eq("value", element).findUnique();
	}

	public static void add(String value) {
		Gender element = new Gender(value);
		element.save();
	}

	public static void delete(Long id) {
		Gender gend = findById(id);
		if (gend == null)
			return;
		
		List<SqlRow> result = Ebean.createSqlQuery("SELECT id"
										+ " FROM profile"
										+ " WHERE gender = :idGender;").setParameter("idGender", id).findList();
		for (SqlRow res: result) {
			Profile prof = Profile.findById(res.getLong("id"));
			if (prof != null) {
				prof.gender = null;
				prof.update();
			}
		}
		
		gend.delete();
    }
	
	public static void updateGender(Long idGender, Gender gender) {
		System.out.println(gender.value);
		Gender findGender = Gender.findById(idGender);
		if (findGender == null)
			return;
	
		findGender.value = gender.value;
		findGender.update();
	}
}
