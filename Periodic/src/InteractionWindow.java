import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class InteractionWindow extends JFrame 
{
    private static final long serialVersionUID = 1L;
    private double evenSplit = 0.5;
    private double minorFeature = 0.1;
    private Folder root;
    private TextFile currentFile;
    public InteractionWindow()
    {
        
        root = SaveAndLoadUtilities.loadDirectoryStructure();
        
        // TODO: declutter this
        setLayout(new BorderLayout());
        JTree filesDisplay = new JTree(root, true);
        JSplitPane outerSplitPane = new JSplitPane();
        JSplitPane innerSplitPane = new JSplitPane();
        JTextPane editor  = new JTextPane();
        JEditorPane display = new JEditorPane("text/html", "");
        display.setEditable(false);
        
        editor.addKeyListener(new KeyListener() 
        {

            @Override
            public void keyTyped(KeyEvent e) {
                /*String markdown = editor.getText();
                Parser parser = Parser.builder().build();
                Node document = parser.parse(markdown);
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                display.setText(html);*/
            }

            @Override
            public void keyPressed(KeyEvent e) {
                /*String markdown = editor.getText();
                Parser parser = Parser.builder().build();
                Node document = parser.parse(markdown);
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                display.setText(html);*/
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String markdown = editor.getText();
                Parser parser = Parser.builder().build();
                Node document = parser.parse(markdown);
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(document);
                display.setText(html);
            }
        });
        
        innerSplitPane.add(editor, JSplitPane.LEFT);
        innerSplitPane.add(display, JSplitPane.RIGHT);
        innerSplitPane.setResizeWeight(evenSplit);
        innerSplitPane.setVisible(true);
        
        outerSplitPane.add(filesDisplay, JSplitPane.LEFT);
        outerSplitPane.add(innerSplitPane, JSplitPane.RIGHT);
        outerSplitPane.setResizeWeight(minorFeature);
        add(outerSplitPane, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Placeholder Menu Name");
        JMenuItem login = new JMenuItem("log in");
        JMenuItem saveFile = new JMenuItem("save changes");
        JMenuItem saveNew = new JMenuItem("save to selected location");
        JMenuItem createNewFolder = new JMenuItem("create new folder");
        
        saveNew.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentFile == null)
                {
                    String name = getNameInput("File");
                    boolean unique = false;
                    String location = "";
                    do 
                    {
                        location = ThreadLocalRandom.current().nextInt(0, 600000) + ".txt";
                        File f = new File(location);
                        unique = !f.exists();
                    } while(unique == false);
                    currentFile = new TextFile(name, location);
                }
                String content = editor.getText();
                SaveAndLoadUtilities.saveFile(currentFile, content);
            }
        });
        
        createNewFolder.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object folderOrFile = filesDisplay.getSelectionPath().getLastPathComponent();
                if(folderOrFile instanceof Folder)
                {
                    Folder containingFolder = (Folder)folderOrFile;
                    String folderName = getNameInput("Folder");
                    Folder newFolder = new Folder(folderName);
                    containingFolder.addChild(newFolder);
                    filesDisplay.updateUI();
                }
                else
                {
                    noFolderSelected();
                }
            }
        });
        
        
        menu.add(login);
        menu.add(saveFile);
        menu.add(saveNew);
        menu.add(createNewFolder);
        menuBar.add(menu);
        add(menuBar, BorderLayout.NORTH);
        setSize(1024, 768);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent arg0)
            {
                SaveAndLoadUtilities.saveDirectoryStructure(root);
                dispose();
            }
        });
    }
    
    private String getNameInput(String folderOrFile)
    {
        String in = JOptionPane.showInputDialog(folderOrFile + " name");
        return in;
    }
    
    private void noFolderSelected()
    {
        JOptionPane.showMessageDialog(this, new JLabel("No Folder Selected"), 
                "Message",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) 
    {
        JFrame f = new InteractionWindow();
        f.setVisible(true);
    }
}
