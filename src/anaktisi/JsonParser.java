/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anaktisi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user
 */
public class JsonParser {
    

/**
 *
 * @author user
 */
    
    private JFileChooser chooser;
    
    JsonParser(JFileChooser ch ){ chooser=ch;}
    
    public JFileChooser getChooser( ){return chooser;}
    
    public ArrayList<String> read(ArrayList<String> stars) throws IOException, ParseException{
        ArrayList<String> reviews = new ArrayList<>();
        JSONParser parser = new JSONParser();       
        
        BufferedReader in = null;
        try {
            String fileName=chooser.getSelectedFile().toString();
            in = new BufferedReader(new FileReader(fileName));
            
            String line;
            while((line = in.readLine()) != null){
                JSONObject jo=(JSONObject)parser.parse(line);
                String str=(String)jo.get("text");
                str=str.replaceAll("\r", " ");
                str=str.replaceAll("\n", " ");
                long f = (long )jo.get("stars");
                stars.add(""+f);
                reviews.add(str);
            }
            return reviews;
        } catch (FileNotFoundException ex) {
            return null;
        } 
       
    }
}

