package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Added_By_Admin")
@Entity
public class AddedByAdmin extends GenericModel {
	
	@Id
	@Required
	@JoinColumn(name="Id_GPM")
	@ManyToOne
    public GPM gpm;

	@Id
	@Required
	@JoinColumn(name="Id_Group")
	@ManyToOne
    public Group group;

	@Required
	@Column(name="Position", nullable=false)
	public int position;
    
    @Required
    @InFuture
	@Column(name="Day_add", nullable=false)
	public Date dateOfAddition;

	@InFuture()
	@Column(name="Day_del")
	public Date dateOfRemoval;
	
	@Column(name="Comment")
	public String comment;
	
	public AddedByAdmin(){};

    public AddedByAdmin(GPM gpm, Group group, int position, Date dateOfAddition, Date dateOfRemoval, String comment) {
        this.gpm = gpm;
        this.group = group;
        this.position = position;
        this.dateOfAddition = dateOfAddition;
        this.dateOfRemoval = dateOfRemoval;
        this.comment = comment;
        }

}
