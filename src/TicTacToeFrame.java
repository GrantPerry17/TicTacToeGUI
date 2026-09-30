import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToeFrame extends JFrame
{
    private TicTacToeTile[][] board;
    private String player;
    private int moveCnt;
    private boolean gameOver;

    public TicTacToeFrame()
    {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new TicTacToeTile[3][3];
        player = "X";
        moveCnt = 0;
        gameOver = false;

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));

        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TicTacToeTile tile = (TicTacToeTile)e.getSource();
                int row = tile.getRow();
                int col = tile.getCol();

                makeMove(row, col);
            }
        };

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                board[row][col] = new TicTacToeTile(row, col);
                board[row][col].setText(" ");
                board[row][col].addActionListener(listener);
                boardPanel.add(board[row][col]);
            }
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));

        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int choice = JOptionPane.showConfirmDialog(
                        TicTacToeFrame.this,
                        "Are you sure you want to quit?",
                        "Quit Game",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(quitButton);

        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void makeMove(int row, int col)
    {
        if (gameOver)
        {
            return;
        }

        if (board[row][col].getText().equals(" "))
        {
            board[row][col].setText(player);
            moveCnt++;

            if (moveCnt >= 5 && isWin(player))
            {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Player " + player + " wins!");
                playAgain();
                return;
            }

            if (moveCnt >= 7 && isTie())
            {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "The game is a tie!");
                playAgain();
                return;
            }

            if (player.equals("X"))
            {
                player = "O";
            }
            else
            {
                player = "X";
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "That is not a valid move.");
        }
    }

    private void playAgain()
    {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Would you like to play another game?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION)
        {
            resetGame();
        }
        else
        {
            System.exit(0);
        }
    }

    private void resetGame()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                board[row][col].setText(" ");
            }
        }

        player = "X";
        moveCnt = 0;
        gameOver = false;
    }

    private boolean isWin(String player)
    {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player)
    {
        for (int row = 0; row < 3; row++)
        {
            if (board[row][0].getText().equals(player)
                    && board[row][1].getText().equals(player)
                    && board[row][2].getText().equals(player))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isColWin(String player)
    {
        for (int col = 0; col < 3; col++)
        {
            if (board[0][col].getText().equals(player)
                    && board[1][col].getText().equals(player)
                    && board[2][col].getText().equals(player))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isDiagonalWin(String player)
    {
        if (board[0][0].getText().equals(player)
                && board[1][1].getText().equals(player)
                && board[2][2].getText().equals(player))
        {
            return true;
        }

        if (board[0][2].getText().equals(player)
                && board[1][1].getText().equals(player)
                && board[2][0].getText().equals(player))
        {
            return true;
        }

        return false;
    }

    private boolean isTie()
    {
        return isRowTie() && isColTie() && isDiagonalTie();
    }

    private boolean isRowTie()
    {
        for (int row = 0; row < 3; row++)
        {
            boolean hasX = false;
            boolean hasO = false;

            for (int col = 0; col < 3; col++)
            {
                if (board[row][col].getText().equals("X"))
                {
                    hasX = true;
                }

                if (board[row][col].getText().equals("O"))
                {
                    hasO = true;
                }
            }

            if (!hasX || !hasO)
            {
                return false;
            }
        }

        return true;
    }

    private boolean isColTie()
    {
        for (int col = 0; col < 3; col++)
        {
            boolean hasX = false;
            boolean hasO = false;

            for (int row = 0; row < 3; row++)
            {
                if (board[0][col].getText().equals("X"))
                {
                    hasX = true;
                }

                if (board[1][col].getText().equals("X"))
                {
                    hasX = true;
                }

                if (board[2][col].getText().equals("X"))
                {
                    hasX = true;
                }

                if (board[0][col].getText().equals("O"))
                {
                    hasO = true;
                }

                if (board[1][col].getText().equals("O"))
                {
                    hasO = true;
                }

                if (board[2][col].getText().equals("O"))
                {
                    hasO = true;
                }
            }

            if (!hasX || !hasO)
            {
                return false;
            }
        }

        return true;
    }

    private boolean isDiagonalTie()
    {
        boolean mainX = false;
        boolean mainO = false;
        boolean otherX = false;
        boolean otherO = false;

        for (int i = 0; i < 3; i++)
        {
            if (board[i][i].getText().equals("X"))
            {
                mainX = true;
            }

            if (board[i][i].getText().equals("O"))
            {
                mainO = true;
            }

            if (board[i][2 - i].getText().equals("X"))
            {
                otherX = true;
            }

            if (board[i][2 - i].getText().equals("O"))
            {
                otherO = true;
            }
        }

        return (mainX && mainO) && (otherX && otherO);
    }
}