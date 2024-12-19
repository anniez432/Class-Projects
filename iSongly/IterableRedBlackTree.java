// FILE HEADER
// Title: P106_IterableRBT
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class extends RedBlackTree into a tree that supports iterating over the values it
 * stores in sorted, ascending order.
 */
public class IterableRedBlackTree<T extends Comparable<T>>
        extends RedBlackTree<T> implements IterableSortedCollection<T> {

    //stores maximum for iterator or null if no max is set
    protected Comparable<T> max = null;

    //stores minimum for iterator or null if no min is set
    protected Comparable<T> min = null;

    /**
     * Allows setting the start (minimum) value of the iterator. When this method is called,
     * every iterator created after it will use the minimum set by this method until this method
     * is called again to set a new minimum value.
     * @param min the minimum for iterators created for this tree, or null for no minimum
     */
    public void setIteratorMin(Comparable<T> min) {
        this.min = min;
    }

    /**
     * Allows setting the stop (maximum) value of the iterator. When this method is called,
     * every iterator created after it will use the maximum set by this method until this method
     * is called again to set a new maximum value.
     * @param max the maximum for iterators created for this tree, or null for no maximum
     */
    public void setIteratorMax(Comparable<T> max) {
        this.max = max;
    }

    /**
     * Returns an iterator over the values stored in this tree. The iterator uses the
     * start (minimum) value set by a previous call to setIteratorMin, and the stop (maximum)
     * value set by a previous call to setIteratorMax. If setIteratorMin has not been called
     * before, or if it was called with a null argument, the iterator uses no minimum value
     * and starts with the lowest value that exists in the tree. If setIteratorMax has not been
     * called before, or if it was called with a null argument, the iterator uses no maximum
     * value and finishes with the highest value that exists in the tree.
     */
    public Iterator<T> iterator() {
        return new RBTIterator<>(this.root, this.min, this.max);
    }

    /**
     * Nested class for Iterator objects created for this tree and returned by the iterator method.
     * This iterator follows an in-order traversal of the tree and returns the values in sorted,
     * ascending order.
     */
    protected static class RBTIterator<R> implements Iterator<R> {
        // stores the start point (minimum) for the iterator
        Comparable<R> min = null;
        // stores the stop point (maximum) for the iterator
        Comparable<R> max = null;
        // stores the stack that keeps track of the inorder traversal
        Stack<BSTNode<R>> stack = null;

        /**
         * Constructor for a new iterator if the tree with root as its root node, and
         * min as the start (minimum) value (or null if no start value) and max as the
         * stop (maximum) value (or null if no stop value) of the new iterator.
         * @param root root node of the tree to traverse
         * @param min the minimum value that the iterator will return
         * @param max the maximum value that the iterator will return 
         */
        public RBTIterator(BSTNode<R> root, Comparable<R> min, Comparable<R> max) {
            //accepts root node for tree to iterate over
            // need to check validity??
            //store start (min) value
            this.min = min;

            //store stop (max) value
            this.max = max;

            //initialize stack field
            this.stack = new Stack<BSTNode<R>>();

            //call buildStackHelper for argument root node to initialize stack
            buildStackHelper(root);
        }

        /**
         * Helper method for initializing and updating the stack. This method both
         * - finds the next data value stored in the tree (or subtree) that is bigger
         *   than or equal to the specified start point (maximum), and
         * - builds up the stack of ancestor nodes that contain values larger than or
         *   equal to the start point so that those nodes can be visited in the future.
         * @param node the root node of the subtree to process
         */
        private void buildStackHelper(BSTNode<R> node) {
            //recurisve method - one base case, 2 recursive cases
            // reaches base case and returns if node is null
            if (node == null) return;

            // calls recursively on node's right subtree if node < min
            // same as if min.compareTo(node) > 0
            // if min is null then you want to go down to smallest value
            if(this.min != null && this.min.compareTo(node.getData()) > 0){
                buildStackHelper(node.getRight());
            } //pushes onto stack and recursively calls left subtree
            // if node >= min point or if min == null
            // same as if min.compareTo(node) <= 0
            else {
                this.stack.push(node);
                buildStackHelper(node.getLeft());
            }
        }

        /**
         * Returns true if the iterator has another value to return, and false otherwise.
         */
        public boolean hasNext() {
            //returns true if there are more nodes to visit with
            // <= max
            // stack contains values that are <= max
            //therefore if stack is not empty, there is a next value
            while(!stack.isEmpty()){
                //check next value
                BSTNode<R> nextNode = this.stack.peek();
                if(this.max != null && this.max.compareTo(nextNode.getData()) < 0){
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns the next value of the iterator.
         * @throws NoSuchElementException if the iterator has no more values to return
         */
        public R next() {
            //returns values from the tree in sorted, ascending order
            //returns only values that are <= max and >= min

            //implements iterative in order traversal

            //uses stack
            //calls buildStackHelper to build stack to contain more
            //nodes to contain more nodes when needed

            //throws a NoSuchElementException if there are no more nodes
            //to visit with values <= max
            if(!this.hasNext()){
                throw new NoSuchElementException("There are no more nodes to return.");
            }

            //the stack is the in order traversal of the tree

            //pop off the stack, left subtree has already been traversed?
            BSTNode<R> returnNode = this.stack.pop();

            //want to go down the right subtree now if it exists
            if (returnNode.getRight() != null) {
                buildStackHelper(returnNode.getRight());
            }

            //return the value of the current node
            return returnNode.getData();
        }
    }

    /**
     * This test determines if the Iterable Red Black Tree correctly inserts String or Integer
     * values and is able to return an in-order traversal with its iterator, specifically when no
     * min or max values are set.
     */
    @Test
    public void test1_IterableRBT(){
        IterableRedBlackTree<String> treeString = new IterableRedBlackTree<>();
        treeString.insert("f");
        treeString.insert("c");
        treeString.insert("m");
        treeString.insert("a");
        treeString.insert("e");
        treeString.insert("l");
        treeString.insert("o");
        treeString.insert("d");
        treeString.insert("g");
        treeString.insert("n");
        treeString.insert("p");

        //check that all values were inserted correctly
        Assertions.assertEquals(treeString.root.toLevelOrderString(), "[ f(b), c(r), m(r), a(b), e(b), l(b), " +
                "o(b), d(r), g(r), n(r), p(r) ]");

        //instantiate a tree iterator for this test
        Iterator<String> treeStringIterator = treeString.iterator();

        //NO MIN/MAX VALUES
        //check that the first value to be returned by next is a
        Assertions.assertEquals(treeStringIterator.next(), "a");

        //puts the iterator's returned values into an ArrayList for comparison
        ArrayList<String> actual = new ArrayList<>();
        while(treeStringIterator.hasNext()){
            actual.add(treeStringIterator.next());
        }

        //the expected ArrayList should contain the correct in-order traversal of the tree
        ArrayList<String> expected = new ArrayList<>();
        expected.add("c");
        expected.add("d");
        expected.add("e");
        expected.add("f");
        expected.add("g");
        expected.add("l");
        expected.add("m");
        expected.add("n");
        expected.add("o");
        expected.add("p");

        //this test fails if the iterator doesn't return an in order traversal
        Assertions.assertEquals(actual, expected);

        //NO MIN/MAX ON AN INTEGER ITERABLE RBT
        IterableRedBlackTree<Integer> treeInteger = new IterableRedBlackTree<>();
        treeInteger.insert((Integer)10);
        treeInteger.insert((Integer)5);
        treeInteger.insert((Integer)15);
        treeInteger.insert((Integer)3);
        treeInteger.insert((Integer)7);
        treeInteger.insert((Integer)12);
        treeInteger.insert((Integer)18);
        treeInteger.insert((Integer)2);
        treeInteger.insert((Integer)4);
        treeInteger.insert((Integer)6);
        treeInteger.insert((Integer)11);

        //ensure values were inserted correctly
        Assertions.assertEquals(treeInteger.root.toLevelOrderString(), "[ 10(b), 5(r), 15(r), 3(b), 7(b), " +
                "12(b), 18(b), 2(r), 4(r), 6(r), 11(r) ]");

        //instantiate an iterator
        Iterator<Integer> treeIntegerIterator = treeInteger.iterator();

        //add the iterator's return values into an ArrayList for comparison
        ArrayList<Integer> actualIntegers = new ArrayList<>();

        while(treeIntegerIterator.hasNext()){
            actualIntegers.add(treeIntegerIterator.next());
        }

        //this is the expected in-order traversal
        ArrayList<Integer> expectedIntegers = new ArrayList<>();
        expectedIntegers.add((Integer) 2);
        expectedIntegers.add((Integer) 3);
        expectedIntegers.add((Integer) 4);
        expectedIntegers.add((Integer) 5);
        expectedIntegers.add((Integer) 6);
        expectedIntegers.add((Integer) 7);
        expectedIntegers.add((Integer) 10);
        expectedIntegers.add((Integer) 11);
        expectedIntegers.add((Integer) 12);
        expectedIntegers.add((Integer) 15);
        expectedIntegers.add((Integer) 18);

        //test fails if the iterator doesn't correctly return the tree's in order traversal
        Assertions.assertEquals(actualIntegers, expectedIntegers);
    }

    /**
     * This test determines if the Iterable Red Black Tree can correctly in-order traverse
     * when a min (start) point is set for the iterator, but no max point is set.
     */
    @Test
    public void test2_IterableRBT() {
        // STRING TREE WITH A SET MIN BUT NO SET MAX
        IterableRedBlackTree<String> treeString = new IterableRedBlackTree<>();
        treeString.insert("f");
        treeString.insert("c");
        treeString.insert("m");
        treeString.insert("a");
        treeString.insert("e");
        treeString.insert("l");
        treeString.insert("o");
        treeString.insert("d");
        treeString.insert("g");
        treeString.insert("n");
        treeString.insert("p");

        //set the min (start) point at c
        treeString.setIteratorMin("c");

        //instantiate iterator
        Iterator<String> treeStringIterator = treeString.iterator();

        //store iterator's returned values in ArrayList for comparison
        ArrayList<String> actualString = new ArrayList<>();
        while(treeStringIterator.hasNext()){
            actualString.add(treeStringIterator.next());
        }

        //create the expected in order traversal list
        ArrayList<String> expectedString = new ArrayList<>();
        expectedString.add("c");
        expectedString.add("d");
        expectedString.add("e");
        expectedString.add("f");
        expectedString.add("g");
        expectedString.add("l");
        expectedString.add("m");
        expectedString.add("n");
        expectedString.add("o");
        expectedString.add("p");

        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualString, expectedString);

        //INTEGER TREE WITH SET MIN/NO MAX
        IterableRedBlackTree<Integer> treeInteger = new IterableRedBlackTree<>();
        treeInteger.insert((Integer)10);
        treeInteger.insert((Integer)5);
        treeInteger.insert((Integer)15);
        treeInteger.insert((Integer)3);
        treeInteger.insert((Integer)7);
        treeInteger.insert((Integer)12);
        treeInteger.insert((Integer)18);
        treeInteger.insert((Integer)2);
        treeInteger.insert((Integer)4);
        treeInteger.insert((Integer)6);
        treeInteger.insert((Integer)11);

        //set min (start) to 15
        treeInteger.setIteratorMin(15);

        //instantiate iterator
        Iterator<Integer> treeIntegerIterator = treeInteger.iterator();

        //store iterator's returned values in an ArrayList for comparison
        ArrayList<Integer> actualIntegers = new ArrayList<>();
        while(treeIntegerIterator.hasNext()){
            actualIntegers.add(treeIntegerIterator.next());
        }

        //expected in-order traversal that starts from 15
        ArrayList<Integer> expectedIntegers = new ArrayList<>();
        expectedIntegers.add((Integer) 15);
        expectedIntegers.add((Integer) 18);

        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualIntegers, expectedIntegers);
    }

    /**
     * This test determines if the Iterable Red Black Tree can correctly in-order traverse
     * when a max (stop) point is set for the iterator, but no min point is set.
     */
    @Test
    public void test3_IterableRBT() {
        // STRING TREE WITH A SET MAX BUT NO SET MIN
        IterableRedBlackTree<String> treeString = new IterableRedBlackTree<>();
        treeString.insert("f");
        treeString.insert("c");
        treeString.insert("m");
        treeString.insert("a");
        treeString.insert("e");
        treeString.insert("l");
        treeString.insert("o");
        treeString.insert("d");
        treeString.insert("g");
        treeString.insert("n");
        treeString.insert("p");

        //set the max (stop) point at l
        treeString.setIteratorMax("l");

        //instantiate iterator
        Iterator<String> treeStringIterator = treeString.iterator();

        //store iterator's returned values in ArrayList for comparison
        ArrayList<String> actualString = new ArrayList<>();
        while(treeStringIterator.hasNext()){
            actualString.add(treeStringIterator.next());
        }

        //create the expected in order traversal list
        ArrayList<String> expectedString = new ArrayList<>();
        expectedString.add("a");
        expectedString.add("c");
        expectedString.add("d");
        expectedString.add("e");
        expectedString.add("f");
        expectedString.add("g");
        expectedString.add("l");


        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualString, expectedString);

        //INTEGER TREE WITH SET MAX / NO MIN
        IterableRedBlackTree<Integer> treeInteger = new IterableRedBlackTree<>();
        treeInteger.insert((Integer)10);
        treeInteger.insert((Integer)5);
        treeInteger.insert((Integer)15);
        treeInteger.insert((Integer)3);
        treeInteger.insert((Integer)7);
        treeInteger.insert((Integer)12);
        treeInteger.insert((Integer)18);
        treeInteger.insert((Integer)2);
        treeInteger.insert((Integer)4);
        treeInteger.insert((Integer)6);
        treeInteger.insert((Integer)11);

        //set max (stop) to 7
        treeInteger.setIteratorMax(7);

        //instantiate iterator
        Iterator<Integer> treeIntegerIterator = treeInteger.iterator();

        //store iterator's returned values in an ArrayList for comparison
        ArrayList<Integer> actualIntegers = new ArrayList<>();
        while(treeIntegerIterator.hasNext()){
            actualIntegers.add(treeIntegerIterator.next());
        }

        //expected in-order traversal that ends with 7
        ArrayList<Integer> expectedIntegers = new ArrayList<>();
        expectedIntegers.add((Integer) 2);
        expectedIntegers.add((Integer) 3);
        expectedIntegers.add((Integer) 4);
        expectedIntegers.add((Integer) 5);
        expectedIntegers.add((Integer) 6);
        expectedIntegers.add((Integer) 7);

        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualIntegers, expectedIntegers);
    }

    /**
     * This test determines if the Iterable Red Black Tree can correctly in-order traverse
     * when min (start) and max (stop) points are both set
     */
    @Test
    public void test4_IterableRBT() {
        // STRING TREE WITH SET MIN AND MAX
        IterableRedBlackTree<String> treeString = new IterableRedBlackTree<>();
        treeString.insert("f");
        treeString.insert("c");
        treeString.insert("m");
        treeString.insert("a");
        treeString.insert("e");
        treeString.insert("l");
        treeString.insert("o");
        treeString.insert("d");
        treeString.insert("g");
        treeString.insert("n");
        treeString.insert("p");

        //set min/max
        treeString.setIteratorMin("e");
        treeString.setIteratorMax("o");

        //instantiate iterator
        Iterator<String> treeStringIterator = treeString.iterator();

        //store iterator's returned values in ArrayList for comparison
        ArrayList<String> actualString = new ArrayList<>();
        while(treeStringIterator.hasNext()){
            actualString.add(treeStringIterator.next());
        }

        //create the expected in order traversal list
        ArrayList<String> expectedString = new ArrayList<>();
        expectedString.add("e");
        expectedString.add("f");
        expectedString.add("g");
        expectedString.add("l");
        expectedString.add("m");
        expectedString.add("n");
        expectedString.add("o");


        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualString, expectedString);

        //INTEGER TREE WITH SET MAX AND SET MIN
        IterableRedBlackTree<Integer> treeInteger = new IterableRedBlackTree<>();
        treeInteger.insert((Integer)10);
        treeInteger.insert((Integer)5);
        treeInteger.insert((Integer)15);
        treeInteger.insert((Integer)3);
        treeInteger.insert((Integer)7);
        treeInteger.insert((Integer)12);
        treeInteger.insert((Integer)18);
        treeInteger.insert((Integer)2);
        treeInteger.insert((Integer)4);
        treeInteger.insert((Integer)6);
        treeInteger.insert((Integer)11);

        //set min/max
        treeInteger.setIteratorMin(3);
        treeInteger.setIteratorMax(12);

        //instantiate iterator
        Iterator<Integer> treeIntegerIterator = treeInteger.iterator();

        //store iterator's returned values in an ArrayList for comparison
        ArrayList<Integer> actualIntegers = new ArrayList<>();
        while(treeIntegerIterator.hasNext()){
            actualIntegers.add(treeIntegerIterator.next());
        }

        //expected in-order traversal that starts with 3 and ends with 12
        ArrayList<Integer> expectedIntegers = new ArrayList<>();
        expectedIntegers.add((Integer) 3);
        expectedIntegers.add((Integer) 4);
        expectedIntegers.add((Integer) 5);
        expectedIntegers.add((Integer) 6);
        expectedIntegers.add((Integer) 7);
        expectedIntegers.add((Integer) 10);
        expectedIntegers.add((Integer) 11);
        expectedIntegers.add((Integer) 12);

        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualIntegers, expectedIntegers);
    }

    /**
     * This test determines if the Iterable Red Black Tree can correctly in-order traverse
     * when there are duplicates present in the tree, whether there are set min/max points
     * or not.
     */
    @Test
    public void test5_IterableRBT() {
        // STRING TREE WITH NO SET MIN/MAX
        IterableRedBlackTree<String> treeString = new IterableRedBlackTree<>();
        treeString.insert("f");
        treeString.insert("c");
        treeString.insert("m");
        treeString.insert("a");
        treeString.insert("c");
        treeString.insert("l");
        treeString.insert("o");


        //instantiate iterator
        Iterator<String> treeStringIterator = treeString.iterator();

        //store iterator's returned values in ArrayList for comparison
        ArrayList<String> actualString = new ArrayList<>();
        while(treeStringIterator.hasNext()){
            actualString.add(treeStringIterator.next());
        }

        //create the expected in order traversal list
        ArrayList<String> expectedString = new ArrayList<>();
        expectedString.add("a");
        expectedString.add("c");
        expectedString.add("c");
        expectedString.add("f");
        expectedString.add("l");
        expectedString.add("m");
        expectedString.add("o");


        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualString, expectedString);

        //INTEGER TREE WITH SET MAX AND SET MIN
        IterableRedBlackTree<Integer> treeInteger = new IterableRedBlackTree<>();
        treeInteger.insert((Integer)10);
        treeInteger.insert((Integer)5);
        treeInteger.insert((Integer)15);
        treeInteger.insert((Integer)3);
        treeInteger.insert((Integer)5);
        treeInteger.insert((Integer)12);
        treeInteger.insert((Integer)18);
        treeInteger.insert((Integer)2);


        //set min/max
        treeInteger.setIteratorMin(5);
        treeInteger.setIteratorMax(12);

        //instantiate iterator
        Iterator<Integer> treeIntegerIterator = treeInteger.iterator();

        //store iterator's returned values in an ArrayList for comparison
        ArrayList<Integer> actualIntegers = new ArrayList<>();
        while(treeIntegerIterator.hasNext()){
            actualIntegers.add(treeIntegerIterator.next());
        }

        //expected in-order traversal that starts with 3 and ends with 12
        ArrayList<Integer> expectedIntegers = new ArrayList<>();
        expectedIntegers.add((Integer) 5);
        expectedIntegers.add((Integer) 5);
        expectedIntegers.add((Integer) 10);
        expectedIntegers.add((Integer) 12);

        //test fails if the iterator doesn't correctly return values in an in-order traversal
        Assertions.assertEquals(actualIntegers, expectedIntegers);
    }

}