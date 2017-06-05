package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

public class MainFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HRIGHT = 600;
    private static final Dimension DIMENSION = 
            java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private JTabbedPane jTabbedPane; 

    private JPanel contentPane;
    
    private Button openFile;
    private Button LoadFiles;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        setSize(DEFAULT_WIDTH,DEFAULT_HRIGHT);
        setLocation(DIMENSION.width/2 - DEFAULT_WIDTH/2, DIMENSION.height/2 - DEFAULT_HRIGHT/2);
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/Language-48.png"));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Detection de langue");
        
        /*** Set JTabbedPane*/
        jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Detecte texte", new DetectionPanel());
        jTabbedPane.add("Detecte files", new PerformencePanel());
        jTabbedPane.add("Corpus", new CorpusPanel());
        
        getContentPane().add(jTabbedPane, "Center");
        
        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);
        
    }
    
    public void getTabStatus(){

        
        
    }

}
