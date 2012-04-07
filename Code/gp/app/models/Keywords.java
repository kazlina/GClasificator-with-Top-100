package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.io.Serializable;
import play.data.validation.*;

@Entity
public class Keywords extends GenericModel {

	@Id
	@JoinColumn(name="Id_GPM")
    @ManyToOne
    public Statistics Id_GPM;
    
    @Id
	@JoinColumn(name="Id_Word")
    @ManyToOne
    public Keywords_Def Id_Word;
    
    @Required
    public int Count;
    
    public Keywords(Statistics Id_GPM, Keywords_Def Id_Word, int Count){
        this.Id_GPM = Id_GPM;
        this.Id_Word = Id_Word;
        this.Count = Count;
        }
}
