package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_Link")
@Entity
public class Group_Link extends Model{//GenericModel {

    @Column(name="Post_weight", nullable=false)
    public int Post_weight;

    @Column(name="Profile_weight", nullable=false)
    public int Profile_weight;
    //@Id
    @Required
    @JoinColumn(name="Id_Group")
    @ManyToOne
    public Group_define Id_Group;

   // @Id
    @Required
    @JoinColumn(name="Id_Link")
    @ManyToOne
    public Link_dictionary Id_Link;
    

    public Group_Link(Group_define Id_Group, Link_dictionary Id_Link, int Post_weight, int Profile_weight) {
        this.Id_Group = Id_Group;
        this.Id_Link = Id_Link;
        this.Post_weight = Post_weight;
        this.Profile_weight = Profile_weight;
        }

}
