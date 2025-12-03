/************************************************************************
 * Author: Nathan Dann
 * Date: 30 November 2025
 * Description: the golfer class is meant to store data for a specific
 * golfer in a database. It keeps track of the golfer's last name,
 * number of rounds played, and average score. The class supplies
 * methods to interact with this data.
 ***********************************************************************/

class Golfer implements Comparable{

    //Invariant of the Golfer Class
    //lastname: the golfer's last name
    //numberOfRounds: the number of rounds a golfer has played
    //averageScore: the mean value of all the golfer's scores

    private String lastname;
    private int numberOfRounds;
    private double averageScore;

    /******************************************************************
     * Description: constructor that takes only a Golfer's last name
     * and initializes rounds and avg score to zero.
     * @param lastname
     * The golfer's last name
     ******************************************************************/
    public Golfer(String lastname){
        this.setLastname(lastname);
        this.setNumberOfRounds(0);
        this.setAverageScore(0.0);
    }

    /******************************************************************
     * Description: this is the non-void constructor for the golfer
     * object
     * @param ln
     * The string of golfer's last name
     * @param rounds
     * The number of rounds played by golfer
     * @param avg
     * The golfer's average score
     ******************************************************************/
    public Golfer(String ln, int rounds, double avg){
        this.setLastname(ln);
        this.setNumberOfRounds(rounds);
        this.setAverageScore(avg);
    }

    /******************************************************************
     * Description: this method is the accessor for the lastname
     * instance variable
     * @return
     * the golfer's last name
     ******************************************************************/
    public String getLastname(){
        return lastname;
    }

    /******************************************************************
     * Description: this method is the accessor for the numberOfRounds
     * instance variable
     * @return
     * the golfer's number of rounds played
     ******************************************************************/
    public int getNumberOfRounds(){
        return numberOfRounds;
    }

    /******************************************************************
     * Description: this method is the accessor for the averageScore
     * instance variable
     * @return
     * the golfer's average score
     ******************************************************************/
    public double getAverageScore(){
        return averageScore;
    }

    /******************************************************************
     * Description: this method is the modifier for the lastname
     * instance variable
     * @param name
     * the golfer's last name
     ******************************************************************/
    public void setLastname(String name){
        lastname = name;
    }

    /******************************************************************
     * Description: this method is the modifier for the numberOfRounds
     * instance variable
     * @param rounds
     * the golfer's number of rounds played
     ******************************************************************/
    public void setNumberOfRounds(int rounds){
        numberOfRounds = rounds;
    }

    /******************************************************************
     * Description: this method is the modifier for the averageScore
     * instance variable
     * @param average
     * the golfer's average score
     ******************************************************************/
    public void setAverageScore(double average){
        averageScore = average;
    }

    /******************************************************************
     * Description: this method is used to enter a new score for a
     * golfer. It affects their number of rounds played and their
     * average score
     * @param newScore
     * the golfer's new score to be added to their database entry
     ******************************************************************/
    public void addScore(int newScore){

        //lumps the new score into the average score calculation; updates the value
        averageScore = ((averageScore * numberOfRounds) + newScore) / (numberOfRounds+1);
        //indicates that number of rounds increased
        numberOfRounds++;

    } /* addScore */


    /******************************************************************
     * Description: this method compares one golfer's last name to
     * another for alphabetical precedence
     * @param obj the golfer whose last name will be compared
     * @return
     * returns 1 if this has a greater last name than passed in
     * returns -1 if this has a lesser last name than passed in
     * note that "greater" last names are those that begin with
     * letters that occur later in the alphabet.
     * For example, "Zeno" compared to "Andre" would return 1
     ******************************************************************/
    public int compareTo(Object obj){

        //default: comparison > this
        int returnVal = 0;

        if(obj instanceof Golfer){

            Golfer compare = (Golfer)obj;

            int golfer1AsciiVal = 0;
            int golfer2AsciiVal = 0;
            int index = 0;
            int cutoff = Math.min(this.lastname.length(), compare.lastname.length());

            while(golfer1AsciiVal == golfer2AsciiVal && index < cutoff){

                golfer1AsciiVal = this.lastname.charAt(index);
                golfer2AsciiVal = compare.lastname.charAt(index);

                //indicates that this > comparison
                if(golfer1AsciiVal > golfer2AsciiVal){
                    returnVal = 1;
                } else if(golfer1AsciiVal < golfer2AsciiVal){
                    returnVal  = -1;
                }

                index++;
            }

        } else{
            throw new IllegalArgumentException("This class does not support compareTo");
        }
        return returnVal;
    }

    /******************************************************************
     * Description: this method converts a golfer's data to string
     * format
     * @return
     * the golfer object's data formatted as a string
     ******************************************************************/
    public String toString(){
        return new String("Last Name: " + lastname + ", Rounds Played: " + numberOfRounds
        + ", Average Score: " + averageScore);
    }

} /* class */
