/********************************************************
 * Nathan Dann
 * CSC 103
 * Project 1
 *
 * This class is the driver program for the DoubleArraySeq class. It will showcase this class's methods as they apply
 * to the DoubleArraySeq object
 ********************************************************/


import java.util.InputMismatchException;
import java.util.Scanner;

class SequenceTest{

    public static void main(String [] args){

        Scanner choiceReader = new Scanner(System.in);

        //instance of the DoubleArraySeq object
        DoubleArraySeq dub = new DoubleArraySeq();

        System.out.println("----------------------------------------------------");

        System.out.println("This program will allow you to interact with a DoubleArraySeq object.\n" +
                "This object is a collection of double values that are stored sequentially.\n" +
                "A menu will allow you to modify this object in various ways.\n\n ");

        System.out.print
                ("Options: \n" +
                        "1. Print sequence to screen\n" +
                        "2. Report the capacity of the sequence\n" +
                        "3. Set the current element location\n" +
                        "4. Add an element to the front of the sequence\n" +
                        "5. Add a number to the end of the sequence\n" +
                        "6. Add a number before the current element\n" +
                        "7. Add a number after the current element\n" +
                        "8. Remove the first element in the sequence\n" +
                        "9. Remove an element in a location\n" +
                        "10. Display the value at a certain location\n" +
                        "11. Display the last element in the sequence\n" +
                        "12. Quit\n");

        System.out.println("----------------------------------------------------");


        boolean terminate = false;

        //loop to allow user to make multiple selections
        while(!terminate){



            System.out.print("Enter choice: ");
            int userChoice = choiceReader.nextInt();

            //System.out.println("----------------------------------------------------");


            //switch for menu selections
            switch (userChoice){

                //option to print the contents of the sequence
                case 1:
                    try {
                        System.out.println("Sequence: " + dub.toString());
                        System.out.println("----------------------------------------------------");
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to retrieve the capacity of the object
                case 2:
                    try {
                        System.out.println("Capacity: " + dub.getCapacity());
                        System.out.println("----------------------------------------------------");
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }

                //option to set the current element
                case 3:
                    try {
                        System.out.print("Desired element location: ");
                        int location = choiceReader.nextInt();
                        dub.setCurrent(location);
                        System.out.println("Current element has been updated to " + location);
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                        choiceReader.next(); //clear buffer of invalid input
                    }
                    break;

                //option to add an element to the front of the sequence
                case 4:
                    try {
                        System.out.print("Number to add to front of sequence: ");
                        double element = choiceReader.nextDouble();
                        dub.addFront(element);
                        System.out.println(element + " has successfully been added to the front of the sequence");
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to add an element to the end of the sequence
                case 5:
                    try {
                        System.out.print("Number to add to end of sequence: ");
                        double element = choiceReader.nextDouble();
                        dub.addAfter(element);
                        dub.setCurrent(dub.size());
                        System.out.println(element + " has successfully been added to the end of the sequence");
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;


                //option to add an element before the current element's position
                case 6:
                    try {
                        System.out.print("Number to add before current element: ");
                        double element = choiceReader.nextDouble();
                        dub.addBefore(element);
                        System.out.println(element + " has been successfully added before the current element");
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to add an element after the current element's position
                case 7:

                    try {
                        System.out.print("Number to add after current element: ");
                        double element = choiceReader.nextDouble();
                        dub.addAfter(element);
                        System.out.println(element + " has been successfully added after the current element");
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }

                    break;

                //option to remove the first element in the sequence
                case 8:
                    try {
                        dub.removeFront();
                        System.out.println("First item has been removed from sequence");
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to remove the element at a user-specified location
                case 9:
                    try {
                        System.out.print("Location of element to remove: ");
                        int location = choiceReader.nextInt();
                        dub.setCurrent(location);
                        dub.removeCurrent();
                        System.out.println("Element at location " + location + " was successfully removed");
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to display the element at a user-specified location
                case 10:
                    try {
                        System.out.print("Location of element to display: ");
                        int location = choiceReader.nextInt();
                        System.out.println("Element at location " + location + ": " + dub.getElement(location));
                        System.out.println("----------------------------------------------------");
                    } catch (InputMismatchException e){
                        System.out.println("Numeric values only!");
                        choiceReader.next();
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to display the last element in the sequence
                case 11:
                    try {
                        dub.setCurrent(dub.size());
                        System.out.println("Last item in sequence: " + dub.getCurrent());
                        System.out.println("----------------------------------------------------");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("----------------------------------------------------");
                    }
                    break;

                //option to exit the program
                case 12:
                    System.out.println("Program terminated, have a great day!");
                    System.out.println("----------------------------------------------------");
                    terminate = true;
                    break;


                //executes in case of an invalid choice number
                default:
                    System.out.println("Enter a number 1-12!");
                    System.out.println("----------------------------------------------------");



            }

        }






    } //end of main




} //end of class