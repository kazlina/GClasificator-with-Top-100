package models;

import java.io.IOException;
import java.util.Vector;

public interface GetData {
	public TempProfile GetProfile(String id) throws IOException;
	public Vector <Post> GetActivity(String id) throws IOException;

}
