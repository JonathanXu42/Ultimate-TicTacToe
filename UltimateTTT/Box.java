//Jonathan Xu
//Professor Borazjany
//CS2336.001
//November 13th, 2022

package UltimateTTT;

public class Box {
   private char mark;
   
   public Box() {
      this.mark = ' ';
      //I can't set this to a random symbol, because the players are allowed to pick their own symbols. If the board is populated by a random
      //symbol and one of the two players happens to pick that symbol, then that player wins immediately, which is no fun. I decided to set this
      //to a whitespace, since players are unlikely to pick a whitespace as their mark
   }
   
   public char getMark() {
      return this.mark;
   }
   
   public void setMark(char newMark) {
      this.mark = newMark;
   }
}