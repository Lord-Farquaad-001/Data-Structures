// File: TreeBag.java 

// The implementation of most methods in this file is left as a student
// exercise from Section 9.5 of "Data Structures and Other Objects Using Java"


/******************************************************************************
* This class is a homework assignment;
* An <CODE>TreeBag</CODE> is a collection of int numbers.
*
* <dl><dt><b>Limitations:</b> <dd>
*   Beyond <CODE>Integer.MAX_VALUE</CODE> elements, <CODE>countOccurrences</CODE>,
*   and <CODE>size</CODE> are wrong. 
*
* <dt><b>Note:</b><dd>
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @version
*   Jan 24, 2016
******************************************************************************/
public class TreeBag<E extends Comparable> implements Cloneable
{
   // The Term E extends Comparable is letting the compiler know that any type
   // used to instantiate E must implement Comparable. i. e. that means that whatever
   // type E is must have a compareTo method so that elements can be compared against one another
   // This is required becuase we are doing comparisons in our methods


   // Invariant of the TreeBag class:
   //   1. The elements in the bag are stored in a binary search tree.
   //   2. The instance variable root is a reference to the root of the
   //      binary search tree (or null for an empty tree).
   private BTNode<E> root;   


   public BTNode<E>getRoot(){
      return root;
   }


   /**
   * Insert a new element into this bag.
   * @param <CODE>element</CODE>
   *   the new element that is being inserted
   * <dt><b>Postcondition:</b><dd>
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new BTNode.
   **/
   public void add(E element) {
      //CASE I: empty tree
      if (root == null) {
         //this element becomes the root
         root = new BTNode<E>(element, null, null);

         //CASE II: nonempty tree
      } else {

         boolean terminate = false;
         BTNode<E>currentNode = root;
         while (!terminate) {

            //indicates that root > elemnt, go left
            if (currentNode.getData().compareTo(element) == 1) {

               //left child is open
               if (currentNode.getLeft() == null) {
                  currentNode.setLeft(new BTNode<>(element, null, null));
                  terminate = true;
               } else {
                  //advance down the tree
                  currentNode = currentNode.getLeft();
               }

               //indicates that root < element
            } else {

               //right child is open
               if (currentNode.getRight() == null) {
                  currentNode.setRight(new BTNode<>(element, null, null));
                  terminate = true;
               } else {
                  //advance down the tree
                  currentNode = currentNode.getRight();
               }
            }
         }
      }
   }


   /**
   * Retrieve location of a specified element from this bag.
   * @param <CODE>target</CODE>
   *   the element to locate in the bag
   * @return 
   *  the return value is a reference to the found element in the tree
   * <dt><b>Postcondition:</b><dd>
   *   If <CODE>target</CODE> was found in the bag, then method returns
   *   a reference to a comparable element. If the target was not found then
   *   the method returns null.
   *   The bag remains unchanged.
   **/

