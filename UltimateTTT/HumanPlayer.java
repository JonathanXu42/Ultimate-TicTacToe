//Jonathan Xu
//Professor Borazjany
//CS2336.001
//November 16th, 2022

package UltimateTTT;

import java.util.Scanner;     //Required to read in the player's inputs

public class HumanPlayer extends Player {
   private Scanner gameSelection = new Scanner(System.in);
   
   //chooseBoard(), chooseBoxRow(), and chooseBoxCol() don't check to make sure that the chosen boards and tiles aren't already full or occupied.
   //The TTTGame class does that, and if the chosen boards and tiles are already full or occupied, then the TTTGame class gives the user an
   //error message and repeatedly invokes chooseBoard(), chooseBoxRow(), and chooseBoxCol() until the user provides valid inputs
   
   @Override
   public int chooseBoard(int numRowsBoards, int numColsBoards) {
      System.out.println(this.getName() + ", pick your board");
      //Using this.name gives the error "name has private access in Player"
   
      int boardChoice = gameSelection.nextInt();
      
      while ( (boardChoice < 0) || (boardChoice > numRowsBoards * numColsBoards - 1) ) {
         System.out.println("You must choose a board from 0 to " + (numRowsBoards * numColsBoards - 1));
         boardChoice = gameSelection.nextInt();
         
         //If the parentheses around (numRowsBoards * numColsBoards - 1) weren't there, I would get the error 
         //"bad operand types for binary operator '-'"
      } 
      
      return boardChoice;      
   }

   @Override
   public int chooseBoxRow(int numRowsBoards) {
      //Using this.name gives the error "name has private access in Player"
      
      //System.out.println(player.getName() + ", pick the row of your square");
      System.out.println(this.getName() + ", pick the row of your square");
      int rowChoice = gameSelection.nextInt();
      
      while ( (rowChoice < 0) || (rowChoice > numRowsBoards - 1) ) {
         System.out.println("You must choose a row from 0 to " + (numRowsBoards - 1));
      }
      
      return rowChoice;
   }
   
   @Override
   public int chooseBoxCol(int numColsBoards) {
      //Using this.name gives the error "name has private access in Player"   
   
      //System.out.println(player.getName() + ", pick the column of your square");
      System.out.println(this.getName() + ", pick the column of your square");
      int colChoice = gameSelection.nextInt();
      
      while ( (colChoice < 0) || (colChoice > numColsBoards - 1) ) {
         System.out.println("You must choose a column from 0 to " + (numColsBoards - 1));
         colChoice = gameSelection.nextInt();
      }
      
      return colChoice;
   }
}