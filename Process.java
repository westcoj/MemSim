package ramSim;

import java.util.ArrayList;

/**************************************************************************************
 * The following class holds the information for processes in the memory simulator.
 * It takes an ID, data size and code size. It also holds its page tables.
 * 
 * @author Cody West
 * @version Memory Sim
 * @date 04/05/2018
 *************************************************************************************/
public class Process {

	private int data;
	private int code;
	private int id;
	
	/** Page tables for process **/
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

	/*************************************************************************
	 * Constructor creates process with proper variables 
	 * @param id ID # of process
	 * @param code size of code segment
	 * @param data size of data segment
	 ************************************************************************/
	public Process(int id, int code, int data){
		this.data = data;
		this.id = id;
		this.code = code;
	}
	
	/** Add frame index to code page table **/
	public void cTable(int cDex){
		codeTable.add(cDex);
	}
	
	/** Add frame index to data page table **/
	public void dTable(int dDex){
		dataTable.add(dDex);
	}
	
	/*************************************************************************
	 * Method that gives combination of information in both page tables. 
	 * @return The list of indexes
	 ************************************************************************/
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
	
	/*************************************************************************
	 * Method that cleans up string interpretation
	 * @return The string
	 ************************************************************************/
	public String toString(){
		String prep = "Proc ID: " + id + "\n" + "Code Table: " + codeTable.toString() +"\n" + "Data Table: " + dataTable.toString();	
		return prep;
	}
}
