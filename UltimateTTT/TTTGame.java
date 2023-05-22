//Jonathan Xu
//Professor Borazjany
//CS2336.001
//November 13th, 2022

package UltimateTTT;

/*
Analysis:

Tic-tac-toe is a two-player game with a 3 by 3 grid, where each player's objective is to fill up an entire row, column, or diagonal before the
other player can do so. It's an incredibly simple game, and if you know how to play it right, you can always force a tie no matter what.
Ultimate Tic-tac-toe tries to keep things fresh and interesting by having nine tic-tac-toe games running simultaneously, in a 3 by 3 grid. In
order to win, a player must win 3 games in a row, column, or diagonal, but the boards don't have to be completely full to win. Once a board has
been won, players can place more marks on it, but it won't affect the outcome of the game. 

In another twist, the board that each player puts their mark on, corresponds to the square that the previous player marked. If you mark square
5 within board 3, your opponent will have to play on board 5 next turn, but is free to choose which square within the board to mark. The only
exception is if the assigned board is full, in which case your opponent can choose one that isn't full to play on.

Design:

My design has six classes. The main class is TTTGame, which is a composition of the Board class, which in turn is a composition of the Box class.
TTTGame also has a dependency relationship with the abstract Player class, which are inherited by the ComputerPlayer and HumanPlayer classes.

TTTGame includes six functions: setup(), printBoardWinners(), checkBoards(), newTurn(), print(), and main(). Setup() and main() are called only
once at the beginning of the program, but the other four are called once every turn. 

Setup() prints out a number of menus that allow the user to choose the type of game he wants to play, name the players, and set the players'
marks. The three types of games are Player vs Player, Player vs AI, and AI vs AI. The rules are the same for each one, but for Players, the
user inputs their board and tile choices manually, while for AI, the program automatically selects random inputs. If the user chooses not to
name the players, they are given the default names Player One and Player Two. In traditional tic-tac-toe, the most common marks are X and O, but
here, the user is free to designate whatever marks he wants to represent the two players, as long as they are not the same.

printBoardWinners() is called once per turn, and uses two for loops to iterate through the 2D array of Board objects and print out which users 
have already won which boards. Each instance of the Board class contains a 2D array of Box objects, a private String variable called winner, a
public getWinner() and checkWinner() function, a public isFull() function, and a public getMark() and setMark() function. Each time the nested
for loop is executed, it tells the current Board instance to run getWinner(). If someone has already won the board or it's full and has been
tied, then getWinner() either returns that player's name or returns "No winner." Otherwise, getWinner() calls checkWinner(), which checks
each of the three rows, three columns, and two diagonals to see if either player has won the board.

checkBoards() does the same thing, but instead of checking to see if either player has won a specific board, checkBoards() verifies whether
either player has won the overall game. 

Each of the two Player objects has a chooseBoard(), chooseBoxRow(), and chooseBoxCol() function, but depending on whether the Player objects
are ComputerPlayers or HumanPlayers, they are implemented differently. chooseBoard() is only called for the very first turn and when the
player's assigned board is full, and the user or computer selects an integer from 0 to 8, inclusive, which corresponds to one of the 9 boards
in the 3 by 3 grid. chooseBoxRow() and chooseBoxCol() function the same way, and the three return values from these three functions, corresponds
to a specific tile within a specific board.

newTurn() accepts the return value from chooseBoard(), runs chooseBoxRow() and chooseBoxCol(), marks a specific tile on a specific board with
one of the two player's marks, and returns the other player's next board. print() prints out all nine boards in a 3 by 3 grid, so that the user
can see the updated boards.
*/

import java.util.Scanner;
import java.util.Random;

public class TTTGame {
   private static int numRowsBoards = 3;
   private static int numColsBoards = 3;
   private static int numPlayers = 2;
   private static Board[][] gameBoard = new Board[numRowsBoards][numColsBoards];
   private static Player[] players = new Player[numPlayers];
   
