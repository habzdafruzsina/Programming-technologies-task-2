package game.logic;

import java.util.Random;

public class GameLogic {

    private Random rand = new Random();
    private Player[][] board;
    private int size;
    private Player actualPlayer;

    public GameLogic(int size){
        newGame(size);
    }

    /**
     * inicializes actual player to 'X', creates new "empty" board with the given size
     * @param size of the board
     */
    public void newGame(int size){
        actualPlayer = Player.X;
        this.size = size;
        board = new Player[size][size];
        for (int row = 0; row < size; ++row) {
            for (int column = 0; column < size; ++column) {
                board[row][column] = Player.NOBODY;
            }
        }
    }

    /**
     * Tries to put a sign at a certain position (which is given by parameters).
     * According to the number of the adjacent signs, deletes signs, or returns the winner.
     * If the step was successful, changes the actual player to the next one.
     * @param row
     * @param column
     * @return winner / empty string / draw
     */
    public String step(int row, int column) {
        if (board[row][column] == Player.NOBODY){
            board[row][column] = actualPlayer;
            if (maxLengthOfAdjacentSigns(row, column) >= 5) {
                return String.valueOf(actualPlayer);
            } else if (maxLengthOfAdjacentSigns(row, column) == 4) {
                deleteRandomSign(actualPlayer);
                deleteRandomSign(actualPlayer);
                nextPlayer();
                return "";
            } else if (maxLengthOfAdjacentSigns(row, column) == 3) {
                deleteRandomSign(actualPlayer);
                nextPlayer();
                return "";
            } else if (boardIsFull()) {
                return " - . A meccs döntetlen.";
            }
            nextPlayer();
        }
        return "";
    }

    /**
     * returns the length of the longest section where same signs are adjacent (across, row, column)
     * @param row
     * @param column
     * @return
     */
    private int maxLengthOfAdjacentSigns(int row, int column){
        int max = sameSignInRow(row);
        if (max < sameSignInColumn(column)){
            max = sameSignInColumn(column);
        }
        if (max < sameSignAcrossBackslash(row, column)){
            max = sameSignAcrossBackslash(row, column);
        }
        if (max < sameSignAcrossSlash(row, column)){
            max = sameSignAcrossSlash(row, column);
        }
        return max;
    }

    /**
     * counts adjacent signs in row, and returns the maximum
     * @param row
     * @return maximum
     */
    private int sameSignInRow(final int row){
        int max = 0;
        for (int i=0; i<size; i++){
            int sum = 0;
            while (i<size && board[row][i]==actualPlayer){
                sum++;
                i++;
            }
            if (max<sum){
                max = sum;
            }
        }
        return max;
    }

    /**
     * counts adjacent signs in column, and returns the maximum
     * @param column
     * @return maximum
     */
    private int sameSignInColumn(final int column){
        int max = 0;
        for (int i=0; i<size; i++){
            int sum = 0;
            while (i<size && board[i][column]==actualPlayer){
                sum++;
                i++;
            }
            if (max<sum){
                max = sum;
            }
        }
        return max;
    }

    /**
     * counts adjacent signs across, from left-top to right-bottom (adds together the first half, second half, and the middle sign)
     * @param row
     * @param column
     * @return
     */
    private int sameSignAcrossBackslash(int row, int column){
        return (1 + firstHalfOfBackslash(row,column) + secondHalfOfBackslash(row,column));
    }

    /**
     * counts the lower half of the left-top to right-bottom section
     * @param row
     * @param column
     * @return
     */
    private int firstHalfOfBackslash(int row, int column){
        int sum = 0;
        int i=row+1;
        int j=column+1;
        while((i < size && j < size) && (i >= 0 && j >= 0) && board[i][j]==actualPlayer)
        {
            i++;
            j++;
            sum++;
        }
        return sum;
    }

    /**
     * counts the upper half of the left-top to right-bottom section
     * @param row
     * @param column
     * @return
     */
    private int secondHalfOfBackslash(int row, int column){
        int sum = 0;
        int i=row-1;
        int j=column-1;
        while((i >= 0 && j >= 0) && (i < size && j < size) && board[i][j]==actualPlayer)
        {
            i--;
            j--;
            sum++;
        }
        return sum;
    }

    /**
     * counts adjacent signs across, from right-top to left-bottom (adds together the first half, second half, and the middle sign)
     * @param row
     * @param column
     * @return
     */
    private int sameSignAcrossSlash(int row, int column){
        return (1 + firstHalfOfSlash(row,column) + secondHalfOfSlash(row,column));
    }

    /**
     * counts the lower half of the right-top to left-bottom section
     * @param row
     * @param column
     * @return
     */
    private int firstHalfOfSlash(int row, int column){
        int sum = 0;
        int i=row+1;
        int j=column-1;
        while((i >= 0 && j < size) && (i < size && j >= 0) && board[i][j]==actualPlayer)
        {
            i++;
            j--;
            sum++;
        }
        return sum;
    }

    /**
     * counts the upper half of the right-top to left-bottom section
     * @param row
     * @param column
     * @return
     */
    private int secondHalfOfSlash(int row, int column){
        int sum = 0;
        int i=row-1;
        int j=column+1;
        while((i >= 0 && j < size) && (i < size && j >= 0) && board[i][j]==actualPlayer)
        {
            i--;
            j++;
            sum++;
        }
        return sum;
    }

    /**
     * "deletes" given sign from a random index
     * @param sign
     */
    private void deleteRandomSign(Player sign){
        int randomRow = rand.nextInt(size);
        int randomColumn = rand.nextInt(size);
        while(board[randomRow][randomColumn] != sign){
            randomRow = rand.nextInt(size);
            randomColumn = rand.nextInt(size);
        }
        board[randomRow][randomColumn] = Player.NOBODY;
    }

    /**
     * changes actual player
     */
    private void nextPlayer(){
        if (actualPlayer == Player.X) {
            actualPlayer = Player.O;
        } else {
            actualPlayer = Player.X;
        }
    }

    /**
     * examines if it's impossible to step
     * @return
     */
    private boolean boardIsFull(){
        for(int row=0; row<size; row++){
            for(int column=0; column<size; column++){
                if(board[row][column]==Player.NOBODY){
                    return false;
                }
            }
        }
        return true;
    }

    public Player getActualPlayer(){
        return actualPlayer;
    }

    public Player getBoardSign(int i, int j){ return board[i][j]; }

    /**
     * starts new game
     */
    public void endGame(){
        newGame(size);
    }


}
