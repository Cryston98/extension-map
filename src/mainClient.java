
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
public class mainClient {
	   static int count = 0;
	   static int progress=0;	
	   static int countComp=0;
	   private static DecimalFormat df2 = new DecimalFormat("#.##");
	   static ArrayList<String> arguments;
	   
	public static void main(String[] args) throws JSONException {
		
		arguments = readPropertiesFile();
		if(arguments == null)
		{return;}
		else
		{
			if(args.length!=0) {
				System.out.println("usage: <program>");
				return;
			}
			String outputFile=arguments.get(2)+"-output.json";
			String path=arguments.get(0);
			
			
			getFile(path);
			ArrayList<Path> pathAllFile =new ArrayList<Path>();
			ArrayList<Component> extComp =new ArrayList<Component>();
			 
		
			System.out.println("System analysis started...");
			try {
				
				Files.walk(new File(path).toPath())
						.filter(Files::isRegularFile)
						.forEach(it ->{pathAllFile.add(it);});
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			for(int j=0;j<pathAllFile.size();j++) 
			{
				   String filePath=pathAllFile.get(j).toString();
				   String extension=getExtension(filePath);
				  if(!extension.equals("")) 
				  {
					   int valid=1;
					   for(int k=0;k<extComp.size();k++) 
					   {
						   if(extComp.get(k).getName().equals(extension))
						   {
							   valid=0;
							   break;
						   }
					   }
					   if(valid==1) {
						   extComp.add(new Component(extension));
					   }
				  }
			}
				System.out.println("+ Get components of the system by extension - COMPLETED !");
			
			 
			for(int w=0;w<pathAllFile.size();w++) 
			  {
				  String extension=getExtension(pathAllFile.get(w).toString());
				  for(int q=0;q<extComp.size();q++) 
				   {
					   if(extension.equals(extComp.get(q).getName()))
					   {
						  extComp.get(q).updateFile(pathAllFile.get(w).toString());
					   }   
				   }
			   }
				System.out.println("+ Construct all components  - COMPLETED !");
			    
			   JSONArray objF = new JSONArray();
			   for(int h=0;h<extComp.size();h++) {
					   for(int p=0;p<extComp.get(h).getSize();p++) 
					   {
						   JSONObject obj = new JSONObject();
						   obj.put("name",extComp.get(h).getName());
						   obj.put("file",getCorectFormat(extComp.get(h).getFile(p)));
						   obj.put("category","Extension Map");
						   obj.put("value",getFileSizeInBytes(extComp.get(h).getFile(p)));
						   objF.put(obj); 
					   }
					   System.out.println("+ Write result for component <."+extComp.get(h).getName()+"> - COMPLETED !");
				   }
			   
			   try(FileWriter file = new FileWriter(outputFile))
			   {
				file.write(objF.toString());
				file.flush();
				System.out.println("Extracted "+pathAllFile.size()+" files from project "+arguments.get(2));
		
			   }catch(IOException e) {
				   e.printStackTrace();
			   }
			   
			
		}
		   
	}
  private static void getFile(String dirPath){
	        File f = new File(dirPath);
	        File[] files = f.listFiles();

	        if (files != null)
	        for (int i = 0; i < files.length; i++) {
	            count++;
	            File file = files[i];
	            if (file.isDirectory()) {   
	                 getFile(file.getAbsolutePath()); 
	            }
	        }
	    }
	   
 public static String getExtension(String fileName) {
		    char ch;
		    int len;
		    if(fileName==null || 
		            (len = fileName.length())==0 || 
		            (ch = fileName.charAt(len-1))=='/' || ch=='\\' || //in the case of a directory
		             ch=='.' ) //in the case of . or ..
		        return "";
		    int dotInd = fileName.lastIndexOf('.'),
		        sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		    if( dotInd<=sepInd )
		        return "";
		    else
		        return fileName.substring(dotInd+1).toLowerCase();
		}
  private static ArrayList<String> readPropertiesFile()
  {
	try {
		File file = new File("config.properties");
		FileInputStream inputStream = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(inputStream);
		inputStream.close();
		
		Enumeration keys = properties.keys();
		System.out.println("Reading property file: "+file.getName());
		ArrayList<String> arguments = new ArrayList<String>();
		while(keys.hasMoreElements())
		{
			String key = (String)keys.nextElement(); //projectName and projectPath
			String value = properties.getProperty(key);
			arguments.add(value);
		}
	return arguments;
	}
	catch(Exception e)
	{
		System.out.print(e.getMessage());
		return null;
	}
	
  }
  public static long getFileSizeInBytes(String fileName) {
	  long sizeFile=0;
	  String aux_path=fileName.replace("\\", "/");
	  File file = new File(aux_path);
      if (file.exists()) {
          // size of a file (in bytes)
          sizeFile= file.length();
      }
      return sizeFile;
  }
  private static String getCorectFormat(String filePath)
  {
	  String path="";
	  String os_type=arguments.get(1);
	 if(os_type.equals("windows"))
	 {
		String p=arguments.get(0)+"\\";
	  	String aux_path = filePath.replace(p,"");
	  	path=aux_path.replace("\\", "/");  
	  	return path;
	 }else {
		 	//String p = arguments.get(0).substring(0,8).trim();// jast define start point so output is "123"
			String p=arguments.get(0)+"/";
			String aux_path = filePath.replace(p,"");
		  	path=aux_path.replace("\\", "/");  
		  	//System.out.println("\nPath: "+path);
		  	return path;
	  }
  }
}
