package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Added_By_Admin")
@Entity
public class Added_By_Admin extends GenericModel {
	
	@Id
	@Required
	@JoinColumn(name="Id_GPM")
	@ManyToOne
    public GPM Id_GPM;

	@Id
	@Required
	@JoinColumn(name="Id_Group")
	@ManyToOne
    public Group_define Id_Group;

	@Required
	@Column(name="Position", nullable=false)
	public int Position;
    
    @Required
    @InFuture
	@Column(name="Day_add", nullable=false)
	public Date Day_add;

	@InFuture()
	@Column(name="Day_del")
	public Date Day_del;
	
	@Column(name="Comment")
	public String Comment;

    public Added_By_Admin(GPM Id_GPM, Group_define Id_Group, int Position, Date Day_add, Date Day_del, String Comment) {
        this.Id_GPM = Id_GPM;
        this.Id_Group = Id_Group;
        this.Position = Position;
        this.Day_add = Day_add;
        this.Day_del = Day_del;
        this.Comment = Comment;
        }

}
