import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	
	public static void main (String []args) throws IOException{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame mainframe= new JFrame("Chess");
				try {
					ChessBoard chessboard= new ChessBoard();
					mainframe.add(chessboard);
					mainframe.setResizable(true);
					mainframe.pack();
					mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainframe.setLocationRelativeTo(null);
					mainframe.setVisible(true);
				}catch(IOException e) {
					
				}
				
			}
		});
		
		
		}
	}
