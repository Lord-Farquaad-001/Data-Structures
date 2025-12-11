/**************************************************************************************************************
 Author:  Nathan Dann [modification of Table class from Michael Main | http://www.cs.colorado.edu/~main/docs]
 Date:    11 December 2025
 Project: CSC 103 Project 5
 Description:
   The TableChainHash class is a modification of the table class that implements a hash table using chain
   hashing. The Node class is used to implement this.
 ***************************************************************************************************************/

public class TableChainHash< K , E >
{
    // Invariant of the Table class:
    //   1. The number of items in the table is in the instance variable manyItems.
    //   2. The preferred location for an element with a given key is at index
    //      hash(key). If a collision occurs, then next-Index is used to search
    //      forward to find the next open address. When an open address is found
    //      at an index i, then the element itself is placed in data[i] and the
    //      element�s key is placed at keys[i].
    //   3. An index i that is not currently used has data[i] and key[i] set to
    //      null.
    //   4. If an index i has been used at some point (now or in the past), then
    //      hasBeenUsed[i] is true; otherwise it is false.
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
    public TableChainHash(int capacity) {
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

    } /* TableChainHash */

    /***************************************************************
     * Description: accessor method for totalCollisions
     * @return
     * totalCollisions: the value stored in the totalCollisions
     * instance variable
     ***************************************************************/
    public int getTotalCollisions(){
        return totalCollisions;
    }

    /***************************************************************
     * Description: accessor method for collisions array
     * @return
     * collisions: the array of collisions at each placement
     ***************************************************************/
    public int [] getCollisions(){
        return collisions;
    }

    /***************************************************************
     * Description: method to determine the average collisions per
     * attempted placement in a TableChainHash
     * @return
     * total collisions / manyItems
     ***************************************************************/
    public double getAverageCollisions(){
        return ((double)totalCollisions / (double)manyItems);
    }

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
     ***************************************************************/
    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    } /* containsKey */

    /***************************************************************
     * Description: determines if a given key value is already in
     * the table, and if so, at what index location it lies.
     * @param key
     * the key value being searched for
     * @return
     * the index of the key location in the table
     * or -1 if the key is not yet in the table
     ***************************************************************/
    private int findIndex(K key){

        //value to be returned
        int returnIdx = -1;

        int i = hash(key);

        //check if keys[i] is a node
        if (keys[i] instanceof Node){

            //cast to node
            Node<K>keyHead = (Node<K>)(keys[i]);

            //indicates removal took place
            if(keyHead.getData() == null){
                //do nothing

            //we have found our key in the table
            } else if(keyHead.getData().equals(key)){
                returnIdx = i;

            //we have not yet found the key, but we still need to search
            } else {

                //advance along linked list
                while (keyHead.getLink() != null) {

                    if (key.equals(keyHead.getData())) {
                        returnIdx = i;
                    } else {
                        keyHead = keyHead.getLink();
                    }
                }
            }
        }
        return returnIdx;
    } /* findIndex */

    /***************************************************************
     * Description: method to get the data value corresponding to
     * a specified key value
     * @param key
     * key value to search for in the table
     * @return
     * the data corresponding to the key value (key / data pair)
     ***************************************************************/
    public E get(K key) {
        int index = findIndex(key);

        if (index == -1) {
            return null;
        } else {
            Node<E> returnNode = (Node<E>)(data[index]);
            return returnNode.getData();
        }
    } /* get */

    /***************************************************************
     * Description: method to convert a key value to an index in
     * the table
     * @param key
     * The key value to be hashed to an index in the table
     ***************************************************************/
    private int hash(Object key) {
        return Math.abs(key.hashCode( )) % data.length;
    } /* hash */

    /***************************************************************
     * Description: method to advance index to next location. Does
     * not serve much purpose in chain hashing.
     * @param i
     * the starting index location
     * @return
     * the next index location from that passed in
     ***************************************************************/
    private int nextIndex(int i){
        if (i+1 == data.length) {
            return 0;
        } else {
            return i + 1;
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

        if (index != -1){  // The key is already in the table.
            answer = (E) data[index];
            data[index] = element;
            return answer;

        //key is not yet in table
        } else {

            index = hash(key);

            //index is not yet occupied
            if(keys[index] == null){
                //add first node to list
                data[index] = new Node<E>(element, null);
                //add key to list
                keys[index] = new Node<K>(key, null);

            //index is occupied
            } else{

                //ensure data at index is a node
                if(data[index] instanceof Node && keys[index] instanceof Node){

                    //cast data at index to node
                    Node<E> head = (Node<E>)data[index];

                    //cast key at index to node
                    Node<K> keyHead = (Node<K>)keys[index];

                    //single node at index
                    if(head.getLink() == null && keyHead.getLink() == null){
                        //add to end of linked list
                        head.setLink(new Node<>(element, null));

                        //add key to table
                        keyHead.setLink(new Node<>(key, null));

                        //collision occurred
                        collisions[manyItems]++;
                        totalCollisions++;

                    //more than one node at index
                    } else{
                        //first base collision
                        collisions[manyItems]++;
                        totalCollisions++;
                        //traverse list until we get to the last node
                        while (head.getLink() != null && keyHead.getLink() != null){

                            //advance along linked list
                            head = head.getLink();

                            //advance along key list
                            keyHead = keyHead.getLink();

                            //each successive collision
                            collisions[manyItems]++;
                            totalCollisions++;
                        }
                        //add node to end of list
                        head.setLink(new Node<>(element, null));

                        //add key to end of list
                        keyHead.setLink(new Node<>(key, null));
                    }

                } else{
                    throw new IllegalArgumentException("Not a node");
                }
            }
            //increment manyItems
            manyItems++;
            return null;
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

        //indicates that key is in table
        if (index != -1) {

            //check if keys[index] and data[index] are nodes
            if(keys[index] instanceof Node && data[index] instanceof Node){

                //tracker for precursors
                int count  = 0;

                Node<K> keyHead = (Node<K>)(keys[index]);
                Node<K> preserveKey = keyHead;
                Node<K> preCursor = keyHead;

                Node<E> head = (Node<E>)(data[index]);
                Node<E> preserveHead = head;
                Node<E> headPreCursor = head;

                //single node at index
                if(keyHead.getData().equals(key) && keyHead.getLink() == null){
                    answer = head.getData();
                    keyHead.setData(null);
                    head.setData(null);

                //more than one node at index
                } else {

                    //advancing along linked list
                    while (keyHead.getLink() != null) {

                        //we have found the key to remove
                        if (keyHead.getData().equals(key)) {

                            //node is not at tail
                            if (keyHead.getLink() != null) {
                                answer = head.getData();
                                keyHead = keyHead.getLink();
                                head = head.getLink();

                            //node is at tail
                            } else {
                                answer = head.getLink().getData();
                                preCursor.setLink(null);
                                headPreCursor.setLink(null);
                            }

                        //we have not yet found the key to remove
                        } else {
                            //advance to next node
                            keyHead = keyHead.getLink();
                            head = head.getLink();

                            if (count == 1) {
                                //advance precursors
                                preCursor = keyHead.getLink();
                                headPreCursor = head.getLink();
                            }
                            count++;
                        }
                    }
                }
                //reset the index
                keys[index] = preserveKey;
                data[index] = preserveHead;
            }
            manyItems--;
        }

        return answer;

    } /* remove */

} /* TableChainHash */

