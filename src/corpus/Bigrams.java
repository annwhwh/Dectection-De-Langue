package corpus;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;

import main.XMLUtils;

public class Bigrams {
    @SerializedName("bigrams")
    private Map<String, Long> bigrams;
    
    
    public Bigrams() {
        this.bigrams = new TreeMap<String,Long>();
    }
    
    
    /*** add the occurrence of the bi-grams from other bigrams;**/
    public void addFrom(Bigrams src){
        Long oldValue = 0l;
        for ( Entry<String, Long> entry : src.bigrams.entrySet()) {
            
            if(this.bigrams.containsKey(entry.getKey())){
                oldValue = this.bigrams.get(entry.getKey());
                this.bigrams.put(entry.getKey(), entry.getValue() + oldValue);
            }else{
                this.bigrams.put(entry.getKey(), entry.getValue());
            }
        }   
    }
    
    /*** get similarity of two bi-grams**/
    public double similarity(Bigrams targetBigrams){
        Double produit = 0.0;
        Double moduleTmp = 0.0;
        Double moduleSrc = 0.0;
        
        String key = null;
        Long value = 0L;
        
        for (Entry<String, Long> entry : targetBigrams.bigrams.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            
            moduleTmp += value * value;
            
            if(this.bigrams.containsKey(key)){
                moduleSrc +=  this.bigrams.get(key) * this.bigrams.get(key);
                produit += value * this.bigrams.get(key);
            }else{
                moduleSrc +=  0L;
                produit += value * 0;
            }
        }
        return produit / (Math.sqrt(moduleSrc) * Math.sqrt(moduleTmp));
    }
    
    @Override
    public String toString() {
        return bigrams.toString();
    }
    
    public void toJson(String filePath) throws FileNotFoundException, IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileOutputStream fi = new FileOutputStream(new File(filePath));
            OutputStreamWriter ir = new OutputStreamWriter(fi,"utf-8")){
            gson.toJson(this,ir);
        } 
    }

    
    /** Get bigrams from the specified Reader**/
    public static Bigrams getBigrams(File file) 
            throws FileNotFoundException, IOException, EncodingAlgorithmException{
        
        Bigrams target = new Bigrams();
        
        try(FileInputStream fi = new FileInputStream(file);
            InputStreamReader ir = new InputStreamReader(fi, XMLUtils.detectEncoding(file));
               BufferedReader in = new BufferedReader(ir)){
            
            String inputLine = null,seg = null;
            int end =0;
            long value;
            
            while((inputLine = in.readLine())!=null){
                for(end = 2; end <= inputLine.length();end++){
                    seg = inputLine.substring(end-2,end).toLowerCase();
                    if(!target.bigrams.containsKey(seg)){
                        target.bigrams.put(seg, (long) 1);
                    }else{
                        value = target.bigrams.get(seg)+1;
                        target.bigrams.put(seg, value);
                    }
                }
            }
            return target;
        }
    }
    
    
    /** Get bigrams from the string donated **/
    public static Bigrams getBigrams(String input){
        Bigrams target = new Bigrams();
        String seg = null;
        long value;
        
        for(int end = 2; end <=  input.length(); end ++){
            seg = input.substring(end-2,end).toLowerCase();
            if(!target.bigrams.containsKey(seg)){
                target.bigrams.put(seg, (long) 1);
            }else{
                value = target.bigrams.get(seg)+1;
                target.bigrams.put(seg, value);
            }
            
        }
        
        return target;
        
    }

}
