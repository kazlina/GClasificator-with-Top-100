package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Group_Link extends GenericModel {

    @Id
	@JoinColumn(name="Id_Group")
    @ManyToOne
    public Group_def Id_Group;

    @Id
	@JoinColumn(name="Id_Link")
    @ManyToOne
    public Links_Def Id_Link;
	
	@Required
    public float Weight;


    public Group_Link(Group_def Id_Group, Links_Def Id_Link, float Weight) {
        this.Id_Group = Id_Group;
        this.Id_Link = Id_Link;
        this.Weight = Weight;
        }

}
