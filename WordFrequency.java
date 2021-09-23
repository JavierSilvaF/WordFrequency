import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class WordFrequency {

    public static void main(String args[]) throws FileNotFoundException {
 
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
        Map<String,Integer> UnsortedMap=new TreeMap<>();
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
        

    }//main
}//class