import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ChessBoard extends JPanel implements MouseListener {

	public static final int WIDTH = 900;
	public static final int HEIGHT = 900;

	public static final int BOARD_WIDTH = 800;
	public static final int BOARD_HEIGHT = 800;
	public static final int square_size = 100;
	public static final int border = 50;

	private BufferedImage chessImage = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage chessBg = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage finalImage = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage hlImage = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	public boolean isSelected;
	private ChessPiece selectedPiece;
	ChessPiece[][] chesspieces;

	public ChessBoard() throws IOException {
		setFocusable(true);
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
		chesspieces = new ChessPiece[8][8];
		loadChessBoard();
		drawBoard();
		drawPieces();
		reloadBoard();
		isSelected = false;
		addMouseListener(this);
	}

	public void drawBoard() {
		Graphics2D g = (Graphics2D) chessBg.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (((b + a) % 2) == 0) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.gray);
				}
				g.fillRect(a * square_size, b * square_size, square_size, square_size);
			}
		}
		g.dispose();
		repaint();
	}

	public void drawPieces() {
		chessImage = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) chessImage.getGraphics();

		for (int a = 0; a < chesspieces.length; a++) {
			for (int b = 0; b < chesspieces.length; b++) {
				BufferedImage img = chesspieces[a][b].getImg();
				int x = chesspieces[a][b].getXVal();
				int y = chesspieces[a][b].getYVal();
				if (img != null) {
					g.drawImage(img, null, x * square_size, y * square_size);
				} else {
					g.drawRect(x * square_size, y * square_size, square_size, square_size);
				}
//				if (chesspieces[a][b].highlight) {
//					g.setColor(new Color(0, 0, 250, 65));
//					g.drawImage(hlImage,null,x * square_size, y * square_size);
//				}
			}
		}
		g.dispose();
		repaint();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) finalImage.getGraphics();
		g2d.drawImage(chessBg, 0, 0, null);
		g2d.drawImage(chessImage, 0, 0, null);
		g.drawImage(finalImage, 50, 50, this);
		g.drawImage(hlImage, 50, 50, this);

	}

	public void loadChessBoard() throws IOException {
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				Color col = Color.black;

				if (b == 0 || b == 7) {
					if (b == 0)
						col = Color.black;
					if (b == 7)
						col = Color.white;
					if (a == 0 || a == 7)
						chesspieces[a][b] = new Rook(col, a, b);
					if (a == 1 || a == 6)
						chesspieces[a][b] = new Knight(col, a, b);
					if (a == 2 || a == 5)
						chesspieces[a][b] = new Bishop(col, a, b);
					if (a == 3)
						chesspieces[a][b] = new Queen(col, a, b);
					if (a == 4)
						chesspieces[a][b] = new King(col, a, b);

				} else if (b == 1 || b == 6) {
					if (b == 6) {
						col = Color.white;
					}
					if (b == 1) {
						col = Color.black;
					}
					chesspieces[a][b] = new Pawn(col, a, b);
				} else {
					chesspieces[a][b] = new ChessPiece(a, b);
				}
			}
		}
	}

	public void reloadBoard() {
		removeAll();
		for (int a = 0; a < chesspieces.length; a++) {
			for (int b = 0; b < chesspieces.length; b++) {
				if (chesspieces[a][b] != null) {
				}
			}
		}
		repaint();
	}

	public void chesspieceClicked(ChessPiece chesspiece) {
		checkMoves();
		if (!isSelected) {
			isSelected = true;
			selectedPiece = chesspiece;
			highlightMoves(chesspiece, true);
		} else if (isSelected) {
			isSelected = false;
			int a = 0;
			boolean pieceMoved = false;

			while (a < selectedPiece.getMoves().size() && !pieceMoved) {
				int x = (int) selectedPiece.getMoves().get(a).getX();
				int y = (int) selectedPiece.getMoves().get(a).getY();
				if (chesspiece.isEqual(chesspieces[x][y])) {
					highlightMoves(selectedPiece, false);
					pieceMoved = true;
					if (chesspiece.isEmpty()) {
						movePiece(selectedPiece, chesspiece);
					} else {
						//takePiece(selectedPiece, chesspiece);
					}
				}
				a++;
			}
			selectedPiece = null;
			if (!pieceMoved) {
				isSelected = true;
				selectedPiece = chesspiece;
				highlightMoves(chesspiece, true);
			}
		}
	}

	public void checkMoves() {
		for (int a=0;a<chesspieces.length;a++) {
			for (int b=0;b<chesspieces.length;b++) {
				if (chesspieces[a][b] instanceof Rook) {
					checkRook(chesspieces[a][b]);
				} else if (chesspieces[a][b] instanceof Pawn) {
					checkPawn(chesspieces[a][b]);
				} else if (chesspieces[a][b] instanceof Knight) {
					checkKnight(chesspieces[a][b]);
				} else if (chesspieces[a][b] instanceof Bishop) {
					checkBishop(chesspieces[a][b]);
				} else if (chesspieces[a][b] instanceof Queen) {
					checkQueen(chesspieces[a][b]);
				} else if (chesspieces[a][b] instanceof King) {
					checkKing(chesspieces[a][b]);
				}

			}
		}
	
	}

	public void highlightMoves(ChessPiece chesspiece, boolean hl) {
		List<Point> moves = chesspiece.getMoves();
		Graphics2D g2d = (Graphics2D) hlImage.getGraphics();
		g2d.setBackground(new Color(0, 0, 0, 0));
		g2d.clearRect(0, 0, hlImage.getWidth(), hlImage.getHeight());
		Point p = null;
		if (hl) {
			for (int i = 0; i < moves.size(); i++) {
				p = (Point) moves.get(i);
				if (chesspieces[(int) p.getX()][(int) p.getY()].isEmpty()) {
					g2d.setColor(new Color(0, 0, 250, 75));
				} else {
					g2d.setColor(new Color(250, 0, 0, 75));
				}
				g2d.fillRect((int) p.getX() * square_size, (int) p.getY() * square_size, square_size, square_size);
			}
		}
		g2d.dispose();
		repaint();
	}

	public void movePiece(ChessPiece piece, ChessPiece move) {
		int x = piece.getXVal();
		int y = piece.getYVal();
		piece.setXVal(move.getXVal());
		piece.setYVal(move.getYVal());
		piece.clearMoves();
		move.setXVal(x);
		move.setYVal(y);
		move.clearMoves();
		chesspieces[piece.getXVal()][piece.getYVal()] = piece;
		chesspieces[move.getXVal()][move.getYVal()] = move;
		drawPieces();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		for (int a = 0; a < chesspieces.length; a++) {
			for (int b = 0; b < chesspieces.length; b++) {
				int pX = (chesspieces[a][b].getXVal() * square_size) + border;
				int pY = (chesspieces[a][b].getYVal() * square_size) + border;
				if (x >= pX && x <= pX + square_size && y >= pY && y <= pY + square_size) {
					chesspieceClicked(chesspieces[a][b]);
				}
			}
		}
	}

	public void checkPawn(ChessPiece chesspiece) {
		int x = chesspiece.getXVal();
		int y = chesspiece.getYVal();

		if (chesspiece.getColor() == Color.white) {
			if (y - 1 >= 0) {
				if (chesspieces[x][y - 1].isEmpty()) {
					chesspiece.addMoves(x, y - 1);
					if (y == 6 && chesspieces[x][y - 2].isEmpty()) {
						chesspiece.addMoves(x, y - 2);
					}
				}
				if (x - 1 >= 0) {
					if (!chesspieces[x - 1][y - 1].isEmpty() && chesspieces[x - 1][y - 1].getColor() != Color.white) {
						chesspiece.addMoves(x - 1, y - 1);
					}
				}
				if (x + 1 < 8) {
					if (!chesspieces[x + 1][y - 1].isEmpty() && chesspieces[x + 1][y - 1].getColor() != Color.white) {
						chesspiece.addMoves(x + 1, y - 1);
					}
				}
			}

		} else {
			if (y + 1 < 8) {
				if (chesspieces[x][y + 1].isEmpty()) {
					chesspiece.addMoves(x, y + 1);
					if (y == 1 && chesspieces[x][y + 2].isEmpty()) {
						chesspiece.addMoves(x, y + 2);
					}
				}
				if (x - 1 >= 0) {
					if (!chesspieces[x - 1][y + 1].isEmpty() && chesspieces[x - 1][y + 1].getColor() != Color.black) {
						chesspiece.addMoves(x - 1, y + 1);
					}
				}
				if (x + 1 < 8) {
					if (!chesspieces[x + 1][y + 1].isEmpty() && chesspieces[x + 1][y + 1].getColor() != Color.black) {
						chesspiece.addMoves(x + 1, y + 1);
					}
				}
			}

		}
	}

	public void checkRook(ChessPiece chesspiece) {
		int x = chesspiece.getXVal();
		int y = chesspiece.getYVal();

		boolean empty = true;
		int a = 0;
		while (empty) {
			empty = false;
			a++;
			if ((x - a) >= 0) {
				if (chesspieces[x - a][y].isEmpty()) {
					chesspiece.addMoves(x - a, y);
					empty = true;
				} else if (!chesspieces[x - a][y].isEmpty()
						&& chesspieces[x - a][y].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x - a, y);
				}
			}
		}
		empty = true;
		a = 0;
		while (empty) {
			empty = false;
			a++;
			if ((x + a) < 8) {
				if (chesspieces[x + a][y].isEmpty()) {
					chesspiece.addMoves(x + a, y);
					empty = true;
				} else if (!chesspieces[x + a][y].isEmpty()
						&& chesspieces[x + a][y].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x + a, y);
				}
			}
		}
		empty = true;
		a = 0;
		while (empty) {
			empty = false;
			a++;
			if ((y - a) >= 0) {
				if (chesspieces[x][y - a].isEmpty()) {
					chesspiece.addMoves(x, y - a);
					empty = true;
				} else if (!chesspieces[x][y - a].isEmpty()
						&& chesspieces[x][y - a].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x, y - a);
				}
			}
		}
		empty = true;
		a = 0;
		while (empty) {
			empty = false;
			a++;
			if ((y + a) < 8) {
				if (chesspieces[x][y + a].isEmpty()) {
					chesspiece.addMoves(x, y + a);
					empty = true;
				} else if (!chesspieces[x][y + a].isEmpty()
						&& chesspieces[x][y + a].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x, y + a);
				}
			}
		}

	}

	public void checkBishop(ChessPiece chesspiece) {
		int x = chesspiece.getXVal();
		int y = chesspiece.getYVal();

		boolean empty = true;
		int a = 1;
		while (empty) {
			empty = false;
			if ((x - a) >= 0 && (y - a) >= 0) {
				if (chesspieces[x - a][y - a].isEmpty()) {
					chesspiece.addMoves(x - a, y - a);
					empty = true;
				} else if (!chesspieces[x - a][y - a].isEmpty()
						&& chesspieces[x - a][y - a].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x - a, y - a);
				}
			}
			a++;
		}
		empty = true;
		a = 1;
		while (empty) {
			empty = false;
			if ((x + a) < 8 && (y - a) >= 0) {
				if (chesspieces[x + a][y - a].isEmpty()) {
					chesspiece.addMoves(x + a, y - a);
					empty = true;
				} else if (!chesspieces[x + a][y - a].isEmpty()
						&& chesspieces[x + a][y - a].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x + a, y - a);
				}
			}
			a++;
		}
		empty = true;
		a = 1;
		while (empty) {
			empty = false;
			if ((x + a) < 8 && (y + a) < 8) {
				if (chesspieces[x + a][y + a].isEmpty()) {
					chesspiece.addMoves(x + a, y + a);
					empty = true;
				} else if (!chesspieces[x + a][y + a].isEmpty()
						&& chesspieces[x + a][y + a].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x + a, y + a);
				}
			}
			a++;
		}
		empty = true;
		a = 1;
		while (empty) {
			empty = false;
			if ((x - a) >= 0 && (y + a) < 8) {
				if (chesspieces[x - a][y + a].isEmpty()) {
					chesspiece.addMoves(x - a, y + a);
					empty = true;
				} else if (!chesspieces[x - a][y + a].isEmpty()
						&& chesspieces[x - a][y + a].getColor() != chesspiece.getColor()) {
					chesspiece.addMoves(x - a, y + a);
				}
			}
			a++;
		}
	}

	public void checkKnight(ChessPiece chesspiece) {
		int x = chesspiece.getXVal();
		int y = chesspiece.getYVal();

		if ((x - 2) >= 0 && (y - 1) >= 0) {
			if (chesspieces[x - 2][y - 1].isEmpty()) {
				chesspiece.addMoves(x - 2, y - 1);
			} else if (!chesspieces[x - 2][y - 1].isEmpty()
					&& chesspieces[x - 2][y - 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 2, y - 1);
			}
		}
		if ((x - 2) >= 0 && (y + 1) < 8) {
			if (chesspieces[x - 2][y + 1].isEmpty()) {
				chesspiece.addMoves(x - 2, y + 1);
			} else if (!chesspieces[x - 2][y + 1].isEmpty()
					&& chesspieces[x - 2][y + 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 2, y + 1);
			}
		}
		if ((x + 2) < 8 && (y - 1) >= 0) {
			if (chesspieces[x + 2][y - 1].isEmpty()) {
				chesspiece.addMoves(x + 2, y - 1);
			} else if (!chesspieces[x + 2][y - 1].isEmpty()
					&& chesspieces[x + 2][y - 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 2, y - 1);
			}
		}
		if ((x + 2) < 8 && (y + 1) < 8) {
			if (chesspieces[x + 2][y + 1].isEmpty()) {
				chesspiece.addMoves(x + 2, y + 1);
			} else if (!chesspieces[x + 2][y + 1].isEmpty()
					&& chesspieces[x + 2][y + 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 2, y + 1);
			}
		}
		if ((x - 1) >= 0 && (y - 2) >= 0) {
			if (chesspieces[x - 1][y - 2].isEmpty()) {
				chesspiece.addMoves(x - 1, y - 2);
			} else if (!chesspieces[x - 1][y - 2].isEmpty()
					&& chesspieces[x - 1][y - 2].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 1, y - 2);
			}
		}
		if ((x - 1) >= 0 && (y + 2) < 8) {
			if (chesspieces[x - 1][y + 2].isEmpty()) {
				chesspiece.addMoves(x - 1, y + 2);
			} else if (!chesspieces[x - 1][y + 2].isEmpty()
					&& chesspieces[x - 1][y + 2].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 1, y + 2);
			}
		}
		if ((x + 1) < 8 && (y - 2) >= 0) {
			if (chesspieces[x + 1][y - 2].isEmpty()) {
				chesspiece.addMoves(x + 1, y - 2);
			} else if (!chesspieces[x + 1][y - 2].isEmpty()
					&& chesspieces[x + 1][y - 2].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 1, y - 2);
			}
		}
		if ((x + 1) < 8 && (y + 2) < 8) {
			if (chesspieces[x + 1][y + 2].isEmpty()) {
				chesspiece.addMoves(x + 1, y + 2);
			} else if (!chesspieces[x + 1][y + 2].isEmpty()
					&& chesspieces[x + 1][y + 2].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 1, y + 2);
			}
		}

	}

	public void checkQueen(ChessPiece chesspiece) {
		checkBishop(chesspiece);
		checkRook(chesspiece);
	}

	public void checkKing(ChessPiece chesspiece) {
		int x = chesspiece.getXVal();
		int y = chesspiece.getYVal();

		if (x - 1 >= 0) {
			if (chesspieces[x - 1][y].isEmpty()) {
				chesspiece.addMoves(x - 1, y);
			} else if (!chesspieces[x - 1][y].isEmpty() && chesspieces[x - 1][y].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 1, y);
			}
		}

		if (x + 1 < 8) {
			if (chesspieces[x + 1][y].isEmpty()) {
				chesspiece.addMoves(x + 1, y);
			} else if (!chesspieces[x + 1][y].isEmpty() && chesspieces[x + 1][y].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 1, y);
			}
		}
		if (y + 1 < 8) {
			if (chesspieces[x][y + 1].isEmpty()) {
				chesspiece.addMoves(x, y + 1);
			} else if (!chesspieces[x][y + 1].isEmpty() && chesspieces[x][y + 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x, y + 1);
			}
		}
		if (y - 1 >= 0) {
			if (chesspieces[x][y - 1].isEmpty()) {
				chesspiece.addMoves(x, y - 1);
			} else if (!chesspieces[x][y - 1].isEmpty() && chesspieces[x][y - 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x, y - 1);
			}
		}

		if (x - 1 >= 0 && y - 1 >= 0) {
			if (chesspieces[x - 1][y - 1].isEmpty()) {
				chesspiece.addMoves(x - 1, y - 1);
			} else if (!chesspieces[x - 1][y - 1].isEmpty()
					&& chesspieces[x - 1][y - 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 1, y - 1);
			}
		}
		if (x - 1 >= 0 && y + 1 < 8) {
			if (chesspieces[x - 1][y + 1].isEmpty()) {
				chesspiece.addMoves(x - 1, y + 1);
			} else if (!chesspieces[x - 1][y + 1].isEmpty()
					&& chesspieces[x - 1][y + 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x - 1, y + 1);
			}
		}
		if (x + 1 < 8 && y - 1 >= 0) {
			if (chesspieces[x + 1][y - 1].isEmpty()) {
				chesspiece.addMoves(x + 1, y - 1);
			} else if (!chesspieces[x + 1][y - 1].isEmpty()
					&& chesspieces[x + 1][y - 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 1, y - 1);
			}
		}
		if (x + 1 < 8 && y + 1 < 8) {
			if (chesspieces[x + 1][y + 1].isEmpty()) {
				chesspiece.addMoves(x + 1, y + 1);
			} else if (!chesspieces[x + 1][y + 1].isEmpty()
					&& chesspieces[x + 1][y + 1].getColor() != chesspiece.getColor()) {
				chesspiece.addMoves(x + 1, y + 1);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
