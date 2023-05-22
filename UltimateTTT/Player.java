//Jonathan Xu
//Professor Borazjany
//CS2336.001
//November 13th, 2022

package UltimateTTT;

public abstract class Player {
   private String name;
   private char symbol;
   
   public String getName() {
      return this.name;
   }
   
   public char getSymbol() {
      return this.symbol;
   }
   
   public void setName(String newName) {
      this.name = newName;
   }
   
   public void setSymbol(char newSymbol) {
      this.symbol = newSymbol;
   }

   //chooseBoard(), chooseBoxRow(), and chooseBoxCol() don't check to make sure that the chosen boards and tiles aren't already full or occupied.
   //The TTTGame class does that, and if the chosen boards and tiles are already full or occupied, then the TTTGame class gives the user an
   //error message and repeatedly invokes chooseBoard(), chooseBoxRow(), and chooseBoxCol() until the user provides valid inputs
   
   public abstract int chooseBoard(int numRowsBoards, int numColsBoards);
   public abstract int chooseBoxRow(int numRowsBoards);
   public abstract int chooseBoxCol(int numColsBoards);
}