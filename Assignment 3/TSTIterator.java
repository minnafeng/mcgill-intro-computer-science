import java.util.Iterator;
import java.util.ArrayList;

class TSTIterator<T extends Comparable<T>> implements Iterator<T> {

    public ArrayList<T> list = new ArrayList<T>();
    int cur = 0;

    public TSTIterator(TST tst){
        inorder(tst.root);
    }

    public void inorder(TSTNode<T> root){
        if (root != null){
            if (root.left != null){
                inorder(root.left);
            }
            list.add(root.element);

            if (root.mid != null){
                inorder(root.mid);
            }

            if (root.right != null){
                inorder(root.right);
            }

        }
    }

    public ArrayList<T> getList(){
        return list;
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
     * would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return cur < list.size();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     *
     * @throws NoSuchElementException
     *         if the iteration has no more elements
     */
    @Override
    public T next() {
        return list.get(cur++);
    }
}