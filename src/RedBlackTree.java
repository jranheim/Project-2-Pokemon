// --== CS400 File Header Information ==--
// Name: Jake Schroeder
// Email: schroeder22@wisc.edu
// Team: KG
// TA: Keren Chen
// Lecturer: Florian
// Notes to Grader: 

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild; 
        public Node<T> rightChild; 
        public Node(T data) { this.data = data; }
        public boolean isBlack; // false for new nodes
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node. The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * Note that the Node's implementation of toString generates a level
         * order traversal. The toString of the RedBlackTree class below
         * produces an inorder traversal of the nodes / values of the tree.
         * This method will be helpful as a helper for the debugging and testing
         * of your rotation implementation.
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() {
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree
    
    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        newNode.isBlack = false; // ensures newly inserted nodes are red
        if(root == null) { 
            root = newNode; 
            newNode.isBlack = true; 
            size++; 
            return true; 
        } else { 
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) {
                root.isBlack = true;
                size++;
            } else {
                throw new IllegalArgumentException(
	    	        "This RedBlackTree already contains that value.");
            }
            return returnValue;
        }
    }

    /** 
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

        // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else { 
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
            // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }
    
     /** 
      * Recursive helper method to resolve any red black tree property
      * violations that occur after inserting a new red node to the tree.
      * @param redNode is the new node that is being added to this tree, 
      * and when called recursively is a red node that violates a tree property.
      */
     private void enforceRBTreePropertiesAfterInsert(Node<T> redNode) {
         // root property : root node is black - handled in insert method
         if (redNode.parent == null) {  
             return; 
         }
         // red property : red nodes must have black children
         Node<T> P = redNode.parent; // get redNode's parent to check color
         Node<T> S = null; // placeholder for Sibling node if needed
         // case 1: redNode's parent P is black, no fix necessary.
         
         // case 2: redNode and it's parent P are red
         if (!P.isBlack && !redNode.isBlack) {
             Node<T> G = P.parent; // since P is red, we know it isn't the root, so it has a parent
             if (P.isLeftChild()) { // get P's sibling in variable S
                 S = G.rightChild; 
             } else {
                 S = G.leftChild;
             }
             // case 2.1: P's sibling S is black (or null)
             if (S == null || S.isBlack) {
                 // remember order for recoloring difference 
                 boolean hetero = (redNode.isLeftChild() == P.isLeftChild());
                 // 1) rotation
                 rotate(redNode, P);
                   
                 // 2) recolor depending on which rotate was applied
                 if (hetero) {
                     P.isBlack = true;
                     G.isBlack = false;
                     redNode.isBlack = false;
                 } else {
                     redNode.isBlack = true;
                     P.isBlack = false;
                     G.isBlack = false;
                 }
             } else { // case 2.2: P's sibling S is red
                 // 1) set P's parent G to red
                 G.isBlack = false;
                 // 2) set P and S to black
                 P.isBlack = true;
                 S.isBlack = true;               
                 // 3) leave C red
                 redNode.isBlack = false;
                 enforceRBTreePropertiesAfterInsert(G);
             }
                             
         }
         return;
                
         // black property : every path from root to a NULL child 
         // must have the same number of black nodes. New nodes are red,
         // so the first calling of this method won't violate this property.

     }
    
    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // start by making sure parent is child's parent, if false then throw exception.
        if (child.parent.data != parent.data) {
            throw new IllegalArgumentException(
                "Provided nodes are not related in the form <child, parent>.");
        }
        
        Node<T> G = parent.parent;
        
        // Case 1: G - leftChild - leftChild - right rotate
        if (child.isLeftChild() && parent.isLeftChild()) {
            G.leftChild = parent.rightChild;
            if (G.leftChild != null) G.leftChild.parent = G;
            parent.parent = G.parent;
            if (G.parent == null) root = parent;
            else if (G.isLeftChild()) G.parent.leftChild = parent;
            else G.parent.rightChild = parent;
            G.parent = parent;
            parent.rightChild = G;
        }
        
        // Case 2: G - rightChild - rightChild - left rotate
        else if (!child.isLeftChild() && !parent.isLeftChild()) {
            G.rightChild = parent.leftChild;
            if (G.rightChild != null) G.rightChild.parent = G;
            parent.parent = G.parent;
            if (G.parent == null) root = parent;
            else if (G.isLeftChild()) G.parent.leftChild = parent;
            else G.parent.rightChild = parent;
            G.parent = parent;
            parent.leftChild = G;
        }
        
        // Case 3: G - leftChild - rightChild - left-right rotate
        else if (!child.isLeftChild() && parent.isLeftChild()) {
            G.leftChild = child;
            child.parent = G;
            parent.rightChild = child.leftChild;
            if (parent.rightChild != null) parent.rightChild.parent = parent;
            child.leftChild = parent;
            parent.parent = child;
            rotate(parent, child);
        }
        
        // Case 4: G - rightChild - leftChild - right-left rotate
        else if (child.isLeftChild() && !parent.isLeftChild()) {
            G.rightChild = child;
            child.parent = G;
            parent.leftChild = child.rightChild;
            if (parent.leftChild != null) parent.leftChild.parent = parent;
            child.rightChild = parent;
            parent.parent = child;
            rotate(parent, child);
        }
        
    }


    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in until we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }

            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements / steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()) );
            }
            
        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    @Override
    public String toString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

}
