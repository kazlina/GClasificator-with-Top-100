package models;

import java.util.ArrayList;

public class DataExtraction {
    public int type_histogram = 0; //0 - activity, 1 - profile
    public int type_content_histigram = 0; //0 - word, 1 - link
    public int count_key_words = 0;
    public HistogramStruct[] histogram;

    public static void main(String[] args) {
        //object h is created
        DataExtraction h = new DataExtraction();
        h.count_key_words = 7;
        int i = 0;

        int count_of_posts = 5;
        String[] activity = new String [count_of_posts];
        activity[0] = "Since then Glenn has been diligently working on creating his own brand of beautifully painted images.";
        activity[1] = "Using acrylic paints on wooden panels, he adds in elements and influential symbols of his past and present to each piece.";
        activity[2] = "Beyond the aesthetics of his artwork, Glenn brings an overwhelming sense of passion to his paintings.";
        activity[3] = "Touching on themes of love, death, conflict and duality, Glenn’s art tells stories of strength and hope";
        activity[4] = "through emotion and sentiment, (with) his sensual beauties and signature hummingbirds.";
        
        //conversion strings to lowercase
        for (i = 0; i < count_of_posts; i++) {
            activity[i] = activity[i].toLowerCase();
        }
        activity[0].split(" ");
        ArrayList<HistogramStruct> histogramm = h.preprocessor(activity);
        
        for (HistogramStruct hm: histogramm){
            System.out.println(hm.word+" "+hm.cout_word);
        }
        //h.create_histogram_activity(from_DB,activity);
    }
    ArrayList<HistogramStruct> preprocessor(String[] activity){
        int i = 0, check = 0;
        String [] temp = null;
        ArrayList<String> mas = new ArrayList<String>();
        ArrayList<HistogramStruct> hist = new ArrayList<HistogramStruct>();
        for (String a: activity){
            temp = get_words_from_sentence(a);
            for(i = 0; i < temp.length ; i++){
                mas.add(temp[i]);
            }
        }
        HistogramStruct elem = new HistogramStruct();
        elem.cout_word++;
        elem.word = mas.get(0);
        hist.add(elem);
        for (String m: mas){
            for (HistogramStruct h: hist){
                if (h.word == null ? m == null : h.word.equals(m)){
                    h.cout_word++;
                    check = 1;
                }
            }
            if (check == 0){
                HistogramStruct elem1 = new HistogramStruct();
                elem1.cout_word++;
                elem1.word = m;
                hist.add(elem1);
            }
            check = 0;
        }
        return hist;
    }
    String[] get_words_from_sentence(String a){
        String[] temp = null;
        temp = a.split("[ ,.!?;:#()]+");
        return temp;
    }
}
