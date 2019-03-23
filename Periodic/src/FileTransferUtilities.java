import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
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
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        try {
            ftp.connect("ec2-18-220-0-36.us-east-2.compute.amazonaws.com");
            ftp.login(username, password);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            
            File container = new File("Files");
            File[] uploadFiles = container.listFiles();
            for (File uploadfile : uploadFiles)
            {
                if(uploadfile.isFile())
                {
                    System.out.println(uploadfile.getName());
                    InputStream input = new FileInputStream(uploadfile);
                    ftp.storeFile(uploadfile.getName(), input);
                }
            }
            ftp.logout();
            ftp.disconnect();

            return true;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        /*FTPClient client = new FTPClient();
        
        try {
            client.connect("ec2-18-220-0-36.us-east-2.compute.amazonaws.com");
            client.login(username, password);
            client.createDirectory("Files");
            
            File container = new File("Files");
            File[] uploadFiles = container.listFiles();
            for (File uploadfile : uploadFiles)
            {
                if(uploadfile.isFile())
                {
                    System.out.println(uploadfile.getName());
                    client.upload(uploadfile);
                }
            }
            /*File[] listofFiles = container.listFiles();
            for(File f: listofFiles)
            {
                if (f.getName().contains(".jar"))
                {
                    continue;
                }
                client.upload(f);
            }*/
    }
}
