import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class SynonymTest {
    
	private Word word;
	private Synonym synonym;
	private String textWord;
	private String textSynonym;
	
    private Word createWord(String value) {
        word = new Word(value);
        Word.create(word);
        return word;
    }
    
    private Synonym createSynonymByWord(String word, String value) {
    	synonym = new Synonym(value);
    	Synonym.create(word, synonym);
        return synonym;
    }
    
    private Synonym createSynonymByIdWord(Long wordId, String value) {
    	synonym = new Synonym(value);
    	Synonym.create(wordId, synonym);
        return synonym;
    }

   @Test
    public void CreateAndDeleteSynonym() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
               createWord(textWord = "Maximus");
               word = Word.findByWord(textWord);
               assertThat(word.word != null);

               synonym = createSynonymByWord(textWord, textSynonym = "Lord of world");
               assertThat(synonym.synonym).isEqualTo(textSynonym);
               
               Synonym findSynonym = Synonym.findById(synonym.id);
               assertThat(findSynonym.synonym == textSynonym);
               
               Synonym.delete(synonym.id);
               Synonym synonymDeleted = Synonym.findById(findSynonym.id);
               assertThat(synonymDeleted == null);
               
               Word.delete(word.id);
           }
        });
    }

   @Test
   public void FindNotExistedSynonym() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   List<Synonym> listSynonym = Synonym.findBySynonim("MaxLord");
               assertThat(listSynonym.size() == 0);
           }
        });
    }
 
   @Test
   public void synonymToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  createWord(textWord = "Maximus");
              word = Word.findByWord(textWord);
              assertThat(word.word != null);
              
              synonym = createSynonymByIdWord(word.id, textSynonym = "Lord of world");
              String text = synonym.toString();
              assertThat(text == textWord);
              
              Synonym.delete(synonym.id);
              Word.delete(word.id);
          }
       });
   }
   
   @Test
   public void GetAllSynonymsForWord() {
   	running(fakeApplication(), new Runnable() {
          public void run() {
              createWord(textWord = "Mari");
              word = Word.findByWord(textWord);
              assertThat(word.word != null);

              createSynonymByWord(textWord, "Marinad");
              createSynonymByWord(textWord, "Student");
              createSynonymByWord(textWord, "Murder");
              
              List<Synonym> synonyms = Synonym.findByWordId(word.id);
              assertThat(synonyms.size() == 3);
              
              for (int i = 0; i < synonyms.size(); i ++)
            	  Synonym.delete(synonyms.get(i).id);
              
              synonyms = Synonym.findByWordId(word.id);
              assertThat(synonyms.size() == 0);
              
              Word.delete(word.id);
          }
       });
   }
   
   @Test
   public void GetSynonymsForSomeWord() {
   	running(fakeApplication(), new Runnable() {
          public void run() {
        	  String textFirstWord = "Mari";
        	  createWord(textFirstWord);
              Word firstWord = Word.findByWord(textFirstWord);
              assertThat(firstWord.word != null);
              
              String textSecondWord = "Killer";
              createWord(textSecondWord);
              Word secondWord = Word.findByWord(textSecondWord);
              assertThat(secondWord.word != null);

              synonym = createSynonymByWord(textFirstWord, "Student");
              createSynonymByWord(textFirstWord, "Murder");
              
              createSynonymByIdWord(secondWord.id, "Assasin");
              createSynonymByIdWord(secondWord.id, "Slayer");
              createSynonymByIdWord(secondWord.id, "Murder");
              
              List<Synonym> synonyms = Synonym.findBySynonim("Murder");
              assertThat(synonyms.size() == 2);
              assertThat(synonyms.get(0).word.word == textFirstWord || synonyms.get(1).word.word == textFirstWord);
              assertThat(synonyms.get(0).word.word == textSecondWord || synonyms.get(1).word.word == textSecondWord);
              
              for (int i = 0; i < synonyms.size(); i ++)
            	  Synonym.delete(synonyms.get(i).id);
              
              List<Synonym> findSynonyms = Synonym.findBySynonim("Murder");
              assertThat(findSynonyms.size() == 0);
              
              List<Synonym> firstWordSynonyms = Synonym.findByWordId(firstWord.id);
              assertThat(firstWordSynonyms.size() == 1);
              for (int i = 0; i < firstWordSynonyms.size(); i ++)
            	  Synonym.delete(firstWordSynonyms.get(i).id);
              
              List<Synonym> secondWordSynonyms = Synonym.findByWordId(secondWord.id);
              assertThat(secondWordSynonyms.size() == 2);
              for (int i = 0; i < secondWordSynonyms.size(); i ++)
            	  Synonym.delete(secondWordSynonyms.get(i).id);
              
              Word.delete(firstWord.id);
              Word.delete(secondWord.id);
          }
       });
   }
}
 