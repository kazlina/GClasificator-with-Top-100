import org.junit.*;
import java.util.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

import com.avaje.ebean.SqlRow;

public class GPMTest {
 /*   
	@Test
    public void getGPMForUpdatePost() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   int count = 2;
        	   List<SqlRow> gpmForGet = GPM.getIdGpmByLastPosts(count);
        	   assertThat(gpmForGet.size()).isEqualTo(count);
        	   
        	   Long gpmId = gpmForGet.get(0).getLong("gpm");
        	   assertThat(gpmId).isEqualTo(2);
        	   GPM man = GPM.findById(gpmId);
        	   assertThat(man.idGpm).isEqualTo("123456789012345678901");
        	   
        	   gpmId = gpmForGet.get(1).getLong("gpm");
        	   assertThat(gpmId).isEqualTo(3);
        	   man = GPM.findById(gpmId);
        	   assertThat(man.idGpm).isEqualTo("123456789012345678945");
           }
        });
    }
	
	@Test
    public void getGPMForUpdatePrifile() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   int count = 2;
        	   List<SqlRow> gpmForGet = GPM.getIdGpmByLastProfile(count);
        	   assertThat(gpmForGet.size()).isEqualTo(count);
        	   
        	   Long gpmId = gpmForGet.get(0).getLong("gpm");
        	   assertThat(gpmId).isEqualTo(1);
        	   GPM man = GPM.findById(gpmId);
        	   assertThat(man.idGpm).isEqualTo("12345678901234567890");
        	   
        	   gpmId = gpmForGet.get(1).getLong("gpm");
        	   assertThat(gpmId).isEqualTo(2);
        	   man = GPM.findById(gpmId);
        	   assertThat(man.idGpm).isEqualTo("123456789012345678901");
           }
        });
    }
	
	/*@Test
    public void CreateAndDeleteGPM() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String idGpm = "100915540970866628562";
        	   GPM.add(idGpm);
               GPM findGPM = GPM.findByIdGpm(idGpm);
               assertThat(findGPM.idGpm == idGpm);

               GPM.delete(findGPM.id);
               
               GPM gpmDeleted = GPM.findByIdGpm(idGpm);
               assertThat(findGPM == null);
           }
        });
    }

    @Test
    public void FindNotExistedGPM() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   GPM gpm = GPM.findByIdGpm("100915540970866628562");
               assertThat(gpm == null);
           }
        });
    }
 
    @Test
    public void GPMtoString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String idGpm = "100915540970866628562";
        	  GPM.add(idGpm);
              GPM findGPM = GPM.findByIdGpm(idGpm);
              assertThat(findGPM.idGpm == idGpm);
              
              String textGPM = findGPM.toString();
              assertThat(textGPM == idGpm);

              GPM.delete(findGPM.id);
         }
	   });
    }
   
    @Test
    public void FindGPMById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String idGpm = "100915540970866628562";
        	   GPM.add(idGpm);
               GPM gpm = GPM.findByIdGpm(idGpm);
               assertThat(gpm != null);
               
               GPM findGPM = GPM.findById(gpm.id);
               assertThat(findGPM != null);

               GPM.delete(findGPM.id);
           }
        });
    }*/
}
 