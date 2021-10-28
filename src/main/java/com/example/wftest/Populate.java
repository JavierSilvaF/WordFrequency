package com.example.wftest;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Populate {
    private final SimpleStringProperty word;
    private final SimpleIntegerProperty freq;

    Populate(String word, Integer freq) {
        this.word = new SimpleStringProperty(word);
        this.freq = new SimpleIntegerProperty(freq);
    }

    public String getWord() {
        return word.get();
    }
    public void setFirstName(String cWord) {
        word.set(cWord);
    }

    public Integer getFreq() {
        return freq.get();
    }
    public void setFreq(Integer cFreq) {
        freq.set(cFreq);
    }

}