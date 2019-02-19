public class Folder extends DirectoryObject{
    private static final long serialVersionUID = 1L;
    private String name;
    
    public Folder(String name)
    {
        this.name = name;
    }
    
    public boolean getAllowsChildren()
    {
        return true;
    }
    
    public String toString()
    {
        return this.name;
    }
}
