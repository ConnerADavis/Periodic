import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class FileTransferUtilities {
    
    public static boolean uploadFiles(String username, String password)
    {
        
        FTPClient ftp = new FTPClient();
        
        try {
            ftp.connect("ec2-18-220-0-36.us-east-2.compute.amazonaws.com");
            check(ftp, "login", ftp.login(username, password));
            System.out.println("Successfully Connected to server");
            
            File container = new File("Files");
            File[] uploadFiles = container.listFiles();
            check(ftp, "cd", ftp.changeWorkingDirectory(username));
            
            FTPFile[] remoteFiles = ftp.listFiles();
            
            for (FTPFile f : remoteFiles)
            {
                for(File g : uploadFiles)
                {
                    if(f.getName().equals(g.getName()))
                    {
                        check(ftp, "delete", ftp.deleteFile(f.getName()));
                    }
                }
            }
            
            for (File uploadfile : uploadFiles)
            {
                if(uploadfile.isFile())
                {
                    InputStream input = new FileInputStream(uploadfile.getAbsolutePath());
                    check(ftp, "store", ftp.storeFile(uploadfile.getName(), input));
                }
            }
            return true;
        }
        catch (IOException e) {
            
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean downloadFiles(String username, String password)
    {
        FTPClient ftp = new FTPClient();
        
        try 
        {
            ftp.connect("ec2-18-220-0-36.us-east-2.compute.amazonaws.com");
            check(ftp, "login", ftp.login(username, password));
            System.out.println("Successfully Connected to server");
            
            check(ftp, "cd", ftp.changeWorkingDirectory(username));
            
            FTPFile[] toDownload = ftp.listFiles();
            
            String[] fileNames = new String[toDownload.length];
            
            for (int i = 0; i < toDownload.length; i++)
            {
                fileNames[i] = toDownload[i].getName();
            }
            
            for (String file : fileNames)
            {
                String localName = "Files/" + file;
                FileOutputStream write = new FileOutputStream(new File(localName));
                ftp.retrieveFile(file, write);
                write.flush();
                write.close();
            }
            ftp.disconnect();
            return true;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    
    private static void check(FTPClient ftp, String cmd, boolean succeeded) throws IOException 
    {
        if (!succeeded) 
        {
          throw new IOException("FTP error: " + ftp.getReplyString());
        }
    }
    
    public static boolean checkLogin(String username, String password)
    {
    	FTPClient ftp = new FTPClient();
        try 
        {
			ftp.connect("ec2-18-220-0-36.us-east-2.compute.amazonaws.com");
            check(ftp, "login", ftp.login(username, password));
            ftp.disconnect();
        	return true;
		} 
        catch (IOException e) 
        {
        	return false;
		}
    }

    public static boolean syncFiles(String username, String password) 
    {
        FTPClient ftp = new FTPClient();
        try 
        {
            ftp.connect("ec2-18-220-0-36.us-east-2.compute.amazonaws.com");
            check(ftp, "login", ftp.login(username, password));
            System.out.println("Successfully Connected to server");
            
            check(ftp, "cd", ftp.changeWorkingDirectory(username));
            
            ArrayList<FTPFile> remote = new ArrayList<FTPFile>(Arrays.asList(ftp.listFiles()));
            ArrayList<File> local = new ArrayList<File>(Arrays.asList(new File("Files").listFiles()));
            ArrayList<String> remoteNames = new ArrayList<String>();
            ArrayList<String> localNames = new ArrayList<String>();
            
            for(FTPFile r : remote)
            {
                remoteNames.add(r.getName());
            }
            
            for(File f : local)
            {
                localNames.add(f.getName());
            }
            
            for(FTPFile r : remote)
            {
                if(!localNames.contains(r.getName()))
                {
                    String localName = "Files/" + r.getName();
                    FileOutputStream write = new FileOutputStream(new File(localName));
                    ftp.retrieveFile(r.getName(), write);
                    write.flush();
                    write.close();
                }
                else
                {
                    for(File f : local)
                    {
                        if(f.getName().equals(r.getName()))
                        {
                            if(new Date(f.lastModified()).before(r.getTimestamp().getTime()))
                            {
                                String localName = "Files/" + r.getName();
                                FileOutputStream write = new FileOutputStream(new File(localName));
                                ftp.retrieveFile(r.getName(), write);
                                write.flush();
                                write.close();
                            }
                        }
                    }
                }
            }
            
            for(File f : local)
            {
                if(!remoteNames.contains(f.getName()))
                {
                    InputStream input = new FileInputStream(f.getAbsolutePath());
                    check(ftp, "store", ftp.storeFile(f.getName(), input));
                }
                else
                {
                    for(FTPFile r : remote)
                    {
                        if(r.getName().equals(f.getName()))
                        {
                            if(new Date(f.lastModified()).after(r.getTimestamp().getTime()))
                            {
                                InputStream input = new FileInputStream(f.getAbsolutePath());
                                check(ftp, "store", ftp.storeFile(f.getName(), input));
                            }
                        }
                    }
                }
            }
            
            ftp.disconnect();
            return true;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}
