import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TicTacToeFrame extends JFrame {
    private char turn = 'X';
    private boolean gameOver = false;
    private Cell[][] cells = new Cell[3][3];
    private JLabel statusLbl = new JLabel("Player X's turn to play");
    private JTextArea resultArea = new JTextArea(5, 20);
    private JButton playAgainBtn = new JButton("Play Again");
    private JButton quitBtn = new JButton("Quit");

    //Sets up the tictactoe frame by adding the tic tac toe board onto the frame
    public TicTacToeFrame() {
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel.add(cells[i][j] = new Cell());
            }
        }
        resultArea.setEditable(false);
        playAgainBtn.addActionListener(e -> clearBoard());
        quitBtn.addActionListener(e -> System.exit(0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLbl, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playAgainBtn);
        buttonPanel.add(quitBtn);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.75);
        int height = (int) (screenSize.height * 0.75);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //Check if the board is full
    //If it's full it means that there is a tie
    public boolean isFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getText().isEmpty()) return false;
            }
        }
        return true;
    }

    //Checks if there is a winner
    public boolean isWon(char player) {
        for (int i = 0; i < 3; i++) {
            if (cells[i][0].getText().equals(String.valueOf(player)) &&
                    cells[i][1].getText().equals(String.valueOf(player)) &&
                    cells[i][2].getText().equals(String.valueOf(player))) return true;
            if (cells[0][i].getText().equals(String.valueOf(player)) &&
                    cells[1][i].getText().equals(String.valueOf(player)) &&
                    cells[2][i].getText().equals(String.valueOf(player))) return true;
        }
        return (cells[0][0].getText().equals(String.valueOf(player)) &&
                cells[1][1].getText().equals(String.valueOf(player)) &&
                cells[2][2].getText().equals(String.valueOf(player))) ||
                (cells[0][2].getText().equals(String.valueOf(player)) &&
                        cells[1][1].getText().equals(String.valueOf(player)) &&
                        cells[2][0].getText().equals(String.valueOf(player)));
    }


    //announces the winner
    public class Cell extends JLabel {
        public Cell() {
            setBorder(new LineBorder(Color.black, 1));
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 60));
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (gameOver || !getText().isEmpty()) return;
                    setText(String.valueOf(turn));
                    setForeground(turn == 'X' ? Color.BLUE : Color.RED);
                    if (isWon(turn)) {
                        statusLbl.setText("Player " + turn + " won! Game Over!");
                        resultArea.append("Player " + turn + " won!\n");
                        gameOver = true;
                    } else if (isFull()) {
                        statusLbl.setText("Draw! Game Over!");
                        resultArea.append("Draw!\n");
                        gameOver = true;
                    } else {
                        turn = (turn == 'X') ? 'O' : 'X';
                        statusLbl.setText("Player " + turn + "'s turn to play");
                    }
                }
            });
        }
    }


    //Rests the board for another game
    //Basically set each cell to blank like in CP I
    private void clearBoard() {
        gameOver = false;
        turn = 'X';
        statusLbl.setText("Player X's turn to play");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setText("");
                cells[i][j].setForeground(Color.BLACK);
            }
        }
    }

}
