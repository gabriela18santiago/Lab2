package ciic4020.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements List<E> {

    private class Node {
        private E value;
        private Node next, prev;

        public Node(E value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public Node(E value) {
            this(value, null, null); // Delegate to other constructor
        }

        public Node() {
            this(null, null, null); // Delegate to other constructor
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void clear() {
            value = null;
            next = prev = null;
        }
    } // End of Node class


    private class ListIterator implements Iterator<E> {

        private Node nextNode;

        public ListIterator() {
            nextNode = header.getNext();
        }

        @Override
        public boolean hasNext() {
            return nextNode != trailer;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E val = nextNode.getValue();
                nextNode = nextNode.getNext();
                return val;
            }
            else
                throw new NoSuchElementException();
        }

    } // End of ListIterator class


    /* private fields */
    private Node header, trailer; // "dummy" nodes
    private int currentSize;


    public DoublyLinkedList() {
        header = new Node();
        trailer = new Node();
        header.setNext(trailer);
        trailer.setPrev(header);
        currentSize = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    @Override
    public void add(E obj) {
        Node newNode = new Node(obj);
        newNode.setPrev(trailer.prev);
        newNode.setNext(trailer);
        trailer.getPrev().setNext(newNode);
        trailer.setPrev(newNode);

        /* TODO With a Doubly Linked List (with header AND trailer), this is easy.
         * The new node must be inserted before the trailer, and that's it.
         * You could use a different constructor, or just add some statements below.
         */

        currentSize++;
    }

    @Override
    public void add(int index, E obj) {
        Node curNode, newNode;

		/* First confirm index is a valid position
		   We allow for index == size() and delegate to add(object). */
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        if (index == size())
            add(obj); // Use our "append" method
        else {
            // Get predecessor node (at position index - 1)
            curNode = get_node(index - 1);
			/* The new node must be inserted between curNode and curNode's next
			   Note that if index = 0, curNode will be header node */
            newNode = new Node(obj, curNode.getNext(), curNode);
            //Exercise 4
            curNode.setNext(newNode); //sets the current node next pointer to ne new node
            curNode.getNext().setPrev(newNode); //sets the current next node its previous to the new node

            // TODO For a DLL, what else needs to be done? Try a diagram; consider edge cases.

            currentSize++;
        }
    }

    @Override
    public boolean remove(E obj) {
        Node curNode = header;
        Node nextNode = curNode.getNext();

        // Traverse the list until we find the element or we reach the end
        while (nextNode != trailer && !nextNode.getValue().equals(obj)) {
            curNode = nextNode;
            nextNode = nextNode.getNext();
        }

        // Need to check if we found it
        if (nextNode != trailer) { // Found it!
            // If we have A <-> B <-> C, need to get to A <-> C
            curNode.setNext(nextNode.getNext());
            //Exercise 4
            nextNode.getNext().setPrev(curNode); //verify added
//            nextNode.setValue(null);
            // TODO For a DLL, what else needs to be done? See comment above for a hint.

            nextNode.clear(); // free up resources
            currentSize--;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean remove(int index) {
        Node rmNode;
        // TODO These variables could be helpful: Node prevNode, nextNode;
        // Feel free to declare and use them in any methods, but they're not required.

        // First confirm index is a valid position
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        // If we have A <-> B <-> C, need to get to A <-> C
        rmNode = get_node(index);
        if(index == 0){
            this.remove(rmNode.getValue());

        }else {
            rmNode = get_node(index); // Get the node that is to be removed
            rmNode.getPrev().setNext(rmNode.getNext()); //added
            rmNode.getNext().setPrev(rmNode.getNext()); //added

            // TODO For a DLL, what needs to be done?

            rmNode.clear();
            currentSize--;
        }
        return true;
    }

    /* Private method to return the node at position index */
    private Node get_node(int index) {
        Node curNode;

		/* First confirm index is a valid position
		   Allow -1 so that header node may be returned */
        if (index < -1 || index >= size())
            throw new IndexOutOfBoundsException();
        curNode = header;
        // Since first node is pos 0, let header be position -1
        for (int curPos = -1; curPos < index; curPos++)
            curNode = curNode.getNext();
        // Perhaps we could traverse backwards instead if index > size/2...
        return curNode;
    }

    @Override
    public int removeAll(E obj) {
        int counter = 0;
        Node curNode = header;
        Node nextNode = curNode.getNext();

        /* We used the following in ArrayList, and it would also work here,
         * but it would have running time of O(n^2).
         *
         * while (remove(obj))
         * 		counter++;
         */

        // Traverse the entire list
        while (nextNode != trailer) {
            if (nextNode.getValue().equals(obj)) {
                // Remove nextNode

                /* TODO For a DLL, what needs to be done?
                 * You can declare more Node variables if it helps make things clear.
                 */
                Node next = nextNode.getNext();
                curNode.setNext(next);
                next.setPrev(curNode);
                counter++;
				/* Node that was pointed to by nextNode no longer exists
				   so reset it such that it's still the node after curNode */
                nextNode = curNode.getNext();
            }
            else {
                curNode = nextNode;
                nextNode = nextNode.getNext();
            }
        }
        return counter;
    }

    @Override
    public E get(int index) {
        // get_node allows for index to be -1, but we don't want get to allow that
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        return get_node(index).getValue();
    }

    @Override
    public E set(int index, E obj) {
        // get_node allows for index to be -1, but we don't want set to allow that
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        Node theNode = get_node(index);
        E theValue = theNode.getValue();
        theNode.setValue(obj);
        return theValue;
    }

    @Override
    public E first() {
        return get(0);
    }

    @Override
    public E last() {
        return get(size()-1);
    }

    @Override
    public int firstIndex(E obj) {
        Node curNode = header.getNext();
        int curPos = 0;
        // Traverse the list until we find the element or we reach the end
        while (curNode != trailer && !curNode.getValue().equals(obj)) {
            curPos++;
            curNode = curNode.getNext();
        }
        if (curNode != trailer)
            return curPos;
        else
            return -1;
    }

    @Override
    public int lastIndex(E obj) {
        Node curNode = trailer.getPrev();
        int curPos = size() - 1;
        // Traverse the list (backwards) until we find the element or we reach the beginning
        while (curNode != header && !curNode.getValue().equals(obj)) {
            curPos--;
            curNode = curNode.getPrev();
        }
        return curPos; // Will be -1 if we reached the header
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(E obj) {
        return firstIndex(obj) != -1;
    }

    @Override
    public void clear() {
        // Avoid throwing an exception if the list is already empty
        while (size() > 0)
            remove(0);
    }
    //Exercise 3
    @Override
    public int replaceAll(E e, E f) {
        int totalRep =0;

        for(int i =0; i<this.size(); i ++) {
            if(this.get_node(i).getValue().equals(e)) {
                this.get_node(i).setValue(f);

                totalRep++;
            }
        }
        return totalRep;
    }
    //Exercise 3
    @Override
    public List<E> reverse() {
        List<E> newList = new DoublyLinkedList<>();

        if(this.isEmpty()) {
            return newList;
        }
        for(int i = this.currentSize-1; i>=0; i--) {
            newList.add(this.get(i));
        }

        return newList;
    }
}