package models;

import java.io.IOException;
import java.util.List;

public interface GetData {
	public TempProfile GetProfile(String id) throws IOException;
	public List <Post> GetActivity(String id, Long amountOf) throws IOException;

}
