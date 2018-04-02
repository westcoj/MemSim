package ramSim;
import java.util.*;
import java.io.*;


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

	//Constructor also loads file
	public OpSim(String fileName){
		temp = new ArrayList<Integer>();
		freeFrames = new ArrayList<Integer>();
		sysTxt = new ArrayList<String>();
		procTable2 = new Process[500];
		txtData = new String[8];
		frameList = new int[8];
		end = false;
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
	
	public ArrayList<String> getSysTxt(){
		return sysTxt;
	}

	//GET ALL OF FILE TXT FOR DISPLAY
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
	
	public String getFrameTxt(int i){
		return txtData[i];
	}
	
	public boolean getEnd(){
		return end;
	}

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
