package Gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class GomokuPanel extends JPanel{
	private JLabel titleLabel;
    private ChessBoard chessboard;
    private JTextArea playerInfoArea;
    private String p1;
    private String p2;
    private String player;
    private JLabel imageLabel;
    private JTextArea statusArea;
    private String status = "on";
    private String winner;
    private int round = 1;
    private int win1 = 0;
    private int win2 = 0;
    private GameDatabase DB = new GameDatabase();

    public GomokuPanel(String p1, String p2) {
    	this.p1 = p1;
    	this.p2 = p2;
        setLayout(new BorderLayout());
        player = p1;

        titleLabel = new JLabel("GOMOKU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 66));
        add(titleLabel, BorderLayout.NORTH);
        
        statusArea = new JTextArea(2,20); 
        statusArea.setEditable(false); 
        statusArea.setFont(new Font("Arial", Font.BOLD, 36));
        statusArea.setBackground(Color.WHITE); 
        statusArea.setForeground(Color.BLACK); 
        add(statusArea, BorderLayout.SOUTH);

        playerInfoArea = new JTextArea();
        playerInfoArea.setFont(new Font("Arial", Font.BOLD, 27));
        playerInfoArea.setBackground(Color.LIGHT_GRAY); 
        playerInfoArea.setForeground(Color.BLACK);
        add(playerInfoArea, BorderLayout.EAST);
        
        ImageIcon computerImage = new ImageIcon("D:\\Course_Material\\Graduate\\Java\\Final Project\\Beat.png");
        imageLabel = new JLabel(computerImage);
        add(imageLabel, BorderLayout.WEST);
        
        updatePlayerInfo();
        
        chessboard = new ChessBoard();
        chessboard.setBackground(new Color(204,119,35));
        chessboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	player = player.equals(p1) ? p2 : p1;
            	if(chessboard.getWinner() == 1) {
            		status = "over";
            		winner = p1;
            		win1++;
            	}
            	else if(chessboard.getWinner() == 2) {
            		status = "over";
            		winner = p2;
            		win2++;
            	}
            	updatePlayerInfo();
            }
        });
        JPanel chessboardPanel = new JPanel(new BorderLayout());
		chessboardPanel.add(chessboard, BorderLayout.CENTER);
        chessboardPanel.setBorder(BorderFactory.createEmptyBorder(41, 113, 42, 113));
        add(chessboardPanel, BorderLayout.CENTER);
    }
    
    public void updatePlayerInfo() {
    	if(status == "on") {
    		statusArea.setText("                                                        "
    							+ "Game status: Game On!        Round: "+ round + "\n" +
     						   "                                                        "
   						    + "TRY YOUR BEST TO WIN THE GOMOKU!!!");
    		playerInfoArea.setText(" GAME between \n " + p1 + " & " + p2 
    				+"\n" + "\nScore: "+ win1 + " vs " + win2 +
					 "\n\n\n\n\n\n\n\n\n\n\n\n\n Current Player: \n" + player);
    		if(player == p1) {
    			playerInfoArea.append("\n\n Shape:\n (Circle Black)");
    		}
    		else if(player == p2) {
    			playerInfoArea.append("\n\n Shape:\n (Circle White)");
    		}
    	}
    	else if(status == "over") {
            statusArea.setBackground(Color.WHITE); 
            statusArea.setForeground(Color.RED); 
    		statusArea.setText("                                                        "
    							+ "Game status: Game Over!!!!!!!!!!\n"+
    						   "                                                        "
    						    + "WINNER IS " + winner);
    		if(winner==p1) {
    			DB.recordMatchResult(winner, p2, "WIN");
    			DB.recordMatchResult(p2, winner, "LOSE");
    		}
    		else if(winner==p2) {
    			DB.recordMatchResult(winner, p1, "WIN");
    			DB.recordMatchResult(p1, winner, "LOSE");
    		}
    		showWinnerDialog(winner);
    	}
    	
    }
    
    public void showWinnerDialog(String winnerName) {
        String message = "The winner is " + winnerName + "!!!!!!\n Next round?";
        
        int result = JOptionPane.showConfirmDialog(
                this,
                message,
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.NO_OPTION) {
        	System.exit(0); 
        }
        else if(result == JOptionPane.YES_OPTION) {
        	status = "on";
        	player = p1;
        	chessboard.clickcount = 0;
        	round++;
        	statusArea.setBackground(Color.WHITE);
            statusArea.setForeground(Color.BLACK);
            statusArea.setText("                                                        "
					+ "Game status: Game On!        Round: "+ round + "\n" +
					   		   "                                                        "
				    + "TRY YOUR BEST TO WIN THE GOMOKU!!!");
            playerInfoArea.setText(" GAME between \n " + p1 + " & " + p2 
    				+"\n" + "\nScore: "+ win1 + " vs " + win2 +
					 "\n\n\n\n\n\n\n\n\n\n\n\n\n Current Player: \n" + player);
    		if(player == p1) {
    			playerInfoArea.append("\n\n Shape:\n (Circle Black)");
    		}
    		else if(player == p2) {
    			playerInfoArea.append("\n\n Shape:\n (Circle White)");
    		}
        	for (int i = 0; i < chessboard.board.length; i++) {
        	    for (int j = 0; j < chessboard.board[i].length; j++) {
        	    	chessboard.board[i][j] = 0;
        	    }
        	}
        	chessboard.repaint();
        }
    }
    
}
