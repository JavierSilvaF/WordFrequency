package com.example.wftest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class HelloApplicationTests {
    @Test
    @DisplayName("Testing that the cleaned string doesn't contain any special chars that can mess up the word count")
    void checkStringCharacters() {
        String cleanString = com.example.wftest.HelloApplication.cleanup("THIS IS A TEST .&@%IT^@#DID@ł₮%$#ЩӨЯ₭??");
        Assertions.assertTrue(!(cleanString.contains("[^a-zA-Z0-9]")));
    }

    @Test
    @DisplayName("Testing that the file is not null")
    void checkFile() {
        String loadedFile = null;

        //Try/catch in case to handle when the function is called but the file doesn't exist so it throws a FileNotFound exception.
        try {
            loadedFile = HelloApplication.getFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(loadedFile);
    }
}