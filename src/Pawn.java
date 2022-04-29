import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Pawn extends ChessPiece {

	public Pawn(Color pawnColor, int pawnx, int pawny) throws IOException{
		color = pawnColor;
		x=pawnx;
		y=pawny;
		if (color == Color.black) {
			imageFile = (new File("src/Images/Black Pieces/Pawn.png"));
		} else {
			imageFile = (new File("src/Images/Gold Pieces/Gold Pawn.png"));
		}		
		
		readImage();
	}

}