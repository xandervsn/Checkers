package group.six;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Checkers extends JFrame {
	boolean whiteTurn = true;
	boolean blackTurn = false;
	int whiteWins = 0;
	int blackWins = 0;
	HashMap<String, Boolean> boardMap = new HashMap<String, Boolean>();
	HashMap<String, Integer> pieceMap = new HashMap<String, Integer>();
	
	JFrame frame = new JFrame();
	JButton forfeit = new JButton();
	Clicklistener click = new Clicklistener();
	
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
			
			pieceMap.put(tile, 000);
			//king, alive, black
			
			if(tile.equals("h6")) {
				x = 10;
			}
		}
		
		forfeit.addActionListener(click);
		
		frame.setSize(800,600);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Checkers();
	}
	
	private class Clicklistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == forfeit) {
				onForfeit();
			}
		}
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
		if(whiteTurn) {
			
		}else if(blackTurn) {
			
		}
	}
	
}
