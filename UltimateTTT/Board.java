//Jonathan Xu
//Professor Borazjany
//CS2336.001
//November 14th, 2022

package UltimateTTT;

public class Board {
   private int numRowsBoxes = 3;
   private int numColsBoxes = 3;
   private String winner;
   public Box[][] gameBox = new Box[numRowsBoxes][numColsBoxes];
   
   //I had to make gameBox public or else I would get a "gamebox has private access in Board" error
   
   public Board() {
      for (int boxRowCounter = 0; boxRowCounter < numRowsBoxes; boxRowCounter++) {
         for (int boxColCounter = 0; boxColCounter < numColsBoxes; boxColCounter++) {
            gameBox[boxRowCounter][boxColCounter] = new Box();
         }
      }
      
      winner = null;
   }
   
   public String getWinner(Player player) {
      if (this.winner != null) {
         return this.winner;
      }
      else {
         return checkWinner(player);
      }
   }
   
   //getWinner() gets called multiple times everytime checkBoards() gets called, and checkBoards() gets called once per turn, so getWinner() gets 
   //called multiple times every turn. getWinner() only tells you whether a single board has a winner and who has won the board, and in order to
   //determine if someone has won the overall game, you need to check each of the three rows, each of the three columns, and each of the two
   //diagonals.
   
   //Every turn, one of the players places down a mark on a specific tile in a specific board. The placement of this mark can cause the player
   //to win the board, and any change in one board can affect a maximum of one row, one column, and two diagonals. That's why it's necessary to
   //call getWinner() multiple times per turn, and to call it after every mark placement
   
   public String checkWinner(Player player) {
      //Checks each of the rows for a winner. The moment it detects a row that is completely occupied by the same mark, it returns the player's
      //name to show that that player has won the entire board
      for (int boxRowCounter = 0; boxRowCounter < numRowsBoxes; boxRowCounter++) {
         if ( (gameBox[boxRowCounter][0].getMark() == player.getSymbol()) && (gameBox[boxRowCounter][1].getMark() == player.getSymbol()) && (gameBox[boxRowCounter][2].getMark() == player.getSymbol()) ) {
            this.winner = player.getName();
            return player.getName();
         }
      }
      
      //Checks each of the columns for a winner
      for (int boxColCounter = 0; boxColCounter < numColsBoxes; boxColCounter++) {
         if ( (gameBox[0][boxColCounter].getMark() == player.getSymbol()) && (gameBox[1][boxColCounter].getMark() == player.getSymbol()) && (gameBox[2][boxColCounter].getMark() == player.getSymbol()) ) {
            this.winner = player.getName();
            return player.getName();
         }
      }
      
      //Checks the diagonal going from the top left corner to the bottom right corner
      if ( (gameBox[0][0].getMark() == player.getSymbol()) && (gameBox[1][1].getMark() == player.getSymbol()) && (gameBox[2][2].getMark() == player.getSymbol()) ) {
         this.winner = player.getName();
         return player.getName();
      }
      
      //Checks the diagonal going from the top right corner to the bottom left corner
      if ( (gameBox[0][2].getMark() == player.getSymbol()) && (gameBox[1][1].getMark() == player.getSymbol()) && (gameBox[2][0].getMark() == player.getSymbol()) ) {
         this.winner = player.getName();
         return player.getName();
      }
      
      //A player can win by completely occupying one of the three rows, one of the three columns, or one of the two diagonals. If the player
      //hasn't occupied any of these, then the player can't win. The board can either be full or not full.
      if (this.isFull()) {
         return "No winner";
      }
      else {
         return "Keep playing";  
      }
   }
   
   //If the board is full, every tile is occupied by a mark, and none of the tiles have whitespace. Therefore, if you encounter a single tile
   //with whitespace, you know the board isn't full. It doesn't necessarily mean it's empty, but there's no need to write an isEmpty() function
   public boolean isFull() {
      for (int boxRowCounter = 0; boxRowCounter < numRowsBoxes; boxRowCounter++) {
         for (int boxColCounter = 0; boxColCounter < numColsBoxes; boxColCounter++) {
            if (gameBox[boxRowCounter][boxColCounter].getMark() == ' ') {
               return false;
            }
         }
      }
      
      return true;
   }
   
   public char getMark(int boxRowNumber, int boxColNumber) {
      return gameBox[boxRowNumber][boxColNumber].getMark();
   }
   
   public boolean setMark(int boxRowNumber, int boxColNumber, char newMark) {
      if (gameBox[boxRowNumber][boxColNumber].getMark() == ' ') {
         return false;
      }
      else {
         gameBox[boxRowNumber][boxColNumber].setMark(newMark);
         return true;
      }
   }
   
//    public void print(int boardRowCounter) {
//       /*
//       for (int boxRowCounter = 0; boxRowCounter < numRowsBoxes; boxRowCounter++) {
//          for (int boxColCounter = 0; boxColCounter < numColsBoxes; boxColCounter++) {
//             System.out.print("|" + gameBox[boxRowCounter][boxColCounter].getMark());
//          }
//          System.out.print("|  ");
//       }
//       */
//       
//       //System.out.print("|");
//       
//       for (int boxColCounter = 0; boxColCounter < numColsBoxes; boxColCounter++) {
//          System.out.print("|" + gameBox[boardRowCounter][boxColCounter].getMark());
//       }
//       
//       System.out.print("|");
//    }
}