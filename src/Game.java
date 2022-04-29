import java.io.IOException;

import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{

	ChessBoard chessboard;
	private Thread thread;
	
	public Game() throws IOException {
		chessboard=new ChessBoard();
	}
	
	public synchronized void start() {
		thread=new Thread(this,"game");
		thread.start();
	}

	public synchronized void stop() {
		System.exit(0);
	}
	
	@Override
	public void run() {
		
	}
	
}
