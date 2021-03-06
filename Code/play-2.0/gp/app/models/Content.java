package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;
import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "Content")
public class Content extends Model {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Constraints.MaxLength(5)
    @Constraints.Required
    @Constraints.Pattern("[a-z]+")
    @Column(name = "kind", length = 5, unique = true, nullable = false)
    public String kind;

    public String toString(){
		return this.kind;
	}

	private Content(String type){
		this.kind = type;
	}

    private static Model.Finder<Long, Content> find = new Model.Finder<Long, Content>(Long.class, Content.class);

	public static List<Content> all() {
		return find.all();
	}

	public static Content findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}

	public static Content findByKind(String element) {
		return find.where().eq("kind", element).findUnique();
	}

	public static void add(String type) {
		Content element = new Content(type);
		element.save();
	}

	public static void delete(Long id) {
		SqlRow result = Ebean.createSqlQuery("SELECT COUNT(*) as count"
									+ " FROM post"
									+ " WHERE kindContent = :id;").setParameter("id", id).findUnique();
		if (result.getLong("count") == 0)
			find.ref(id).delete();
    }

    public static void updateContent(Long idContent, Content content) {
	System.out.println(content.kind);
	Content findContent = Content.findById(idContent);
	if (findContent == null)
		return;

	findContent.kind = content.kind;
	findContent.update();
	}
	
	
}
