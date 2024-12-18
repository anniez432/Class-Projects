// FILE HEADER
// Title: P102_BSTRotation
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

/**
 * This class rotates a Binary Search Tree's specific child and parent node.
 */
public class BSTRotation <T extends Comparable<T>> extends BinarySearchTree<T>{
    public static void main(String[] args) {
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(test3());
        System.out.println(test4());
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     *     nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BSTNode<T> child, BSTNode<T> parent)
            throws NullPointerException, IllegalArgumentException {

        //throw a NullPointerException if either are null
        if(child==null || parent == null) {
            throw new NullPointerException("Please enter a valid value");
        }

        //determine if child is left or right child and call correct method to rotate
        if(parent.left == child){
            rotateRight(child, parent);
        } //if child is a right child, do a left rotation
        else if(parent.right == child){
            rotateLeft(child, parent);
        }  //throw an IllegalArgumentException when they're not correctly related
        else {
            throw new IllegalArgumentException("Incorrect parent-child relationship.");
        }
    }

    /**
     * This helper method rotates the provided child and parent nodes to the left.
     * @param child The child node to be rotated
     * @param parent The parent node to be rotated
     */
    private void rotateLeft(BSTNode<T> child, BSTNode<T> parent){
        //if parent doesn't have an up reference, it is the root
        //so child becomes root
        if(parent.getUp() == null){
            root = child;
            child.setUp(null);
        }
        else {
            //set child's new parent to the grandparent
            child.setUp(parent.getUp());

            //if it's a right child, set the grandparent's right child to child
            if(parent.isRightChild()){
                parent.getUp().setRight(child);

            } //if it's a left child, set grandparent's left child to child
            else {
                parent.getUp().setLeft(child);
            }
        }
        //if the child has a left subtree, it becomes the parent's new child right
        if(child.getLeft() != null){
            parent.setRight(child.getLeft());
            //the left subtree's new parent is set to parent
            child.getLeft().setUp(parent);
        } else {
            parent.setRight(null);
        }

        //set child's left to the parent node
        child.setLeft(parent);
        //set parent's new parent to child
        parent.setUp(child);

    }

    /**
     * This helper method rotates the provided child and parent nodes to the right.
     * @param child The child node to be rotated
     * @param parent The parent node to be rotated
     */
    private void rotateRight(BSTNode<T> child, BSTNode<T> parent){

        if(parent.getUp() == null){
            root = child;
            child.setUp(null);
        }
        else {
            //set child's new parent to the grandparent
            child.setUp(parent.getUp());
            //if it's a right child, set the grandparent's right child to child
            if(parent.isRightChild()){
                parent.getUp().setRight(child);

            } //if it's a left child, set grandparent's left child to child
            else {
                parent.getUp().setLeft(child);
            }

        }

        //if child has a right subtree, it becomes the parent's new left child
        if(child.getRight() != null){
            //set parent's left child to right subtree
            parent.setLeft(child.getRight());
            //the subtree's parent is changed to the parent node
            child.getRight().setUp(parent);
        } else {
            parent.setLeft(null);
        }

        //set child's right child to parent
        child.setRight(parent);
        //set parent's new parent to child
        parent.setUp(child);
    }

