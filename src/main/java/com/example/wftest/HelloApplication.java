package com.example.wftest;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class HelloApplication extends Application {
    private TableView table = new TableView();

    public static void main(String args[]) throws FileNotFoundException {
        launch(args);
    }//main

    public void start(Stage stage) throws FileNotFoundException {
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

        //Deleting all special characters as they could mess up the HashMap
        String fileNoChars = File.replaceAll("[^a-zA-Z0-9]", " ");
        //Deleting all the whitespace created by the previous replace all.
        String cleanFile = fileNoChars.replaceAll("^ +| +$|( )+", "$1");
        //Splitting the string into an array with each word
        String splitString[]=cleanFile.split(" ");

        //Counting the word frequency
        Map<String,Integer> UnsortedMap =new TreeMap<>();
        for(int i=0;i<splitString.length;i++)
        {
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
}//class