package com.example.wftest;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *  Constructor for the Populated type, used in the Observable List.
 */

public class Populate {
    private final SimpleStringProperty word;
    private final SimpleIntegerProperty freq;

    /**
     * Creates a new property for the object.
     *
     * @param word used to refer to the current object.
     * @param freq used to refer to the current object.
     */

    Populate(String word, Integer freq) {
        this.word = new SimpleStringProperty(word);
        this.freq = new SimpleIntegerProperty(freq);
    }

    /**
     * Getter to access the word variable
     *
     * @return word - string variable
     */

    public String getWord() {
        return word.get();
    }

    /**
     * Setter for Word variable.
     * @param cWord
     */

    public void setFirstName(String cWord) {
        word.set(cWord);
    }

    /**
     * Getter to access the frequency variable
     *
     * @return freq - integer variable
     */

    public Integer getFreq() {
        return freq.get();
    }

    /**
     * Setter for frequency variable.
     * @param cFreq
     */
    public void setFreq(Integer cFreq) {
        freq.set(cFreq);
    }

}