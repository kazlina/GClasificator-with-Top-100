package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.*;

import javax.persistence.*;

@Entity
@Table(name = "GroupWord", uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"groupDescr", "word"
		})
	})
public class GroupWord extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

	@Constraints.Required
	@JoinColumn(name = "groupDescr", nullable = false)
	@ManyToOne
	public Group group;

	@Constraints.Required
	@JoinColumn(name = "word", nullable = false)
	@ManyToOne
	public Word word;

	@Constraints.Required
	@Column(name = "postWeight", nullable = false)
	public float postWeight;

	@Constraints.Required
	@Column(name = "profileWeight", nullable = false)
	public float profileWeight;
/*
	public GroupWord(Group group, Word word, float postWeight, float profileWeight) {
		this.group = group;
		this.word = word;
		this.postWeight = postWeight;
		this.profileWeight = profileWeight;
	}
*/
	public static Model.Finder<Long, GroupWord> find = new Model.Finder<Long, GroupWord>(Long.class, GroupWord.class);

	public static List<GroupWord> all() {
		return find.all();
	}

	public static GroupWord groupWordById(Long Id) {
		return find.ref(Id);
	}

	public static void create(GroupWord element) {
		element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
