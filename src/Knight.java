import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class Knight extends ChessPiece {
	public Knight(Color knightColor, int knightx, int knighty) throws IOException{
		color = knightColor;
		x=knightx;
		y=knighty;
		if (color == Color.black) {
			imageFile =  new File("src/Images/Black Pieces/Knight.png");
		} else {
			imageFile =  (new File("src/Images/Gold Pieces/Gold Knight.png"));
		}
		readImage();
	}
}
