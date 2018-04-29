package ramSim;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**************************************************************************************
 * This class sets up basic GUI for the simulation. 
 * 
 * @author Cody West
 * @version Memory Sim
 * @date 04/05/2018
 *************************************************************************************/
public class SimGUI extends JFrame implements ActionListener{

	JTextArea results;
	JTextArea fileTxt;
	JButton nextButton;
	JMenu fileMenu;
	OpSim g;
	JButton[] frames = new JButton[8];

	JOptionPane panel;
	String display;
	String fileDisplay;
	ArrayList<String> displayTxt;

	//OLD MAIN, USE MAIN CLASS FOR RUNNING SIMULATION
	//	public static void main(String args[]){
	//		SimGUI gui = new SimGUI();
	//				try {
	//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	//				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	//					// TODO Auto-generated catch block
	//					e.printStackTrace();
	//				}
	//		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		gui.setTitle("Memory Simulator");
	//		gui.pack();
	//		gui.setVisible(true);
	//	}

	/*************************************************************************
	 * Constructor creates the simulation GUI with proper sizes and filename
	 * for the OpSim class. Places both text displays in proper place along
	 * with button.
	 ************************************************************************/
	public SimGUI(){
		g = new OpSim("Process.txt");
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();

		fileTxt = new JTextArea(15,15);
		JScrollPane filePane = new JScrollPane(fileTxt);
		loc.gridx = 1;
		loc.gridy = 0;
		loc.gridwidth = 1;
		loc.gridheight = 8;  
		loc.insets.left = 5;
		loc.insets.right = 20;
		//		loc.insets.bottom = 20;
		add(filePane, loc);
		fileTxt.append(g.getTxt());
		loc = new GridBagConstraints();

		results = new JTextArea(25,60);
		JScrollPane scrollPane = new JScrollPane(results);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		loc.gridx = 3;
		loc.gridy = 0;
		loc.gridwidth = 1;
		loc.gridheight = 10;  
		loc.insets.left = 20;
		//		loc.insets.right = 20;
		//		loc.insets.bottom = 20;
		add(scrollPane, loc);

		displayTxt = g.getSysTxt();
		for(int i = 0; i<displayTxt.size();i++){
			results.append(displayTxt.get(i));
			results.append("\n");
		}

		loc = new GridBagConstraints();
		loc.gridx = 2;
		loc.insets.left = 20;
		loc.insets.right = 20;
		loc.gridwidth = 1;
		loc.gridheight = 1; 

		for(int i = 0;i<8;i++){
			loc.gridy = i;
			frames[i] = new JButton(g.getFrameTxt(i));
			frames[i].setPreferredSize(new Dimension(110,40));
			add(frames[i],loc);
		}

		loc = new GridBagConstraints();
		loc.gridx = 2;
		loc.gridy = 9;
		loc.insets.left = 20;
		loc.insets.right = 20;
		loc.insets.top = 20;
		loc.gridwidth = 1;
		loc.gridheight = 1; 
		nextButton = new JButton("Next");
		nextButton.setPreferredSize(new Dimension(70,40));
		add(nextButton,loc);
		nextButton.addActionListener(this);



	}

	/*************************************************************************
	 * Method for handling next presses, and ending the simulation.
	 * @param e Action event initiated
	 ************************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent buttonPress = (JComponent) e.getSource();

		if(buttonPress == nextButton){

			if(g.getEnd()){
				System.exit(0);
			}

			g.readInput();
			displayTxt = g.getSysTxt();
			for(int i = 0; i<displayTxt.size();i++){
				results.append(displayTxt.get(i));
				results.append("\n");
			}
			for(int i = 0;i<8;i++){
				frames[i].setText(g.getFrameTxt(i));
			}

			if(g.getEnd()){
				results.append("GUI Shutting down...\n");
				return;
			}
		}

	}



}
