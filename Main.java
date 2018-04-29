package ramSim;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**************************************************************************************
 * This class is used as the proper main for the simulation.
 * 
 * @author Cody West
 * @version Memory Sim
 * @date 04/05/2018
 *************************************************************************************/
public class Main {

	public static void main(String args[]){
		SimGUI gui = new SimGUI();
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Memory Simulator");
		gui.pack();
		gui.setVisible(true);
	}

}
