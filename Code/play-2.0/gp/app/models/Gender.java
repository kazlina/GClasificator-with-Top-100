package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

@Entity
@Table(name = "Gender")
public class Gender extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.Required
    @Column(name = "value", length = 10, unique = true, nullable = false)
    public String value;

    public static Model.Finder<Long, Gender> find = new Model.Finder<Long, Gender>(Long.class, Gender.class);

	public static List<Gender> all() {
		return find.all();
	}

	public static Gender genderById(Long Id) {
		return find.ref(Id);
	}

	public static void create(Gender element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
