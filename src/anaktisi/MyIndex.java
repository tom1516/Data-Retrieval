/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anaktisi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


/**
 *
 * @author user
 */
public class MyIndex {
    private final ArrayList<String> reviews;
    
    MyIndex(ArrayList<String> reviews){
        this.reviews=reviews;
    }
    Directory makeIndex(ArrayList<String> stars) throws IOException, ParseException{
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);

    // 1. create the index
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
        IndexWriter w = null;
        try {
            w = new IndexWriter(index, config);
        } catch (IOException ex) {
            Logger.getLogger(MyIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<reviews.size();i++){
            Document doc = new Document();
            FieldType type = new FieldType();
            type.setIndexed(true);
            type.setStored(true); // it needs to be stored to be properly highlighted
            type.setTokenized(true);
            type.setIndexOptions(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS); // necessary for PostingsHighlighter
            float boost = Float.parseFloat(stars.get(i));
            Field field = new Field("text", reviews.get(i), type);
            field.setBoost(boost);
            doc.add(field);

    // use a string field for isbn because we don't want it tokenized
          String str = "" +i;
          doc.add(new StringField("id", str, Field.Store.YES));
          w.addDocument(doc);
        }
         w.close();
        
        return(index);
   // }
    }
}
