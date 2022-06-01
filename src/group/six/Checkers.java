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
	HashMap<String, Boolean> boardMap = new HashMap<String, Boolean>();
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
				//TODO: boardMap<tile, piece> - not a boolean, but an actual value you can use
				//(will still functionally be a boolean if returned as null
				boardMap.put(tile, false);
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
			boardMap.put(tile, true);
			//king, alive, white, position
			
			if(y > 'c') {
				button[x][y-97].setIcon(whitePiece);
				pieceMap.put(tile, 900000);
			}else {
				button[x][y-97].setIcon(blackPiece);
				pieceMap.put(tile, 900100);
				System.out.println(pieceMap.get(tile));
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
	
	public void onTile(JButton current, JButton preCurrent, String currentTile, String PreCurrentTile, int k, int l) {
		System.out.println(preCurrent + " " + current);
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(button[j][i].getIcon() == null) {
					
				}else if(button[j][i].getIcon().toString().equals("blackPieceH.png")) {
					boardMap.replace((char)(i+97) + String.valueOf(j), false);
					boardMap.replace(currentTile, true);
					pieceMap.replace(currentTile, null);
					
					preCurrent.setIcon(null);
					current.setIcon(whitePiece);
				}else if(button[j][i].getIcon().toString().equals("whitePieceH.png")) {
					boardMap.replace((char)(i+97) + String.valueOf(j), false);
					boardMap.replace(currentTile, true);
					button[j][i].setIcon(null);
					current.setIcon(blackPiece);
				}
			}
		}
	}
	
	public void onPiece(JButton current, JButton preCurrent, String currentTile, String PreCurrentTile) {
		if(String.valueOf(pieceMap.get(currentTile)).charAt(3) == '1') {
			current.setIcon(whitePieceH);
		}else {
			current.setIcon(blackPieceH);
		}
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(e.getSource().equals(button[j][i])) {
					/*if((pieceMap.get((char)(i+97) + String.valueOf(j))).toString().charAt(3) == '1'){
						boolean isWhite = true;
						System.out.println("white");
					}else {
						boolean isBlack = true;
						System.out.println("black");
					}*/
					if(ran && current.getIcon() != null) {
						preCurrent = current;
						preCurrentTile = currentTile;
					}else {
						preCurrent = button[j][i];
					}
					currentTile = (char)(i+97) + String.valueOf(j);
					current = button[j][i];
					ran = true;
					if(boardMap.get(currentTile)) {
						onPiece(current, preCurrent, currentTile, preCurrentTile, i, j);
					}else {
						onTile(current, preCurrent, currentTile, preCurrentTile);
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
}
