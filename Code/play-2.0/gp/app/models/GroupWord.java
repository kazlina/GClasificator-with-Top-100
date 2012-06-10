package models;

import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;
import play.data.validation.*;

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

	@JoinColumn(name = "groupDescr", nullable = false)
	@ManyToOne
	public Group group;

	@Constraints.Required
	@JoinColumn(name = "word", nullable = false)
	@ManyToOne
	public Word word;

	@Constraints.Min(0)
	@Constraints.Max(1)
	@Constraints.Required
	@Column(name = "postWeight", nullable = false)
	public float postWeight;

	@Constraints.Min(0)
	@Constraints.Max(1)
	@Constraints.Required
	@Column(name = "profileWeight", nullable = false)
	public float profileWeight;

	public GroupWord(Group group, Word word, float postWeight, float profileWeight) {
		this.group = group;
		this.word = word;
		this.postWeight = postWeight;
		this.profileWeight = profileWeight;
	}
	
	public GroupWord(Long groupId, String word, float postWeight, float profileWeight) {
		Word findWord = Word.findByWord(word);
		Group findGroup = Group.findById(groupId);
		if (findWord != null && findGroup != null) {
			this.group = findGroup;
			this.word = findWord;
			this.postWeight = postWeight;
			this.profileWeight = profileWeight;
		}
	}

	private static Model.Finder<Long, GroupWord> find = new Model.Finder<Long, GroupWord>(Long.class, GroupWord.class);

	public static List<GroupWord> findByGroup(Long id) {
		return Group.findById(id).words;
	}
	
	public static List<GroupWord> findByWord(Long id) {
		return Word.findById(id).groupWords;
	}

	public static GroupWord findById(Long Id) {
		return find.where().eq("id", Id).findUnique();
	}

	public static void add(GroupWord element) {
		GroupWord findGroupWord = find.where().eq("group", element.group).eq("word", element.word).findUnique();
		if (findGroupWord == null)
			element.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
    }
}
