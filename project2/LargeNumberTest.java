/*
Nathan Dann
Data Structures Project II
16 October 2025

This is the driver program for the UnboundedInt class. It operates in the following manner:

1. User is prompted to enter two integer numbers as large as they wish
2. A menu displays giving the user the option to select from the following choices:
    * Display the two integers
    * Enter two new integers
    * Check for equality between the two integers
    * Report the sum of the two integers
    * Report the product of the two integers
    * Clone the first integer and report the resulting value
    * Exit the program


 */

import java.util.InputMismatchException;
import java.util.Scanner;

class LargeNumberTest{

    public static void main(String [] args){

        System.out.println("\nThis program will ask you to enter two integer values of unlimited length.\n" +
                "You will then be given a menu of choices 1-7 that allow you to work with these values.\n");

        //scanners to read user input
        Scanner userChoice = new Scanner(System.in);
        Scanner readNums = new Scanner(System.in);


        //strings that will be passed as parameters to the UnboundedInt objects
        String text1;
        String text2;

        //UnboundedInt instantiation
        UnboundedInt num1 = null;
        UnboundedInt num2 = null;

        //run condition for outer loop
        boolean run = true;

        //run condition for input-validation loops
        boolean repeatInput = true;

        //input validation for two numbers
        while(repeatInput) {

            System.out.print("Enter number one: ");
            text1 = readNums.nextLine();

            System.out.print("Enter number two: ");
            text2 = readNums.nextLine();

            System.out.println("--------------------------------------");

            try {

                num1 = new UnboundedInt(text1);
                num2 = new UnboundedInt(text2);

                repeatInput = false;

            } catch (Exception e) {
                System.out.println("Error, positive integer values only!");
            }
        }



        //menu of choices
        System.out.print("Choices" +
                "\n1. Display both numbers" +
                "\n2. Input two new numbers" +
                "\n3. Check if the two numbers are equal" +
                "\n4. Report the sum of the two numbers" +
                "\n5. Report the product of the two numbers" +
                "\n6. Create and output the clone of the first number" +
                "\n7. Exit\n");

        System.out.println("--------------------------------------");


        //outer loop
        while(run){

            int choice = 0;
            repeatInput = true;

            try {
                System.out.print("Enter choice: ");
                choice = userChoice.nextInt();

                if(choice < 8) {
                    System.out.println("--------------------------------------");
                }

            } catch (InputMismatchException e){
                userChoice.next();
            }




            switch(choice){

                case 1:
                    System.out.println("Number 1: " + num1.toString());
                    System.out.println("Number 2: " + num2.toString());

                    System.out.println("--------------------------------------");
                    break;

                case 2:

                    //input validation
                    while(repeatInput) {

                        try {
                            System.out.print("Enter number 1: ");
                            text1 = readNums.nextLine();
                            System.out.print("Enter number 2: ");
                            text2 = readNums.nextLine();

                            num1 = new UnboundedInt(text1);
                            num2 = new UnboundedInt(text2);

                            repeatInput = false;

                        } catch(Exception e){
                            System.out.println("Error: positive integer values only!");
                        }
                        System.out.println("--------------------------------------");
                    }
                    break;

                case 3:
                    if(num1.equals(num2)){
                        System.out.println("Number 1 is equal to Number 2");
                    } else{
                        System.out.println("Number 1 is not equal to Number 2");
                    }

                    System.out.println("--------------------------------------");
                    break;

                case 4:
                    UnboundedInt sum = num1.add(num2);
                    System.out.println("Sum: " + sum.toString());

                    System.out.println("--------------------------------------");
                    break;

                case 5:
                    UnboundedInt product = num1.multiply(num2);
                    System.out.println("Product: " + product.toString());

                    System.out.println("--------------------------------------");
                    break;

                case 6:
                    UnboundedInt clone = num1.clone();
                    System.out.println("Clone: " + clone.toString());

                    System.out.println("--------------------------------------");
                    break;

                case 7:
                    System.out.println("Program terminated");
                    run = false;

                    System.out.println("--------------------------------------");
                    break;

                default:

                    //all invalid inputs within loop are sent here
                    System.out.println("Error: valid choices: integers 1-7");

                    System.out.println("--------------------------------------");
                    break;
            }
        }

    } /* main */
} /* class LargeNumberTest */