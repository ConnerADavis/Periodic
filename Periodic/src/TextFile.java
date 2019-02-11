
public class TextFile {
    private String name;
    private String actualLocation;
    
    public TextFile(String name, String location)
    {
        this.name = name;
        this.actualLocation = location;
    }
    
    public boolean equals(Object other)
    {
        if (other == null)
        {
            return false;
        }
        if(!(other instanceof TextFile))
        {
            return false;
        }
        TextFile f = (TextFile)other;
        if(f.getLocation() != this.actualLocation)
        {
            return false;
        }
        return true;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getLocation()
    {
        return this.actualLocation;
    }
    
    public Object clone()
    {
        try 
        {
            return super.clone();
        }
        catch (CloneNotSupportedException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
