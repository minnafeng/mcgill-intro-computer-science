import java.util.ArrayList;
import java.util.Iterator;

public class TST<T extends Comparable<T>> implements Iterable<T>{
    // root node of the tree
    TSTNode<T> root;

    // constructor
    public TST() {
        this.root = null;
    }

    // TODO: implement the tree class here

    public TSTNode<T> _insert(TSTNode<T> root, T element){
        if (root == null){
            root = new TSTNode<T>(element);
        } else if ((element.compareTo(root.element) < 0)){
            root.left = _insert(root.left, element);
        } else if ((element.compareTo(root.element) > 0)){
            root.right = _insert(root.right, element);
        } else { // element == root
            root.mid = _insert(root.mid, element);
        }
        return root;
    }

    public void insert(T element){
        root = _insert(root,element);
    }

    public void remove(T element){ // do we have to account for root == null
        root = _remove(root, element);
    }

    public TSTNode<T> _remove(TSTNode<T> root, T element){
        if (root == null){
            return null;
        } else if (element.compareTo(root.element)<0) {
            root.left = _remove(root.left, element);
        } else if (element.compareTo(root.element)>0){
            root.right = _remove(root.right, element);
        } else { // element == root.element
            if (root.mid != null){ // if mid child
                root = root.mid;
            } else if (root.left == null && root.right == null){ // if leaf node
                root = null;
            } else if (root.left == null){ // right child
                root = root.right;
            } else if (root.right == null){ // right child
                root = root.left;
            } else { // right and left child
                root.element = root.left.findMax().element;
                root.left = _remove(root.left, root.element);
            }
        }
        return root;
    }

    public TSTNode<T> _contains(TSTNode<T> root, T element){
        if (root == null){
            return null;
        } else if ((element.compareTo(root.element) < 0)){
            return _contains(root.left, element);
        } else if ((element.compareTo(root.element) > 0)){
            return _contains(root.right, element);
        } else { // element == root
            return root;
        }
    }

    public boolean contains(T element){
        if (_contains(root,element) != null){
            return true;
        }
        return false;
    }

    public void rebalance(){
        //get sorted list from iterator
        TSTIterator<T> iter = new TSTIterator<T>(this);
        ArrayList<T> list = iter.list;

        // creating a new balanced tree
        TST<T> newTree = new TST<T>();
        partition(newTree, list);

        // reassign the root
        root = newTree.root;
    }

    // add your own helper methods if necessary
    public TST<T> partition(TST<T> tree, ArrayList<T> list){ // helper method
        int mid = list.size()/2;
        tree.insert(list.get(mid));
        if (list.size() <= 2){
            tree.insert(list.get(0));
            return tree;
        }
        ArrayList<T> sub1 = new ArrayList<T>(list.subList(0,mid));
        ArrayList<T> sub2 = new ArrayList<T>(list.subList(mid+1,list.size()));
        partition(tree, sub1);
        partition(tree, sub2);
        return tree;
    }

    /**
     * Calculate the height of the tree.
     * You need to implement the height() method in the TSTNode class.
     *
     * @return -1 if the tree is empty otherwise the height of the root node
     */
    public int height(){
        if (this.root == null)
            return -1;
        return this.root.height();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        // TODO: implement the iterator method here
        return new TSTIterator(this);
    }

    // --------------------PROVIDED METHODS--------------------
    // The code below is provided to you as a simple way to visualize the tree
    // This string representation of the tree mimics the 'tree' command in unix
    // with the first child being the left child, the second being the middle child, and the last being the right child.
    // The left child is connect by ~~, the middle child by -- and the right child by __.
    // e.g. consider the following tree
    //               5
    //            /  |  \
    //         2     5    9
    //                   /
    //                  8
    // the tree will be printed as
    // 5
    // |~~ 2
    // |   |~~ null
    // |   |-- null
    // |   |__ null
    // |-- 5
    // |   |~~ null
    // |   |-- null
    // |   |__ null
    // |__ 9
    //     |~~ 8
    //     |   |~~ null
    //     |   |-- null
    //     |   |__ null
    //     |-- null
    //     |__ null
    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "|   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "|   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    /**
     * Print out the tree as a list using an enhanced for loop.
     * Since the Iterator performs an inorder traversal, the printed list will also be inorder.
     */
    public void inorderPrintAsList(){
        String buffer = "[";
        for (T element: this) {
            buffer += element + ", ";
        }
        int len = buffer.length();
        if (len > 1)
            buffer = buffer.substring(0,len-2);
        buffer += "]";
        System.out.println(buffer);
    }

    public static void main(String[] args) {
        TST<Integer> tree = new TST<>();
        tree.insert(5);
        tree.insert(5);
        tree.insert(5);
        tree.insert(5);
        tree.insert(5);
        tree.insert(5);
        tree.insert(7);
        tree.insert(3);
        System.out.println(tree.toString());
    }
}

