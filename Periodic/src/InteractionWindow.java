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
import javax.swing.tree.DefaultTreeModel;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private String username;
    private String password;
    public InteractionWindow()
    {
        
        root = SaveAndLoadUtilities.loadDirectoryStructure();
        
        File container = new File("Files");
        if(!container.exists())
        {
            container.mkdir();
        }
        
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
        
        filesDisplay.addMouseListener(new MouseListener() 
        {

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount() == 2)
				{
					Object toOpen = filesDisplay.getPathForLocation(e.getX(), e.getY()).getLastPathComponent();
					if(toOpen instanceof TextFile)
					{
						currentFile = (TextFile)toOpen;
						String content = SaveAndLoadUtilities.loadFile(currentFile);
						editor.setText(content);
						Parser parser = Parser.builder().build();
						Node document = parser.parse(content);
						HtmlRenderer renderer = HtmlRenderer.builder().build();
						String html = renderer.render(document);
						display.setText(html);
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
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
        
        JMenu fileMenu = new JMenu("File");
        JMenu cloudMenu = new JMenu("Cloud");
        
        JMenuItem saveFile = new JMenuItem("save changes");
        JMenuItem saveNew = new JMenuItem("save current file to selected folder");
        JMenuItem createNewFolder = new JMenuItem("create new folder");
        JMenuItem delete = new JMenuItem("delete");
        

        JMenuItem login = new JMenuItem("log in");
        JMenuItem logout = new JMenuItem("log out");
        JMenuItem upload = new JMenuItem("upload files");
        JMenuItem download = new JMenuItem("download files");
        
        saveFile.addActionListener(new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(currentFile == null)
				{
					dialogMessage("current file is not saved anywhere");
					return;
				}
				String content = editor.getText();
				SaveAndLoadUtilities.saveFile(currentFile, content);
			}
        });
        
        saveNew.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(currentFile == null)
                {
                    String name = getNameInput("File");
                    boolean unique = false;
                    String location = "";
                    do 
                    {
                        location = name + ThreadLocalRandom.current().nextInt(0, 600000) + ".txt";
                        File f = new File(location);
                        unique = !f.exists();
                    } while(unique == false);
                    currentFile = new TextFile(name, location);
                }
                String content = editor.getText();
                SaveAndLoadUtilities.saveFile(currentFile, content);
                
                Object folderOrFile = filesDisplay.getSelectionPath().getLastPathComponent();
                if(folderOrFile instanceof Folder)
                {
                	Folder containingFolder = (Folder)folderOrFile;
                	containingFolder.addChild(currentFile);
                	filesDisplay.updateUI();
                }
                else
                {
                	dialogMessage("You must have one folder selected to save the file in.");
                }
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
                    dialogMessage("No folder selected or object selected is not a folder.");
                }
            }
        });
        
        delete.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object folderOrFile = filesDisplay.getSelectionPath().getLastPathComponent();
                if(folderOrFile instanceof DirectoryObject)
                {
                    DirectoryObject toDelete = (DirectoryObject)folderOrFile;
                    Folder parent = (Folder)(toDelete.getParent());
                    if(parent != null)
                    {
                        parent.removeChild(toDelete);
                        filesDisplay.updateUI();
                    }
                    else
                    {
                        System.out.println("Cannot delete root");
                    }
                }
                else
                {
                    dialogMessage("No folder selected or object selected is not a folder.");
                }
            }
        });
        

        login.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMessage();
            }
        });
        
        logout.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                username = null;
                password = null;
            }
        });
        
        upload.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(username == null || password == null)
                {
                    dialogMessage("Please log in before attempting to upload files");
                    loginMessage();
                }
                
                SaveAndLoadUtilities.saveDirectoryStructure(root);
                String content = editor.getText();
                SaveAndLoadUtilities.saveFile(currentFile, content);
                
                if(FileTransferUtilities.uploadFiles(username, password))
                {
                    dialogMessage("Successfully uploaded files");
                }
                else
                {
                    dialogMessage("Failed to upload files");
                }
            }
            
        });
        
        download.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(username == null || password == null)
                {
                    dialogMessage("Please log in before attempting to upload files");
                    loginMessage();
                }
                
                if(FileTransferUtilities.downloadFiles(username, password))
                {
                    root = SaveAndLoadUtilities.loadDirectoryStructure();
                    
                    Folder tmp = root;
                    
                    DefaultTreeModel model = (DefaultTreeModel)filesDisplay.getModel();
                    model.setRoot(root);                    
                    model.reload(root);
                    
                    filesDisplay.updateUI();
                    
                    dialogMessage("Successfully downloaded remote files");
                }
                else
                {
                    dialogMessage("Failed to download files");
                }
            }
        });
        
        /*JMenuItem debug = new JMenuItem("refresh tree");
        
        debug.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println(root);
                filesDisplay.updateUI();
            }
        });*/
        
        fileMenu.add(saveFile);
        fileMenu.add(saveNew);
        fileMenu.add(createNewFolder);
        fileMenu.add(delete);
        
        
        cloudMenu.add(login);
        cloudMenu.add(logout);
        cloudMenu.add(upload);
        cloudMenu.add(download);
        
        //menu.add(debug);
        
        menuBar.add(fileMenu);
        menuBar.add(cloudMenu);
        add(menuBar, BorderLayout.NORTH);
        setSize(1024, 768);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Periodic");
        
        
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent arg0)
            {
                SaveAndLoadUtilities.saveDirectoryStructure(root);
                dispose();
            }
        });
        
        setLocationRelativeTo(null);
    }
    
    private String getNameInput(String folderOrFile)
    {
        String in = JOptionPane.showInputDialog(folderOrFile + " name");
        return in;
    }
    
    private void dialogMessage(String str)
    {
        JOptionPane.showMessageDialog(this, new JLabel(str), 
                "Message",JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void loginMessage()
    {
        username = JOptionPane.showInputDialog("Username");
        password = JOptionPane.showInputDialog("Password");
    }
    
    public static void main(String[] args) 
    {
        JFrame f = new InteractionWindow();
        f.setVisible(true);
    }
}
