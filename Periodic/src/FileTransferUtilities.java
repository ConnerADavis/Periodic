import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

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
            check(ftp, "cd", ftp.changeWorkingDirectory("username"));
            
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
    
    
    private static void check(FTPClient ftp, String cmd, boolean succeeded) throws IOException 
    {
        if (!succeeded) 
        {
          throw new IOException("FTP error: " + ftp.getReplyString());
        }
    }
}
