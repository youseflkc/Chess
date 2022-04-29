import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class Queen extends ChessPiece{

	public Queen(Color queenColor, int queenx, int queeny) throws IOException{
		color = queenColor;
		x=queenx;
		y=queeny;
		
		if (color == Color.black) {
			imageFile =  (new File("src/Images/Black Pieces/Queen.png"));
		} else {
			imageFile =  (new File("src/Images/Gold Pieces/Gold Queen.png"));
		}
		readImage();
	}
}
