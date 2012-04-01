package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Group_Word extends GenericModel {

	@Id
	@JoinColumn(name="Id_Group")
    @ManyToOne
    public Group_def Id_Group;

    @Id
	@JoinColumn(name="Id_Word")
    @ManyToOne
    public Keywords_Def Id_Word;
	
	@Required
    public float Weight;


    public Group_Word(Group_def Id_Group, Keywords_Def Id_Word, float Weight) {
        this.Id_Group = Id_Group;
        this.Id_Word = Id_Word;
        this.Weight = Weight;
        }

}