   public E retrieve(E target)
   {
      E returnValue = null;
      BTNode<E> currentNode = root;

      boolean terminate = false;

      while(!terminate) {
         //currentNode == target
         if (currentNode.getData().compareTo(target) == 0) {
            returnValue = currentNode.getData();
            terminate = true;

         //currentNode > target; target lies to left of currentNode
         } else if(currentNode.getData().compareTo(target) == 1) {
            //advance to left
            if (currentNode.getLeft() != null) {
               currentNode = currentNode.getLeft();
            } else {
               //cannot branch any further; did not find target
               terminate = true;
               throw new IllegalArgumentException("Error, name not present in database!");
            }
         //currentNode < target; target lies to right of currentNode
         } else {
            //advance to right
            if (currentNode.getRight() != null) {
               currentNode = currentNode.getRight();
            } else {
               //cannot branch any further; did not find target
               terminate = true;
               throw new IllegalArgumentException("Error, name not present in database!");
            }
         }
      }
      return returnValue;
   }

   
   /**
   * Remove one copy of a specified element from this bag.
   * @param <CODE>target</CODE>
   *   the element to remove from the bag
   * <dt><b>Postcondition:</b><dd>
   *   If <CODE>target</CODE> was found in the bag, then one copy of
   *   <CODE>target</CODE> has been removed and the method returns true. 
   *   Otherwise the bag remains unchanged and the method returns false. 
   **/
   public boolean remove(E target) {

      BTNode<E> currentNode = root;
      BTNode<E> previousNode = currentNode;

      boolean successFlag = false;
      boolean terminate = false;

      //root is target
      if(root.getData().compareTo(target) == 0){
         successFlag = true;

         //CASE I: root is a leaf
         if(root.getLeft() == null && root.getRight() == null){
            root = null;

         //CASE II: root has a left child
         } else if(root.getLeft() != null){

            //root.getLeft is a leaf
            if(root.getLeft().isLeaf()){
               root.setData(root.getLeft().getData());
               root.setLeft(null);

            //root.getLeft has two children
            } else if(root.getLeft().getRight() != null){
               root.setData(root.getLeft().getRightmostData());
               root.getLeft().removeRightmost();

            //root.getLeft.getleft is the only child
            } else{
               root.setData(root.getLeft().getData());
               root.setLeft(root.getLeft().getLeft());
            }

         //root a right child only
         } else{
            //root.getRight is a leaf
            if(root.getRight().isLeaf()){
               root.setData(root.getRight().getData());
               root.setRight(null);

               //root.getRight has two children
            } else if(root.getRight().getLeft() != null){
               root.setData(root.getRight().getLeftmostData());
               root.getRight().removeLeftmost();

               //root.getLeft.getRight is the only child
            } else{
               root.setData(root.getRight().getData());
               root.setLeft(root.getRight().getRight());
            }
         }
         terminate = true;
      }

      //root is not target
      while(!terminate) {

         //current node = target
         if(currentNode.getData().compareTo(target) == 0){
            successFlag = true;

            //CASE I: target is a leaf node
            if(currentNode.isLeaf()){
               //target is the left child of parent
               if(previousNode.getLeft() != null && previousNode.getLeft().getData().compareTo(target) == 0){
                  previousNode.setLeft(null);
                  terminate = true;
               //target is the right child of parent
               } else if(previousNode.getRight() != null){
                  previousNode.setRight(null);
                  terminate = true;
               }

            //CASE II: target has two children
            } else if(currentNode.getLeft() != null && currentNode.getRight() != null){

               //current node is left child of parent
               if(previousNode.getLeft() != null && previousNode.getLeft().getData().compareTo(target) == 0){
                  //set current node equal to largest node in left subtree
                  if(!currentNode.getLeft().isLeaf()) {
                     previousNode.setLeft(new BTNode<>(currentNode.getLeft().getRightmostData(), currentNode.getLeft(), currentNode.getRight()));
                     currentNode.getLeft().removeRightmost();
                  } else{
                     previousNode.setLeft(new BTNode<>(currentNode.getLeft().getRightmostData(), null, currentNode.getRight()));
                  }
                  terminate = true;

               //current node is right child of parent
               } else{
                  //set current node equal to largest node in left subtree (if that node is not a leaf)
                  if(!currentNode.getLeft().isLeaf()) {
                     previousNode.setRight(new BTNode<>(currentNode.getLeft().getRightmostData(), currentNode.getLeft(), currentNode.getRight()));
                     currentNode.getLeft().removeRightmost();
                  } else{
                     //this prevents duplicate values caused by leaf children
                     previousNode.setRight(new BTNode<>(currentNode.getLeft().getRightmostData(), null, currentNode.getRight()));
                  }
                  terminate = true;
               }

            //CASE III: target has one child
            } else if(currentNode.getLeft() != null || currentNode.getRight() != null){

               //current node has a left child, but not a right
               if(currentNode.getLeft() != null){

                  //current node is left child of parent
                  if(previousNode.getLeft().getData().compareTo(target) == 0){
                     //link parent to current node's only child: the left one
                     previousNode.setLeft(currentNode.getLeft());
                     terminate = true;

                  //current node is right child of parent
                  } else{
                     //link parent to current node's only child: the right one
                     previousNode.setRight(currentNode.getLeft());
                     terminate = true;
                  }
               //current node has a right child but not a left
               } else{
                  //current node is left child of parent
                  if(previousNode.getLeft() != null && previousNode.getLeft().getData().compareTo(target) == 0){
                     //link the parent to current node's only child: the right one
                     previousNode.setLeft(currentNode.getRight());
                     terminate = true;

                  //current node is right child of parent
                  } else{
                     //link the parent to current node's only child: the right one
                     previousNode.setRight(currentNode.getRight());
                     terminate = true;
                  }
               }
            }

         //currentNode != target

         //CASE I: current node > target
         } else if(currentNode.getData().compareTo(target) == 1){

            //advance left if not null
            if(currentNode.getLeft() != null) {
               previousNode = currentNode;
               currentNode = currentNode.getLeft();
            } else{
               terminate = true;
            }

         //CASE II: currentNode < target
         } else{
            //advance right if not null
            if(currentNode.getRight() != null) {
               previousNode = currentNode;
               currentNode = currentNode.getRight();
            } else{
               terminate = true;
            }
         }
      }
      return successFlag;
   }

   
   /**
   * Displays the entire tree of Node elements in a order specified
   * by the elements compareTo method
   * 
   * @param 
   *   none
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/
   public void display()
   {
      root.inorderPrint();
   } 
     
   /**
   * Displays the entire tree of Node elements using the
   * built in print method of BTNode
   * which displays the entire tree in tree format
   * 
   * @param 
   *   none
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/   
   public void displayAsTree() {
      if (root == null)
         throw new IllegalArgumentException("The tree is empty");
      root.print(0);
   }
      
   
   
   /**
   * Generate a copy of this bag.
   * @param - none
   * @return
   *   The return value is a copy of this bag. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an <CODE>TreeBag</CODE> before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public TreeBag<E> clone( )
   {  // Clone an IntTreeBag object.
      // Student will replace this return statement with their own code:
      return null; 
   } 

   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param <CODE>target</CODE>
   *   the element that needs to be counted
   * @return
   *   the number of times that <CODE>target</CODE> occurs in this bag
   **/
   public int countOccurrences(E target)
   {
      // Student will replace this return statement with their own code:
      return 0;
   }
   
       
   /**
   * Determine the number of elements in this bag.
   * @param - none
   * @return
   *   the number of elements in this bag
   **/                           
   public int size( )
   {
      return BTNode.treeSize(root);
   }




   /**
   * Add the contents of another bag to this bag.
   * @param <CODE>addend</CODE>
   *   a bag whose contents will be added to this bag
   * <dt><b>Precondition:</b><dd>
   *   The parameter, <CODE>addend</CODE>, is not null.
   * <dt><b>Postcondition:</b><dd>
   *   The elements from <CODE>addend</CODE> have been added to this bag.
   * @exception IllegalArgumentException
   *   Indicates that <CODE>addend</CODE> is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of the bag.
   **/
   public void addAll(TreeBag<E> addend)
   {
      // Implemented by student.
   }
   
   /**
   * Create a new bag that contains all the elements from two other bags.
   * @param <CODE>b1</CODE>
   *   the first of two bags
   * @param <CODE>b2</CODE>
   *   the second of two bags
   * <dt><b>Precondition:</b><dd>
   *   Neither b1 nor b2 is null.
   * @return
   *   the union of b1 and b2
   * @exception IllegalArgumentException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.
   **/   
   public static TreeBag union(TreeBag b1, TreeBag b2)
   {
      // Student will replace this return statement with their own code:
      return null;
   }
      
}
           
