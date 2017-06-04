package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;
import corpus.Apprentisage;
import corpus.Propriete;

public class CorpusTableModel extends AbstractTableModel implements Iterable<String[]> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static String EXISTED ="Already learn by programme";
    public static String READY = "Ready to study";
    public static String ANALYZE = "Analyzing...";
    
    String[] columnNames = { "File Name", "Langue", "File path","Status","Size","Time" };
    
    public CorpusTableModel() {
        super();
        apprentisage = Apprentisage.getInstance();
        
        File file = new File(Propriete.PATH_CORPUS);
        File[] list = file.listFiles();
        
        for (File langue : list) {
            File[] subList = langue.listFiles();
            for (File file2 : subList) {
                addFile(file2,langue.getName());
            }
        }
    }
    
    
    public  String[] getLangueList(){
        return apprentisage.getLangueList().toArray( new String[apprentisage.getLangueList().size()] );
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public int getRowCount() {
        return data.size();
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = (String)value;
        fireTableCellUpdated(row, col);
    }
    
    
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears on screen.
        if (col != 1) {
            return false;
        } else {
            return true;
        }
    }

    
    public boolean addFile(File file, String langue){
        for (int i = 0; i < getRowCount();i++) {
            if(file.getAbsolutePath().equals(getValueAt(i, 2)))
                return false; 
         }      String[] contenue = new String[7];
                
        contenue[0] = file.getName();
        contenue[1] = langue;
        contenue[2] = file.getAbsolutePath();
        contenue[6] = Double.toHexString(file.length()/1024.0);
        contenue[4] = XMLUtils.readableFileSize(file.length());
        contenue[5] = "0";
        if(apprentisage.hasCorpus(file.getAbsolutePath()))
            contenue[3] = EXISTED;
        else
        contenue[3] = READY;
        
        
        data.add(contenue);
        fireTableRowsInserted(data.size()-1, data.size()-1);
        return true;
    }
    
   public boolean addLanguage(String langue){
       return apprentisage.addLangue(langue);
   }
   
   public void removeRow(int[] row){
       java.util.Arrays.sort(row);
       for(int i = row.length-1; i>=0 ; i--){
           data.remove(i);
           fireTableRowsDeleted(i, i);
       }    
   }
   
   public void learnFromFile(String langue, String filepath) throws FileNotFoundException{
        apprentisage.learnFromFile(langue, filepath);
   }
   
   

@Override
public Iterator<String[]> iterator() {
    return data.iterator();
}
}
