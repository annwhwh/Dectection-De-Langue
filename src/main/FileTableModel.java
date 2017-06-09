package main;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


import javax.swing.table.AbstractTableModel;

import corpus.Propriete;

public class FileTableModel extends AbstractTableModel implements Iterable<String[]> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static String TESTED  ="TESTED";
    public static String READY = "Ready to test";
    public static String ANALYZE = "Testing...";
    
    String[] columnNames 
        = { "File Name", "Real Langue", "Test Results","File path","Status","Size","Time" };
    private ArrayList<String[]> data = new ArrayList<>();
    
    public FileTableModel() {
        super();
       
        File file = new File(Propriete.PATH_TEXT);
        
        File[] list = file.listFiles();
        
        for (File langue : list) {
            File[] subList = langue.listFiles();
            if(!langue.isDirectory()) continue;
            for (File file2 : subList) {
                addFile(file2,langue.getName());
            }
        }
    }
    
    
    public  String[] getLangueList(){
        Set<String> langueSet= XMLUtils.getLangueList().keySet();
        return langueSet.toArray( new String[langueSet.size()] );
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
         }      String[] contenue = new String[9];
         
        // File name 
        contenue[0] = file.getName();
        // Real Langue
        contenue[1] = langue;
        // Test results
        contenue[2] = "";
        // file path
        contenue[3] = file.getAbsolutePath();
        // file status
        contenue[4] = READY;
        // file size
        contenue[7] = Double.toHexString(file.length());
        
        // readable file size
        contenue[5] = XMLUtils.readableFileSize(file.length());
        contenue[6] = "0";

       data.add(contenue);
        fireTableRowsInserted(data.size()-1, data.size()-1);
        return true;
    }
    
   
    public void removeRow(int[] row){
        java.util.Arrays.sort(row);
        for(int i = row.length-1; i>=0 ; i--){
            data.remove(row[i]);
            fireTableRowsDeleted(row[i], row[i]);
        }    
    }

  
   
@Override
public Iterator<String[]> iterator() {
    return data.iterator();
}
}
