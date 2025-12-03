/*********************************************************************************
 * Nathan Dann
 * 2025-10-31
 * Project: Data Structures Project03
 * File Name: Queen.java
 * Description:
   This class is meant to simulate queen chess pieces being placed on a board.
   A queen object is created by a call to a constructor that accepts two integer
   values, one for the row position, and the other for the column.
*********************************************************************************/

class Queen{

    //*** invariant of the Queen class ***
    // 1. row is the Y-coordinate of the queen chess piece on the board
    // 2. column is the X-coordinate of the queen chess piece on the board
    private int row;
    private int column;

    /***********************************************************************
     * Constructor for Queen class
     * @param rowLocation
     * The row location of the queen
     * @param columnLocation
     * The column location of the queen
     * @exception java.util.InputMismatchException
     * Indicates that a non-integer value was passed as a parameter
     ***********************************************************************/
    public Queen(int rowLocation, int columnLocation){
        this.row = rowLocation;
        this.column = columnLocation;
    }

    /**
     * Accessor for private row instance variable
     * @return
     * The integer value stored in row
     */
    public int getRow(){
        return row;
    }

    /**
     * Accessor for private column instance variable
     * @return
     * The integer value stored in column
     */
    public int getColumn(){
        return column;
    }

    /**
     * Outputs a Queen's row and column in accessible format
     * @return
     * A string displaying the queen's row and column values
     */
    public String toString() {
        return ("(Row = " + this.row + ", Column = " + this.column + ")");
    }

    /**
     * Checks if a second queen object threatens the current one
     * @param candidate
     * A queen object to compare to the current one
     * @return
     * Boolean T = conflict, F = no conflict
     */
    public boolean conflict(Queen candidate){

        boolean flag = false;

        //checks for vertical / horizontal conflicts
        if(this.row == candidate.row || this.column == candidate.column){
            flag = true;

        //checks for diagonal conflicts
        } else if(Math.abs(this.row - candidate.row) == Math.abs(this.column - candidate.column)){
            flag = true;
        }

        return flag;
    }

    /**
     * Method to check for equality between two queen objects
     * @param obj
     * A second queen object to be compared to the current one
     * @return
     * Boolean: T = equal, F = not equal
     */
    public boolean equals(Object obj){
        boolean flag = false;
        if(obj instanceof Queen candidate){
            //Queen candidate = (Queen)obj;
            if(candidate.row == this.row && candidate.column == this.column){
                flag = true;
            }
        } else{
            throw new IllegalArgumentException("Object is not an instance of Queen");
        }
        return flag;
    }
}