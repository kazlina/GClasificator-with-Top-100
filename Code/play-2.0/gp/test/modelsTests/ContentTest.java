import org.junit.*;

import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class ContentTest {
    
	private Content content;
	private String text;
	
    private Content create(String value) {
    	content = new Content(value);
    	Content.create(content);
        return content;
    }

    @Test
    public void CreateAndDeleteContent() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   content = create(text = "link");
               assertThat(content.kind).isEqualTo(text);
               
               Content findContent = Content.findByKind(text);
               assertThat(findContent.kind == text);

               Content.delete(findContent.id);
               Content contentDeleted = Content.findByKind(text);
               assertThat(contentDeleted == null);
           }
        });
    }

    @Test
    public void FindNotExistedContent() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   content = Content.findByKind("video");
               assertThat(content == null);
           }
        });
    }
 
    @Test
    public void ContentToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  content = create(text = "photo");
              
              String contentText = content.toString();
              assertThat(contentText == text);

              Content.delete(content.id);
          }
       });
    }
   
    @Test
    public void GetAllContents() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create("link");
              create("text");
              create("audio");
              
              List<Content> allContent = Content.all();
              assertThat(allContent.size() == 3);
              
              for (int i = 0; i < allContent.size(); i ++)
            	  Content.delete(allContent.get(i).id);
              
              allContent = Content.all();
              assertThat(allContent.size() == 0);
          }
       });
    }
    
    @Test
    public void FindContentById() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
        	   content = create("link");
               
        	   Content findContent = Content.findById(content.id);
               assertThat(findContent != null);

               Content.delete(findContent.id);
           }
        });
    }
}
 