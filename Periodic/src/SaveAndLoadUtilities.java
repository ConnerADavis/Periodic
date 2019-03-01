import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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
    
    public static Folder loadDirectoryStructure()
    {
        Folder root = new Folder("root");
        try 
        {
            FileInputStream saveFile = new FileInputStream("root.sav");
            ObjectInputStream save = new ObjectInputStream(saveFile);
            Object obj = save.readObject();
            root = (Folder)obj;
            save.close();
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
        if(file == null)
        {
            return false;
        }
        String location = file.getLocation();
        File realFile = new File(location);
        try 
        {
            FileWriter writer = new FileWriter(realFile);
            writer.write(content);
            writer.close();
            return true;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String loadFile(TextFile file)
    {
    	String toReturn = "";
    	String location = file.getLocation();
    	try 
    	{
    		toReturn = new String(Files.readAllBytes(Paths.get(location)));
    		return toReturn;
		} 
    	catch (FileNotFoundException e) 
    	{
			e.printStackTrace();
			return toReturn;
		} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    		return toReturn;
		}
    }
}
