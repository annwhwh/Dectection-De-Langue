package main;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import corpus.Apprentisage;
import testCorpus.TestSingleText;

public class DetectionModel extends AbstractTableModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private TestSingleText tester = null;
    private Vector<String> data;
    private Vector<String> columnName;
    private Map<String,Double> result;
    
    public DetectionModel() {
        tester = new TestSingleText(Apprentisage.getInstance());
       update("");
    }
    
    
    public void update(String msg){
        tester.setText(msg);
        tester.doInBackground();
        
        result = tester.getResult();
        data = new Vector<>();
        columnName = new Vector<>();
        for (Entry<String, Double> entry: result.entrySet()) {
            columnName.add(entry.getKey());
            data.add(entry.getValue().toString());            
        }
        fireTableStructureChanged();
        fireTableRowsUpdated(0, 0);
    }

    @Override
    public int getColumnCount() {
        return this.columnName.size();
    }
    @Override
    public int getRowCount() {
        return 1;
    }
    
    @Override
    public String getColumnName(int col) {
        return this.columnName.get(col);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        return data.get(col);
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.set(col, (String) value);
        fireTableCellUpdated(row, col);
    }

}
    