   private static void setup() {
      System.out.println("Welcome to the Ultimate Tic Tac Toe board game!\n");
      
      System.out.println("Would you like to play Player vs Player, Player vs AI, or AI vs AI?");
      System.out.println("1 – Player vs Player");
      System.out.println("2 - Player vs AI");
      System.out.println("3 - AI vs AI");
      System.out.println("4 - Exit the menu");
      
      Scanner menuSelection = new Scanner(System.in);
      int gameChoice = (int)menuSelection.nextFloat();

      //int gameChoice = menuSelection.nextInt();
      //If the user inputs a decimal, character, or String, the user gets a "java.util.InputMisMatchException." I fixed the decimal error by 
      //reading in the value as a float, and casting it to an int, which truncates the decimal. However, inputting a character or String will
      //still lead to an error

      while ( (gameChoice < 1) || (gameChoice > 4) ) {
         System.out.println("Please enter an integer from 1 to 4");
         gameChoice = (int)menuSelection.nextFloat();
      }
      
      //The HumanPlayer and ComputerPlayer classes both inherit the abstract Player class, and don't add any extra attributes or methods.
      //However, they override the chooseBoard(), chooseBoxCol(), and chooseBoxRow() methods. Whether the Player instances are HumanPlayer or
      //ComputerPlayer objects will affect the body of these three functions
      
      switch (gameChoice) {
         case 1:
            System.out.println("Thank you for choosing Player vs Player");
            players[0] = new HumanPlayer();
            players[1] = new HumanPlayer();
            break;
            
         case 2:
            System.out.println("Thank you for choosing Player vs AI");
            players[0] = new HumanPlayer();
            players[1] = new ComputerPlayer();
            break;
         
         case 3:
            System.out.println("Thank you for choosing AI vs AI");
            players[0] = new ComputerPlayer();
            players[1] = new ComputerPlayer();
            break;
            
         case 4: 
            System.out.println("We hope to see you again soon!");
            break;
      }
      
      System.out.println();
      System.out.println("Would you like to name the players?");
      System.out.println("1 - Name both players");
      System.out.println("2 - Name player one");
      System.out.println("3 - Name player two");
      System.out.println("4 - Default names – Player One and Player Two");
      int nameChoice = (int)menuSelection.nextFloat();
      
      while ( (nameChoice < 1) || (nameChoice > 4) ) {
         System.out.println("Please type 1 or 2");
         nameChoice = (int)menuSelection.nextFloat();
      }
      
      String firstPlayerName;
      String secondPlayerName;
      
      //The names can have upper and lowercase characters, numbers, and special characters, but not whitespace
            
      switch (nameChoice) {
         case 1:
            System.out.println("What would you like to name the first player?");
            firstPlayerName = menuSelection.next();
            players[0].setName(firstPlayerName);
            //players[0].setName(menuSelection.nextLine());
 
            System.out.println("The name of the first player is " + players[0].getName()); 
         
            System.out.println("What would you like to name the second player?");
            secondPlayerName = menuSelection.next();
            players[1].setName(secondPlayerName);
            //players[1].setName(menuSelection.nextLine());
         
            System.out.println("The name of the second player is " + players[1].getName()); 
            break;
            
         case 2:
            System.out.println("What would you like to name the first player?");
            firstPlayerName = menuSelection.next();
            players[0].setName(firstPlayerName);
            //players[0].setName(menuSelection.nextLine());
 
            System.out.println("The name of the first player is " + players[0].getName()); 
            
            players[1].setName("Player Two");
            System.out.println("The name of the second player is Player Two");
            break;
         
         case 3:
            players[0].setName("Player One");
            System.out.println("The name of the first player is Player One");
            
            System.out.println("What would you like to name the second player?");
            secondPlayerName = menuSelection.next();
            players[1].setName(secondPlayerName);
            //players[1].setName(menuSelection.nextLine());
 
            System.out.println("The name of the second player is " + players[1].getName()); 
            break;
         
         case 4:
            players[0].setName("Player One");
            System.out.println("The name of the first player is Player One");
           
            players[1].setName("Player Two");
            System.out.println("The name of the second player is Player Two");   
      }       
      
      System.out.println();
      
      System.out.println("What mark do you want to represent " + players[0].getName() + "?");
      char firstPlayerSymbol = menuSelection.next().charAt(0);
      players[0].setSymbol(firstPlayerSymbol);
      //The Scanner class doesn't have a nextChar() function. menuSelection.next() reads in the user's inputs until it encounters a whitespace,
      //and charAt(0) returns the character found at index 0
      
      System.out.println(players[0].getName() + " will use the symbol " + players[0].getSymbol());
      
      System.out.println("What mark do you want to represent " + players[1].getName() + "?");
      char secondPlayerSymbol = menuSelection.next().charAt(0);
      
      while (firstPlayerSymbol == secondPlayerSymbol) {
         System.out.println("The players can't have the same mark. Pick a different symbol for " + players[1].getName());
         secondPlayerSymbol = menuSelection.next().charAt(0);
      }
      
      players[1].setSymbol(secondPlayerSymbol);
      System.out.println(players[1].getName() + " will use the symbol " + players[1].getSymbol());
   }
   