    /**
     * This tester method tests rotations on child-parent pairs with 0 and 1 shared children,
     * including rotations that should throw an exception, ones involving the root node of the
     * BST, and both left/right rotations.
     * @return true if all tests pass, false if not
     */
    public static boolean test1(){ //tests rotations on pairs with 0 and 1 shared children
        //0 SHARED CHILDREN
        //test a rotation with just one node (root)
        BSTRotation<Integer> newBST = new BSTRotation<>();
        newBST.insert((Integer)5);
        try{
            //should throw a NullPointerException
            newBST.rotate(null, newBST.root);
            return false;
        } catch (Exception e){
            if(e.getMessage() == null || e.getMessage().isBlank()) return false;
            System.out.println(e.getMessage());
        }

        //test a rotation with just one node and a left child
        //should perform a right rotation with 3 as root and 5 as right child
        newBST.insert((Integer)3);
        try{
            newBST.rotate(newBST.root.left, newBST.root);
            if(!newBST.root.toLevelOrderString().equals("[ 3, 5 ]")) return false;
            if(!newBST.root.toInOrderString().equals("[ 3, 5 ]")) return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        //test rotation back (left rotation so 5 is root again and 3 is left child)
        try{
            newBST.rotate(newBST.root.right, newBST.root);
            if(!newBST.root.toInOrderString().equals("[ 3, 5 ]")) return false;
            if(!newBST.root.toLevelOrderString().equals("[ 5, 3 ]")) return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        //1 SHARED CHILD
        newBST.insert((Integer)8);
        //rotate 5 (root) and 8 (right child) - should perform a left rotation
        //should end up with 8 as root, 5 as its left child, and 3 as 5's left child
        try{
            newBST.rotate(newBST.root.right, newBST.root);

            if(!newBST.root.toLevelOrderString().equals("[ 8, 5, 3 ]")) return false;
            if(!newBST.root.toInOrderString().equals("[ 3, 5, 8 ]")) return false;

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //test a right rotation with 1 shared child
        //rotate 5 and 3 - should perform a right rotation
        //8 stays as the root, its left child is 3, 3's right child is 5
        try{
            newBST.rotate(newBST.root.getLeft().getLeft(), newBST.root.getLeft());

            if(!newBST.root.toLevelOrderString().equals("[ 8, 3, 5 ]")) return false;
            if(!newBST.root.toInOrderString().equals("[ 3, 5, 8 ]")) return false;

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //test a right rotation with 1 shared child
        //rotate 8 and 3 - should perform a right rotation
        try{
            newBST.rotate(newBST.root.getLeft(), newBST.root);

            if(!newBST.root.toLevelOrderString().equals("[ 3, 8, 5 ]")) return false;
            if(!newBST.root.toInOrderString().equals("[ 3, 5, 8 ]")) return false;

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        return true;
    }


    /**
     * This tester method tests rotations involving 2 shared child nodes between the
     * child-parent node pair, including both left/right rotations and ones including/not
     * including the root node.
     * @return true if all tests pass, false if not.
     */
    public static boolean test2(){
        //2 SHARED CHILDREN
        BSTRotation<Integer> newBST = new BSTRotation<>();
        newBST.insert((Integer)5);
        newBST.insert((Integer)3);
        newBST.insert((Integer)8);
        newBST.insert((Integer)2);

        try{
            //rotate 5 and its left child 3 - right rotation
            //should end up with 3 as root, 5 as its left child
            newBST.rotate(newBST.root.getLeft(), newBST.root);
            if(!newBST.root.toInOrderString().equals("[ 2, 3, 5, 8 ]")) return false;
            if(!newBST.root.toLevelOrderString().equals("[ 3, 2, 5, 8 ]")) return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //rotate 5 and 8 - 8 is 5's right child - left rotation
        //testing a left rotation without involving the root node
        try{
            newBST.rotate(newBST.root.getRight().getRight(), newBST.root.getRight());
            if(!newBST.root.toInOrderString().equals("[ 2, 3, 5, 8 ]")) return false;
            if(!newBST.root.toLevelOrderString().equals("[ 3, 2, 8, 5 ]")) return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        //try a left rotation with 2 shared children
        //rotate 3 and 8 - 8 is 3's right child - left rotation
        //testing a left rotation involving the root node
        try{
            newBST.rotate(newBST.root.getRight(), newBST.root);
            if(!newBST.root.toInOrderString().equals("[ 2, 3, 5, 8 ]")) return false;
            if(!newBST.root.toLevelOrderString().equals("[ 8, 3, 2, 5 ]")) return false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * This tester method tests rotations where the child-parent node pair has
     * 3 shared children, including both left/right rotations and ones involving/not
     * involving the root node.
     * @return
     */
    public static boolean test3() {
        //3 SHARED CHILDREN - right rotation
        BSTRotation<Integer> newBST = new BSTRotation<>();
        newBST.insert((Integer) 5);
        newBST.insert((Integer) 3);
        newBST.insert((Integer) 8);
        newBST.insert((Integer) 2);
        newBST.insert((Integer) 4);

        try {
            //rotate 5 and its left child 3 - right rotation
            newBST.rotate(newBST.root.getLeft(), newBST.root);
            if (!newBST.root.toInOrderString().equals("[ 2, 3, 4, 5, 8 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 3, 2, 5, 4, 8 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            //rotate 5 and its left child 4 - right rotation
            newBST.rotate(newBST.root.getRight().getLeft(), newBST.root.getRight());
            if (!newBST.root.toInOrderString().equals("[ 2, 3, 4, 5, 8 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 3, 2, 4, 5, 8 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            //rotate 4 and its right child 5 - left rotation
            newBST.rotate(newBST.root.getRight().getRight(), newBST.root.getRight());
            if (!newBST.root.toInOrderString().equals("[ 2, 3, 4, 5, 8 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 3, 2, 5, 4, 8 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        //TEST 3 SHARED CHILDREN - left rotation
        try {
            //rotate 3 and its right child 5 - left rotation
            newBST.rotate(newBST.root.getRight(), newBST.root);
            if (!newBST.root.toInOrderString().equals("[ 2, 3, 4, 5, 8 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 5, 3, 8, 2, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * This tester method focuses on testing larger Binary Search Trees.
     */
    public static boolean test4(){
        BSTRotation<Integer> newBST = new BSTRotation<>();


        newBST.insert((Integer) 5);
        newBST.insert((Integer) 3);
        newBST.insert((Integer) 8);
        newBST.insert((Integer) 2);
        newBST.insert((Integer) 4);
        newBST.insert((Integer) 9);

        try {
            //rotate 5 and its right child 8 - left rotation, involves root
            newBST.rotate(newBST.root.getRight(), newBST.root);

            if (!newBST.root.toInOrderString().equals("[ 2, 3, 4, 5, 8, 9 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 8, 5, 9, 3, 2, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            //rotate 3 and its left child 2 - right rotation, doesn't involve root
            newBST.rotate(newBST.root.getLeft().getLeft().getLeft(), newBST.root.getLeft().getLeft());

            if (!newBST.root.toInOrderString().equals("[ 2, 3, 4, 5, 8, 9 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 8, 5, 9, 2, 3, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }


        newBST.insert((Integer) 1);

        try {
            //rotate 8 and its right child 9 - left rotation, involves root
            newBST.rotate(newBST.root.getRight(), newBST.root);
            if (!newBST.root.toInOrderString().equals("[ 1, 2, 3, 4, 5, 8, 9 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 9, 8, 5, 2, 1, 3, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            //rotate 5 and its left child 2 - right rotation, doesn't involve root
            newBST.rotate(newBST.root.getLeft().getLeft().getLeft(), newBST.root.getLeft().getLeft());
            if (!newBST.root.toInOrderString().equals("[ 1, 2, 3, 4, 5, 8, 9 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 9, 8, 2, 1, 5, 3, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }



        newBST.insert((Integer) 10);

        try {
            //rotate 9 and its left child 8 - right rotation, involves root
            newBST.rotate(newBST.root.getLeft(), newBST.root);
            if (!newBST.root.toInOrderString().equals("[ 1, 2, 3, 4, 5, 8, 9, 10 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 8, 2, 9, 1, 5, 10, 3, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            //rotate 2 and its right child 5 - left rotation, doesn't involve root
            newBST.rotate(newBST.root.getLeft().getRight(), newBST.root.getLeft());
            if (!newBST.root.toInOrderString().equals("[ 1, 2, 3, 4, 5, 8, 9, 10 ]")) return false;
            if (!newBST.root.toLevelOrderString().equals("[ 8, 5, 9, 2, 10, 1, 3, 4 ]")) return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}