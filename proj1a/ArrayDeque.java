public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int INIT_CAPACITY = 8;

    /** Create an empty array deque. */
    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private int plusOne(int index) {
        return (index + 1) % items.length;
    }
    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    private void resize() {
        if (size == items.length) {
            resizeHelper(items.length * 2);
        }
        if (size < items.length / 4 && items.length > 15) {
            resizeHelper(items.length / 2);
        }
    }
    private void resizeHelper(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int curr = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newArray[i] = items[curr];
            curr = plusOne(curr);
        }
        items = newArray;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /** Adds an item of type T to the front of the deque.
     *  Must not involve any looping or recursion.
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resize();
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = minusOne(nextFirst);
    }

    /** Adds an item of type T to the back of the deque.
     *  Must not involve any looping or recursion.
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize();
        }
        items[nextLast] = item;
        size += 1;
        nextLast = plusOne(nextLast);
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque.
     *  Must take constant time.
     */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        int curr = plusOne(nextFirst);
        while (curr != nextLast) {
            System.out.print(items[curr] + " ");
            curr = plusOne(curr);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     *  A single such operation must take “constant time”
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int first = plusOne(nextFirst);
        T firstItem = items[first];
        items[first] = null;
        nextFirst = first;
        size -= 1;
        resize();
        return firstItem;
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     *  A single such operation must take “constant time”
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int last = minusOne(nextLast);
        T lastItem = items[last];
        items[last] = null;
        nextLast = last;
        size -= 1;
        resize();
        return lastItem;
    }

    /** Gets the item at the given index.
     *  If no such item exists, returns null.
     *  Must not alter the deque!
     *  Must take constant time.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        index = (plusOne(nextFirst) + index) % items.length;
        return items[index];
    }


    /*
    public static void main(String[] args) {
        ArrayDeque<Integer> aq = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            aq.addLast(i);
        }
        aq.printDeque();
        for (int i = 0; i < 98; i++) {
            aq.removeFirst();
        }
        aq.printDeque();
        System.out.println(aq.get(0));
    }
    */

}
