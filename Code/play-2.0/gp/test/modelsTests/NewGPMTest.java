import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class NewGPMTest {
    
	@Test
    public void CreateAndDeleteNewGPM() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String idGpm = "100915540970866628562";
        	   NewGPM.add(idGpm);
               NewGPM findGPM = NewGPM.findByIdGpm(idGpm);
               assertThat(findGPM.idGpm == idGpm);

               NewGPM.delete(findGPM.id);
               
               NewGPM gpmDeleted = NewGPM.findByIdGpm(idGpm);
               assertThat(findGPM == null);
           }
        });
    }

    @Test
    public void FindNotExistedNewGPM() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   NewGPM gpm = NewGPM.findByIdGpm("100915540970866628562");
               assertThat(gpm == null);
           }
        });
    }
 
    @Test
    public void NewGPMtoString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String idGpm = "100915540970866628562";
        	  NewGPM.add(idGpm);
              NewGPM findGPM = NewGPM.findByIdGpm(idGpm);
              assertThat(findGPM.idGpm == idGpm);
              
              String textGPM = findGPM.toString();
              assertThat(textGPM == idGpm);

              NewGPM.delete(findGPM.id);
         }
	   });
    }
   
    @Test
    public void GetAllNewGPM() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              NewGPM.add("100915540970866628562");
              
              NewGPM.add("100915540970868628562");
              NewGPM.add("100915540970868628562");
              NewGPM.add("100915540970868628562");
              
              NewGPM.add("100915540970867628562");
              NewGPM.add("100915540970867628562");
              
              List<NewGPM> allGPM = NewGPM.all();
              assertThat(allGPM.size() == 3);
              assertThat(allGPM.get(0).nMentiens == 3);
              assertThat(allGPM.get(1).nMentiens == 2);
              assertThat(allGPM.get(2).nMentiens == 1);
              
              for (int i = 0; i < allGPM.size(); i ++)
            	  NewGPM.delete(allGPM.get(i).id);
              
              allGPM = NewGPM.all();
              assertThat(allGPM.size() == 0);
          }
       });
    }
   
    @Test
    public void FindNewGPMById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   String idGpm = "100915540970866628562";
        	   NewGPM.add(idGpm);
               NewGPM gpm = NewGPM.findByIdGpm(idGpm);
               assertThat(gpm != null);
               
               NewGPM findGPM = NewGPM.findById(gpm.id);
               assertThat(findGPM != null);

               NewGPM.delete(findGPM.id);
           }
        });
    }
}
 