package com.example.tictactoeapplication;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Board board;

    private Button[][] buttons = new Button[3][3];

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.board = new Board();

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        // dynamically get all of the button IDs and store them into buttons 2D array
        for (int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                String buttonID = "button_" + i + j;
                // finds ID associated to the buttons, stores reference in 2D array buttons
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                // passes this main activity class, and class itself implements onclicklistener below
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                player1Points = 0;
                player2Points = 0;
                updatePointsText();
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!board.isGameOver()) {
            char currMove;
            boolean isValid;
            if (board.getCurrMove() == 1) {
                currMove = Constants.PLAYER_ONE_SIGN;
                isValid = board.move(currMove, buttonIDToDim(v.getId()));
                if (isValid) {
                    ((Button) v).setText("X");
                }
            }
            else {
                currMove = Constants.PLAYER_TWO_SIGN;
                isValid = board.move(currMove, buttonIDToDim(v.getId()));
                if (isValid) {
                    ((Button) v).setText("O");
                }
            }

            board.gameOver(currMove);
            if (board.isGameOver()) {
                if (board.getWinState() == 'X'){
                    player1Points++;
                    Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
                }
                else if(board.getWinState() == 'O'){
                    player2Points++;
                    Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
                }
                updatePointsText();
                resetBoard();
            }
        }
    }

    private void updatePointsText(){
        textViewPlayer1.setText("P1 Win Count: " + player1Points);
        textViewPlayer2.setText("P2 Win Count: " + player2Points);
    }

    private void resetBoard(){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                buttons[i][j].setText("");
            }
        }
        board = new Board();
    }

    private String buttonIDToDim(int id){
        switch (id){
            case R.id.button_00:
                return "00";
            case R.id.button_01:
                return "01";
            case R.id.button_02:
                return "02";
            case R.id.button_10:
                return "10";
            case R.id.button_11:
                return "11";
            case R.id.button_12:
                return "12";
            case R.id.button_20:
                return "20";
            case R.id.button_21:
                return "21";
            case R.id.button_22:
                return "22";
        }
        return "";
    }

    // used to save data when orientation is changed
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        Gson gson = new Gson();
        String json = gson.toJson(board);
        outState.putString("board", json);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
    }

    // used to set data when orientation is changed
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            String json= savedInstanceState.getString("board");
            if(!json.isEmpty()) {
                Gson gson = new Gson();
                board = gson.fromJson(json, Board.class);
            }
        }

        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
    }

}
