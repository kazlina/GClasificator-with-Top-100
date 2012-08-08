package models;

import java.io.IOException;
import java.util.List;
import play.libs.*;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

public interface GetData {
	public TempProfile getProfile(String id) throws IOException;
	public List <TempPost> getActivity(String id, long amountOf) throws IOException;

}
