package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="New_Id")
@Entity
public class New_Id extends GenericModel {

	@Id
	@Required
	@MinSize(value=21)
	@MaxSize(value=21)
	@Match(value="^\\d{21}$", message="Incorrect identifer")
    @Column(name="Id", length=21)
    public String Id;

	@Required
	@Column(name="nMentiens", nullable=false)
    public int nMentiens;
    
    public New_Id(String Id, int Severity) {
        this.Id = Id;
        this.nMentiens = Severity;
        }

}
