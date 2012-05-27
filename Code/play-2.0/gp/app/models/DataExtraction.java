package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataExtraction {

    public static int newGPM(String id) {
	GPM gpm = GPM.add(id);
	updateActivity(gpm,100);
        // i should add a validator!
    updateProfile(gpm);
    return 0;
    }

    public static int updateProfile(GPM gpm){
        TempProfile profile = new TempProfile();

        //get 'profile' from GooglePlus
        try {
			profile = GAPI.getProfile(gpm.idGpm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // i should add a validator!

        //add profile to DB
        Gender gender =  Gender.findByValue(profile.gender);
        Relationship relationshipStatus = Relationship.findByStatus(profile.relationshipStatus);
        Profile man = new Profile(gpm,profile.displayName,profile.image, gender,
					profile.tagline, relationshipStatus,100);
        Profile.add(man);
        // i should add a validator!

        //words extraction
        ArrayList<HistogramForWords> profileHistogram = new ArrayList<HistogramForWords>();
        String text = null;
        text = profile.aboutMe + " " + profile.tagline;
        profileHistogram = wordPreprocessor(text);
        if (profileHistogram != null) {
            //for each word from profile
            for (HistogramForWords pH: profileHistogram) {
                //for pH.word search it (word) in WordDictionary
                //add word from profile to table 'ProfileWord'
                ProfileWord.add(man, pH.word, pH.countWord);
                // i should add a validator!
            }
        }

        //links extraction (there is no, Artem has not written this feature yet)

        return 0;
    }

    public static int updateActivity(GPM gpm, int countOfPosts) {

        List <TempPost> activity = new ArrayList <TempPost>();
        GAPI temp = new GAPI();

        //get 'activity' from GooglePlus
        try {
			activity = temp.getActivity(gpm.idGpm,countOfPosts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // i should add a validator

        //for each 'post' from 'activity'
        for (TempPost post: activity) {
            //add posts to DB (class Post)
        	post.print();
        	Content kindContent =  Content.findByKind(post.kindContent);
            Post postToDB = new Post (gpm, post.postId, post.publishedData, kindContent, post.nComments,
				post.nPlusOne, post.nResharers,post.isRepost);
            Post.add(postToDB);
            // i should add a validator!

            //words extraction
            //build word histogram
            ArrayList<HistogramForWords> postHistogram = new ArrayList<HistogramForWords>();
            String text = null;
            text = post.content;
            if (post.isRepost && post.annotation != null) {
                text = text + " " + post.annotation;
            }
            postHistogram = wordPreprocessor(text);
            if (postHistogram != null){
                //for each word from post
                for (HistogramForWords pH: postHistogram) {
                    //for pH.word search it (word) in WordDictionary
                    //add word from post to table 'PostWord'
                    PostWord.add(postToDB, pH.word, pH.countWord);
                    // i should add a validator!
                }
            }

            //links extraction (there is no, Artem has not written this feature yet)

            //ids extraction
            if ((post.isRepost)) {
                NewGPM newGPM = null;
                newGPM.add(post.actorId);
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

        if (!(mas.isEmpty())) {
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
        } else {
            hist = null;
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
