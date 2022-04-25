package oogasalad.engine.model.driver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import oogasalad.engine.model.board.Board;

/**
 * Game class that sets up the current board and contains history of all the previous boards.
 * @author: Jake Heller, Haris Adnan
 */
public class Game {

  private Board myBoard;
  private Consumer<Board> myUpdateView;
  private List<Board> myBoardHistory;
  private int backInHistory;


  public Game(Board startingBoard, Consumer<Board> updateView) {
    myBoard = startingBoard;
    myBoardHistory = new ArrayList<Board>();
    myUpdateView = updateView;
    backInHistory = 0;
  }

  /**
   * removes all boards after the current board from history and sets the param Board as the current board
   * @param board
   */
  public void setBoard(Board board) {
    if (backInHistory != 0){

      for(int i = 0; i<backInHistory; i++){
        myBoardHistory.remove(myBoardHistory.size() -1);
      }
    }
    myBoardHistory.add(myBoard);
    myBoard = board;
    updateView(myBoard);
    backInHistory = 0;
  }

  public Board getBoard() {
    return myBoard;
  }

  /**
   * function saves the current board and sets the previous board as the current board
   */
  public void back() throws BoardHistoryException{
    if (myBoardHistory.size() == 0){
      throw new BoardHistoryException("You have gone too far back in the history, no board to show");
    }
    else{
      backInHistory++;
      // changing this - removing the -1 from the get parameter makes the tests work. However, in the
      // checkers game for 2 players currently, the board undoes the AI's move and then doesnt allow us to move
      myBoard = myBoardHistory.get(myBoardHistory.size()-backInHistory);
      myBoardHistory.add(myBoard);
      updateView(myBoard);

    }

  }
  /**
   * function sets the current board as the previous booard (adds it to the history of boards ) and
   * then makes the new board the current board.
   */
  public void forward() throws BoardHistoryException{
    if (backInHistory == 0){
      throw new BoardHistoryException("you have gone too far up in history, no board to show");
    }
    else{
      myBoardHistory.add(myBoard);
      backInHistory--;
      myBoard = myBoardHistory.get(myBoardHistory.size()-1-backInHistory);
      updateView(myBoard);
    }

  }

  /**
   * function goes Number boards back in History of Boards and makes this the current board
   * @param number
   */
  public void backByAmount(int number) throws BoardHistoryException{
    for(int i = 0; i< number; i++){
      back();
    }
  }

  private void updateView(Board board) {
    if (myUpdateView != null) {
      myUpdateView.accept(board);
    }
  }

  /**
   * function goes number boards forward in the history of boards and makes this the current board
   * @param number
   * @throws BoardHistoryException
   */
  public void forwardByAmount(int number) throws BoardHistoryException{
    for(int i = 0; i< number; i++){
      forward();
    }
  }


}
