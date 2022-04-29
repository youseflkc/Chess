import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

public class ChessPiece{
	public Color color;
	public static final int size=100;
	File imageFile;
	public int x,y;
	public BufferedImage img;
	public BufferedImage hlImg;
	private boolean selected=false;
	public boolean highlight=false;
	public boolean unHighlight=false;
	public Rectangle2D bounds;
	private List<Point> possibleMoves;
	private boolean empty;

	
	public ChessPiece(int Ex, int Ey)  throws IOException{
		x=Ex;
		y=Ey;
		empty=true;
		img=null;
		possibleMoves=new LinkedList<Point>();
	}
	
	public ChessPiece(){
		empty=false;
		possibleMoves=new LinkedList<Point>();
	}
	
	public void readImage() {
		try {
			img=ImageIO.read(imageFile);
		}catch(IOException e) {
			
		}
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public int getXVal() {
		return x;
	}
	
	public int getYVal() {
		return y;
	}
	
	public void setXVal(int xVal) {
		x=xVal;
	}
	
	public void setYVal(int yVal) {
		y=yVal;
	}

	public void setHighlight(boolean hl) {
		this.highlight=hl;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void addMoves(int x, int y) {
		Point p=new Point(x,y);
		if (!possibleMoves.contains(p)) {
			possibleMoves.add(p);
		}
	}
	
	public void clearMoves() {
		possibleMoves.clear();
	}
	
	public List<Point> getMoves() {
		return possibleMoves;
	}


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setunHighlight(boolean hl) {
		unHighlight=hl;
	}
	public boolean isEqual(ChessPiece chesspiece) {
		if(chesspiece.getXVal()==x && chesspiece.getYVal()==y) {
			return true;
		}
		return false;
	}
	
}

	