//Jonathan Xu
//Professor Borazjany
//CS2336.001
//November 15th, 2022

package UltimateTTT;

import java.util.Random;

public class ComputerPlayer extends Player {
   private Random RNG = new Random();

   //chooseBoard(), chooseBoxRow(), and chooseBoxCol() don't check to make sure that the chosen boards and tiles aren't already full or occupied.
   //The TTTGame class does that, and if the chosen boards and tiles are already full or occupied, then the TTTGame class gives the user an
   //error message and repeatedly invokes chooseBoard(), chooseBoxRow(), and chooseBoxCol() until the user provides valid inputs

   @Override
   public int chooseBoard(int numRowsBoards, int numColsBoards) {
      int boardChoice = RNG.nextInt(numRowsBoards * numColsBoards);
      System.out.println("The AI player " + this.getName() + " has selected BOARD #" + boardChoice + " to play on");
      
      //Using this.name gives the error "name has private access in Player"
      
      return boardChoice;
   }

   @Override
   public int chooseBoxRow(int numRowsBoards) {
      int rowChoice = RNG.nextInt(numRowsBoards);
      System.out.println("The AI player " + this.getName() + " has chosen row " + rowChoice);
      
      //Using this.name gives the error "name has private access in Player"
      
      return rowChoice;
   }
   
   @Override 
   public int chooseBoxCol(int numColsBoards) {
      int colChoice = RNG.nextInt(numColsBoards);
      System.out.println("The AI player " + this.getName() + " has chosen col " + colChoice);
      
      //Using this.name gives the error "name has private access in Player"
      
      return colChoice;
   }
}