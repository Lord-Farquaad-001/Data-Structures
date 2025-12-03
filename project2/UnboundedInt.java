/*
Nathan Dann
Data Structures Project II
16 October 2025

The UnboundedInt class allows for arithmetic operations on integer values that would regularly cause overflows
due to the fact that they exceed the standard integer size of 4 bytes. The integer is stored in a linked list comprised
of various nodes, each of which can hold up to three digits.
 */

class UnboundedInt implements Cloneable{

    // Invariant of the DoubleArraySeq class:
    //   1. The number of nodes in the list is in the instance variable
    //      manyNodes.
    //   2. The head of the list (first node in the chain) is stored in the
    //      instance variable front.
    //   3. The tail of the list (last node in the chain) is stored in the
    //      instance variable back. This variable may sometimes be used as
    //      a cursor by some methods.


    private int manyNodes;

    private IntNode front;

    private IntNode back;


    /**
     * Constructor for the UnboundedInt class.
     * @param bigNum
     * a string representing the integer value that the UnboundedInt will represent.
     * @postcondition
     * the UnboundedInt object has the desired value
     * @exception IllegalArgumentException
     * indicates that non-integer input was passed as a parameter
     *
     */
    UnboundedInt(String bigNum){

        //reverses the order of characters in bigNum
        String reverse = reverseOfString(bigNum);

        //temp storage
        StringBuilder storage = new StringBuilder();
        storage.append(reverse);


        //adds leading zeros so that all nodes contain 3 characters
        //gets the string's characters in proper order for addition to list
        if(bigNum.length() % 3 == 1){
            storage.append("00");
            bigNum = reverseOfString(storage.toString());
        } else if(bigNum.length() % 3 == 2){
            storage.append("0");
            bigNum = reverseOfString(storage.toString());
        }
        //bigNum as been reversed and leading zeros added


        //starts at back, works to front
        int endIndex = bigNum.length();
        int startIndex = endIndex-3;


        try {
            //adds nodes to list
            for (int i = 0; i < bigNum.length() / 3; i++) {

                if (i == 0) {
                    front = new IntNode(Integer.parseInt(bigNum.substring(startIndex, endIndex)), null);
                    back = front;
                    manyNodes++;
                } else {
                    back.setLink(new IntNode(Integer.parseInt(bigNum.substring(startIndex, endIndex)), null));
                    back = back.getLink();
                    manyNodes++;
                }
                endIndex = startIndex; //shifts the characters in view
                startIndex -= 3; //above^
            }
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Error: UnboundedInt accepts integer values only!");
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Method to display an UnboundedInt's data as a single number with commas.
     * @param - none
     * @return
     * a string representing the UnboundedInt's value with commas.
     *
     */
    public String toString(){

        StringBuilder storage = new StringBuilder();

        IntNode tracker = this.getFront();

        boolean run = true;

        while(run){

            String data = reverseOfString(String.valueOf(tracker.getData()));

            //add extra zeros
            if(data.length() == 1){
                data = data + "00";
            } else if(data.length() == 2){
                data = data + "0";
            }
            data = reverseOfString(data); //restore string

            storage.append(data); //append node value

            //runs until the last node is encountered
            if(tracker.getLink() != null){
                tracker = tracker.getLink(); //update tracker in list
            } else{
                run = false;
            }

        }

        //formats output
        return trimZeros(reverseNodes(storage.toString()));

    }
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Constructor for the UnboundedInt class.
     * @param obj
     * a widening conversion of the UnboundedInt object against which this is being checked for equality
     * @postcondition
     * the UnboundedInt object has the desired value
     * @exception - none
     *
     * @return
     * boolean: true if the objects are equal, otherwise false
     *
     */
    public boolean equals(Object obj){
        boolean check = true;

        //check for object being of UnboundedInt type
        if(obj instanceof UnboundedInt){

            UnboundedInt num2 = (UnboundedInt) obj;

            if(num2.getManyNodes() != this.getManyNodes()){
                check = false;

            } else{

                //track position in lists
                IntNode cursor1 = this.getFront();
                IntNode cursor2 = num2.getFront();

                int count = 0;
                while(count < this.getManyNodes() && check){

                    if(cursor1.getData() != cursor2.getData()){
                        check = false;
                    } else{
                        cursor1 = cursor1.getLink();
                        cursor2 = cursor2.getLink();
                    }
                    count++;
                }

            }
        }
        return check; //returns false if obj is not an UnboundedInt
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Method to create a copy of an UnboundedInt object.
     * @param - none
     *
     * @exception CloneNotSupportedException
     * indicates that the object being cloned is not an UnboundedInt.
     * @return
     * an UnboundedInt object whose data is identical to the one on which the method was called.
     */
    public UnboundedInt clone(){

        UnboundedInt copy;

        //attempts to copy the current object
        try{
            copy = (UnboundedInt) super.clone();
        } catch(CloneNotSupportedException e){
            throw new RuntimeException("This class does not implement Cloneable");
        }

        //set the front of the new list
        copy.front = IntNode.listCopy(front);

        return copy;
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Accessor for manyNodes.
     * @param - none
     *
     * @exception - none
     *
     * @return
     * the integer value stored in manyNodes.
     */
    public int getManyNodes(){
        return this.manyNodes;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Accessor for front instance variable.
     * @param - none
     *
     * @exception - none
     *
     * @return
     * the IntNode value stored in front.
     */
    public IntNode getFront(){
        return this.front;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Accessor for back instance variable.
     * @param - none
     *
     * @exception - none
     *
     * @return
     * the IntNode value stored in back
     */
    public IntNode getBack(){
        return this.back;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Modifier for the front instance variable.
     * @param front
     * The IntNode object whose value front is assuming.
     * @postcondition
     * front has the value of the IntNode passed in
     *
     */
    public void setFront(IntNode front) {
        this.front = front;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Modifier for the back instance variable.
     * @param back
     * The IntNode object whose value back is assuming.
     * @postcondition
     * back has the value of the IntNode passed in
     *
     */
    public void setBack(IntNode back){
        this.back = back;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Performs addition of two UnboundedInt objects.
     * @param addend
     * The UnboundedInt being added to the one on which the method is being called
     * @return
     * A new UnboundedInt whose data is equal to current object + addend.
     *
     */
    public UnboundedInt add(UnboundedInt addend){

        UnboundedInt result = new UnboundedInt("0"); //arbitrary value passed in for initialization

        //cursors for lists
        IntNode num1 = this.getFront();
        IntNode num2 = addend.getFront();

        IntNode num3Front = result.getFront();
        IntNode num3Cursor = num3Front;

        //temp values to evaluate nodes
        int data;
        int remainder = 0;

        //"index" node at which there are no longer two rows of data to sum
        int cutoff = Math.min(this.manyNodes, addend.manyNodes);

        for(int i = 0; i < Math.max(this.manyNodes, addend.manyNodes); i++){

            //calculate node data value

            //two summations
            if(i < cutoff) {

                if ((num1.getData() + num2.getData() + remainder) / 1000 > 0) {
                    data = (num1.getData() + num2.getData() + remainder) % 1000;

                    if(remainder < 1) {
                        remainder++;
                    }

                } else {
                    data = num1.getData() + num2.getData() + remainder;
                    remainder = 0; //reset remainder if it is successfully added with no carry

                }

                //single summations
            } else{

                if(this.manyNodes >= addend.manyNodes){

                    if((num1.getData() + remainder) / 1000 > 0){
                        data = (num1.getData() + remainder) % 1000;
                        //never carrying more than one
                        if(remainder < 1) {
                            remainder++;
                        }
                    } else{
                        data = num1.getData() + remainder;
                        remainder = 0;
                    }

                    //addend.manyNodes > this.manyNodes
                } else{

                    if((num2.getData() + remainder) / 1000 > 0){
                        data = (num2.getData() + remainder) % 1000;
                        //never carrying more than one
                        if(remainder < 1) {
                            remainder++;
                        }
                    } else{
                        data = num2.getData() + remainder;
                        remainder = 0;
                    }
                }
            }


            //assign list values
            if(i == 0){
                num3Front.setData(data);
            } else{
                num3Cursor.setLink(new IntNode(data, null));
                num3Cursor = num3Cursor.getLink();
                result.manyNodes++;
            }


            //update current nodes
            if(num1.getLink() != null) {
                num1 = num1.getLink();
            }
            if(num2.getLink() != null){
                num2 = num2.getLink();
            }

        }

        return result;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Performs multiplication of two UnboundedInt objects.
     * @param multiplier
     * The UnboundedInt by which the current object is being multiplied.
     * @return
     * A new UnboundedInt whose data is equal to current object * addend.
     *
     */
    public UnboundedInt multiply(UnboundedInt multiplier){

        //initialize two UnboundedInts
        UnboundedInt result = new UnboundedInt("0"); //return value
        UnboundedInt accumulator = new UnboundedInt("0"); //used to store temporary products to be added

        //cursors
        IntNode num1 = this.getFront();
        IntNode num2 = multiplier.getFront();

        //temporary values to evaluate nodes
        int data = 0;
        int remainder = 0;

        //the number of nodes of the smaller UnboundedInt
        int minNodes = Math.min(this.manyNodes, multiplier.manyNodes);

        //track what "index" we are on for each number
        int lesserNodeIndex = 0;
        int greaterNodeIndex = 0;


        //case 1 (this has more nodes than multiplier)
        if(this.manyNodes > multiplier.manyNodes){

            while(lesserNodeIndex < minNodes) {

                data = num1.getData() * num2.getData() + remainder;

                //sets value of node data
                if(data / 1000 >= 1){
                    remainder = data / 1000;
                    data = data % 1000;
                } else{
                    remainder = 0;
                }

                //sets initial data for accumulator
                if(greaterNodeIndex == 0){

                    accumulator.setFront(new IntNode(data, null));
                    accumulator.back = accumulator.front;

                    if(lesserNodeIndex > 0) {
                        accumulator.manyNodes++;
                    }

                } else{
                    accumulator.back.setLink(new IntNode(data, null));
                    accumulator.back = accumulator.back.getLink();
                    accumulator.manyNodes++;
                }

                //advances num1 index
                if(num1.getLink() != null){
                    num1 = num1.getLink();
                    greaterNodeIndex++;

                    //advances num2 index
                } else {

                    //adds remainder to accumulator at end
                    if (remainder > 0) {
                        accumulator.back.setLink(new IntNode(remainder, null));
                        accumulator.back = accumulator.back.getLink();
                        accumulator.manyNodes++;
                        remainder = 0;
                    }

                    //add a node of zeros to accumulator
                    for (int i = 0; i < lesserNodeIndex; i++) {
                        accumulator.setFront(new IntNode(0, accumulator.front));
                        accumulator.manyNodes++;
                    }


                    //adds accumulator to result each pass
                    result = result.add(accumulator);


                    num2 = num2.getLink(); //advance current node of bottom number
                    num1 = this.getFront(); //reset current node of top number to beginning


                    //reset accumulator (commenting this does something)
                    accumulator.front.setLink(null);
                    accumulator.manyNodes = 0;

                    greaterNodeIndex = 0; //reset index of greater number
                    lesserNodeIndex++;


                }
            }

            //case 2 (multiplier has more nodes than this)
        } else{

            while(lesserNodeIndex < minNodes) {

                data = num2.getData() * num1.getData() + remainder;

                //sets value of node data
                if(data / 1000 >= 1){
                    remainder = data / 1000;
                    data = data % 1000;
                } else{
                    remainder = 0;
                }

                if(greaterNodeIndex == 0){

                    accumulator.setFront(new IntNode(data, null));
                    accumulator.back = accumulator.front;

                    if(lesserNodeIndex > 0) {
                        accumulator.manyNodes++;
                    }

                } else{
                    accumulator.back.setLink(new IntNode(data, null));
                    accumulator.back = accumulator.back.getLink();
                    accumulator.manyNodes++;
                }

                //advances num1 index
                if(num2.getLink() != null){
                    num2 = num2.getLink();
                    greaterNodeIndex++;

                    //advances num2 index
                } else{

                    //adds remainder to accumulator at end
                    if(remainder > 0){
                        accumulator.back.setLink(new IntNode(remainder, null));
                        accumulator.back = accumulator.back.getLink();
                        accumulator.manyNodes++;
                        remainder = 0;
                    }

                    //add a node of zeros to accumulator
                    for(int i = 0; i < lesserNodeIndex; i++){
                        accumulator.setFront(new IntNode(0, accumulator.front));
                        accumulator.manyNodes++;
                    }


                    result = result.add(accumulator); //add accumulator to result

                    //advances cursors
                    num1 = num1.getLink();

                    num2 = multiplier.getFront();


                    //reset accumulator (commenting this does something)
                    accumulator.front.setLink(null);
                    accumulator.manyNodes = 0;

                    greaterNodeIndex = 0; //reset index of greater number
                    lesserNodeIndex++;



                }
            }
        }
        return result;
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    // Utility static methods

    /**
     * Reverses the order of characters in a string.
     * @param s
     * The string to be reversed.
     * @return
     * A new string whose contents are the reverse of that which was passed in.
     *
     */
    public static String reverseOfString(String s){

        StringBuilder storage = new StringBuilder();

        int endIndex = s.length();
        for(int startIndex = endIndex-1; startIndex > -1; startIndex--){

            storage.append(s.substring(startIndex, endIndex));
            endIndex--;

        }
        return storage.toString();
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Shifts a string's characters three at a time, simulating nodes. This puts the string
     * in proper order to be displayed. (simulates little endian storage for decimal numbers)
     * For example "123456789" -> "789567123"
     * @param s
     * The string to be manipulated.
     * @return
     * A new string whose contents are reversed in clusters of three, separated by commas.
     *
     */
    public static String reverseNodes(String s) {

        StringBuilder storage = new StringBuilder();

        int endIndex = s.length();
        int startIndex = endIndex - 3;

        for(int i = 0; i < s.length() / 3; i++){

            storage.append(s.substring(startIndex, endIndex));

            if(i != (s.length() / 3) - 1){
                storage.append(",");
            }

            endIndex = startIndex;
            startIndex -= 3;

        }
        return storage.toString();
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Eliminates leading zeros in a string.
     * For example "00034" -> "34"
     * @param s
     * The string to be manipulated.
     * @return
     * A new string whose contents include no leading zeros.
     *
     */
    public static String trimZeros(String s) {

        StringBuilder storage = new StringBuilder();

        int start = 0;
        int end = start + 1;

        int zeroCount = 0;
        boolean run = true;

            while (run && end != s.length()) {
                if (s.substring(start, end).equals("0") || s.substring(start,end).equals(",")) {
                    zeroCount++;
                    start++;
                    end++;
                } else {
                    run = false;
                }
            }

            storage.append(s.substring(zeroCount, s.length()));

            return storage.toString();
    }
}