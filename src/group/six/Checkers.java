package group.six;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
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
	JButton current;
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	ImageIcon whitePiece = new ImageIcon("C:\\whitePiece.png");
	ImageIcon blackPiece = new ImageIcon("C:\\blackPiece.png");
	
	public Checkers() { //initializes the board
		for (int y = 'a'; y < 'i'; y++) {
			for (int x = 0; x < 8; x++) {
				String tile = (char)y + String.valueOf(x);
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
			boardMap.put(tile, false);
			
			pieceMap.put(tile, 900000);
			//king, alive, black, position
			
			if(tile.equals("h6")) {
				x = 10;
			}
		}
		
		frame.setSize(800,800);
		
		// Center Grid Container
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(8,8));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton();
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
	
	public void onTile() {
		
	}
	
	public void onPiece() {
		
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
		if(whiteTurn) {
			
		}else if(blackTurn) {
			
		}
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if(e.getSource().equals(button[j][i])) {
					JButton current = button[j][i];
					current.setIcon(whitePiece);
					button[j+1][i].setIcon(blackPiece);
				}
			}
		}
	}
	
}
