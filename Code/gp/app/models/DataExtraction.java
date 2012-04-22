package models;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;

public class DataExtraction {

    public int newGPM(String id) {
        updateActivity(id,100);
        // i should add a validator!
        updateProfile(id);
        return 0;
    }

    public int updateProfile(String id){
        TempProfile profile = new TempProfile();
        Parser temp = new Parser();

        //get 'profile' from GooglePlus
        profile = temp.getProfile(id); // i should add a validator!

        //add profile to DB
        Profile man = Profile(id,profile.displayName,profile.image,profile.gender,profile.tagline,profile.relationshipStatus,100).save();
        // i should add a validator!

        //words extraction
        ArrayList<HistogramForWords> profileHistogram = new ArrayList<HistogramForWords>();
        String text = null;
        text = profile.aboutMe + " " + profile.tagline;
        profileHistogram = wordPreprocessor(text);
        //for each word from profile
        for (HistogramForWords pH: profileHistogram) {
                //for pH.word search it (word) in WordDictionary
                List <Word> word1 = Word.find("byWord",pH.word).fetch();
                List <Synonyms> word2 = Synonyms.find("byWord",pH.word).fetch();
                //there is the word (pH.word) in dictionary (Word or Synonyms)
                if (!(word1.isEmpty()) || !(word2.isEmpty())) {
                    //add word from profile to table 'ProfileWord'
                    if (word1.isEmpty()) {
                        Synonyms wordFromSynonyms = new Synonyms();
                        wordFromSynonyms = word2.get(0);
                        new ProfileWord(man, wordFromSynonyms, pH.countWord).save();
                    } else {
                        Word wordFromDictionary = new Word();
                        wordFromDictionary = word1.get(0);
                        new ProfileWord(man, wordFromDictionary, pH.countWord).save();
                        // i should add a validator!
                    }
                }

            }

        //links extraction (there is no, Artem has not written this feature yet)

        return 0;
    }

    public int updateActivity(String id, int countOfPosts) {

        List <TempPost> activity = new ArrayList <TempPost>();
        GAPI temp = new GAPI();

        //get 'activity' from GooglePlus
        activity = temp.getActivity(id,countOfPosts); // i should add a validator

        //for each 'post' from 'activity'
        for (TempPost post: activity) {
            //add posts to DB (class Post)
            Post postToDB = new Post (id,post.publishedData,post.kindContent,post.nComments,post.nPlusOne,post.nResharers,post.isRepost).save();
            // i should add a validator!

            //words extraction
            //build word histogram
            ArrayList<HistogramForWords> postHistogram = new ArrayList<HistogramForWords>();
            postHistogram = wordPreprocessor(post.content);
            //for each word from post
            for (HistogramForWords pH: postHistogram) {
                //for pH.word search it (word) in WordDictionary
                List <Word> word1 = Word.find("byWord",pH.word).fetch();
                List <Synonyms> word2 = Synonyms.find("byWord",pH.word).fetch();
                //there is the word (pH.word) in dictionary (WordDictionary or WordSynonyms)
                if (!(word1.isEmpty()) || !(word2.isEmpty())) {
                    //add word from post to table 'PostWord'
                    if (word1.isEmpty()) {
                        Synonyms wordFromSynonyms = new Synonyms();
                        wordFromSynonyms = word2.get(0);
                        new PostWord(postToDB, wordFromSynonyms, pH.countWord).save();
                    } else {
                        Word wordFromDictionary = new Word();
                        wordFromDictionary = word1.get(0);
                        new PostWord(postToDB, wordFromDictionary, pH.countWord).save();
                        // i should add a validator!
                    }
                }
            }

            //links extraction (there is no, Artem has not written this feature yet)

            //ids extraction
            if (!(post.kindPost)) {
                List <NewGPM> newId = NewGPM.find("byId").fetch();
                if (newId.isEmpty()) {
                    new NewGPM(post.actorId,1).save();
                    // i should add a validator!
                } else {
                    //add +1 to nMentiens for actorId ??? How?
                }
            }
        }
        return 0;
    }

    ArrayList<HistogramForWords> wordPreprocessor(String a) {
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
        elem.word = mas.get(0);
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

    String[] getWordsFromSentence(String a) {
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

