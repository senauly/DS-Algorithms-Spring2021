public abstract class CompareTree{
    private static boolean isAVL = true;
    private static boolean isRBT = true;

    /**
     * Chech height difference
     * @param <E> data
     * @param bst bst
     * @param height to count
     * @return height
     */
    private static <E extends Comparable<E>> int checkHeightDifference(BinarySearchTree<E> bst, int height){
        if(bst == null || bst.root == null){
            return 0;
        }

        int leftHeight = checkHeightDifference(bst.getLeftSubtree(), height+1);
        int rightHeight = checkHeightDifference(bst.getRightSubtree(), height+1);

        if(Math.abs(leftHeight - rightHeight) > 2) isAVL = false;
        if(leftHeight < rightHeight) return rightHeight+1;
        else return leftHeight+1;
    }

    /**
     * Check if can be avl.
     * @param <E> data
     * @param bst bst
     * @return true if can
     */
    public static <E extends Comparable<E>> boolean canBeAVL(BinarySearchTree<E> bst){
        isAVL = true;
        checkHeightDifference(bst, 0);
        return(isAVL); 
    }

    /**
     * Check if rbt.
     * @param <E> data
     * @param bst bst
     * @return true if it is rbt
     */
    public static <E extends Comparable<E>> boolean isRedBlackTree(BinarySearchTree<E> bst){
        if(bst != null && bst.root != null && bst.isRed(bst.root)) return false;
        isRBT = true;
        countBlackNodes(bst, 0);
        return (isRBT);
    }

    /**
     * Count black nodes to check rbt.
     * @param <E> data
     * @param bst bst
     * @param count black nodes count
     * @return black nodes count
     */
    private static <E extends Comparable<E>> int countBlackNodes(BinarySearchTree<E> bst, int count){
        if(bst == null || bst.root == null){
            return 0;
        }

        if(!bst.isRed(bst.root)) count++;
        else{
            if(bst.getLeftSubtree() != null && bst.isRed(bst.getLeftSubtree().root)) isRBT = false;
            if(bst.getRightSubtree() != null && bst.isRed(bst.getRightSubtree().root)) isRBT = false;
        }
        
        int leftCount = countBlackNodes(bst.getLeftSubtree(), count);
        int rightCount = countBlackNodes(bst.getRightSubtree(), count);

        int returnVal;
        if(leftCount != rightCount) isRBT = false;
        if(leftCount < rightCount) returnVal = rightCount;
        else returnVal = leftCount;

        if(!bst.isRed(bst.root)) return(returnVal+1);
        else return(returnVal);
    }
    
    /**
     * Checks which type of tree is the given BST.
     * @param <E> BST data
     * @param bst BST to check
     * @return 1 if Red Black Tree, 0 if AVLTree, -1 if neither of them.
     */
    public static <E extends Comparable<E>> int whichTree(BinarySearchTree<E> bst){
        if(isRedBlackTree(bst)) return 1;
        if(!isRedBlackTree(bst) && canBeAVL(bst)) return 0;
        else return -1;
    }
}
