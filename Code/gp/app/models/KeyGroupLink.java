package models;

import java.util.*;
import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import play.db.jpa.*;
import play.data.validation.*;

@Embeddable
public class KeyGroupLink implements Serializable {

	@Required
	public Group_define Id_Group;
	
	@Required
        public Link_dictionary Id_Link;
	 }