   /*
   The player only wins the game if the player has won three boards in a row, column, or diagonal. However, winning just one board can affect the
   other player's strategy. printBoardWinners() iterates through all of the boards and lets the players know who has won which boards. It's 
   called once per turn
   */
   
   private static void printBoardWinners() {
      for (int boardRowCounter = 0; boardRowCounter < numRowsBoards; boardRowCounter++) {
         for (int boardColCounter = 0; boardColCounter < numColsBoards; boardColCounter++) {
            if (gameBoard[boardRowCounter][boardColCounter].getWinner(players[0]) == players[0].getName()) {
               System.out.println(players[0].getName() + " has won BOARD #" + (boardRowCounter * numColsBoards + boardColCounter));
            }
            
            if (gameBoard[boardRowCounter][boardColCounter].getWinner(players[1]) == players[1].getName()) {
               System.out.println(players[1].getName() + " has won BOARD #" + (boardRowCounter * numColsBoards + boardColCounter));
            }
            
            if ( (gameBoard[boardRowCounter][boardColCounter].getWinner(players[0]) == "No winner")) {
               System.out.println("BOARD #" + (boardRowCounter * numColsBoards + boardColCounter) + " has resulted in a tie");
            }
         }
      }
   }
   
   private static String checkBoards(Player player) {      
      for (int boardRowCounter = 0; boardRowCounter < numRowsBoards; boardRowCounter++) {
         if ( (gameBoard[boardRowCounter][0].getWinner(player) == player.getName()) && (gameBoard[boardRowCounter][1].getWinner(player) == player.getName()) && (gameBoard[boardRowCounter][2].getWinner(player) == player.getName()) ) {
            return player.getName();
         }
      }
      
      for (int boardColCounter = 0; boardColCounter < numColsBoards; boardColCounter++) {
         if ( (gameBoard[0][boardColCounter].getWinner(player) == player.getName()) && (gameBoard[1][boardColCounter].getWinner(player) == player.getName()) && (gameBoard[2][boardColCounter].getWinner(player) == player.getName()) ) {
            return player.getName();
         }
      }      
      
      if ( (gameBoard[0][0].getWinner(player) == player.getName()) && (gameBoard[1][1].getWinner(player) == player.getName()) && (gameBoard[2][2].getWinner(player) == player.getName()) ) {
         return player.getName();
      }
      if ( (gameBoard[0][2].getWinner(player) == player.getName()) && (gameBoard[1][1].getWinner(player) == player.getName()) && (gameBoard[2][0].getWinner(player) == player.getName()) ) {
         return player.getName();
      }
      if ( (gameBoard[0][0].getWinner(player) != "Keep playing") && (gameBoard[0][1].getWinner(player) != "Keep playing") && (gameBoard[0][2].getWinner(player) != "Keep playing") && (gameBoard[1][0].getWinner(player) != "Keep playing") && (gameBoard[1][1].getWinner(player) != "Keep playing") && (gameBoard[1][2].getWinner(player) != "Keep playing") && (gameBoard[2][0].getWinner(player) != "Keep playing") && (gameBoard[2][1].getWinner(player) != "Keep playing") && (gameBoard[2][2].getWinner(player) != "Keep playing") ) {
         return "Tie";
      }
      
      return "Still playing";
   }
   /*
   private static int chooseBoxRow(Player player) {
      int rowChoice; 
      
      if (player instanceof HumanPlayer) {
         Scanner gameSelection = new Scanner(System.in);  
         System.out.println(player.getName() + ", pick the row of your square");
         rowChoice = gameSelection.nextInt();
      
         while ( (rowChoice < 0) || (rowChoice > numRowsBoards - 1) ) {
            System.out.println("You must choose a row from 0 to " + (numRowsBoards - 1));
            rowChoice = gameSelection.nextInt();
         }
      }
      else {
         Random RNG = new Random();
         rowChoice = RNG.nextInt(numRowsBoards);
         System.out.println("The AI player " + player.getName() + " has chosen row " + rowChoice);
      }
         
      return rowChoice;
   }
   */
   
