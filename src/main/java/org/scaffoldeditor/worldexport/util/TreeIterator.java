package org.scaffoldeditor.worldexport.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An iterator that iterates over a tree-like structure in definition order.
 */
public class TreeIterator<T extends TreeNode<T>> implements Iterator<T> {

    /**
     * Construct a tree iterator.
     * @param root An iterator containing the children of the root node.
     */
    public TreeIterator(Iterator<T> root) {
        iterators.add(root);
    }
    
    private List<Iterator<T>> iterators = new ArrayList<>();

    @Override
    public boolean hasNext() {
        return iterators.get(0).hasNext() || iterators.get(iterators.size() - 1).hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Iterator<T> iterator = iterators.get(iterators.size() - 1);
        if (!iterator.hasNext()) {
            iterators.remove(iterators.size() - 1);
            return next(); // Recursively remove iterators until we find one with more items.
        }

        T node = iterator.next();
        if (node.hasChildren()) {
            iterators.add(node.getChildren());
        }
        return node;
    }
    
}
