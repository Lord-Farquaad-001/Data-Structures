/**************************************************************************************************************
 Author:  Nathan Dann
 Date:    11 December 2025
 Project: CSC 103 Project 5
 Description:
   This program is the driver for the Table, TableDoubleHash, and TableChainHash classes. It attempts to place
   200 key / data pairs into the three aforementioned hash table types, determining the number of collisions
   for each attempted placement.
***************************************************************************************************************/

//imports
import java.io.File;
import java.util.Scanner;

class HashTesting{

    //macro for table capacity
    private static final int CAPACITY = 241;

    public static void main(String [] args) throws Exception {

        //declare and define tables of each type being analyzed
        Table <Integer, String> linearHashTable = new Table<>(CAPACITY);
        TableDoubleHash <Integer, String> doubleHashTable = new TableDoubleHash<>(CAPACITY);
        TableChainHash <Integer, String> chainHashTable = new TableChainHash<>(CAPACITY);

        //declare new File object for names.txt
        File nameFile = new File("names.txt");

        //set up a scanner to read names.txt
        Scanner readFile = new Scanner(nameFile);

        //read in the file's contents
        while(readFile.hasNextLine()){

            //get each line of the file
            String line = readFile.nextLine();

            //extract the integer portion of the line
            int key = Table.extractInt(line);

            //extract the name portion of the line
            String name = Table.extractName(line);

            //put the data from line into the linear table
            linearHashTable.put(key, name);

            //put the data from line into the double hash table
            doubleHashTable.put(key, name);

            //put the data from line into the chain hash table
            chainHashTable.put(key, name);

        } /* while */

        //output the collision results
        printCollisions(linearHashTable, doubleHashTable, chainHashTable);

    } /* main */

    public static void printCollisions(Table<Integer, String>T1, TableDoubleHash<Integer, String>T2, TableChainHash<Integer, String>T3){
        System.out.println("\nCollisions per Attempted Placement in Hash Tables");
        System.out.println("Attempt   Linear  Double  Chain");

        for(int i = 0; i < T1.getManyItems(); i++){
            System.out.println("   " + (i+1)+ "\t\t" +
                    T1.getCollisions()[i] + "\t\t" +
                    T2.getCollisions()[i] + "\t\t" +
                    T3.getCollisions()[i]);
        } /* for */

        //output averages
        System.out.println("************************************");
        System.out.println("Linear average = " + T1.getAverageCollisions());
        System.out.println("Double average = " + T2.getAverageCollisions());
        System.out.println("Chain average = " + T3.getAverageCollisions());
        System.out.println("************************************");

    } /* printCollisions */

} /* class HashTesting */