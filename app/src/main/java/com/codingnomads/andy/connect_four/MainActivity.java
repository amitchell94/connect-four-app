package com.codingnomads.andy.connect_four;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons;
    private String[][] gameBoard;
    private String currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameBoard = new String[7][6];
        buttons = new Button[7][6];

        initialiseButtons();
    }

    public void setButtonColors(){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if ("X".equals(gameBoard[i][j])) {
                    buttons[i][j].setTextColor(Color.YELLOW);
                } else if ("O".equals(gameBoard[i][j])) {
                    buttons[i][j].setTextColor(Color.RED);
                } else {
                    buttons[i][j].setTextColor(Color.WHITE);
                }
            }
        }
    }

    public void initialiseButtons() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                char row = 'a';
                row += i;

                int column = 1;
                column += j;
                String buttonId = Character.toString(row) + column;

                int viewId = getResources().getIdentifier(buttonId, "id", getPackageName());

                buttons[i][j] = findViewById(viewId);
                buttons[i][j].setTextColor(Color.WHITE);
                buttons[i][j].setClickable(true);
                gameBoard[i][j] = "";
            }
        }

        currentPlayer = "O";
        setText("Red to play first");
    }

    public void disableButtons(){
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setClickable(false);
            }
        }
    }

    public void userInput(View view) {
        int buttonId = view.getId();

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                char row = 'a';
                row += i;

                int column = 1;
                column += j;
                String buttonIdString = Character.toString(row) + column;

                int viewId = getResources().getIdentifier(buttonIdString, "id", getPackageName());

                if (viewId == buttonId) {
                    for (int k = gameBoard[i].length - 1; k >= 0; k--) {
                        if (!"O".equals(gameBoard[i][k]) &&
                                !"X".equals(gameBoard[i][k])) {
                            gameBoard[i][k] = currentPlayer;

                            setButtonColors();

                            if (currentPlayer.equals("O")) {
                                currentPlayer = "X";
                                setText("Yellow to play");
                            } else {
                                currentPlayer = "O";
                                setText("Red to play");
                            }

                            if (checkWin("O")) {
                                setText("Red wins");
                                disableButtons();
                            } else if (checkWin("X")) {
                                setText("Yellow wins");
                                disableButtons();
                            }
                            return;
                        }

                    }

                }
            }
        }
    }


    public boolean checkWin(String player) {
        int playerCount = 0;

        //Vertical
        for (int i = 0; i < gameBoard.length; i++) {
            playerCount = 0;
            for (int j = 0; j < gameBoard[i].length; j++) {
                String test = gameBoard[i][j];
                if (player.equals(gameBoard[i][j])) {
                    playerCount += 1;
                    if (playerCount >= 4) {
                        return true;
                    }
                } else {
                    playerCount = 0;
                }
            }
        }

        playerCount = 0;

        //Horizontal
        for (int i = 0; i < gameBoard[0].length; i++) {
            playerCount = 0;
            for (int j = 0; j < gameBoard.length; j++) {
                String test = gameBoard[j][i];
                if (player.equals(gameBoard[j][i])) {
                    playerCount += 1;
                    if (playerCount >= 4) {
                        return true;
                    }
                } else {
                    playerCount = 0;
                }
            }
        }

        playerCount = 0;

        //Diagonally Right
        for (int i = 0; i < gameBoard.length - 3; i++) {
            for (int j = 0; j < gameBoard[i].length - 3; j++) {
                playerCount = 0;
                boolean diagonalWin = true;
                for (int k = 0; k < 4; k++) {
                    String test = gameBoard[i + k][j + k];
                    if (!player.equals(gameBoard[i + k][j + k])) {
                        diagonalWin = false;
                        break;
                    }
                }

                if(diagonalWin) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = gameBoard.length-1; i > 3; i--) {
            for (int j = 0 ; j < gameBoard[i].length -3; j++) {
                playerCount = 0;
                boolean diagonalWin = true;
                for (int k = 0; k < 4; k++) {
                    String test = gameBoard[i - k][j + k];
                    if (!player.equals(gameBoard[i - k][j + k])) {
                        diagonalWin = false;
                        break;
                    }
                }

                if(diagonalWin) {
                    return true;
                } else {
                    break;
                }
            }
        }

        return false;
    }

    public void setText(String text) {
        TextView textView = findViewById(R.id.game_text);

        textView.setText(text);
    }

    public void newGame(View view) {
        initialiseButtons();
    }
}
