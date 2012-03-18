package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Pets extends Model {

    @ManyToOne
    public GPM Id_GPM;

    @ManyToOne
    public Group Id_Group;

    public int Position;
    public Date Kill_Day;


    public Pets(GPM Id_GPM, Group Id_Group, int Position, Date Kill_Day) {
        this.Id_GPM = Id_GPM;
        this.Id_Group = Id_Group;
        this.Position = Position;
        this.Kill_Day = Kill_Day;
        }

}
