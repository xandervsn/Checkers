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

public class Checkers implements ActionListener {
	boolean blackTurn = false;
	boolean whiteTurn = true;
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
	JLabel whiteLabel = new JLabel("X's wins: 0");
	JLabel blackLabel = new JLabel("O's wins: 0");
	JButton forfeit = new JButton("Forefeit");
	JButton preCurrent;
	JButton current;
	String preCurrentTile;
	String currentTile;
	ImageIcon whitePiece = new ImageIcon("whitePiece.png");
	ImageIcon blackPiece = new ImageIcon("blackPiece.png");
	ImageIcon blackPieceH = new ImageIcon("blackPieceH.png");
	ImageIcon whitePieceH = new ImageIcon("whitePieceH.png");
	ImageIcon whiteKing = new ImageIcon("whiteKing.png");
	ImageIcon blackKing = new ImageIcon("blackKing.png");
	ImageIcon whiteKingH = new ImageIcon("whiteKingH.png");
	ImageIcon blackKingH = new ImageIcon("blackKingH.png");
	boolean take = false;
	
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
		north.add(whiteLabel);
		north.add(blackLabel);
		north.add(forfeit);
		forfeit.addActionListener((ActionListener) this);
		frame.add(north, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		place();
	}

	public static void main(String[] args) {
		new Checkers();
	}
	
	public void place(){
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
		whiteLabel.setText("White's Wins: " + whiteWins);
		blackLabel.setText("Black's Wins: " + blackWins);
		
		whiteTurn = true;
		blackTurn = false;
	}
	
