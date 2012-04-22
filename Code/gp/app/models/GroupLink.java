package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.data.validation.*;

@Table(name="Group_Links")
@Entity
@IdClass(KeyGroupLink.class)
public class GroupLink extends GenericModel {

    @Id
    @Required
    @JoinColumn(name="Id_Group")
    @ManyToOne
    public Group group;

    @Id
    @Required
    @JoinColumn(name="Id_Link")
    @ManyToOne
    public Link link;
	
	@Required
	@Range(min=0, max=1)
	@Column(name="Post_weight", nullable=false)
	public float postWeight;

	@Required
	@Range(min=0, max=1)
	@Column(name="Profile_weight", nullable=false)
	public float profileWeight;
	
	public GroupLink(){};

    public GroupLink(Group group, Link link, float postWeight, float profileWeight) {
        this.group = group;
        this.link = link;
        this.postWeight = postWeight;
        this.profileWeight = profileWeight;
        }

}
