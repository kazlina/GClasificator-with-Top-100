package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_Word")
@Entity
@IdClass(KeyGroupWord.class)
public class Group_Word extends GenericModel {

	@Id
	@Required
	@JoinColumn(name="Id_Group")
	@ManyToOne
	public Group_define Id_Group;

	@Id
	@Required
	@JoinColumn(name="Id_Word")
	@ManyToOne
	public Word_dictionary Id_Word;
	
	@Required
	@Range(min=0, max=1)
	@Column(name="Post_weight", nullable=false)
	public float Post_weight;

	@Required
	@Range(min=0, max=1)
	@Column(name="Profile_weight", nullable=false)
	public float Profile_weight;

 	public Group_Word(Group_define Id_Group, Word_dictionary Id_word, float Post_weight, float Profile_weight) {
		this.Id_Group = Id_Group;
		this.Id_Word = Id_word;
		this.Post_weight = Post_weight;
		this.Profile_weight = Profile_weight;
	}
}
