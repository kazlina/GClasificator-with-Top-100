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
               assertThat(findGPM).isNotNull();

               NewGPM.delete(findGPM.id);
               
               NewGPM gpmDeleted = NewGPM.findByIdGpm(idGpm);
               assertThat(gpmDeleted).isNull();
           }
        });
    }

    @Test
    public void FindNotExistedNewGPM() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   NewGPM gpm = NewGPM.findByIdGpm("900915540970866628562");
               assertThat(gpm).isNull();
           }
        });
    }
 
    @Test
    public void NewGPMToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String idGpm = "111915540970866628562";
        	  NewGPM.add(idGpm);
              NewGPM findGPM = NewGPM.findByIdGpm(idGpm);
              assertThat(findGPM).isNotNull();
              
              String textGPM = findGPM.toString();
              assertThat(textGPM).isEqualTo(idGpm);

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
              
              assertThat(NewGPM.size()).isEqualTo(3);
              
              List<NewGPM> allGPM = NewGPM.get(NewGPM.size());
              assertThat(allGPM.size()).isEqualTo(3);
              assertThat(allGPM.get(0).nMentiens).isEqualTo(3);
              assertThat(allGPM.get(1).nMentiens).isEqualTo(2);
              assertThat(allGPM.get(2).nMentiens).isEqualTo(1);
              
              for (int i = 0; i < allGPM.size(); i ++)
            	  NewGPM.delete(allGPM.get(i).id);
              
              assertThat(NewGPM.size()).isEqualTo(0);
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
               assertThat(gpm).isNotNull();
               
               NewGPM findGPM = NewGPM.findById(gpm.id);
               assertThat(findGPM).isNotNull();

               NewGPM.delete(findGPM.id);
           }
        });
    }
}
 