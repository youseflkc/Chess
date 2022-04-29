import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class Rook extends ChessPiece {

	public Rook(Color rookColor, int rookx, int rooky)throws IOException {
		color = rookColor;
		x=rookx;
		y=rooky;
		if (color == Color.black) {
			imageFile= (new File("src/Images/Black Pieces/Rook.png"));
		} else {
			imageFile = (new File("src/Images/Gold Pieces/Gold Rook.png"));
		}
		
		readImage();
	}
	

}
