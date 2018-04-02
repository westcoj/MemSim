package ramSim;

import java.util.ArrayList;

public class Process {

	private int data;
	private int code;
	private int id;
	ArrayList<Integer> codeTable = new ArrayList<Integer>();
	ArrayList<Integer> dataTable = new ArrayList<Integer>();
	
	public int getData() {
		return data;
	}


	public int getCode() {
		return code;
	}


	public int getID() {
		return id;
	}


	public Process(int id, int code, int data){
		this.data = data;
		this.id = id;
		this.code = code;
	}
	
	public void cTable(int cDex){
		codeTable.add(cDex);
	}
	
	public void dTable(int dDex){
		dataTable.add(dDex);
	}
	
	public ArrayList<Integer> getFrameDex(){
		ArrayList<Integer> fullDex = new ArrayList<Integer>();
		for(int i=0;i<codeTable.size();i++){
			fullDex.add(codeTable.get(i));
		}
		
		for(int i=0;i<dataTable.size();i++){
			fullDex.add(dataTable.get(i));
		}
		
		return fullDex;
	}
	
	public String toString(){
		String prep = "Proc ID: " + id + "\n" + "Code Table: " + codeTable.toString() +"\n" + "Data Table: " + dataTable.toString();	
		return prep;
	}
}