   /*
   private static int chooseBoxCol(Player player) {
      int colChoice;
      Scanner gameSelection = new Scanner(System.in);
   
      if (player instanceof HumanPlayer) {
         //Scanner gameSelection = new Scanner(System.in);
         System.out.println(player.getName() + ", pick the column of your square");
         colChoice = gameSelection.nextInt();
      
         while ( (colChoice < 0) || (colChoice > numColsBoards - 1) ) {
            System.out.println("You must choose a col from 0 to " + (numColsBoards - 1));
            colChoice = gameSelection.nextInt();
         }
      }
      else {
         Random RNG = new Random();
         colChoice = RNG.nextInt(numColsBoards);
         System.out.println("The AI player " + player.getName() + " has chosen column " + colChoice);
      }
      
      return colChoice;
   }
   */
   
   /*
   private static int chooseBoard() {
      Scanner gameSelection = new Scanner(System.in);
      int boardChoice = gameSelection.nextInt();
      
      while ( (boardChoice < 0) || (boardChoice > numRowsBoards * numColsBoards - 1) ) {
         System.out.println("You must choose a board from 0 to " + (numRowsBoards * numColsBoards - 1));
         boardChoice = gameSelection.nextInt();
      } 
      
      return boardChoice;      
   }
   */
      
   private static int newTurn(int boardRowNumber, int boardColNumber, Player player) {
      /*
      Scanner gameSelection = new Scanner(System.in);
      
      System.out.println(player.getName() + ", pick the row of your square");
      int rowChoice = gameSelection.nextInt();
      
      while ( (rowChoice < 0) || (rowChoice > numRowsBoards - 1) ) {
         System.out.println("You must choose a row from 0 to " + (numRowsBoards - 1));
         rowChoice = gameSelection.nextInt();
      }

      System.out.println(player.getName() + ", pick the column of your square");
      int colChoice = gameSelection.nextInt();
      
      while ( (colChoice < 0) || (colChoice > numColsBoards - 1) ) {
         System.out.println("You must choose a col from 0 to " + (numColsBoards - 1));
         colChoice = gameSelection.nextInt();
      }
      */
      
      //int rowChoice = chooseBoxRow(player);
      //int colChoice = chooseBoxCol(player);
      int rowChoice = player.chooseBoxRow(numRowsBoards);
      int colChoice = player.chooseBoxCol(numColsBoards);
      
      //System.out.println("boardRowNumber is " + boardRowNumber);
      //System.out.println("boardColNumber is " + boardColNumber);
      //System.out.println("rowChoice is " + rowChoice);
      //System.out.println("colChoice is " + colChoice);      
      
      while (gameBoard[boardRowNumber][boardColNumber].gameBox[rowChoice][colChoice].getMark() != ' ') {
         System.out.println("That box is already occupied. You'll have to pick a different box");
         //rowChoice = chooseBoxRow(player);
         //colChoice = chooseBoxCol(player);
         rowChoice = player.chooseBoxRow(numRowsBoards);
         colChoice = player.chooseBoxCol(numColsBoards);
      }
      
      gameBoard[boardRowNumber][boardColNumber].gameBox[rowChoice][colChoice].setMark(player.getSymbol());
     
      int boardNum = rowChoice * numColsBoards + colChoice;
      
      return boardNum;
   }
      
   private static void print() {
      //System.out.println("print() has been called");      
      int boardNum = 0;
           
      for (int boardRowCounter = 0; boardRowCounter < numRowsBoards; boardRowCounter++) {
         System.out.println("BOARD #" + boardNum++ + "---- " + "BOARD #" + boardNum++ + "---- " + "BOARD #" + boardNum++ + "---- ");
         for (int boxRowCounter = 0; boxRowCounter < numRowsBoards; boxRowCounter++) {
            for (int boardColCounter = 0; boardColCounter < numColsBoards; boardColCounter++) {
               for (int boxColCounter = 0; boxColCounter < numColsBoards; boxColCounter++) {
                  System.out.print("|" + gameBoard[boardRowCounter][boardColCounter].gameBox[boxRowCounter][boxColCounter].getMark());
               }
               System.out.print("|     ");
            }
            System.out.println();
         }
         System.out.println();
      }      
   }
   
