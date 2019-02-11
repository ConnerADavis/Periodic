import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InteractionWindow extends JFrame 
{
    private double evenSplit = 0.5;
    private double minorFeature = 0.1;
    public InteractionWindow()
    {
        // TODO: declutter this
        setLayout(new BorderLayout());
        JScrollPane scrollpane = new JScrollPane();
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
        
        outerSplitPane.add(scrollpane, JSplitPane.LEFT);
        outerSplitPane.add(innerSplitPane, JSplitPane.RIGHT);
        outerSplitPane.setResizeWeight(minorFeature);
        add(outerSplitPane, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Placeholder Menu Name");
        JMenuItem login = new JMenuItem("log in");
        JMenuItem saveFile = new JMenuItem("save");
        menu.add(login);
        menu.add(saveFile);
        menuBar.add(menu);
        add(menuBar, BorderLayout.NORTH);
        setSize(800, 600);
    }
    
    public static void main(String[] args) 
    {
        JFrame f = new InteractionWindow();
        f.setVisible(true);
    }
}
