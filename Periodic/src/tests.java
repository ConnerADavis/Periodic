import javax.swing.JTree;

public class tests {
    public static void main(String[] args) {
        Folder root = new Folder("root");
        Folder a = new Folder("a");
        Folder b = new Folder("b");
        Folder aa = new Folder("aa");
        Folder ab = new Folder("ab");
        Folder ba = new Folder("ba");
        Folder bb = new Folder("bb");
        TextFile x = new TextFile("x", "E:\\x.txt");
        TextFile y = new TextFile("y", "C:\\y.txt");
        TextFile z = new TextFile("z", "F:\\z.txt");
        
        bb.addChild(z);
        b.addChild(ba);
        b.addChild(bb);
        a.addChild(aa);
        a.addChild(ab);
        a.addChild(y);
        root.addChild(a);
        root.addChild(b);
        root.addChild(x);
        
        
        String expected = root.toString();
        
        SaveAndLoadUtilities.saveDirectoryStructure(root);
        
        Folder rootCopy = SaveAndLoadUtilities.loadDirectoryStructure();
        
        String actual = rootCopy.toString();
        System.out.println(expected);
        System.out.println(actual);
        System.out.println(expected.equals(actual));
        
        JTree tree = new JTree(root);
        
        System.out.println("");
    }
}
