package ramSim;
import java.util.*;
import java.io.*;

/**************************************************************************************
 * The following class runs the simulator taking in data from a specified file and 
 * distributing it amongs frames. 
 * 
 * @author Cody West
 * @version Memory Sim
 * @date 04/05/2018
 *************************************************************************************/
public class OpSim {
	final int totalRam = 4096;
	final double pageSize = 512.0;
	final int frames = 8;
	File file;
	Scanner sc;
	int[] frameList;
	ArrayList<Integer> temp;
	List<Integer> freeFrames;
	Process[] procTable2;
	String[] txtData;
	ArrayList<String> sysTxt;
	boolean end;

	/*************************************************************************
	 * Constructor creates the simulation with proper file.
	 * @param fileName The name of the file to be used.
	 ************************************************************************/
	public OpSim(String fileName){
		temp = new ArrayList<Integer>();
		freeFrames = new ArrayList<Integer>();
		sysTxt = new ArrayList<String>();
		procTable2 = new Process[500];
		txtData = new String[8];
		frameList = new int[8];
		end = false;
		
		//This is changed in linux version due to differences in OS.
		file = new File("C:\\server\\" + fileName);

		for(int i=0;i<8;i++){
			freeFrames.add(i);
		}

		for(int i=0;i<8;i++){
			frameList[i] = 0;
		}
		
		for(int i=0;i<8;i++){
			txtData[i] = "Free";
		}

		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sysTxt.add("Loading Mem Simulation....");
	}
	
	/*************************************************************************
	 * Method for getting proper display text for GUI
	 * @return Current list of display texts.
	 ************************************************************************/
	public ArrayList<String> getSysTxt(){
		return sysTxt;
	}

	/*************************************************************************
	 * Method for getting proper text from file for GUI
	 * @return Entirity of text from the file
	 ************************************************************************/
	public String getTxt(){
		String txt = "";
		try {
			Scanner fs = new Scanner(file);
			while(fs.hasNextLine()){
				txt += fs.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return txt;
	}
	
	/*************************************************************************
	 * Method for getting text for each individual frame for GUI
	 * @return Individual frame's text status
	 ************************************************************************/
	public String getFrameTxt(int i){
		return txtData[i];
	}
	
	/*************************************************************************
	 * Method for checking for end of simulation
	 * @return if simulation is over
	 ************************************************************************/
	public boolean getEnd(){
		return end;
	}

	/*************************************************************************
	 * Method for getting next line from file and running it through the
	 * simulation. Either a new process or terminating an old one. Can also
	 * signal end of simulation.
	 ************************************************************************/
	public void readInput(){
		sysTxt.clear();
		
		String output;
		
		if(!sc.hasNextLine()){
			return;
		}

		String token = "";
		int id;
		int code;
		int data;

		token = sc.next();
		//System.out.println(token);
		
		//if token quit
		if(token.equals("#")){
			sysTxt.add("Shutting down simulation...");
			end = true;
			return;
		}
		
		id = Integer.parseInt(token);
		token = sc.next();
		//System.out.println(token);
		code = Integer.parseInt(token);

		//HANDLE REMOVE PROCESS
		if(code==-1){
			if(procTable2[id]!=null){
				temp = procTable2[id].getFrameDex();
				sysTxt.add("Removing P" + id + " from memory");

				for(int i = 0;i<temp.size();i++){
					if(temp.get(i)>freeFrames.size()){
						freeFrames.add(temp.get(i));

					}
					
					else{
						freeFrames.add(temp.get(i), temp.get(i));

					}
					
					frameList[temp.get(i)] = 0;
					txtData[temp.get(i)] = "Free";
					sysTxt.add("Freeing page " + temp.get(i));
				}

				procTable2[id] = null;
			}
		}

		//NEW PROCESS INCOMING
		else{

			token = sc.next();
			//System.out.println(token);
			data = Integer.parseInt(token);
			procTable2[id] = new Process(id,code,data);
			double cPages = Math.ceil(code/512.0);
			//System.out.println("CPAGES: " + cPages);
			double dPages = Math.ceil(data/512.0);

			sysTxt.add("Loading P" + id + " into RAM: code=" + code + " (" + cPages + " pages), data=" + data + " (" + dPages + " pages)");
			if(freeFrames.size()>=cPages+dPages){
				for(int i = 0;i< (int) cPages;i++){
					int takeFrame = freeFrames.get(0);
					frameList[takeFrame] = 1;
					procTable2[id].cTable(takeFrame);
					freeFrames.remove(0);
					txtData[takeFrame] = "Code-" + i + " of P" + id;
					sysTxt.add("Load Code page " + i + " of P" + id + " to frame " + takeFrame);
				}

				for(int i = 0;i< (int) dPages;i++){
					int takeFrame = freeFrames.get(0);
					frameList[takeFrame] = 1;
					procTable2[id].dTable(takeFrame);
					freeFrames.remove(0);
					txtData[takeFrame] = "Data-" + i + " of P" + id;
					sysTxt.add("Load Data page " + i + " of P" + id + " to frame " + takeFrame);

				}
				
			}


		}
		


	}

}
