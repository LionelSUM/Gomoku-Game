package Gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class ChessBoard extends JPanel {
    private static final int SIZE = 15;
    private static final int GRID = 50; 
    public int[][] board = new int[SIZE][SIZE]; 
    public int clickcount = 0;
    public int winner = 0;

    public ChessBoard() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / GRID;
                int y = e.getY() / GRID;

                if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && clickcount%2 == 0) {
                    board[x][y] = 1;
                }
                else if(x >= 0 && x < SIZE && y >= 0 && y < SIZE && clickcount%2 == 1) {
                    board[x][y] = 2;
                }
                repaint();
                clickcount++;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i <= SIZE; i++) {
            g2.drawLine(i * GRID, 0, i * GRID, SIZE * GRID); 
            g2.drawLine(0, i * GRID, SIZE * GRID, i * GRID); 
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 1) {
                    g2.setColor(Color.BLACK); 
                    g2.fillOval(i * GRID + GRID / 4, j * GRID + GRID / 4, GRID / 2, GRID / 2);
                    
                }
                else if (board[i][j] == 2) {
                	g2.setColor(Color.WHITE);
                    g2.fillOval(i * GRID + GRID / 4, j * GRID + GRID / 4, GRID / 2, GRID / 2);
                }
            }
        }
    }

    public int checkHorizontal(int x, int y) {
    	if(board[x][y] == 1 && x <= 10){
    		if(board[x+1][y]==1 && board[x+2][y]==1 && board[x+3][y]==1 && board[x+4][y]==1) {
    			return 1;
    		}
    	}
    	else if(board[x][y] == 2 && x <= 10){
    		if(board[x+1][y]==2 && board[x+2][y]==2 && board[x+3][y]==2 && board[x+4][y]==2) {
    			return 2;
    		}
    	}
		return 0;
    	
    }
    public int checkVertical(int x, int y) {
    	if(board[x][y] == 1 && y <= 10){
    		if(board[x][y+1]==1 && board[x][y+2]==1 && board[x][y+3]==1 && board[x][y+4]==1) {
    			return 1;
    		}
    	}
    	else if(board[x][y] == 2 && y <= 10){
    		if(board[x][y+1]==2 && board[x][y+2]==2 && board[x][y+3]==2 && board[x][y+4]==2) {
    			return 2;
    		}
    	}
    	return 0;
    }
    public int checkSlope(int x, int y) {
    	if(board[x][y] == 1 && x <= 10 && y <= 10){
    		if(board[x+1][y+1]==1 && board[x+2][y+2]==1 && board[x+3][y+3]==1 && board[x+4][y+4]==1) {
    			return 1;
    		}
    	}
    	else if(board[x][y] == 2 && x <= 10 && y <= 10){
    		if(board[x+1][y+1]==2 && board[x+2][y+2]==2 && board[x+3][y+3]==2 && board[x+4][y+4]==2) {
    			return 2;
    		}
    	}
    	if(board[x][y] == 1 && x >= 4 && y <= 10){
    		if(board[x-1][y+1]==1 && board[x-2][y+2]==1 && board[x-3][y+3]==1 && board[x-4][y+4]==1) {
    			return 1;
    		}
    	}
    	else if(board[x][y] == 2 && x >= 4 && y <= 10){
    		if(board[x-1][y+1]==2 && board[x-2][y+2]==2 && board[x-3][y+3]==2 && board[x-4][y+4]==2) {
    			return 2;
    		}
    	}
    	return 0;
    	
    }
    
    public int getWinner() {
    	int winStatue = 0;
    	for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != 0) {
                	winStatue = Math.max(checkHorizontal(i, j),checkVertical(i, j));
                	winStatue = Math.max(checkSlope(i, j),winStatue);
                }
                if (winStatue != 0) {
                	break;
                }
            }
            if (winStatue != 0) {
            	break;
            }
        }
    	return winStatue;
    }
}
