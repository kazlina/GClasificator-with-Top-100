package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Links extends GenericModel {

	@Id
	@JoinColumn(name="Id_GPM")
    @ManyToOne
    public Statistics Id_GPM;
    
    @Id
	@JoinColumn(name="Id_Link")
    @ManyToOne
    public Links_Def Id_Link;
    
    @Required
    public int Count;
    
    public Links(Statistics Id_GPM, Links_Def Id_Link, int Count){
        this.Id_GPM = Id_GPM;
        this.Id_Link = Id_Link;
        this.Count = Count;
        }

}
