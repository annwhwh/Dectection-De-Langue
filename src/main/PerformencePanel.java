package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.google.common.base.Stopwatch;
import java.beans.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import corpus.Apprentisage;
import corpus.Propriete;
import testCorpus.TestSingleText;


public class PerformencePanel extends JPanel implements TableModelListener, ActionListener , PropertyChangeListener{

    private final JTable fileTable;
    private final JButton openButton;
    private final JButton startButton;
    private final JButton removeButton;
    private final JProgressBar progressBar;
    private final JComboBox<String> jComboBox;
    private final DefaultComboBoxModel<String> comBoxModel;
    private Task task;
    private final FileTableModel filemodel;
    private final JTable resulsTable;
    private final TestSingleText tester;
    private final DefaultTableModel resultsTableModel;

    class Task extends SwingWorker<Void, Void> {
        private long length = 0L;
        
        public Task() {
        
            for (String[] file : filemodel) {
                length += Double.parseDouble(file[7]);
            }

        }
        
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Double progress = 0.0;
        
            setProgress(0);
            int i = 0;
            for (String[] file : filemodel) {
                if(file[4].equals(FileTableModel.READY)){
                    
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    
                    filemodel.setValueAt(FileTableModel.ANALYZE, i, 4);
                    System.out.println("Status：" + FileTableModel.ANALYZE);
                    filemodel.setValueAt(tester.testFile(file[3]), i, 2);
                    filemodel.setValueAt(FileTableModel.TESTED, i, 4);
                    
                    stopwatch.stop();
                    
                    filemodel.setValueAt(stopwatch.toString(), i, 6);
                    filemodel.setValueAt(Long.toString(stopwatch.elapsed(TimeUnit.NANOSECONDS)), i, 8);
                    System.out.println(Long.toString(stopwatch.elapsed(TimeUnit.NANOSECONDS)));

                }
                progress += Double.parseDouble(file[7]);
                
                setProgress((int)((double)progress/ this.length * 100));
                i++;
                
            }
            return null;

    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        startButton.setEnabled(true);
        setCursor(null); //turn off the wait cursor
        setResultTable();
    }
}


    /**
     * Create the panel.
     */
    public PerformencePanel() {
        setLayout(new BorderLayout());
        tester = new TestSingleText(Apprentisage.getInstance());
        
        JScrollPane fileScrollPane = new JScrollPane();
        fileTable = new JTable();
        filemodel = new FileTableModel();
        fileTable.setModel(filemodel);
        fileTable.setFillsViewportHeight(true);
        fileScrollPane.setViewportView(fileTable);
 
        
        
        JScrollPane resultsScrollPane = new JScrollPane();
        resulsTable = new JTable();
        resultsTableModel = new DefaultTableModel();
        resulsTable.setModel(resultsTableModel);
        resultsScrollPane.setViewportView(resulsTable);
       resulsTable.setPreferredSize(new Dimension(resulsTable.getRowHeight(),resulsTable.getRowHeight()));
        
        this.jComboBox = new JComboBox<String>();
        this.comBoxModel = new DefaultComboBoxModel<>(XMLUtils.getLangueList().keySet().toArray(new String[XMLUtils.getLangueList().size()]));   
        jComboBox.setModel(comBoxModel);
        
        TableColumn tableCol = this.fileTable.getColumnModel().getColumn(1);
        tableCol.setCellEditor(new DefaultCellEditor(jComboBox));
        
       
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT
                , fileScrollPane, resulsTable);
        splitPane.setDividerLocation(0.5);
        add(splitPane,BorderLayout.CENTER);
        
        JPanel outilPanel = new JPanel();
        outilPanel.setLayout(new BoxLayout(outilPanel,BoxLayout.Y_AXIS));
        progressBar= new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        outilPanel.add(progressBar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        openButton = new JButton("Open Files",new ImageIcon("res/open.ico"));
        openButton.setPreferredSize(new Dimension(100, 33));
        openButton.addActionListener(this);
        
        removeButton = new JButton("remove Files",new ImageIcon("res/open.ico"));
        removeButton.setPreferredSize(new Dimension(150, 33));
        removeButton.addActionListener(this);
         
        startButton = new JButton("Start",new ImageIcon("res/open.ico"));
        startButton.setPreferredSize(new Dimension(100, 33));
        startButton.addActionListener(this);
        
        buttonPanel.add(openButton);        
        buttonPanel.add(removeButton);
        buttonPanel.add(startButton);
        
        outilPanel.add(buttonPanel);
        
        add(outilPanel,BorderLayout.PAGE_END) ;      
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        // TODO Auto-generated method stub
     }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.openButton){
            openButtonAction();
        }else if (e.getSource() == this.removeButton) {
            removeButtonAction();
        }
        else if(e.getSource() == this.startButton){
            startButtonAction();
        }
        
    }

    private void startButtonAction() {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener((PropertyChangeListener) this);
        task.execute();
        
        
    }

    private void removeButtonAction() {
        int[] row = fileTable.getSelectedRows();
            filemodel.removeRow(row);

    }

    /**
     * set the action of openButton
     */
    private void openButtonAction() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(Propriete.PATH_CORPUS));
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setMultiSelectionEnabled(true);
        jFileChooser.showOpenDialog(this);
        
        File[] files = jFileChooser.getSelectedFiles();
        
        int counter = 0;
        StringBuilder missFile = new StringBuilder();
        
        for (File file : files) {
            if(!filemodel.addFile(file,"")){
                counter++;
                missFile.append(file.getAbsolutePath());
                missFile.append("\n");
            };
        }
        
        if(counter == 0) 
              JOptionPane.showMessageDialog(null, "Add " + files.length 
                      +  " files in the list" ,
                      "Open files", JOptionPane.INFORMATION_MESSAGE);
        else{
            missFile.insert(0, "Add " + (files.length - counter )
                      +  " files choosen  in the list. The following files has existe in list \n");
            JOptionPane.showMessageDialog(null, missFile.toString(),"Open files", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 

        
    }     
    
    public void setResultTable(){
        Set<String> langueList = XMLUtils.getLangueList().keySet();
        String[][] data = new String[6][langueList.size()+1];
        String[] item = new String[langueList.size()+1];
        int j = 1;
        item[0] = "";
        data[1][0] = "Nombre de Texte";
        data[2][0] = "Résultat positif";
        data[3][0] = "Résultat négative";
        data[4][0] = "Précision positif";
        data[5][0] = "Temps d'algo";
        
        
        for (String langue : langueList) {
            item[j] = langue;
            data[0][j] = langue;
            int number = 0, positif = 0, negatif = 0;
            double accuracy = 0;
            Long temp = 0L;
            
            for(int i=0 ; i<filemodel.getRowCount();i++){
                if(filemodel.getValueAt(i,1).equals(langue)){
                    number ++;
                    Long time =Long.parseLong((String) filemodel.getValueAt(i, 8)) ;
                    temp += time;
                    
                    if(filemodel.getValueAt(i,1).equals(filemodel.getValueAt(i, 2))){
                        positif ++;
                    }else{
                        negatif ++;
                    }
                }
             }
            
            accuracy = positif * 1.0 / number;
            data[1][j] = Integer.toString(number);
            data[2][j] = Integer.toString(positif);
            data[3][j] = Integer.toString(negatif);
            data[4][j] = Double.toString(accuracy);
            data[5][j] = toString(temp);
            j++;
            resultsTableModel.setDataVector(data, item);
            resultsTableModel.fireTableStructureChanged();
        }
        
    }
    
    public String toString(long nanos) {
        TimeUnit unit = chooseUnit(nanos);
        double value = (double) nanos / NANOSECONDS.convert(1, unit);

        // Too bad this functionality is not exposed as a regular method call
        NumberFormat.getNumberInstance().setMaximumFractionDigits(4);
        return NumberFormat.getNumberInstance().format(value) + " " + abbreviate(unit);
      }
    
    private static TimeUnit chooseUnit(long nanos) {
        if (DAYS.convert(nanos, NANOSECONDS) > 0) {
          return DAYS;
        }
        if (HOURS.convert(nanos, NANOSECONDS) > 0) {
          return HOURS;
        }
        if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
          return MINUTES;
        }
        if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
          return SECONDS;
        }
        if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
          return MILLISECONDS;
        }
        if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
          return MICROSECONDS;
        }
        return NANOSECONDS;
      }

      private static String abbreviate(TimeUnit unit) {
        switch (unit) {
          case NANOSECONDS:
            return "ns";
          case MICROSECONDS:
            return "\u03bcs"; // μs
          case MILLISECONDS:
            return "ms";
          case SECONDS:
            return "s";
          case MINUTES:
            return "min";
          case HOURS:
            return "h";
          case DAYS:
            return "d";
          default:
            throw new AssertionError();
        }
      }
    }
