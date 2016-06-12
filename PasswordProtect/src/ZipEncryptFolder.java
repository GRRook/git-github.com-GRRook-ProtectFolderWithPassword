
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

public class ZipEncryptFolder
{
    private String password;
    private List<String> fileList;
    private String OUTPUT_ZIP_FILE = "";
    private String SOURCE_FOLDER = "";
    private static final String EXTENSION = "zip";
    private ZipFile zipFile;

    public ZipEncryptFolder(String password, String output, String source)
    {
    	fileList = new ArrayList<String>();
    	this.OUTPUT_ZIP_FILE = output;
    	this.SOURCE_FOLDER = source;
        this.password = password;
    }

    public void pack() throws ZipException
    {
    	ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        zipParameters.setPassword(password);
        //"pass", "D:\\MyFile.zip", "D:\\zip"
        zipFile = new ZipFile(this.OUTPUT_ZIP_FILE);
        
    	for(String file : this.fileList){			   		

    		System.out.println("folder: " + this.SOURCE_FOLDER + "\\" + file);     		
    		zipFile.addFile(new File(this.SOURCE_FOLDER + "\\" + file), zipParameters); 		
    	}
    	
    	
        System.out.println("Done");
    }

    public void unpack(String sourceZipFilePath, String extractedZipFilePath) throws ZipException
    {
        ZipFile zipFile = new ZipFile(sourceZipFilePath + "." + EXTENSION);

        if (zipFile.isEncrypted())
        {
            zipFile.setPassword(password);
        }

        zipFile.extractAll(extractedZipFilePath);
    }
    
    //recursive call
    public void generateFileList(File node){

    	//add file only
    	System.out.println("absolute path : " + node.getAbsolutePath().toString());
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsolutePath().toString()));
		}
			
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}
 
    }
    
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
    
    public static void main(String[] arguments) throws ZipException
    {
    	//ZipEncryptFolder ZipEncryptFolder = new ZipEncryptFolder("password");
    	//ZipEncryptFolder.pack("encrypt-me.txt");
    	//ZipEncryptFolder.unpack("encrypt-me", "D:\\");
    }
}