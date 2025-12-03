/********************************************************************************
 * Author: Nathan Dann
 * Date: 2025-10-31
 * Project: Data Structures Project03
 * File: QueenSimulation.java
 * Description:
   This program is the driver for the Queen class. It allows the user to find
   the number of solutions to the n-queens problem for a user-defined board
   size. The idea of the n-queens problem is to find configurations of placing
   n queens (where n = number of columns / rows) on a chess board in such a
   manner that none of them threaten another. It is known that one queen may
   occupy each row.
 ********************************************************************************/

import java.util.InputMismatchException;
import java.util.Scanner;

class QueenSimulation{

    public static void main(String [] args) {

        System.out.println("This program will ask you to enter a board size. It will then calculate\n" +
                "the number of possible configurations in which n queens (one on each row) may be placed\n" +
                "on a chess board such that no one of them rivals another.\n" +
                "Note that the board must be square, so only one input value is required.\n" +
                "Also note that values above 12 will take several minutes or more to compute\n\n");
        Scanner keyScan = new Scanner(System.in);

        LinkedStack<Queen> queenStack = new LinkedStack<Queen>();

        int boardSize = 0;
        boolean validInput = false;
        while(!validInput){
            System.out.print("Enter board size (nxn): ");
            try {
                boardSize = keyScan.nextInt();

                if(boardSize > 1) {
                    validInput = true;
                } else{
                    System.out.println("Enter a board size greater than or equal to 2!");
                }

                if(boardSize > 10) {
                    System.out.println("Calculating...");
                }
            } catch(InputMismatchException e){
                System.out.println("Enter a positive integer value!");
                keyScan.next();
            }
        }

        //current positions of queen candidate
        int currentRow = 1;
        int currentColumn = 1;

        //number of queens currently on board
        int queensOnBoard = 0;

        //number of solutions found
        int numSolutions = 0;


        boolean terminate = false;
        boolean foundConflict = false;

        while(!terminate) {

            Queen candidate = new Queen(currentRow, currentColumn);

            //checks for conflicts between candidate and all queens in stack
            if(queensOnBoard > 0){
                for(int i = 1; i <= queensOnBoard && !foundConflict; i++){
                    if(candidate.conflict(queenStack.itemAt(i))){
                        foundConflict = true;
                    }
                }
            }

            //no conflict found
            if(!foundConflict){

                queenStack.push(candidate);
                queensOnBoard++;


                if(queensOnBoard < boardSize) {
                    currentRow++; //advance row
                    currentColumn = 1; //reset column

                //indicates that a solution has been found
                } else{

                    numSolutions++;



                    //formatting asterisks
                    if(numSolutions == 1) {
                        System.out.println();
                        for(int i = 0; i < 117; i ++){
                            System.out.print("*");
                        }
                    }



                    System.out.print("\n\n");
                    System.out.print("SOLUTION " + numSolutions + ": ");
                    int numPrints = 0;
                    for(int i = queensOnBoard; i > 0; i--){
                        System.out.print(queenStack.itemAt(i).toString() + '\t');
                        numPrints++;
                        if(numPrints % 5 == 0){
                            System.out.println();
                        }
                    }
                }

            //conflict found
            } else{

                foundConflict = false;

                //residual queen
                if(candidate.equals(queenStack.peek())){
                    queenStack.pop();
                    queensOnBoard--;
                }

                //if there is still space on the row
                if(currentColumn < boardSize){
                    currentColumn++; //advance column

                //current column = board size
                } else{

                    //loop has reached terminal state with row one queen on in the last column
                    if(queenStack.peek().getRow() == 1 && queenStack.peek().getColumn() == boardSize){
                        terminate = true;
                        queenStack.pop();
                        System.out.println("\n\nCONCLUSION: number of solutions found for N = " + boardSize + ": " + numSolutions);

                        //formatting asterisks
                        System.out.println();
                        for(int i = 0; i < 117; i ++){
                            System.out.print("*");
                        }

                        //loop is not at terminal state
                    } else {

                        currentColumn = queenStack.peek().getColumn();
                        currentRow--;

                    } /* else */
                } /* else */
            } /* else */
        } /* while */
    } /* main*/
} /* class QueenSimulation */