package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_Word")
@Entity
public class Group_Word extends Model{//GenericModel {

	//@Id
	@JoinColumn(name="Id_Group")
    @ManyToOne
    @Required
    public Group_define Id_Group;

    //@Id
	@JoinColumn(name="Id_Word")
    @ManyToOne
    @Required
    public Word_dictionary Id_Word;
	
	@Column(name="Post_weight", nullable=false)
	public int Post_weight;

	@Column(name="Profile_weight", nullable=false)
	public int Profile_weight;

    public Group_Word(Group_define Id_Group, Word_dictionary Id_word, int Post_weight, int Profile_weight) {
        this.Id_Group = Id_Group;
        this.Id_Word = Id_word;
        this.Post_weight = Post_weight;
        this.Profile_weight = Profile_weight;
        }

}
