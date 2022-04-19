package oogasalad.engine.model.rule.terminal_conditions;

import oogasalad.engine.model.actions.winner.WinDecision;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.conditions.Condition;

/**
 * Ahstract class that determines when end condtions - draw and or one player wins - are acheived.
 * @author Haris Adnan
 */
public abstract class EndCondition {
  private Condition[] myEndConditions;
  private WinDecision myWinDecision;

  /**
   * Returns true if all the end game conditions are met and false otherwise
   * @param board current game board
   * @return whether end conditions have been met
   */
  public boolean isOver(Board board) {
    for(Condition endCondition : myEndConditions){
      if(!endCondition.isTrue(board, null)){
        return false;
      }
    }
    return true;
  }

}