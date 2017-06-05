package main;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import com.google.common.base.Stopwatch;

import java.beans.*;
import java.io.File;
import java.io.FileNotFoundException;

import corpus.Propriete;

public class CorpusPanel extends JPanel implements TableModelListener, ActionListener , PropertyChangeListener{
    private JTable table;
    CorpusTableModel corpusModel;
    private JButton openButton;
    private JButton startButton;
    private JButton removeButton;
    private JPanel panel;
    private JPanel panel_1;
    private JProgressBar progressBar;
    private JComboBox<String> jComboBox;
    private JButton addLanguage;
    private DefaultComboBoxModel<String> comBoxModel;
    private Task task;
    

class Task extends SwingWorker<Void, Void> {
    private long length = 0L;
    
    public Task() {

        for (String[] file : corpusModel) {
            length += Double.parseDouble(file[6]);
        }
    }
    
    
    /*
     * Main task. Executed in background thread.
     */
    @Override
    public Void doInBackground() {
        Double progress = 0.0;
        //Initialize progress property.
        setProgress(0);
        int i = 0;
        for (String[] file : corpusModel) {
            try {
                if(file[3].equals(CorpusTableModel.READY)){
                    
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    
                    corpusModel.setValueAt(CorpusTableModel.ANALYZE, i, 3);
                    corpusModel.learnFromFile(file[1], file[2]);
                    corpusModel.setValueAt(CorpusTableModel.EXISTED, i, 3);
                    
                    stopwatch.stop();
                    
                    corpusModel.setValueAt(stopwatch.toString(), i, 5);
                }
                progress += Double.parseDouble(file[6]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
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
    }
}


    /**
     * Create the panel.
     */
    public CorpusPanel() {

        
        setLayout(new BorderLayout());
        
        corpusModel = new CorpusTableModel();
        table = new JTable(corpusModel);
        JScrollPane jScrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        
        this.jComboBox = new JComboBox<String>();
        this.comBoxModel = new DefaultComboBoxModel<String>(
                corpusModel.getLangueList() );   
        jComboBox.setModel(comBoxModel);
        
        TableColumn tableCol = this.table.getColumnModel().getColumn(1);
        tableCol.setCellEditor(new DefaultCellEditor(jComboBox));
        
        
        add(jScrollPane,BorderLayout.CENTER);
        
        panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
       
        progressBar= new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        
        panel.add(progressBar);
        
        panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        /** add openButton button*/
        openButton = new JButton("Open Files",new ImageIcon("res/open.ico"));
        openButton.setPreferredSize(new Dimension(100, 33));
        openButton.addActionListener(this);
        panel_1.add(openButton);
        
        /** add remove button*/
        removeButton = new JButton("remove Files",new ImageIcon("res/open.ico"));
        removeButton.setPreferredSize(new Dimension(150, 33));
        removeButton.addActionListener(this);
        panel_1.add(removeButton);
        
        /*** add Start button*/
        startButton = new JButton("Start",new ImageIcon("res/open.ico"));
        startButton.setPreferredSize(new Dimension(100, 33));
        startButton.addActionListener(this);
        panel_1.add(startButton);
        
        /*** add addLanguage button*/
        addLanguage = new JButton("Add new Language");
        addLanguage.setPreferredSize(new Dimension(150, 33));
        addLanguage.addActionListener(this);
        panel_1.add(addLanguage);
        

        
        
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
        }else if(e.getSource() == this.addLanguage){
            addLanguageAction();
        }else if(e.getSource() == this.startButton){
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

    private void addLanguageAction() {
        String inputValue = JOptionPane.showInputDialog("Please input the language name:");
        if(!this.corpusModel.addLanguage(inputValue)){
            JOptionPane.showMessageDialog(this, "This language has exist!");
            return;
        }
       
        this.comBoxModel.addElement(inputValue);
        
        
    }

    private void removeButtonAction() {
        int[] row = table.getSelectedRows();
            corpusModel.removeRow(row);

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
            if(!corpusModel.addFile(file,"")){
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

}

