/************************************************************************
 * Author: Nathan Dann
 * Date: 30 November 2025
 * Description: this program is the driver for the Golfer and TreeBag
   classes. It allows the user to interact with a database storing
   information of golfers that is stored in a binary search tree.
 ***********************************************************************/

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

class GolferScoresTree{

    public static void main(String [] args) throws Exception{

      TreeBag<Golfer> golferTree = new TreeBag<Golfer>();

      File golferFile = new File("golferinfo.txt");
      Scanner readFile = new Scanner(golferFile);

      Scanner intScanner = new Scanner(System.in);
      Scanner strScanner = new Scanner(System.in);

      //read file to get data
      while (readFile.hasNextLine()){

        String line = readFile.nextLine();

        int [] spaceIndexes = getCriticalIndexes(line);

        String name = line.substring(0, spaceIndexes[0]);

        int rounds = Integer.parseInt(line.substring(spaceIndexes[0]+1, spaceIndexes[1]));

        double avgScore = Double.parseDouble(line.substring(spaceIndexes[1]+1, line.length()));

        golferTree.add(new Golfer(name, rounds, avgScore));

      }

      //menu
      System.out.println("Choices:\n" +
              "1. Display listing of all golfers' stats\n" +
              "2. Display golfers in tree format\n" +
              "3. Display an individual golfer's stats\n" +
              "4. Update an individual golfer's stats\n" +
              "5. Add a new golfer to the database\n" +
              "6. Remove a golfer from the database\n" +
              "7. Exit");

      boolean terminate = false;
      while(!terminate) {

        int choice = 0;

        System.out.print("\nEnter choice (1-7): ");

        try {
          choice = intScanner.nextInt();
        } catch (InputMismatchException e){
          choice = -1;
          intScanner.next();
        }

        switch (choice) {

          case 1:
            try {
              golferTree.display();
            } catch(NullPointerException e){
              System.out.println("Error, there are no golfers in the database!");
            }
            break;

          case 2:
            try {
              golferTree.displayAsTree();
            } catch(NullPointerException e){
              System.out.println("Error, there are no golfers in the database!");
            }
            break;

          case 3:
            System.out.print("\nEnter golfer's last name: ");
            String name = strScanner.nextLine();
            try {
              System.out.println(golferTree.retrieve(new Golfer(name)).toString());
            } catch (IllegalArgumentException e){
              System.out.println("Error, " + name + " is not present in database!");
            }
            break;

          case 4:
            System.out.print("\nEnter golfer's last name: ");
            name = strScanner.nextLine();
            try{
              Golfer tempG = golferTree.retrieve(new Golfer(name));
              System.out.print("Enter " + name + "'s new score: ");
              int score = intScanner.nextInt();
              tempG.addScore(score);
              System.out.println(name + "\'s stats were updated");
            } catch (IllegalArgumentException e){
              System.out.println("Error, " + name + " is not present in database!");
            }
            break;

          case 5:
            System.out.print("\nEnter new golfer's last name: ");
            name = strScanner.nextLine();
            golferTree.add(new Golfer(name));
            System.out.println(name + " was added to the database");
            break;

          case 6:
            System.out.print("\nEnter last name of golfer to remove: ");
            name = strScanner.nextLine();
            boolean status = golferTree.remove(new Golfer(name));
            if(status) {
              System.out.println(name + " was successfully removed from the database");
            } else{
              System.out.println("\"" + name + "\"" + " was not present in database!");
            }

            break;

          case 7:
            System.out.print("\nProgram terminated\n************************************************");
            terminate = true;
            break;

          default:
            System.out.println("Error: integer input 1-7 only!");
            break;
        }
      }
    } /* main */


  /******************************************************************
   * Description: this method identifies the critical indexes of
   * the strings in the golferinfo file. It assumes that the file's
   * lines are formatted in the following way:
   * "lastname x y" where x is the number of rounds the golfer has
   * played, and y is the golfer's average score
   * @param line
   * The line from the file to be assessed
   * @return
   * An integer array of the indexes at which space characters are
   * present (should be exactly two values)
   ******************************************************************/
    public static int[] getCriticalIndexes(String line){

      //array storing indexes of space characters
      int [] indexArray = new int[2];

      //index value trackers
      int arrayIndex = 0;
      int stringIndex = 0;

      //searches string for space characters
      while(arrayIndex < 2 && stringIndex < line.length()){
        if(line.charAt(stringIndex) == ' '){
          indexArray[arrayIndex] = stringIndex;
          arrayIndex++;
        }
        stringIndex++;
      }
      return indexArray;
    } /* getCriticalIndexes */

} /* GolferScoresTree */