
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class mainClient {
	   static int  count = 0;
	   static int progress=0;	
	   static int  countComp=0;
	
	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		if(args.length!=2) {
			System.out.print("usage: <program> --of <outputfile.json> --ftg <baseRooth>");
			return;
		}else 
		
		{
		String outputFile=args[0];
		String path=args[1];
		
		getFile(path);
		System.out.println("size: "+count);
		ArrayList<Path> pathAllFile =new ArrayList();
		ArrayList<Component> extComp =new ArrayList();
		extComp.add(new Component("@"));
		 
	
		  
		int ctn=0;
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
			   System.out.println("Ext : "+extension);
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
		    
		   JSONArray objF = new JSONArray();
		   for(int h=0;h<extComp.size();h++) {
			   JSONObject obj = new JSONObject();
			   obj.put("name", extComp.get(h).getName());
			   obj.put("files", extComp.get(h).getFiles());
			   obj.put("category", "Extension");
			   obj.put("value", extComp.get(h).getSize());
			   objF.put(obj); 
		   }
		   
		   try(FileWriter file = new FileWriter(outputFile))
		   {
			file.write(objF.toString());
			file.flush();
		   }catch(IOException e) {
			   e.printStackTrace();
		   }
		   System.out.println(objF);
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
	
}
