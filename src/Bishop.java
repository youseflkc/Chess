import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class Bishop extends ChessPiece{

	public Bishop(Color bishopColor, int bishopx, int bishopy)throws IOException {
		color = bishopColor;
		x=bishopx;
		y=bishopy;
		if (color == Color.black) {
			imageFile =(new File("src/Images/Black Pieces/Bishop.png"));
		} else {
			imageFile = (new File("src/Images/Gold Pieces/Gold Bishop.png"));
		}
		readImage();
	}
}
