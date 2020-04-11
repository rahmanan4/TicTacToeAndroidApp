package com.example.tictactoeapplication;

public class Board {
    private char[][] board = new char[Constants.ROW_DIMENSION][Constants.COL_DIMENSION];
    private int currMove = 1;
    private boolean gameOver = false;
    private char winState;

    public Board(){
        makeBoard();
    }

    private void makeBoard(){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                board[i][j] = '_';
            }
        }
    }

    private boolean checkBounds(int row, int col){
        return row >= 0 && row < Constants.ROW_DIMENSION && col >= 0 && col < Constants.COL_DIMENSION;
    }

    public boolean move(char player, String m){
        if (m.length() != 2){
            return false;
        }

        int row = Character.getNumericValue(m.charAt(0));
        int col = Character.getNumericValue(m.charAt(1));

        if (!checkBounds(row, col)){
            return false;
        }

        if (board[row][col] == '_') {
            board[row][col] = player;
            if (currMove == 1) {
                currMove = 2;
            }
            else {
                currMove = 1;
            }
            return true;
        }
        return false;
    }

    public int getCurrMove(){
        return currMove;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public boolean emptySpaceCheck(){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                if (board[i][j] == '_'){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rowCheck(){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '_'){
                return true;
            }
        }
        return false;
    }

    public boolean colCheck(){
        for (int i = 0; i < Constants.COL_DIMENSION; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '_'){
                return true;
            }
        }
        return false;
    }

    public boolean diagCheck(){
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '_'){
            return true;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '_'){
            return true;
        }
        return false;
    }

    public void gameOver(char move){
        if (rowCheck() || colCheck() || diagCheck()){
            gameOver = true;
            winState = move;
        }
        else if(!emptySpaceCheck()){
            gameOver = true;
            winState = Constants.DRAW_SIGN;
        }
    }

    public char getWinState(){
        return winState;
    }
}
