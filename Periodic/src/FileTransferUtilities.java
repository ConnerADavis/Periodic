import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/*import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;*/

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
                    System.out.println(uploadfile.getName());
                    System.out.println(ftp.printWorkingDirectory());
                    System.out.println(ftp.printWorkingDirectory());
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
        
        try {
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
            return true;
        }
        catch (IOException e) {
            
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
}
