import java.util.ArrayList;

public class Folder {
    
    private ArrayList<Folder> folders;
    private ArrayList<TextFile> files;
    private String name;
    
    public Folder(String name)
    {
        this.name = name;
        folders = new ArrayList<Folder>();
        files = new ArrayList<TextFile>();
    }
    
    public boolean addSubFolder(Folder folder)
    {
        if(!folder.getSubFolders().isEmpty())
        {
            return false;
        }
        folders.add(folder);
        return true;
    }
    
    public boolean addFile(TextFile file)
    {
        for(TextFile f: files)
        {
            if(f.equals(file))
            {
                // You can't add the same file to a folder twice
                return false;
            }
        }
        files.add(file);
        return true;
    }
    
    public ArrayList<Folder> getSubFolders()
    {
        ArrayList<Folder> toReturn = new ArrayList<Folder>();
        for(Folder f: folders)
        {
            try
            {
                toReturn.add((Folder)f.clone());
            }
            catch(CloneNotSupportedException e)
            {
                System.out.println("failed to clone object");
                return new ArrayList<Folder>();
            }
        }
        return toReturn;
    }
    public ArrayList<TextFile> getFiles()
    {
        ArrayList<TextFile> toReturn = new ArrayList<TextFile>();
        for(TextFile f: files)
        {
            toReturn.add((TextFile)f.clone());
        }
        return toReturn;
    }
}
