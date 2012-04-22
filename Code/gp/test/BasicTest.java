import org.junit.*;
import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
 

@Test
public void addProfileToGpm() throws InterruptedException {
    // Create a new GPM and save it
    GPM gpm = new GPM("107332580040286178426").save();
   // Create a new Profile
    new Profile(gpm, "yulia", "yulia.jpg", "female","pop","pop",900).save();
    Thread.sleep(1000);
    new Profile(gpm, "yulia", "yulia.jpg", "female","cats","cats",1000).save();
        
    List <Profile> profiles = Profile.find("byId_GPM", "107332580040286178426").fetch();
    
    assertEquals(2, profiles.size());
    Profile firstProfile = profiles.get(0);
    assertNotNull(firstProfile);
    assertEquals("yulia", firstProfile.name);
    assertEquals("yulia.jpg", firstProfile.image);
    assertNotNull(firstProfile.date);    
}
    @Test
    public void addPostToGpm(){
        // Create a new GPM and save it
        GPM gpm = GPM.findById("107332580040286178426");
       // Create a new Profile
        /*new Post(gpm, "yulia", "yulia.jpg", true, "pop","pop","pop",900).save();
        Thread.sleep(1000);
        new Post(gpm, "yulia", "yulia.jpg", true, "cats","cats","cats",1000).save();
            
        List <Posts> posts = Posts.find("byId_GPM", "107332580040286178426").fetch();
        
        assertEquals(2, posts.size());
        Profile firstPost = posts.get(0);
        assertNotNull(firstPost);
        assertEquals("yulia", firstPost.Name);
        assertEquals("yulia.jpg", firstPost.Image_URL);
        assertNotNull(firstPost.date); */   
    }
	@Test
	public void addPlusOneToNewGpm(){
	new NewGPM("107332580040286178426",1).save();
	NewGPM newId = NewGPM.findById("107332580040286178426");
	newId.nMentiens = newId.nMentiens+1;
	newId.save();
	assertEquals(2, newId.nMentiens);
}
}
