// FILE HEADER
// Title: P104_RBT
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class creates a Red Black BST, utilizing the comparable and
 * BSTRotation classes.
 * @param <T>
 */
public class RedBlackTree <T extends Comparable<T>> extends BSTRotation<T> {

    // only field: protected field root inherited from BinarySearchTree

    /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newRedNode) {
        // determine other nodes that will be used
        RBTNode<T> parentNode;
        RBTNode<T> grandparentNode;
        RBTNode<T> auntNode;

        // if the new node doesn't have a parent,
        // set the family nodes all as null
        if(newRedNode.getUp() == null){
            parentNode = null;
            grandparentNode = null;
            auntNode = null;
        } else { //otherwise, if the parent isn't null, check if the
            //grandparent node exists as well
            parentNode = newRedNode.getUp();
            if(newRedNode.getUp().getUp() == null){
                grandparentNode = null;
            } else {
                grandparentNode = newRedNode.getUp().getUp();
            }
        }

        //if new node is black for some reason then just return
        if(!newRedNode.isRed) return;

        //if it's the root node, just flip its color to black and return
        if(newRedNode == this.root) {
            newRedNode.flipColor();
            return;
        }

        // determine if there's a violation by checking the parent

        //if the parent is black or null, method terminates since
        // there's no violation
        if(parentNode == null || !parentNode.isRed) return;

        //if violation is detected, repair oit and any others later generated

        //check if grandparent node exists
        if(grandparentNode == null){
            //if it doesn't exist, parent should be the root
            if(parentNode != this.root) {
                //set parent as the root and make sure its black
                this.root = parentNode;
                if(parentNode.isRed) {
                    parentNode.flipColor();
                }
            }
        } else {
            //if parent node is the right child of grandparent
            if(parentNode == grandparentNode.getRight()){
                //if grandparent doesn't have a left child (no aunt node, call the
                // aunt is black method
                if(grandparentNode.getLeft() == null){
                    auntIsBlack(newRedNode, false);
                } //if grandparent has a left child, determine if aunt is red or black
                else {
                    auntNode = grandparentNode.getLeft();
                    if(auntNode.isRed) auntIsRed(newRedNode, auntNode);
                    else auntIsBlack(newRedNode, false);
                }
            } //if parent node is left child of grandparent
            else if(parentNode == grandparentNode.getLeft()) {
                //if grandparent doesn't have a right child (no aunt node, call the
                // aunt is black method
                if(grandparentNode.getRight() == null) {
                    auntIsBlack(newRedNode, true);
                } //if grandparent has a right child, determine if aunt is red or black
                else {
                    auntNode = grandparentNode.getRight();
                    if(auntNode.isRed) auntIsRed(newRedNode, auntNode);
                    else auntIsBlack(newRedNode, true);
                }
            } // if the above if statements aren't right print error
            else System.out.println("There is an error - parent node isn't grandparent's left nor right child");
        }

        //if the end root is red, need to flip it to black
        if(((RBTNode<T>)this.root).isRed){
            ((RBTNode<T>) this.root).flipColor();
        }
    }

    /**
     * This method fixes a violation if the aunt node is red as well.
     * @param redChildNode The node that caused the violation (the red child)
     * @param auntNode The aunt node of the redChildNode
     */
    private void auntIsRed(RBTNode<T> redChildNode, RBTNode<T> auntNode){
        // initialize the other nodes that will be used
        // since the aunt node exists, so do the parent/grandparent nodes
        RBTNode<T> parentNode = redChildNode.getUp();
        RBTNode<T> grandparentNode = redChildNode.getUp().getUp();

        // red parent node should be flipped to black
        if(parentNode.isRed) parentNode.flipColor();

        // black grandparent node should be flipped to red
        if(!grandparentNode.isRed) grandparentNode.flipColor();

        // red aunt node should be flipped to black
        if(auntNode.isRed) auntNode.flipColor();

        // ensure no new violations occurred when switching grandparent
        // node to red
        ensureRedProperty(grandparentNode);
    }

    /**
     * This method fixes a violation if the aunt node is null or black.
     * @param redChildNode The node that caused the violation (a red child node)
     * @param isParentLeftChild Boolean value of whether the parent node is the
     *                          grandparent node's left child
     */
    private void auntIsBlack(RBTNode<T> redChildNode, boolean isParentLeftChild){
        // initialize the other nodes
        RBTNode<T> parentNode = redChildNode.getUp();
        RBTNode<T> grandparentNode = redChildNode.getUp().getUp();

        //if the redChild is the right child of parent but parent is a left child
        //or if redChild is left child but parent is a right child
        // (if the grandparent/parent/child relationship isn't a line)
        // then rotate the child and parent nodes
        // then the child node is in the parent spot, so we need to update the parent reference
        if((redChildNode.isRightChild() && isParentLeftChild)
                || (!redChildNode.isRightChild() && !isParentLeftChild)){
            rotate(redChildNode, parentNode);
            parentNode = redChildNode;
        }

        // we then need to rotate the node in the parent spot and the grandparent
        rotate(parentNode, grandparentNode);

        //then flip the colors of the parent and grandparent nodes
        parentNode.flipColor();
        grandparentNode.flipColor();

    }


    /**
     * This method inserts the provided data in the form of a Red Black Tree
     * node into the Red Black Tree.
     * @param data the new value being inserted
     * @throws NullPointerException if the data entered is null
     */
    @Override
    public void insert(Comparable data) throws NullPointerException{
        //check if data is valid, throw exception if invalid
        if(data == null) {
            throw new NullPointerException("Invalid data");
        }

        // creates a new red node for the data
        RBTNode<T> newNode = new RBTNode<T>((T) data);

        //if the tree is empty, new node is the new root - should be black
        if(this.isEmpty() || this.root == null) {
            newNode.isRed = false;
            this.root = newNode;
            return;
        }

        //call the insert helper
        insertHelper(newNode, this.root);

        //call the method to ensure there are no RBT violations
        ensureRedProperty(newNode);


        //((RBTNode<T>)this.root).isRed = false;
    }


    /**
     * This test method tests the very basic functionalities of the red black tree -
     * inserting values that do not require flipping colors or rotations.
     */
    @Test
    public void test1_RBT(){
        //try inserting a null value
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        // ensure that a newly created tree is empty and the root is null
        Assertions.assertTrue(tree.root == null);
        Assertions.assertTrue(tree.isEmpty());

        try{
            tree.insert(null);
            Assertions.fail(); //test fails if the NullPointerException isn't thrown
        } catch (NullPointerException e){
            if(e.getMessage() == null || e.getMessage().isEmpty()){
                Assertions.fail(); //test fails if the exception message is null/empty
            }
            System.out.println(e.getMessage());
        }

        //ensure that the first node entered becomes the root node (and thus black)
        tree.insert((Integer) 5);
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 1);

        //if the root = 5, test passes
        Assertions.assertEquals(tree.root.toString(), "5(b)");

        //ensure that a smaller value node added becomes root's left child
        tree.insert((Integer) 3);
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 2);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        Assertions.assertEquals(tree.root.toLevelOrderString(), "[ 5(b), 3(r) ]");
        Assertions.assertEquals(tree.root.toInOrderString(), "[ 3(r), 5(b) ]");

        //ensure that a larger value node added becomes root's right child
        tree.insert((Integer) 8);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 3);
        Assertions.assertEquals(tree.root.toLevelOrderString(), "[ 5(b), 3(r), 8(r) ]");
        Assertions.assertEquals(tree.root.toInOrderString(), "[ 3(r), 5(b), 8(r) ]");

    }

    /**
     * This test method tests the auntIsRed() method - ensuring recoloring operations
     * are performed correctly when the aunt node is red. This method tests 2 examples
     * from Q03 as well.
     */
    @Test
    public void test2_RBT() {
        //test the auntIsRed() method - no rotations should be performed, just recoloring
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert((Integer) 5);
        tree.insert((Integer) 3);
        tree.insert((Integer) 8);
        tree.insert((Integer) 10);
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 4);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        Assertions.assertEquals(tree.root.toLevelOrderString(), "[ 5(b), 3(b), 8(b), 10(r) ]");
        Assertions.assertEquals(tree.root.toInOrderString(), "[ 3(b), 5(b), 8(b), 10(r) ]");

        tree.clear();

        // example from Q03 question #3: tests the auntIsRed() method - just recoloring
        RedBlackTree<String> treeString = new RedBlackTree<>();
        treeString.insert("L");
        treeString.insert("G");
        treeString.insert("V");
        treeString.insert("D");
        treeString.insert("J");
        treeString.insert("P");
        treeString.insert("Y");
        treeString.insert("C");
        treeString.insert("E");
        treeString.insert("N");
        //check that the size is as expected
        Assertions.assertEquals(treeString.size(), 10);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        Assertions.assertEquals(treeString.root.toLevelOrderString(), "[ L(b), G(r), V(r), D(b), J(b), P(b), Y(b), C(r), E(r), N(r) ]");
        Assertions.assertEquals(treeString.root.toInOrderString(), "[ C(r), D(b), E(r), G(r), J(b), L(b), N(r), P(b), V(r), Y(b) ]");

        treeString.clear();

        //example from Q03 #4 : tests auntIsRed() - recoloring and cascading fix
        treeString.insert("M");
        treeString.insert("E");
        treeString.insert("T");
        treeString.insert("C");
        treeString.insert("G");
        treeString.insert("P");
        treeString.insert("V");
        treeString.insert("B");
        treeString.insert("D");
        treeString.insert("A");
        //check that the size is as expected
        Assertions.assertEquals(treeString.size(), 10);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        Assertions.assertEquals(treeString.root.toLevelOrderString(), "[ E(b), C(r), M(r), B(b), D(b), G(b), T(b), A(r), P(r), V(r) ]");
        Assertions.assertEquals(treeString.root.toInOrderString(), "[ A(r), B(b), C(r), D(b), E(b), G(b), M(r), P(r), T(b), V(r) ]");

    }

    /**
     * This tester method evaluates the functionality of the auntIsBlack() method,
     * testing the various aunt and rotation possibilities.
     */
    @Test
    public void test3_RBT(){
        //test the auntIsBlack()

        //tests where the aunt is null and only the parent-grandparent rotation/recoloring is performed
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert((Integer) 5);
        tree.insert((Integer) 3);
        tree.insert((Integer) 8);
        tree.insert((Integer) 10);
        tree.insert((Integer) 12);
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 5);

        //ensure the actual level/in order strings are the same as the expected ones
        Assertions.assertEquals(tree.root.toLevelOrderString(), "[ 5(b), 3(b), 10(b), 8(r), 12(r) ]");
        Assertions.assertEquals(tree.root.toInOrderString(), "[ 3(b), 5(b), 8(r), 10(b), 12(r) ]");

        tree.clear();

        //tests the null aunt and both rotation operations
        tree.insert((Integer) 5);
        tree.insert((Integer) 3);
        tree.insert((Integer) 8);
        tree.insert((Integer) 10);
        tree.insert((Integer) 9);
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 5);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        Assertions.assertEquals(tree.root.toLevelOrderString(), "[ 5(b), 3(b), 9(b), 8(r), 10(r) ]");
        Assertions.assertEquals(tree.root.toInOrderString(), "[ 3(b), 5(b), 8(r), 9(b), 10(r) ]");

        //creates a larger tree, tests left child rotation (rotate right) operations
        tree.insert((Integer) 2);
        tree.insert((Integer) 1);
        //check that the size is as expected
        Assertions.assertEquals(tree.size(), 7);

        //ensure the actual level/in order strings are the same as the expected ones - if not, test fails
        Assertions.assertEquals(tree.root.toLevelOrderString(), "[ 5(b), 2(b), 9(b), 1(r), 3(r), 8(r), 10(r) ]");
        Assertions.assertEquals(tree.root.toInOrderString(), "[ 1(r), 2(b), 3(r), 5(b), 8(r), 9(b), 10(r) ]");

    }


}