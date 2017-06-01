package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import javafx.beans.binding.BooleanBinding;



/**Cette classe nous permet de lire et modifier les fichier de configuration xml*/
public class XMLUtils {
    private static String CORPUS_XML = "res" + File.separator + "corpus.xml";
    private static XMLUtils instance = null;
    private Document  document = null;
    private Element root = null;
    private Element bigramme = null;
    private Element corpusList = null;
 
    private XMLUtils(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXReader reader = new SAXReader();
            this.document = reader.read(new File(CORPUS_XML));
            this.root = document.getRootElement();
            if((this.bigramme = root.element("bigramme")) == null)
                this.bigramme = root.addElement("bigramme");
            if((this.corpusList  = root.element("corpus_list")) == null)
                this.corpusList = root.addElement("corpus_list");
            
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.getRootElement();
    }
    
    private static XMLUtils getInstance(){
        if (instance==null){
            instance = new XMLUtils();
        }
        return instance;
    }
    
    
    public static Map<String, String> getLangueList(){
        Map<String, String> langueMap = new HashMap<>();
        List<Element> langueList = getInstance().bigramme.elements("langue");
        String langue = null, bigrammeUri = null;
        for (Element element : langueList) {
            langue = element.attributeValue("name");
            bigrammeUri = element.attributeValue("path");
            langueMap.put(langue, bigrammeUri);
        }
        return langueMap;
    }
    
    public static void addLangue(String langue, String bigrammeUri){
        if(hasLangue(langue)) 
            throw new IllegalAddException(langue + " has existed! Can not add this language");
        Element langueElem = getInstance().bigramme.addElement("langue");
        langueElem.addAttribute("name", langue);
        langueElem.addAttribute("path", bigrammeUri);
        
        update();        
    }
    
    public static void update(){
        XMLWriter writer = null;
        try{
            OutputFormat format = OutputFormat.createPrettyPrint();
           writer = new XMLWriter(new FileWriter(CORPUS_XML),format);
           writer.write(getInstance().document);
 
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void removeLangue(String targetLangue){
        List<Element> langueList = getInstance().bigramme.elements();
        String langue = null;
        for (Element element : langueList) {
            langue = element.attributeValue("name");
            if(langue.equals(targetLangue)){
                getInstance().bigramme.remove(element);
            }                     
        }
        
        update(); 
    }
    
    public static boolean hasLangue(String langue){
        List<Element> langueList = getInstance().bigramme.elements();
        for (Element element : langueList) {
            if(langue.equals(element.attributeValue("name"))){
                return true;
            }                     
        }
        
        return false;
    }

    public static boolean hasCorpus(HashCode hashcode){
            HashCode targetSHA = null;
            
            List<Element> langueList = getInstance().corpusList.elements();
            for (Element element : langueList) {
                targetSHA =HashCode.fromString(element.attributeValue("sha512"));  
                if(targetSHA.equals(hashcode)){
                    return true;
                }   
            }
        return false;
    }
    
    
    public static void addCorpus(String langue,String fileUri){
        HashCode hc;
        try {
            hc = Files.hash(new File(fileUri), Hashing.sha512());
            if(hasCorpus(hc)){
                throw new IllegalAddException(fileUri + " has existed! Can not add this corpus");
            }
            
            Element fileElem = getInstance().corpusList.addElement("file");
            fileElem.addAttribute("langue", langue);
            fileElem.addAttribute("sha512",hc.toString() );
            
            update();      
        } catch (IOException e) {
            e.printStackTrace();
        }
        
  
        
        
    }
    
    
    
    
    
    
    
}
