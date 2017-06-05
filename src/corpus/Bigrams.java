package corpus;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.sun.prism.impl.Disposer.Target;


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
    
    public void toJson(String filePath){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter fileWriter
                = new FileWriter(new File(filePath));){
            gson.toJson(this,fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Get bigrams from the specified Reader**/
    public static Bigrams getBigrams(InputStream inputStream){
        Bigrams target = new Bigrams();
        
        try(InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader in = new BufferedReader(inputStreamReader);){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return target;
    }
    
    /** Get bigrams from the spécified string**/
    public static Bigrams getBigrams(String input){
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        return Bigrams.getBigrams(stream);
    }

}
