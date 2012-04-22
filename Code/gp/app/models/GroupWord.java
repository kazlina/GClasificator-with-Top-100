package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_Words")
@Entity
@IdClass(KeyGroupWord.class)
public class GroupWord extends GenericModel {

	@Id
	@Required
	@JoinColumn(name="Id_Group")
	@ManyToOne
	public Group group;

	@Id
	@Required
	@JoinColumn(name="Id_Word")
	@ManyToOne
	public Word word;
	
	@Required
	@Range(min=0, max=1)
	@Column(name="Post_weight", nullable=false)
	public float postWeight;

	@Required
	@Range(min=0, max=1)
	@Column(name="Profile_weight", nullable=false)
	public float profileWeight;
	
	public GroupWord(){};

 	public GroupWord(Group group, Word word, float postWeight, float profileWeight) {
		this.group = group;
		this.word = word;
		this.postWeight = postWeight;
		this.profileWeight = profileWeight;
	}
}
