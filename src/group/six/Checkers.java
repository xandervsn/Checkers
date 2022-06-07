package group.six;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Checkers implements ActionListener {
	boolean whiteTurn = true;
	boolean blackTurn = false;
	boolean ran = false;
	int whiteWins = 0;
	int blackWins = 0;
	HashMap<String, String> boardMap = new HashMap<String, String>();
	HashMap<String, Integer> pieceMap = new HashMap<String, Integer>();
	
	//containers
	Container center = new Container();
	Container north = new Container();
	
	//JThings
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[8][8];
	JLabel xLabel = new JLabel("X's wins: 0");
	JLabel oLabel = new JLabel("O's wins: 0");
	JButton xChangeName = new JButton("Change X's Name.");
	JButton oChangeName = new JButton("Change O's Name.");
	JButton preCurrent;
	JButton current;
	String preCurrentTile;
	String currentTile;
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	ImageIcon whitePiece = new ImageIcon("blackPiece.png");
	ImageIcon blackPiece = new ImageIcon("whitePiece.png");
	ImageIcon whitePieceH = new ImageIcon("whitePieceH.png");
	ImageIcon blackPieceH = new ImageIcon("blackPieceH.png");
	
	public Checkers() { //initializes the board
		frame.setSize(800,800);
		
		// Center Grid Container
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(8,8));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton("");
				center.add(button[j][i]);
				if((j % 2 == 0 && i % 2 == 0) || (j % 2 == 1 && i % 2 == 1)) {
					button[j][i].setBackground(Color.BLUE);
				}else {
					button[j][i].setBackground(Color.RED);
					button[j][i].addActionListener((ActionListener) this);
				}
			}
		}
		frame.add(center, BorderLayout.CENTER);
		//North Container
		north.setLayout(new GridLayout(1,2));
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		xChangeName.addActionListener((ActionListener) this);
		north.add(xChangeField);
		north.add(oChangeName);
		oChangeName.addActionListener((ActionListener) this);
		north.add(oChangeField);
		frame.add(north, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		for (int y = 'a'; y < 'i'; y++) {
			for (int x = 0; x < 8; x++) {
				String tile = (char)y + String.valueOf(x);
				//DONE: boardMap<tile, piece> - not a boolean, but an actual value you can use
				//(will still functionally be a boolean if returned as null
				boardMap.put(tile, null);
			}
		}
		/* a b c d e f g h
		 * 0 1 2 3 4 5 6 7
		 */
		int y = 'a';
		for (int x = 1; x < 10; x += 2) {
			if(x == 9) {
				x = 0;
				y++;
			}else if(x == 8) {
				x = 1;
				y++;
			}
			if(y == 'd') {
				y = 'f';
			}
			String tile = (char)y + String.valueOf(x);
			boardMap.put(tile, tile);
			//king, alive, white, position
			
			if(y > 'c') {
				button[x][y-97].setIcon(whitePiece);
				pieceMap.put(tile, 900000);
			}else {
				button[x][y-97].setIcon(blackPiece);
				pieceMap.put(tile, 900100);
			}
			
			if(tile.equals("h6")) {
				x = 10;
			}
		}
	}

	public static void main(String[] args) {
		new Checkers();
	}
	
	
	public void onForfeit() {
		if(whiteTurn) {
			blackWins++;
		}else if(blackTurn) {
			whiteWins++;
		}
		new Checkers();
	}
	
	public void onTile(JButton current, JButton preCurrent, String currentTile, String preCurrentTile, int x, int y) {
		if(preCurrent.getIcon() != null) {
			if(preCurrent.getIcon().toString().equals("blackPieceH.png")) {
				preCurrent.setIcon(null);
				current.setIcon(whitePiece);
			}else if(preCurrent.getIcon().toString().equals("whitePieceH.png")) {
				preCurrent.setIcon(null);
				current.setIcon(blackPiece);
			}else {
				swap();
			}
		}
	}
	
	public void onPiece(JButton current, JButton preCurrent, String currentTile, String PreCurrentTile) {
		if(current.getIcon() != null) {
			if(current.getIcon().toString().equals("blackPiece.png") && blackTurn) {
				if(preCurrent.getIcon().toString().equals("blackPieceH.png")) {
					preCurrent.setIcon(whitePiece);
				}
				current.setIcon(blackPieceH);
			}if(current.getIcon().toString().equals("whitePiece.png") && whiteTurn) {
				if(preCurrent.getIcon().toString().equals("whitePieceH.png")) {
					preCurrent.setIcon(blackPiece);
				}
				current.setIcon(whitePieceH);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(e.getSource().equals(button[j][i])) {
					if(ran && current.getIcon() != null) {
						preCurrent = current;
						preCurrentTile = currentTile;
					}else {
						preCurrent = button[j][i];
						preCurrentTile = (char)(j+97) + String.valueOf(i);
					}
					
					currentTile = (char)(j+97) + String.valueOf(i);
					current = button[j][i];
					ran = true;
					
					if(current.getIcon() != null) {
						onPiece(current, preCurrent, currentTile, preCurrentTile);
					}else {
						if(isLegal(current, j, i)) {
							onTile(current, preCurrent, currentTile, preCurrentTile, j, i);
						}else {
							if(preCurrent.getIcon() != null) {
								if(preCurrent.getIcon().toString().equals("blackPieceH.png")) {
									preCurrent.setIcon(whitePiece);
								}else if(preCurrent.getIcon().toString().equals("whitePieceH.png")) {
									preCurrent.setIcon(blackPiece);
								}
							}
						}
						swap();
					}
				}
			}
		}
	}
	
	public void check() {
		//checks for kings
		for (int y = 'a'; y < 'i'; y++) {
			for (int x = 0; x < 8; x++) {
				String tile = (char)y + String.valueOf(x);
				if(pieceMap.get(tile) != null) {
					int temp = pieceMap.get(tile);
					char yy = String.valueOf(pieceMap.get(tile)).charAt(5);
					char isBlack = String.valueOf(pieceMap.get(tile)).charAt(3);
					char isKing = String.valueOf(pieceMap.get(tile)).charAt(1);
					if((yy == 0) && (isBlack == 0) && (isKing == 0)) {
						pieceMap.put(tile, temp + 10000);
					}else if((yy == 7) && (isBlack == 1) && (isKing == 0)) {
						pieceMap.put(tile, temp + 10000);
					}
				}
			}
		}
	}
	
	public void swap() {
		if(whiteTurn) {
			whiteTurn = false;
			blackTurn = true;
		}else if(blackTurn) {
			whiteTurn = true;
			blackTurn = false;
		}
	}
	
	/*	
	button[g+1][y+1] down
	button[g-1][y+1]
	
	button[g+1][y-1] up
	button[g-1][y-1]
	 */
	
	public boolean isLegal(JButton current, int j, int i) {
		for (int y = 0; y < button.length; y++) {
			for (int g = 0; g < button[0].length; g++) {
				if(button[g][y].getIcon() != null && ((button[g][y].getIcon().toString().equals("blackPieceH.png")) || (button[g][y].getIcon().toString().equals("whitePieceH.png")))) {
					int xModA = 1; int xModB = 1;
					int yModA = 1; int yModB = 1;
					int xMod2A = 2; int xMod2B = 2;
					int yMod2A = 2; int yMod2B = 2;
					
					//TODO this part is fucked idk y
					if(g == 6) {
						xMod2A = 0;
					}else if(y == 6) {
						yMod2A = 0;
					}else if(g == 0) {
						xModA = 0;
						xMod2A = 0;
					}else if(y == 0) {
						yModA = 0;
						yMod2A = 0;
					}
					
					if(g == 1) {
						xMod2B = 0;
					}else if(y == 1) {
						yMod2B = 0;
					}else if(g == 7) {
						xModB = 0;
						xMod2B = 0;
					}else if(y == 7) {
						yModB = 0;
						yMod2B = 0;
					}


					if(blackTurn) {
						if(button[g][y].getIcon().toString().equals("blackPieceH.png")) {
							if(current == button[g+xModA][y-yModA] || current == button[g-xModB][y-yModB]) {
								return true;
							}else if(current == button[g+xMod2A][y-yMod2A]) {
								if(button[g+xMod2A-xModA][y-yMod2A+yModA].getIcon().toString().equals("whitePiece.png")) {
									button[g+xMod2A-xModA][y-yMod2A+yModA].setIcon(null);
									return true;
								}
							}else if(current == button[g-xMod2B][y-yMod2B]) {
								if(button[g-xMod2B+xModB][y-yMod2B+yModB].getIcon().toString().equals("whitePiece.png")) {
									button[g-xMod2B+xModB][y-yMod2B+yModB].setIcon(null);
									return true;
								}
							}
						}
					}else if(whiteTurn) {
						if(button[g][y].getIcon().toString().equals("whitePieceH.png")) {
							if(current == button[g+xModA][y+yModA] || current == button[g-xModB][y+yModB]) {
								return true;
							}else if(current == button[g+xMod2A][y+yMod2A]) {
								if(button[g+xMod2A-xModA][y+yMod2A-yModA].getIcon().toString().equals("blackPiece.png")) {
									button[g+xMod2A-xModA][y+yMod2A-yModA].setIcon(null);
									return true;
								}
							}else if(current == button[g-xMod2B][y+yMod2B]) {
								if(button[g-xMod2B+xModB][y+yMod2B-yModB].getIcon().toString().equals("blackPiece.png")) {
									button[g-xMod2B+xModB][y+yMod2B-yModB].setIcon(null);
									return true;
								}
							}
						}
					}
				}
			}
		}
		swap();
		return false;
	}
	
}
