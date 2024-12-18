// FILE HEADER
// Title: P101_BST
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

/**
 * This class creates a Binary Search Tree that can hold several BSTNodes containing
 * data values.
 */
public class BinarySearchTree <T extends Comparable<T>> implements SortedCollection<T>{

    /**
     * This main method calls the tester methods.
     * @param args Unused
     */
    public static void main(String[] args){
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(test3());
        System.out.println(test4());
        System.out.println(test5());
    }

    //The root of the BST
    protected BSTNode<T> root;

    /**
     * Inserts a new data value into the BST.
     * @param data the new value being inserted
     * @throws NullPointerException if data argument is null, we do not allow
     * null values to be stored within a BST
     */
    @Override
    public void insert(Comparable data) throws NullPointerException {
        // throw exception if data value is null
        if(data == null) throw new NullPointerException("Null values cannot be stored");
        BSTNode<T> newNode = new BSTNode<>((T) data);

        // if the tree is empty, the newNode is the root of the new tree
        if(root == null){
            root = newNode;
        }
        // if not, the newNode needs to be inserted
        else {
            insertHelper(newNode, root);
        }
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree.  When the provided subtree
     * is null, this method does nothing.
     * @param newNode The BSTNode to be inserted
     * @param subtree The BSTNode that is the root of the subtree
     */
    protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {

        //provided subtree is null
        if(subtree == null) return;
            //if newNode < root or duplicate, go down left
        else if (newNode.getData().compareTo(subtree.getData()) <= 0){
            if(subtree.getLeft() == null){
                subtree.setLeft(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getLeft());
            }
        } //if newNode > root, go down right
        else if (newNode.getData().compareTo(subtree.getData()) > 0){
            if(subtree.getRight() == null){
                subtree.setRight(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getRight());
            }
        }
    }

    /**
     * Check whether data is stored in the BST.
     * @param data the value to check for in the BST
     * @return true if the BST contains data one or more times,
     * and false otherwise
     * @param data The data to be found
     */
    @Override
    public boolean contains(Comparable data) {
        //if data is null, return false
        if(data == null) {
            return false;
        }

        BSTNode<T> newNode = new BSTNode<>((T) data);

        //call the helper method
        return containsHelper(newNode, root);
    }

    /**
     * Recursively determines if the BST of the given subtree contains a
     * specified data value
     * @param data The value being searched for
     * @param subtree The root BSTNode of the current BST being searched
     * @return true if the subtree contains the value, false if not
     */
    protected boolean containsHelper(BSTNode<T> data, BSTNode<T> subtree){
        //if subtree or data is null, return false
        if(data == null || subtree == null){
            return false;
        } // if data < root's data, go down the left subtree
        else if (data.getData().compareTo(subtree.getData()) < 0){
            return containsHelper(data, subtree.getLeft());
        } // if data > root's data, go down right subtree
        else if(data.getData().compareTo(subtree.getData()) > 0){
            return containsHelper(data, subtree.getRight());
        } // if they're equal, we've found the value and can return true
        else {
            return true;
        }
    }

    /**
     * Counts the number of values in the BST, with each duplicate value
     * being counted separately within the value returned.
     * @return the number of values in the BST, including duplicates
     */
    @Override
    public int size() {
        //call the helper method with the root of the BST
        return sizeHelper(root);
    }

    /**
     * Determines the number of nodes in the BST of the provided root
     * @param rootNode The root BSTNode of the BST being searched
     * @return int The int number of nodes in the specified BST
     */
    protected int sizeHelper(BSTNode<T> rootNode){
        //if rootNode is null, return 0
        if(rootNode == null){
            return 0;
        }

        //otherwise, return the rootNode count (1) + the number of nodes in the left subtree
        // + the number of nodes in the right subtree
        return 1 + sizeHelper(rootNode.getLeft()) + sizeHelper(rootNode.getRight());
    }

    /**
     * Checks if the BST is empty.
     * @return true if the BST contains 0 BSTNodes, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Removes all values and duplicates from the BST.
     */
    @Override
    public void clear() {
        root = null;
    }

    // test methods - use different shaped tests and Integers and Strings

    /**
     * This test method tests the BinarySearchTree methods with a BST
     * with a root, one left child, and one right child.
     * @return true if all conditions are met and passed, false if not
     */
    public static boolean test1(){
        //root, 1 left child, 1 right child
        BinarySearchTree<Integer> newBST = new BinarySearchTree<>();

        //insert a valid value into the BST
        try{
            newBST.insert((Integer)5);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //insert a null value into the BST
        try{
            newBST.insert(null);
            return false;
        } catch (Exception e){
            if(e.getMessage().isBlank() || e.getMessage() == null){
                return false;
            }
            System.out.println(e.getMessage());
        }

        //inserting as left child
        try{
            newBST.insert((Integer)2);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //return false if newBST doesn't contain 2 and its root's left child isn't 2
        if(!newBST.contains((Integer)2) || !newBST.root.getLeft().getData().equals((Integer) 2)){
            return false;
        }

        //inserting as right child
        try{
            newBST.insert((Integer)8);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        //return false if newBST doesn't contain 8 and its root's left child isn't 8
        if(!newBST.contains((Integer)8) || !newBST.root.getRight().getData().equals((Integer) 8)){
            return false;
        }

        //FINDING VALUES
        //if newBST doesn't contain the left leaf (2), right leaf (8), nor the root, return false
        if(!newBST.contains((Integer)2) || !newBST.contains((Integer)8) || !newBST.contains((Integer)5)){
            return false;
        }

        if(!newBST.root.toInOrderString().equals("[ 2, 5, 8 ]")) return false;
        //size and clear

        if(newBST.size() != 3) return false;

        newBST.clear();


        if(newBST.size() != 0) return false;

        return true;
    }

    //making different shapes

    /**
     * This test method determines if all BinarySearchTree methods
     * work correctly on a BST with a root node and only
     * left children.
     *
     * @return true if all conditions are met and passed,
     * false if not.
     */
    public static boolean test2(){
        //all left children
        BinarySearchTree<String> newBST = new BinarySearchTree<>();

        //insert root node
        try{
            newBST.insert("d");
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //add several left children
        try{
            newBST.insert("c");
            newBST.insert("b");
            newBST.insert("a");
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //find leaf node
        if(!newBST.contains("a")) return false;

        //find an interior node
        if(!newBST.contains("b")) return false;

        //find root node
        if(!newBST.contains("d")) return false;

        if(!newBST.root.toInOrderString().equals("[ a, b, c, d ]")) return false;
        if(!newBST.root.toLevelOrderString().equals("[ d, c, b, a ]")) return false;

        //check size
        if(newBST.size() != 4) return false;

        //check clear
        newBST.clear();
        if(newBST.size() != 0) return false;

        return true;
    }

    /**
     * This test method determines if the BinarySearchTree's methods
     * work correctly on a BST with a root node and only right children.
     * @return true if all conditions are met, false if not
     */
    public static boolean test3(){
        //all right children
        BinarySearchTree<String> newBST = new BinarySearchTree<>();

        //insert root node
        try{
            newBST.insert("a");
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //add several right children
        try{
            newBST.insert("b");
            newBST.insert("c");
            newBST.insert("d");
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //find leaf node
        if(!newBST.contains("d")) return false;

        //find an interior node
        if(!newBST.contains("b")) return false;

        //find root node
        if(!newBST.contains("a")) return false;

        if(!newBST.root.toInOrderString().equals("[ a, b, c, d ]")) return false;

        if(!newBST.root.toLevelOrderString().equals("[ a, b, c, d ]")) return false;

        //check size
        if(newBST.size() != 4) return false;

        //check clear
        newBST.clear();
        if(newBST.size() != 0) return false;

        return true;
    }

    /**
     * Determines if the BinarySearchTree methods work correctly on a BST
     * with a more random structure - one node with two children, who each have
     * varying amounts of left/right children.
     * @return true if all conditions are met, false if not
     */
    public static boolean test4() {
        //random tree structure
        BinarySearchTree<Integer> newBST = new BinarySearchTree<>();

        try {
            //add root
            newBST.insert((Integer) 5);

            //add a left child, add a right child to it
            newBST.insert((Integer) 3);
            newBST.insert((Integer) 4);

            //add a right child, add a left and right child to it
            newBST.insert((Integer) 8);

            newBST.insert((Integer) 6);
            newBST.insert((Integer) 9);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        //find values
        if (!newBST.contains((Integer) 5) || !newBST.contains((Integer) 3) || !newBST.contains((Integer) 4) ||
                !newBST.contains((Integer) 8) || !newBST.contains((Integer) 6) || !newBST.contains((Integer) 9))
            return false;

        if (!newBST.root.toInOrderString().equals("[ 3, 4, 5, 6, 8, 9 ]")) return false;
        if (!newBST.root.toLevelOrderString().equals("[ 5, 3, 8, 4, 6, 9 ]")) return false;
        //check size
        if (newBST.size() != 6) return false;

        //check empty
        newBST.clear();
        if (newBST.size() != 0) return false;

        return true;
    }

    public static boolean test5() {
        //random tree structure
        BinarySearchTree<Integer> newBST = new BinarySearchTree<>();

        try {
            //add root
            newBST.insert("K");

            //add a left child, add a right child to it
            newBST.insert("G");
            newBST.insert("G");
            newBST.insert("Z");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        //find values
        System.out.println(newBST.root.toInOrderString());
        System.out.println(newBST.root.toLevelOrderString());


        return true;
    }


}