	public void clear() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i].setIcon(null);
			}
		}
	}
	
	
	public void onForfeit() {
		if(blackTurn) {
			whiteWins++;
		}else if(whiteTurn) {
			blackWins++;
		}
		clear();
		place();
	}
	
	public void onTile(JButton current, JButton preCurrent, String currentTile, String preCurrentTile, int x, int y) {
		if(preCurrent.getIcon() != null) {
			if(preCurrent.getIcon() == whitePieceH) {
				preCurrent.setIcon(null);
				current.setIcon(whitePiece);
			}else if(preCurrent.getIcon() == blackPieceH) {
				preCurrent.setIcon(null);
				current.setIcon(blackPiece);
			}else if(preCurrent.getIcon()== whiteKingH) {
				preCurrent.setIcon(null);
				current.setIcon(whiteKing);
			}else if(preCurrent.getIcon() == blackKingH) {
				preCurrent.setIcon(null);
				current.setIcon(blackKing);
			}else {
				
			}
		}
	}
	
	public void onPiece(JButton current, JButton preCurrent, String currentTile, String PreCurrentTile) {
		if(current.getIcon() != null) {
			if(blackTurn) {
				if(current.getIcon() == blackPiece) {
					unhighlight();
					current.setIcon(blackPieceH);
				}
				if(current.getIcon() == blackKing) {
					unhighlight();
					current.setIcon(blackKingH);
				}
			}else if(whiteTurn) {
				if(current.getIcon() == whitePiece) {
					unhighlight();
					current.setIcon(whitePieceH);
				}
				if(current.getIcon() == whiteKing) {
					unhighlight();
					current.setIcon(whiteKingH);
				}
			}
			unhighlight();
		}
	}
	
	public void unhighlight() {
		if(preCurrent != current) {
			if(preCurrent.getIcon() == whitePieceH) {
				preCurrent.setIcon(whitePiece);
			}else if(preCurrent.getIcon() == whiteKingH) {
				preCurrent.setIcon(whiteKing);
			}else if(preCurrent.getIcon() == blackPieceH) {
				preCurrent.setIcon(blackPiece);
			}else if(preCurrent.getIcon() == blackKingH) {
				preCurrent.setIcon(blackKing);
			}else if(preCurrent.getIcon() == whitePiece) {
				preCurrent.setIcon(whitePiece);
			}else if(preCurrent.getIcon() == whiteKing) {
				preCurrent.setIcon(whiteKing);
			}else if(preCurrent.getIcon() == blackPiece) {
				preCurrent.setIcon(blackPiece);
			}else if(preCurrent.getIcon() == blackKing) {
				preCurrent.setIcon(blackKing);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(forfeit)) {
			onForfeit();
		}
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
						if(isLegal(current, j, i) && (take || isLast())) {
							System.out.println(isLast());
							onTile(current, preCurrent, currentTile, preCurrentTile, j, i);
							checkKings();
							checkWin();
							if(take && !isLast()) {
								System.out.println(isLast());
								swap();
							}
							take = false;
							swap();
						}else {
							if(preCurrent.getIcon() != null) {
								if(preCurrent.getIcon() == whitePieceH) {
									preCurrent.setIcon(whitePiece);
								}else if(preCurrent.getIcon() == blackPieceH) {
									preCurrent.setIcon(blackPiece);
								}else if(preCurrent.getIcon() == whiteKingH) {
									preCurrent.setIcon(whiteKing);
								}else if(preCurrent.getIcon() == blackKingH) {
									preCurrent.setIcon(blackKing);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void checkKings() {
		//checks for kings
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(button[j][i].getIcon() != null) {
					if(i == 0 && button[j][i].getIcon() == whitePiece) {
						button[j][i].setIcon(whiteKing);
					}else if(i == 7 && button[j][i].getIcon() == blackPiece){
						button[j][i].setIcon(blackKing);
					}
				}
			}
		}
	}
	
	public void swap() {
		if(blackTurn) {
			blackTurn = false;
			whiteTurn = true;
		}else if(whiteTurn) {
			blackTurn = true;
			whiteTurn = false;
		}
	}
	
	/*	
	button[g+1][y+1] down
	button[g-1][y+1]
	
	button[g+1][y-1] up
	button[g-1][y-1]
	 */
	
	public void checkWin() {
		boolean foundBlack = false;
		boolean foundWhite = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(button[j][i].getIcon() == blackPiece || button[j][i].getIcon() == blackKing) {
					foundBlack = true;
				}
				if(button[j][i].getIcon() == whitePiece || button[j][i].getIcon() == whiteKing) {
					foundWhite = true;
				}
			}
		}
		if(!foundBlack) {
			blackWins++;
			clear();
			place();
		}
		if(!foundWhite) {
			whiteWins++;
			clear();
			place();
		}
	}
	
	public boolean isLast() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(button[j][i].getIcon() != null) {
					if(whiteTurn) {
						//DONE: currently if 2 pieces r doubled up it returns false
						if(button[j][i].getIcon() == whitePieceH || button[j][i].getIcon() == whiteKingH || button[j][i].getIcon() == whitePiece || button[j][i].getIcon() == whiteKing) {
							if(j < 6 && i > 1) {
								if((button[j+1][i-1].getIcon() == blackPiece && button[j+2][i-2].getIcon() == null) || (button[j+1][i-1].getIcon() == blackKing && button[j+2][i-2].getIcon() == null)) {
									System.out.println("1");
									return false;
								}
							}
							if(j > 1 && i > 1) {
								if((button[j-1][i-1].getIcon() == blackPiece && button[j-2][i-2].getIcon() == null) || (button[j-1][i-1].getIcon() == blackKing && button[j-2][i-2].getIcon() == null)) {
									System.out.println("2");
									return false;
								}
							}
						}if(button[j][i].getIcon() == whiteKingH || button[j][i].getIcon() == whiteKing) {
							if(j < 6 && i < 6) {
									if((button[j+1][i+1].getIcon() == blackPiece && button[j+2][i+2].getIcon() == null) || (button[j+1][i+1].getIcon() == blackKing && button[j+2][i+2].getIcon() == null)) {
										System.out.println("3");
										return false;
									}
							}
							if(j > 1 && i < 6) {
									if((button[j-1][i+1].getIcon() == blackPiece && button[j-2][i+2].getIcon() == null) || ((button[j-1][i+1].getIcon() == blackKing) && button[j-2][i+2].getIcon() == null)) {
										System.out.println("4");
										return false;
									}
							}
						}	
					}else if(blackTurn) {
						if(button[j][i].getIcon() == blackPieceH || button[j][i].getIcon() == blackKingH || button[j][i].getIcon() == blackPiece || button[j][i].getIcon() == blackKing) {
							if(j > 1 && i < 6) {
									if((button[j-1][i+1].getIcon() == whitePiece && button[j-2][i+2].getIcon() == null) || (button[j-1][i+1].getIcon() == whiteKing && button[j-2][i+2].getIcon() == null)) {
										System.out.println("5");
										return false;
									}
							}
							if(j < 6 && i < 6) {
									if((button[j+1][i+1].getIcon() == whitePiece && button[j+2][i+2].getIcon() == null) || (button[j+1][i+1].getIcon() == whiteKing && button[j+2][i+2].getIcon() == null)) {
										System.out.println("6");
										return false;
									}
							}if(button[j][i].getIcon() == blackKingH || button[j][i].getIcon() == blackKing) {
								if(j < 6 && i > 1) {
										if((button[j+1][i-1].getIcon() == whitePiece && button[j+2][i-2].getIcon() == null) || (button[j+1][i-1].getIcon() == whiteKing && button[j+2][i-2].getIcon() == null)) {
											System.out.println("7");
											return false;
										}
								}
								if(j > 1 && i > 1) {
										if((button[j-1][i-1].getIcon() == whitePiece && button[j-2][i-2].getIcon() == null) || (button[j-1][i-1].getIcon() == whiteKing && button[j-2][i-2].getIcon() == null)) {
											System.out.println("8");
											return false;
										}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean isLegal(JButton current, int j, int i) {
		isLast();
		for (int y = 0; y < button.length; y++) {
			for (int g = 0; g < button[0].length; g++) {
				if(button[g][y].getIcon() != null){
					if(button[g][y].getIcon() == blackPieceH ||
							button[g][y].getIcon() == whitePieceH ||
							button[g][y].getIcon() == blackKingH ||
							button[g][y].getIcon() == whiteKingH) {
						if(whiteTurn) {
							if(button[g][y].getIcon() == whitePieceH || button[g][y].getIcon() == whiteKingH) {
								if(g < 7 && y > 0) {
									if(current == button[g+1][y-1]) {
									return true;
									}
								}
								if(g > 0 && y > 0) {
									if(current == button[g-1][y-1]) {
										return true;
									}
								}
								if(g < 6 && y > 1) {
									if(current == button[g+2][y-2]) {
										if(button[g+1][y-1].getIcon() == blackPiece || button[g+1][y-1].getIcon() == blackKing) {
											button[g+1][y-1].setIcon(null);
											take = true;
											return true;
										}
									}
								}
								if(g > 1 && y > 1) {
									if(current == button[g-2][y-2]) {
										if(button[g-1][y-1].getIcon() == blackPiece || button[g-1][y-1].getIcon() == blackKing) {
											button[g-1][y-1].setIcon(null);
											take = true;
											return true;
										}
									}
								}
							}if(button[g][y].getIcon() == whiteKingH) {
								if(g < 7 && y < 7) {
									if(current == button[g+1][y+1]) {
									return true;
									}
								}
								if(g > 0 && y < 7) {
									if(current == button[g-1][y+1]) {
										return true;
									}
								}
								if(g < 6 && y < 6) {
									if(current == button[g+2][y+2]) {
										if(button[g+1][y+1].getIcon() == blackPiece || button[g+1][y+1].getIcon() == blackKing) {
											button[g+1][y+1].setIcon(null);
											take = true;
											return true;
										}
									}
								}
								if(g > 1 && y < 6) {
									if(current == button[g-2][y+2]) {
										if(button[g-1][y+1].getIcon() == blackPiece || button[g-1][y+1].getIcon() == blackKing) {
											button[g-1][y+1].setIcon(null);
											take = true;
											return true;
										}
									}
								}
							}	
						}else if(blackTurn) {
							if(button[g][y].getIcon() == blackPieceH || button[g][y].getIcon() == blackKingH) {
								if(g < 7 && y < 7) {
									if(current == button[g+1][y+1]) {
									return true;
									}
								}
								if(g > 0 && y < 7) {
									if(current == button[g-1][y+1]) {
										return true;
									}
								}
								if(g < 6 && y < 6) {
									if(current == button[g+2][y+2]) {
										if(button[g+1][y+1].getIcon() == whitePiece || button[g+1][y+1].getIcon() == whiteKing) {
											button[g+1][y+1].setIcon(null);
											take = true;
											return true;
										}
									}
								}
								if(g > 1 && y < 6) {
									if(current == button[g-2][y+2]) {
										if(button[g-1][y+1].getIcon() == whitePiece || button[g-1][y+1].getIcon() == whiteKing) {
											button[g-1][y+1].setIcon(null);
											take = true;
											return true;
										}
									}
								}if(button[g][y].getIcon() == blackKingH) {
									if(g < 7 && y > 0) {
										if(current == button[g+1][y-1]) {
										return true;
										}
									}
									if(g > 0 && y > 0) {
										if(current == button[g-1][y-1]) {
											return true;
										}
									}
									if(g < 6 && y > 1) {
										if(current == button[g+2][y-2]) {
											if(button[g+1][y-1].getIcon() == whitePiece || button[g+1][y-1].getIcon() == whiteKing) {
												button[g+1][y-1].setIcon(null);
												take = true;
												return true;
											}
										}
									}
									if(g > 1 && y > 1) {
										if(current == button[g-2][y-2]) {
											if(button[g-1][y-1].getIcon() == whitePiece || button[g-1][y-1].getIcon() == whiteKing) {
												button[g-1][y-1].setIcon(null);
												take = true;
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
