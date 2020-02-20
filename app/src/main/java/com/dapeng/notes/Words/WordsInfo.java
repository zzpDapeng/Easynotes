package com.dapeng.notes.Words;

/**
 * Created by 10341 on 2018/3/20.
 */

public class WordsInfo {
    public String id;
    public String words;
    public String translation;
    public String phrase;
    public String others;

    public  WordsInfo(String id, String words, String translation, String phrase, String others){
        this.id = id;
        this.words = words;
        this.translation = translation;
        this.phrase = phrase;
        this.others = others;
    }
    public String getId() {
        return id;
    }
    public String getWords() {
        return words;
    }
    public void setWords(String words) {
        this.words = words;
    }
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public String getOthers() {
        return others;
    }
    public void setOthers(String others) {
        this.others = others;
    }
}
