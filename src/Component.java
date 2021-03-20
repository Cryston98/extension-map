import java.util.ArrayList;

import org.json.JSONArray;

public class Component {
	private String name;
	private int size;
	private double procent;
	private ArrayList<String> pathFile;
	
	public Component(String name) {
		this.name=name;
		this.size=0;
		this.procent=0;
		pathFile= new ArrayList();
	}
	
	public void updateFile(String file)
	{
		pathFile.add(file);
		this.size++;
	//	this.procent=totalFile/this.size;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	public JSONArray getFiles() {
		JSONArray nns= new JSONArray();
		for(int i=0;i<pathFile.size();i++)
			nns.put(pathFile.get(i).toString());
		return nns;
				
	}
	public String toString(){
		//return name;
		String pt="\n";
		for(int i=0;i<pathFile.size();i++){
			if(i!=pathFile.size()-1)
				pt=pt+pathFile.get(i).toString()+",\n";
			else
				pt=pt+pathFile.get(i).toString()+"\n";
		}
		return "\n{\n component:"+name+",\n files:"+pt+"\n}";
	}
	
}
