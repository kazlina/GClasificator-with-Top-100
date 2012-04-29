import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class WordTest {
    
	private Word word;
	private String text;
	
    private Word create(String value) {
        Word word = new Word(value);
        Word.create(word);
        return word;
    }

    @Test
    public void CreateAndDeleteWord() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
               word = create(text = "Peter");
               assertThat(word.word).isEqualTo(text);
               
               Word findWord = Word.findByWord(text);
               assertThat(findWord.word == text);

               Word.delete(findWord.id);
               Word wordDeleted = Word.findByWord(text);
               assertThat(wordDeleted == null);
           }
        });
    }

   @Test
   public void FindNotExistedWord() {
        running(fakeApplication(), new Runnable() {
           public void run() {
               Word word = create("Marina");             
               
               Word findWord = Word.findByWord("Yuri");
               assertThat(findWord == null);
               
               Word.delete(word.id);
           }
        });
    }
 
   @Test
   public void WordtoString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              word = create(text = "Peter");
              
              String textWord = word.toString();
              assertThat(textWord == text);
              
              Word.delete(word.id);
          }
       });
   }
   
   @Test
   public void GetAllWords() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create("Julia");
              create("Nasty");
              create("Kate");
              
              List<Word> allWord = Word.all();
              assertThat(allWord.size() == 3);
              
              for (int i = 0; i < allWord.size(); i ++)
            	  Word.delete(allWord.get(i).id);
              
              allWord = Word.all();
              assertThat(allWord.size() == 0);
          }
       });
   }
   
   @Test
   public void FindAndDeleteByWord() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
              create(text = "Maximus");
              
              word = Word.findByWord(text);
              assertThat(word != null);
              
              Word.delete(word);
              
              word = Word.findByWord(text);
              assertThat(word == null);
          }
       });
   }
}
 