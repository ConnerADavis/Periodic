import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveAndLoadUtilities 
{
    public static boolean saveDirectoryStructure(Folder folder)
    {
        try 
        {
            FileOutputStream saveFile = new FileOutputStream("root.sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(folder);
            save.close();
            return true;
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Folder loadDirectoryStructure(String fileName)
    {
        Folder root = new Folder("root");
        try 
        {
            FileInputStream saveFile = new FileInputStream("root.sav");
            ObjectInputStream save = new ObjectInputStream(saveFile);
            Object obj = save.readObject();
            root = (Folder)obj;
            return root;
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
            return root;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return root;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return root;
        }
    }
    
    public static boolean saveFile(TextFile file, String content)
    {
        String location = file.getLocation();
        File realFile = new File(location);
        return true;
    }
}
