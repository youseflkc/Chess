import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class King extends ChessPiece{

	public King(Color kingcolor,int kingx,int kingy) throws IOException {
		color = kingcolor;
		x=kingx;
		y=kingy;
			if (color == Color.black) {
				imageFile = new File("src/Images/Black Pieces/King.png");
			} else {
				imageFile=new File("src/Images/Gold Pieces/Gold King.png");
			}
		readImage();
	}
}
