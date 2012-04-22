package models;

import java.io.IOException;
import java.util.List;

public interface GetData {
	public TempProfile getProfile(String id) throws IOException;
	public List <TempPost> getActivity(String id, long amountOf) throws IOException;

}
