

class TSTNode<T extends Comparable<T>>{
    T element;     	            // The data in the node
    TSTNode<T>  left;   		// left child
    TSTNode<T>  mid;   		    // middle child
    TSTNode<T>  right;  		// right child

    TSTNode(T element){
        this.element = element;
    }

    TSTNode<T> findMax(){
        if (element == null) {
            return null;
        } else if (this.right == null) {
            return this;
        } else {
            return this.right.findMax();
        }
    }

    TSTNode<T> findMin(){
        if (element == null) {
            return null;
        } else if (this.left == null) {
            return this;
        } else {
            return this.left.findMin();
        }
    }

    int height(){
        if (element == null){ // tree doesn't exist
            return -1;
        }

        int leftHeight = -1,rightHeight = -1,midHeight = -1;

        if (this.left != null){
            leftHeight = this.left.height();
        }
        if (this.right != null){
            rightHeight = this.right.height();
        }
        if (this.mid != null){
            midHeight = this.mid.height();
        }

        int biggestHeight = Math.max(leftHeight,rightHeight);
        biggestHeight = Math.max(biggestHeight,midHeight);

        return biggestHeight +1;
    }
}