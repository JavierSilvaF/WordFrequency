package com.example.wftest;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 *  Write a text analyzer that reads a file and outputs statistics about that file.
 *  It should output the word frequencies of all words in the file, sorted by the most frequently used word.
 *  The output should be a set of pairs, each pair containing a word and how many times it occurred in the file. <br>
 *  <br>
 *  <b>Sample Output:</b>
 *  <b>Command Line:</b><br>
 *  <img src="https://i.imgur.com/eiZMzFE.png" alt="Command Line Output"> <br>
 *  <br>
 *  <b>GUI:</b><br>
 *  <img src="https://i.imgur.com/1Wsxvnj.png" alt="Interface Output"><br>
 *
 * @author Javier Silva
 * @version 1.0.0
 *
 */

public class HelloApplication extends Application {

    /**
     * Starting point of the Word Frequency program.
     *
     * @param args Stores Java command line arguments
     * @throws FileNotFoundException Exception thrown when the poem file is not found
     */

    public static void main(String args[]) throws FileNotFoundException {
        launch(args);
    }//main


    /**
     * This method creates the TableView containing the word frequency of the input text.
     *
     * @param stage top level JavaFX container.
     * @throws FileNotFoundException Exception thrown when the poem file is not found
     */
    public void start(Stage stage) throws FileNotFoundException, ClassNotFoundException, SQLException {

        String caughtFile = getFile();
        //Sending the file for cleanup
        String fileNoChars = cleanup(caughtFile);
        //Deleting all the whitespace created by the previous replace all.
        String cleanFile = fileNoChars.replaceAll("^ +| +$|( )+", "$1");
        //Splitting the string into an array with each word
        String splitString[]=cleanFile.split(" ");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        Statement stmt = null;
        conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/wordoccurrences?useSSL=false&allowPublicKeyRetrieval=true", "root", "j15589170");

        //Counting the word frequency
        Map<String,Integer> UnsortedMap =new TreeMap<>();
        for(int i=0;i<splitString.length;i++)
        {
            //Insert into MySQL Database, will catch if there's an error or the word already exists in the DB!
            try {
                stmt = (Statement) conn.createStatement();
                String query = "INSERT INTO wordOccurrences.word (word) values('" + splitString[i] + "');";
                stmt.executeUpdate(query);
            }catch (SQLException exception){
                exception.printStackTrace();
            }

            //If the word is in the TreeMap, then add one to its value
            if(UnsortedMap.containsKey(splitString[i]))
            {   UnsortedMap.put(splitString[i], UnsortedMap.get(splitString[i])+1);}
            //If it isn't in the TreeMap, then insert it and set it's value to 1
            else
            {   UnsortedMap.put(splitString[i],1);}
        }

        //entrySet creates a set based out of UnsortedMap, stream takes the sequence of elements
        //which then gets sorted by comparing it's values, reversed and printed.
        UnsortedMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(20)
                .forEach(System.out::println);

        Scene scene = new Scene(new Group());
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setTitle("Valencia College");
        stage.setWidth(300);
        stage.setHeight(500);

        //Adding to the ObservableList
        ObservableList<Populate> populatedTable = FXCollections.observableArrayList();
        int value = 0;
        Integer i = 0;

        for (Map.Entry<String, Integer> entry : UnsortedMap.entrySet()) {
            System.out.println("[" + entry.getKey() + ", " + entry.getValue() + "]");
            i = i + 1;
            value = entry.getValue();
            boolean newWord = populatedTable.add(new Populate(entry.getKey(), value));
        }

        TableView table = new TableView();

        final Label label = new Label("Word Frequency");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Populate, String> wordCol = new TableColumn("Word");
        wordCol.setCellValueFactory(
                new PropertyValueFactory<Populate, String>("word")
        );

        TableColumn<Populate, Integer> freqCol = new TableColumn("Frequency");
        freqCol.setCellValueFactory(
                new PropertyValueFactory<Populate, Integer>("freq")
        );

        table.setItems(populatedTable);
        table.getColumns().addAll(wordCol, freqCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method retrieves the poem from a .txt file in the directory.
     *
     * @return File - String containing the poem.
     * @throws FileNotFoundException Exception thrown when the poem file is not found
     */

    public static String getFile() throws FileNotFoundException{
        //creating File instance to reference text file in Java
        File text = new File("poem.txt");
        String File = "The poem is: ";

        //Creating Scanner instance to read File in Java
        Scanner scnr = new Scanner(text);
        //Reading each line of the file using Scanner class and adding it to a string
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            File = File.concat(" " + line);
        }
        return File;
    }

    /**
     * This method receives a string which contains the poem and cleans all special characters, which could mess up the word counting process.
     *
     * @param DirtyString String containing the poem.
     * @return fileNoChars - String with no special characters.
     */

    public static String cleanup(String DirtyString) {
        //Deleting all special characters as they could mess up the TreeMap
        String fileNoChars = DirtyString.replaceAll("[^a-zA-Z0-9]", " ");

        return fileNoChars;
    };

}//class