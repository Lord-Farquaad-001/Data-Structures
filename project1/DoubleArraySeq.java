import java.util.InputMismatchException;

/******************************************************************************
     * Nathan Dann
     * CSC 103
     * Project 1
     * ----------------------------------------------------------------------------
     * A DoubleArraySeq is a collection of double numbers.
     * The sequence can have a special "current element," which is specified and
     * accessed through four methods that are not available in the bag class
     * (start, getCurrent, advance and isCurrent).
     *
     * @note
     *   (1) The capacity of one a sequence can change after it's created, but
     *   the maximum capacity is limited by the amount of free memory on the
     *   machine. The constructor, addAfter,
     *   addBefore, clone,
     *   and concatenation will result in an
     *   OutOfMemoryError when free memory is exhausted.
     *   <p>
     *   (2) A sequence's capacity cannot exceed the maximum integer 2,147,483,647
     *   (Integer.MAX_VALUE). Any attempt to create a larger capacity
     *   results in a failure due to an arithmetic overflow.
     *
     * @note
     *   This file contains only blank implementations ("stubs")
     *   because this is a Programming Project for my students.
     *
     * @see
     *   <A HREF="../../../../edu/colorado/collections/DoubleArraySeq.java">
     *   Java Source Code for this class
     *   (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
     *   </A>
     *
     * @version
     *   March 5, 2002
     ******************************************************************************/
    public class DoubleArraySeq implements Cloneable
    {
        // Invariant of the DoubleArraySeq class:
        //   1. The number of elements in the sequences is in the instance variable
        //      manyItems.
        //   2. For an empty sequence (with no elements), we do not care what is
        //      stored in any of data; for a non-empty sequence, the elements of the
        //      sequence are stored in data[0] through data[manyItems-1], and we
        //      don’t care what’s in the rest of data.
        //   3. If there is a current element, then it lies in data[currentIndex];
        //      if there is no current element, then currentIndex equals manyItems.

        private double [] data;
        private int manyItems;
        private int currentIndex;

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Initialize an empty sequence with an initial capacity of 10.  Note that
         * the addAfter and addBefore methods work
         * efficiently (without needing more memory) until this capacity is reached.
         * @param - none
         * @postcondition
         *   This sequence is empty and has an initial capacity of 10.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for:
         *   new double[10].
         **/
        public DoubleArraySeq( )
        {
            manyItems = 0;
            data = new double[10];
            currentIndex = manyItems-1;
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Initialize an empty sequence with a specified initial capacity. Note that
         * the addAfter and addBefore methods work
         * efficiently (without needing more memory) until this capacity is reached.
         * @param initialCapacity
         *   the initial capacity of this sequence
         * @precondition
         *   initialCapacity is non-negative.
         * @postcondition
         *   This sequence is empty and has the given initial capacity.
         * @exception IllegalArgumentException
         *   Indicates that initialCapacity is negative.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for:
         *   new double[initialCapacity].
         **/
        public DoubleArraySeq(int initialCapacity)
        {
            if(initialCapacity < 0){
                throw new IllegalArgumentException("Error: negative values prohibited!");
            } else {
                manyItems = 0;
                data = new double[initialCapacity];
                currentIndex = manyItems-1;
            }
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * sets the location of the current element
         * @param n
         * the number of the element in the sequence (index + 1)
         * @precondition
         * n is greater than 0, n is less than manyItems, n
         * @postcondition
         * the current element is now the nth element passed in as a parameter
         * @exception IllegalStateException
         * indicates that the sequence is empty
         * @exception IllegalArgumentException
         * invalid position entered (either exceeds number of items in sequence, or is a value less than or equal to 0)
         *
         **/
        
        public void setCurrent(int n) {

            if(manyItems == 0) {
                throw new IllegalStateException("Error: sequence must not be empty!");
            } else if(n > manyItems || n < 1) {
                throw new IllegalArgumentException("Error: element " + n + " is not a valid position!");
            } else {
                currentIndex = n-1;
            }
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Add a new element to this sequence, after the current element.
         * If the new element would take this sequence beyond its current capacity,
         * then the capacity is increased before adding the new element.
         * @param element
         *   the new element that is being added
         * @postcondition
         *   A new copy of the element has been added to this sequence. If there was
         *   a current element, then the new element is placed after the current
         *   element. If there was no current element, then the new element is placed
         *   at the end of the sequence. In all cases, the new element becomes the
         *   new current element of this sequence.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for increasing the sequence's capacity.
         * @note
         *   An attempt to increase the capacity beyond
         *   Integer.MAX_VALUE will cause the sequence to fail with an
         *   arithmetic overflow.
         **/
        public void addAfter(double element) {

            ensureCapacity(manyItems+1);

            if (isCurrent()) {

                if(currentIndex == manyItems-1){
                    data[currentIndex+1] = element;

                } else{
                    for (int i = manyItems; i > currentIndex+1; i--) {
                        data[i] = data[i - 1];
                    }
                }

                //switches current and element
                //data[currentIndex+1] = element;
                currentIndex++;
                manyItems++;

            } else{
                currentIndex = 0;
                data[currentIndex] = element;
                manyItems++;

            }
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Add a new element to this sequence, before the current element.
         * If the new element would take this sequence beyond its current capacity,
         * then the capacity is increased before adding the new element.
         * @param element
         *   the new element that is being added
         * @postcondition
         *   A new copy of the element has been added to this sequence. If there was
         *   a current element, then the new element is placed before the current
         *   element. If there was no current element, then the new element is placed
         *   at the start of the sequence. In all cases, the new element becomes the
         *   new current element of this sequence.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for increasing the sequence's capacity.
         * @note
         *   An attempt to increase the capacity beyond
         *   Integer.MAX_VALUE will cause the sequence to fail with an
         *   arithmetic overflow.
         **/
        public void addBefore(double element) {



            this.ensureCapacity(manyItems + 1);

            if (isCurrent()) {

                //moves all elements over starting at currentIndex, then inserts element at currentIndex
                for (int i = manyItems; i > currentIndex; i--) {
                    data[i] = data[i - 1];
                }

                //switches current and element
                data[currentIndex] = element;
                manyItems++;


            } else {
                currentIndex = 0;
                data[currentIndex] = element;
                manyItems++;
            }
        }


        //--------------------------------------------------------------------------------------------------------------
        /**
         * adds a double value to index 0 of the sequence
         * @param element
         * the double value to be added to the front of the sequence
         * @postcondition
         * the user's double value is now the first element in the sequence, and the current index is set to 0
         **/
        public void addFront(double element){
            currentIndex = 0;
            addBefore(element);
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Place the contents of another sequence at the end of this sequence.
         * @param addend
         *   a sequence whose contents will be placed at the end of this sequence
         * @precondition
         *   The parameter, addend, is not null.
         * @postcondition
         *   The elements from addend have been placed at the end of
         *   this sequence. The current element of this sequence remains where it
         *   was, and the addend is also unchanged.
         * @exception NullPointerException
         *   Indicates that addend is null.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory to increase the size of this sequence.
         * @note
         *   An attempt to increase the capacity beyond
         *   Integer.MAX_VALUE will cause an arithmetic overflow
         *   that will cause the sequence to fail.
         **/
        //--------------------------------------------------------------------------------------------------------------

        public void addAll(DoubleArraySeq addend) {
            // Implemented by student.

            ensureCapacity(this.manyItems + addend.manyItems);


            for(int i = 0; i < addend.manyItems; i++){

                this.addAfter(addend.data[i]);

            }



        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Move forward, so that the current element is now the next element in
         * this sequence.
         * @param - none
         * @precondition
         *   isCurrent() returns true.
         * @postcondition
         *   If the current element was already the end element of this sequence
         *   (with nothing after it), then there is no longer any current element.
         *   Otherwise, the new element is the element immediately after the
         *   original current element.
         * @exception IllegalStateException
         *   Indicates that there is no current element, so
         *   advance may not be called.
         **/
        public void advance() {
            currentIndex++;
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Generate a copy of this sequence.
         * @param - none
         * @return
         *   The return value is a copy of this sequence. Subsequent changes to the
         *   copy will not affect the original, nor vice versa.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for creating the clone.
         **/
        public DoubleArraySeq clone() {
            DoubleArraySeq answer;

            try {
                answer = (DoubleArraySeq) super.clone( );
            }
            catch (CloneNotSupportedException e) {  // This exception should not occur. But if it does, it would probably
                // indicate a programming error that made super.clone unavailable.
                // The most common error would be forgetting the "Implements Cloneable"
                // clause at the start of this class.
                throw new RuntimeException
                        ("This class does not implement Cloneable");
            }

            answer.data = data.clone( );

            return answer;
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Create a new sequence that contains all the elements from one sequence
         * followed by another.
         * @param s1
         *   the first of two sequences
         * @param s2
         *   the second of two sequences
         * @precondition
         *   Neither s1 nor s2 is null.
         * @return
         *   a new sequence that has the elements of s1 followed by the
         *   elements of s2 (with no current element)
         * @exception NullPointerException
         *   Indicates that one of the arguments is null.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for the new sequence.
         * @note
         *   An attempt to create a sequence with a capacity beyond
         *   Integer.MAX_VALUE will cause an arithmetic overflow
         *   that will cause the sequence to fail.
         **/
        public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) {

            DoubleArraySeq returnSeq = new DoubleArraySeq();

            for(int i = 0; i < s1.manyItems+s2.manyItems; i++) {
                if(i < s1.manyItems) {
                    returnSeq.addAfter(s1.data[i]);
                } else {
                    returnSeq.addAfter(s2.data[i]);

                }
            }
            return returnSeq;
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Change the current capacity of this sequence.
         * @param minimumCapacity
         *   the new capacity for this sequence
         * @postcondition
         *   This sequence's capacity has been changed to at least minimumCapacity.
         *   If the capacity was already at or greater than minimumCapacity,
         *   then the capacity is left unchanged.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for: new int[minimumCapacity].
         **/
        public void ensureCapacity(int minimumCapacity) { //check

            if (data.length < minimumCapacity) {
                double [] newArray;
                newArray = new double[minimumCapacity];

                for (int i = 0; i < data.length; i++) {
                    newArray[i] = data[i];
                }
                data = newArray;

            }
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Accessor method to get the current capacity of this sequence.
         * The add method works efficiently (without needing
         * more memory) until this capacity is reached.
         * @param - none
         * @return
         *   the current capacity of this sequence
         **/
        public int getCapacity() {
            return data.length;
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * Accessor method to get the current element of this sequence.
         * @param - none
         * @precondition
         *   isCurrent() returns true.
         * @return
         *   the current element of this sequence
         * @exception IllegalStateException
         *   Indicates that there is no current element, so
         *   getCurrent may not be called.
         **/
        public double getCurrent() {
            return data[currentIndex];
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Accessor method to determine whether this sequence has a specified
         * current element that can be retrieved with the
         * getCurrent method.
         * @param - none
         * @return
         *   true (there is a current element) or false (there is no current element at the moment)
         **/
        public boolean isCurrent() {
            // Implemented by student.
            boolean check = false;
            if(currentIndex >= 0) {
                check = true;
            }
            return check;
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Remove the current element from this sequence.
         * @param - none
         * @precondition
         *   isCurrent() returns true.
         * @postcondition
         *   The current element has been removed from this sequence, and the
         *   following element (if there is one) is now the new current element.
         *   If there was no following element, then there is now no current
         *   element.
         * @exception IllegalStateException
         *   Indicates that there is no current element, so
         *   removeCurrent may not be called.
         **/
        public void removeCurrent() {

            if(isCurrent()) {

                for(int i = currentIndex; i < manyItems-1; i++) {
                    data[i] = data[i+1];
                }
                data[manyItems-1] = 0;
                currentIndex++;
                manyItems--;
            } else{
                throw new IllegalStateException("No current element!");
            }
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * @param - none
         * @precondition
         * sequence must not be empty
         * @postcondition
         * first element in the sequence has been removed and currentIndex set to 0
         **/
        public void removeFront() {
            if(manyItems == 0){
                throw new IllegalStateException("Sequence is empty!");
            } else {
                currentIndex = 0;
                removeCurrent();
            }
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Determine the number of elements in this sequence.
         * @param - none
         * @return
         *   the number of elements in this sequence
         **/
        public int size() {
            return manyItems;
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Set the current element at the front of this sequence.
         * @param - none
         * @postcondition
         *   The front element of this sequence is now the current element (but
         *   if this sequence has no elements at all, then there is no current
         *   element).
         **/
        public void start() {
            // Implemented by student.
            currentIndex = 0;
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * Reduce the current capacity of this sequence to its actual size (i.e., the
         * number of elements it contains).
         * @param - none
         * @postcondition
         *   This sequence's capacity has been changed to its current size.
         * @exception OutOfMemoryError
         *   Indicates insufficient memory for altering the capacity.
         **/
        public void trimToSize() {
            double [] trimmedArray;

            if (data.length != manyItems)
            {
                trimmedArray = new double[manyItems];
                System.arraycopy(data, 0, trimmedArray, 0, manyItems);
                data = trimmedArray;
            }
        }
        //--------------------------------------------------------------------------------------------------------------

        /**
         * check for equality between two DoubleArraySeq objects
         * @param obj
         * the object being tested against this for equality
         * @return boolean T/F
         **/
        public boolean equals(Object obj){ //check
            boolean check = true;

            if(obj instanceof DoubleArraySeq){
                DoubleArraySeq test = (DoubleArraySeq) obj;
                if(test.manyItems != manyItems){
                    check = false;
                }

                if(test.data.length == data.length) {
                    for (int i = 0; i < data.length; i++){
                        if(test.data[i] != data[i]){
                            check = false;
                        }
                    }
                }

            } else{
                check = false;
            }
            return check;
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * converts data to string form
         * @param - none
         * @return
         * string of values in data separated by a space character
         **/
        public String toString() { //check

            if (manyItems == 0) {
                throw new IllegalStateException("Sequence is empty!");
            } else {

                StringBuilder storage = new StringBuilder();

                int placeholder = currentIndex;
                this.start();

                while (currentIndex < manyItems) {
                    storage.append(data[currentIndex] + " ");
                    this.advance();

                }
                currentIndex = placeholder;
                return storage.toString();
            }
        }

        //--------------------------------------------------------------------------------------------------------------

        /**
         * retrieves a double value in the sequence at the user's specified location (index + 1)
         * @param location
         * the number (index + 1) of the user's desired element in the sequence
         * @precondition
         * sequence must not be empty
         * desired element number must be non-negative
         * desired element number must be within range of manyItems
         * @postcondition
         * the current element is now the one at the user's specified location
         * @return
         * the element at the desired location
         **/
        public double getElement(int location){

            if(manyItems == 0){
                throw new IllegalStateException("Sequence is empty!");
            } else if(location < 1){
                throw new IllegalArgumentException("Argument must be greater than zero!");
            } else if(location > manyItems){
                throw new IllegalArgumentException("Argument exceeds number of elements in sequence!");
            } else {
                this.setCurrent(location);
                return (this.getCurrent());
            }
        }

        //--------------------------------------------------------------------------------------------------------------


    }

