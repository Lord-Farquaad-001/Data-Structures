/**************************************************************************************************************
 Author:  Nathan Dann [modification of Table class from Michael Main | http://www.cs.colorado.edu/~main/docs]
 Date:    11 December 2025
 Project: CSC 103 Project 5
 Description:
 The TableDoubleHash class implements a hash table using a technique of double hashing if collisions occur.
 ***************************************************************************************************************/

public class TableDoubleHash< K , E > {
    // Invariant of the Table class:
    //   1. The number of items in the table is in the instance variable manyItems.
    //   2. The preferred location for an element with a given key is at index
    //      hash(key). If a collision occurs, then next-Index is used to search
    //      forward to find the next open address. When an open address is found
    //      at an index i, then the element itself is placed in data[i] and the
    //      element�s key is placed at keys[i].
    //   3. An index i that is not currently used has data[i] and key[i] set to
    //      null.
    //   5. collisions is an integer array storing how many collisions occurred during
    //      each attempted placement
    //   6. totalCollisions is an integer representing the total number of collisions that
    //      occurred while inserting values into the hash table

    private int manyItems;
    private Object[ ] keys;
    private Object[ ] data;
    private int [] collisions;
    private int totalCollisions = 0;



    /***************************************************************
     * Description: constructor method for a TableChainHash object
     * @param capacity
     * the initial capacity of the table. Note that this should be
     * a prime number, ideally of the for 4k + 3 for some integer k.
     * @exception IllegalArgumentException
     * negative value entered
     ***************************************************************/
    public TableDoubleHash(int capacity) {
        // The manyItems instance variable is automatically set to zero.
        // which is the correct initial value. The three arrays are allocated to
        // be the specified capacity. The boolean array is automatically
        // initialized to falses, and the other two arrays are automatically
        // initialized to all null.
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity is negative");
        }
        keys = new Object[capacity];
        data = new Object[capacity];
        collisions = new int[capacity];

    } /* TableDoubleHash */

    /***************************************************************
     * Description: accessor method for total collisions instance
     * variable
     * @return
     * totalCollisions: the number of total collisions
     ***************************************************************/
    public int getTotalCollisions(){
        return totalCollisions;
    } /* getTotalCollisions */

    /***************************************************************
     * Description: method to determine the average number of
     * collisions per attempted placement in hash table
     * @return
     * totalCollisions: the array of collisions at each placement
     ***************************************************************/
    public double getAverageCollisions(){
        return ((double)totalCollisions / (double)manyItems);
    } /* getAverageCollisions */

    /***************************************************************
     * Description: accessor method for collisions array
     * @return
     * totalCollisions: the array of collisions at each placement
     ***************************************************************/
    public int [] getCollisions(){
        return collisions;
    } /* getCollisions */

    /***************************************************************
     * Description: accessor method for manyItems
     * @return
     * value stored in instance variable manyItems
     ***************************************************************/
    public int getManyItems(){
        return manyItems;
    }

    /***************************************************************
     * Description: method to determine if a specified key value is
     * in the table
     * @return
     * Boolean (T / F) key is in table
     ****************************************************************/
    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    } /* containsKey */

    /***************************************************************
     * Description: method to determine the index at which a
     * specified key lies. Utilizes the nextIndex method, which is
     * modified to accommodate double hashing.
     * @param key
     * the key value to be searched for
     * @return
     * index of the key in the hash table
     ***************************************************************/
    private int findIndex(K key) {

        int count = 0;
        int i = hash(key);

        //run through whole table
        while (count < data.length) {

            //key has been found
            if (key.equals(keys[i])) {
                return i;
            }
            count++;
            //increment by jump value
            i = nextIndex(i, (int)key);
        }
        return -1;
    } /* findIndex */


    /***************************************************************
     * Description: method to get the data value corresponding to
     * a specified key value
     * @param key
     * key value to search for in the table
     * @return
     * the data corresponding to the key value (key / data pair)
     ***************************************************************/
    public E get(K key){
        int index = findIndex(key);

        if (index == -1) {
            return null;
        } else {
            return (E) data[index];
        }
    } /* get */

    /***************************************************************
     * Description: method to determine a table index location for
     * a given key value
     * @param key
     * the key value to be hashed
     * @return
     * the index location in the table corresponding to the key
     * value
     ***************************************************************/
    private int hash(Object key) {
        return Math.abs(key.hashCode( )) % data.length;
    } /* hash */

    /***************************************************************
     * Description: invoked in the event of a collision; gives a
     * value by which to increment the index in an attempt to place
     * the key value
     * @param key
     * the key value on which to invoke the hash2 method
     * @return
     * value by which to increment the current index
     ***************************************************************/
    private int hash2(Object key){
        return Math.abs(key.hashCode()) % (data.length-2) + 1;
    } /* hash2 */

    /***************************************************************
     * Description: method to determine the new index in the event
     * of a collision
     * event of a collision
     * @param i
     * the starting index
     * @param key the key value to be used to determine the jump
     * value
     * @return
     * index + jump value determined by hash2 method
     ***************************************************************/
    private int nextIndex(int i, int key) {

        //determine jump value
        int jumpVal = hash2(key);

        //determine new index
        if (i+jumpVal >= data.length) {
            return (i + jumpVal) - data.length;
        } else {
            return i + jumpVal;
        }

    } /* nextIndex */


    /***************************************************************
     * Description: method to insert a key and its corresponding
     * data into the table
     * @param key
     * the key value to be inserted into the table
     * @param element
     * the data to be inserted into the table
     * @return
     * null = success
     * value = overwritten data
     * @exception IllegalStateException
     * Indicates that there is no room for a new object in this table.
     * @exception NullPointerException
     * Indicates that <CODE>key</CODE> or <CODE>element</CODE> is null.
     ***************************************************************/
    public E put(K key, E element) {

        int index = findIndex(key);
        E answer;

        if (index != -1) {  // The key is already in the table.

            answer = (E) data[index];
            data[index] = element;
            return answer;

            // The key is not yet in this Table.
        } else if (manyItems < data.length) {
            index = hash(key);

            while (keys[index] != null){
                index = nextIndex(index, (int)key);
                collisions[manyItems]++;
                totalCollisions++;
            }

            keys[index] = key;
            data[index] = element;
            manyItems++;
            return null;

        } else {  // The table is full.
            throw new IllegalStateException("Table is full.");
        }
    } /* put */

    /***************************************************************
     * Description: removes a specified key / data pair from the
     * table
     * @param key
     * the key value of the key / data pair to be removed
     * @return
     * null = key value was not in table
     * E data = the data that was removed from the table
     ***************************************************************/
    public E remove(K key) {

        int index = findIndex(key);
        E answer = null;

        if (index != -1) {

            answer = (E) data[index];
            keys[index] = null;
            data[index] = null;
            manyItems--;
        }

        return answer;
    } /* remove */

} /* TableDoubleHash */

