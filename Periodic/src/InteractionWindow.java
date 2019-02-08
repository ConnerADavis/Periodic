import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.BorderLayout;

public class InteractionWindow extends JFrame 
{
    
    public InteractionWindow()
    {
        setLayout(new BorderLayout());
        JScrollPane scrollpane = new JScrollPane();
        add(scrollpane, BorderLayout.WEST);
        JInternalFrame internalFrame = new JInternalFrame();
        JTextPane editor  = new JTextPane();
        JEditorPane display = new JEditorPane("text/html", "<!DOCTYPE html>\r\n" + 
                "<html>\r\n" + 
                "<body>\r\n" + 
                "\r\n" + 
                "<h1>My First Heading</h1>\r\n" + 
                "<p>My first paragraph.</p>\r\n" + 
                "\r\n" + 
                "</body>\r\n" + 
                "</html>");
        internalFrame.setLayout(new BorderLayout());
        internalFrame.add(editor, BorderLayout.WEST);
        internalFrame.add(display, BorderLayout.EAST);
        add(internalFrame, BorderLayout.CENTER);
        
        internalFrame.setVisible(true);
        pack();
    }
    
    public static void main(String[] args) 
    {
        JFrame f = new InteractionWindow();
        f.setVisible(true);
    }
}