   public static void main(String[] args) {
      for (int boardRowCounter = 0; boardRowCounter < numRowsBoards; boardRowCounter++) {
         for (int boardColCounter = 0; boardColCounter < numColsBoards; boardColCounter++) {
            gameBoard[boardRowCounter][boardColCounter] = new Board();
         }
      }
      
      setup();
      print();
      
      int boardChoice;
      
      //If the user selects option 2 in setup(), which is Player vs AI, players[0] will be a HumanPlayer object and players[1] will be a ComputerPlayer
      //object. Since players[0] gets to choose the board, the board will be chosen manually by a HumanPlayer object. The only time the board is randomly
      //selected by a ComputerPlayer object is when the user selects option 3 in setup(), which is AI vs AI. 
      
      boardChoice = players[0].chooseBoard(numRowsBoards, numColsBoards);
         
      int boardRowNumber = boardChoice / numColsBoards;
      int boardColNumber = boardChoice % numColsBoards;
      
      //After the first turn, both HumanPlayers and ComputerPlayers don't get to choose what board they want to play on, and have to play on the board
      //corresponding to their opponent's last move
      
      System.out.println(players[0].getName() + ", you'll be playing on BOARD #" + boardChoice);
      boardChoice = newTurn(boardRowNumber, boardColNumber, players[0]);
      print();
      
      while (checkBoards(players[0]) == "Still playing") {
         printBoardWinners();
         
         boardRowNumber = boardChoice / numColsBoards;
         boardColNumber = boardChoice % numColsBoards;
         while (gameBoard[boardRowNumber][boardColNumber].isFull() == true) {
            System.out.println(players[1].getName() + ", the board you were assigned is full, so you can choose your own board.");
            boardChoice = players[1].chooseBoard(numRowsBoards, numColsBoards);
            
            boardRowNumber = boardChoice / numColsBoards;
            boardColNumber = boardChoice / numColsBoards;
            
            /*
            if (players[1] instanceof HumanPlayer) {
               boardChoice = chooseBoard();
               boardRowNumber = boardChoice / numColsBoards;
               boardColNumber = boardChoice % numColsBoards;
            }
            else {
               boardChoice = rand.nextInt(numRowsBoards * numColsBoards);
               System.out.println("The AI player " + players[0].getName() + " has selected BOARD #" + boardChoice + " to play on");
               
               boardRowNumber = boardChoice / numColsBoards;
               boardColNumber = boardChoice % numColsBoards;
            }
            */
         }
         System.out.println(players[1].getName() + ", you'll be playing on BOARD #" + boardChoice);
         boardChoice = newTurn(boardRowNumber, boardColNumber, players[1]);
         print();
         
         printBoardWinners();
         if (checkBoards(players[1]) != "Still playing") {
            break;
         }
         
         boardRowNumber = boardChoice / numColsBoards;
         boardColNumber = boardChoice % numColsBoards;
         while (gameBoard[boardRowNumber][boardColNumber].isFull() == true) {
            System.out.println(players[0].getName() + ", the board you were assigned is full, so you can choose your own board.");
            boardChoice = players[0].chooseBoard(numRowsBoards, numColsBoards);
            
            boardRowNumber = boardChoice / numColsBoards;
            boardColNumber = boardChoice % numColsBoards;
            
            /*
            if (players[0] instanceof HumanPlayer) {
               boardChoice = chooseBoard();
               boardRowNumber = boardChoice / numColsBoards;
               boardColNumber = boardChoice % numColsBoards;
            }
            else {
               boardChoice = rand.nextInt(numRowsBoards * numColsBoards);
               System.out.println("The AI player " + players[0].getName() + " has selected BOARD #" + boardChoice + " to play on");
               
               boardRowNumber = boardChoice / numColsBoards;
               boardColNumber = boardChoice % numColsBoards;
            }
            */
         }  
         System.out.println(players[0].getName() + ", you'll be playing on BOARD #" + boardChoice);
         boardChoice = newTurn(boardRowNumber, boardColNumber, players[0]);
         print();
      }
      
      if (checkBoards(players[0]) == players[0].getName()) {
         System.out.println(players[0].getName() + " has won the game");
         return;
      }
      
      if (checkBoards(players[1]) == players[1].getName()) {
         System.out.println(players[1].getName() + " has won the game");
         return;
      }
      
      if (checkBoards(players[0]) == "Tie") {
         System.out.println("We have a tie!");
         return;
      }
   }
}