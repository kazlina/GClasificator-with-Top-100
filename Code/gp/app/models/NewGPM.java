package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="New_Ids")
@Entity
public class NewGPM extends GenericModel {

	@Id
	@Required
	@MinSize(value=21)
	@MaxSize(value=21)
	@Match(value="^\\d{21}$", message="Incorrect identifer")
    @Column(name="Id", length=21)
    public String id;

	@Required
	@Column(name="nMentiens", nullable=false)
    public int nMentiens;
    
	public NewGPM(){};
	
    public NewGPM(String id, int severity) {
        this.id = id;
        this.nMentiens = severity;
        }

}
