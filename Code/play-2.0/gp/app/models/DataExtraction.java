package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
//import java.util.Locale;

public class DataExtraction {

    public int newGPM(String id) {
	GPM gpm = new GPM(id);
	GPM.create(gpm); 
	updateActivity(gpm,100);
        // i should add a validator!
    updateProfile(gpm);
    return 0;
    }

    public int updateProfile(GPM gpm){
        TempProfile profile = new TempProfile();

        //get 'profile' from GooglePlus
        try {
			profile = GAPI.getProfile(gpm.id_gpm);
			profile.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // i should add a validator!

        //add profile to DB
    Gender gender =  Gender.find.where().eq("value", profile.gender).findUnique();
    Relationship relationshipStatus = Relationship.find.where().eq("status", profile.relationshipStatus).findUnique();
	Profile man = new Profile(gpm,profile.displayName,profile.image, gender,
					profile.tagline, relationshipStatus,100);
	Profile.create(man);
        // i should add a validator!

        //words extraction
        ArrayList<HistogramForWords> profileHistogram = new ArrayList<HistogramForWords>();
        String text = null;
        text = profile.aboutMe + " " + profile.tagline;
        profileHistogram = wordPreprocessor(text);
        //for each word from profile
        for (HistogramForWords pH: profileHistogram) {
                //for pH.word search it (word) in WordDictionary
                Word wordFromDictionary = Word.findByWord(pH.word);
                Synonym wordFromSynonyms = Synonym.find.where().eq("synonym", pH.word).findUnique();
                //there is the word (pH.word) in dictionary (Word or Synonym)
                if ((wordFromDictionary != null) || (wordFromSynonyms != null)) {
                    //add word from profile to table 'ProfileWord'
                    if (wordFromDictionary == null) {
                    	Word word = wordFromSynonyms.word;			
			ProfileWord profileWord = new ProfileWord(man, word, pH.countWord);	
                        ProfileWord.create(profileWord);
                    } else {
                    	ProfileWord profileWord = new ProfileWord(man, wordFromDictionary, pH.countWord);
                    	ProfileWord.create(profileWord);
                        // i should add a validator!
                    }
                }

            }

        //links extraction (there is no, Artem has not written this feature yet)

        return 0;
    }

    public int updateActivity(GPM gpm, int countOfPosts) {

        List <TempPost> activity = new ArrayList <TempPost>();
        GAPI temp = new GAPI();

        //get 'activity' from GooglePlus
        try {
			activity = temp.getActivity(gpm.id_gpm,countOfPosts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // i should add a validator

        //for each 'post' from 'activity'
        for (TempPost post: activity) {
            //add posts to DB (class Post)
        	Content kindContent =  Content.find.where().eq("kind", post.kindContent).findUnique();
            Post postToDB = new Post (gpm, post.postId, post.publishedData, kindContent, post.nComments,
				post.nPlusOne, post.nResharers,post.isRepost);
            Post.create(postToDB);
            // i should add a validator!

            //words extraction
            //build word histogram
            ArrayList<HistogramForWords> postHistogram = new ArrayList<HistogramForWords>();
            postHistogram = wordPreprocessor(post.content);
            //for each word from post
            for (HistogramForWords pH: postHistogram) {
                //for pH.word search it (word) in WordDictionary
            	Word wordFromDictionary = Word.findByWord(pH.word);
                Synonym wordFromSynonyms = Synonym.find.where().eq("synonym", pH.word).findUnique();
                //there is the word (pH.word) in dictionary (WordDictionary or WordSynonyms)
                if ((wordFromDictionary!=null) || (wordFromSynonyms!=null)) {
                    //add word from post to table 'PostWord'
                    if (wordFromDictionary==null) {
                    	Word word = wordFromSynonyms.word;			
		        PostWord postWord = new PostWord(postToDB, word, pH.countWord);	
                        PostWord.create(postWord);
                    } else {
                        PostWord postWord = new PostWord(postToDB, wordFromDictionary, pH.countWord);	
                        PostWord.create(postWord);
                        // i should add a validator!
                    }
                }
            }

            //links extraction (there is no, Artem has not written this feature yet)

            //ids extraction
            if ((post.isRepost)) {
                NewGPM newId =  NewGPM.find.where().eq("id_gpm", post.actorId).findUnique();
                if (newId==null) {
                    NewGPM newGpm= new NewGPM(post.actorId,1);
                    NewGPM.create(newGpm);
                    // i should add a validator!
                } else {
                    //add +1 to nMentiens for actorId
			 newId.nMentiens = newId.nMentiens+1;
			 NewGPM.create(newId);//newId.save();
                }
            }
        }
        return 0;
    }
    static ArrayList<HistogramForWords> wordPreprocessor(String a) {
        int i = 0, check = 0;
        String [] temp = null;
        ArrayList<String> mas = new ArrayList<String>();
        ArrayList<HistogramForWords> hist = new ArrayList<HistogramForWords>();
        temp = getWordsFromSentence(a);
        if(temp != null) {
            for(i = 0; i < temp.length; i++) {
                mas.add(temp[i]);
            }
        }
        HistogramForWords elem = new HistogramForWords();
        elem.countWord++;
        elem.word = mas.get(0);//на этом месте ломается
        hist.add(elem);
        mas.remove(0);
        for (String m: mas){
            for (HistogramForWords h: hist){
                if (h.word == null ? m == null : h.word.equals(m)) {
                    h.countWord++;
                    check = 1;
                }
            }
            if (check == 0) {
                HistogramForWords elem1 = new HistogramForWords();
                elem1.countWord++;
                elem1.word = m;
                hist.add(elem1);
            }
            check = 0;
        }
        return hist;
    }

    static String[] getWordsFromSentence(String a) {
        //conversion string to lowercase
        a = a.toLowerCase();
        String[] temp = null;
        if (a != null && !"".equals(a))
        {
            temp = a.split("[ ,.!?;:#()]+");
        }
        return temp;
    }
}
