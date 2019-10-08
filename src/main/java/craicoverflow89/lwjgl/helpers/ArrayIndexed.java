package craicoverflow89.lwjgl.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class ArrayIndexed<T> implements Iterable<Pair<T, Integer>> {

    private final ArrayList<T> data;

    public ArrayIndexed(ArrayList<T> data) {
        this.data = data;
    }

    public ArrayIndexed(T[] data) {

        // Create List
        this.data = new ArrayList();

        // Iterate Data
        for(int x = 0; x < data.length; x ++) this.data.add(data[x]);
    }

    @Override
    public Iterator<Pair<T, Integer>> iterator() {
        return new ArrayIndexedIterator();
    }

    final class ArrayIndexedIterator implements Iterator<Pair<T, Integer>> {

        private int iteratorPos = 0;

        public boolean hasNext() {
            return iteratorPos < data.size();
        }

        public Pair<T, Integer> next() {

            // Nothing More
            if(iteratorPos == data.size()) throw new NoSuchElementException();

            // Next Pair
            Pair<T, Integer> pair = new Pair(data.get(iteratorPos), iteratorPos);

            // Increment Position
            iteratorPos ++;

            // Return Pair
            return pair;
        }

    }

}