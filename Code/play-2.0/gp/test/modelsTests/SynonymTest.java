import org.junit.*;
import java.util.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

public class SynonymTest {
    
	private Word createWord(String value) {
    	Word word = new Word(value);
        Word.add(word);
        return word;
    }
    
    private Synonym createSynonymByIdWord(Long wordId, String value) {
    	Synonym synonym = new Synonym(value);
    	Synonym.add(wordId, synonym);
        return synonym;
    }

   @Test
    public void CreateAndDeleteSynonym() {
    	running(fakeApplication(), new Runnable() {
           public void run() {
               String textWord = "Max";
        	   createWord(textWord);
               Word word = Word.findByWord(textWord);
               assertThat(word != null);

               String textSynonym = "Maximus";
               Synonym synonym = createSynonymByIdWord(word.id, textSynonym);
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
   public void SynonymToString() {
	   running(fakeApplication(), new Runnable() {
          public void run() {
        	  String textWord = "Maximus";
        	  createWord(textWord);
              Word word = Word.findByWord(textWord);
              assertThat(word.word != null);
              
              String textSynonym = "Lord of the world";
              Synonym synonym = createSynonymByIdWord(word.id, textSynonym);
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
        	  String textWord = "Mari";
        	  createWord(textWord);
        	  Word word = Word.findByWord(textWord);
              assertThat(word != null);

              createSynonymByIdWord(word.id, "Marinad");
              createSynonymByIdWord(word.id, "Student");
              createSynonymByIdWord(word.id, "Murder");
              
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

              Synonym synonym = createSynonymByIdWord(firstWord.id, "Student");
              createSynonymByIdWord(firstWord.id, "Murder");
              
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
 