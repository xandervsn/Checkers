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
	
	public Checkers() {
		for (int y = 'a'; y < 'i'; y++) {
			for (int x = 0; x < 8; x++) {
				String tile = x + String.valueOf(y);
				boardMap.put(tile, false);
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
				if(whiteTurn) {
					blackWins++;
				}else if(blackTurn) {
					whiteWins++;
				}
				new Checkers();
			}
		}
	}
}
