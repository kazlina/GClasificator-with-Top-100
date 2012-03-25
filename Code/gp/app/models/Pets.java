package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Pets extends GenericModel {
	
	@Id
	@JoinColumn(name="Id_GPM")
	@ManyToOne
    public GPM Id_GPM;

	@Id
	@JoinColumn(name="Id_Group")
	@ManyToOne
    public Group_def Id_Group;

	@Required
    public int Position;
    
    public Date Kill_Day;


    public Pets(GPM Id_GPM, Group_def Id_Group, int Position, Date Kill_Day) {
        this.Id_GPM = Id_GPM;
        this.Id_Group = Id_Group;
        this.Position = Position;
        this.Kill_Day = Kill_Day;
        }

}
