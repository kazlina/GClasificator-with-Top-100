package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Group_Word extends Model {

    @ManyToOne
    public Group Id_Group;

    @ManyToOne
    public Keywords_Def Id_Def;

    public double Weight;


    public Group_Word(Group Id_Group, Keywords_Def Id_Def, double Weight) {
        this.Id_Group = Id_Group;
        this.Id_Def = Id_Def;
        this.Weight = Weight;
        }

}
