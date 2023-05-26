package log;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class LimitedBlockingDeque<E> {
    private final BlockingDeque<E> deque;
    private final int maxSize;
    private int numberMessage;

    public LimitedBlockingDeque(int maxSize) {
        this.maxSize = maxSize;
        this.deque = new LinkedBlockingDeque<>(maxSize);
        this.numberMessage = 0;
    }

    public void add(E e) {
        if (deque.size() == maxSize) {
            deque.removeFirst();
        }
        deque.addLast(e);
        numberMessage++;
    }

    public int getNumberMessage() {
        return numberMessage;
    }

    public E getFirst() {
        return deque.getFirst();
    }

    public E getLast() {
        return deque.getLast();
    }


    public int size() {
        return deque.size();
    }

    public Iterable<E> getSegment(int startIndex, int endIndex) {
        Object[] array = deque.toArray(new Object[0]);
        return new Iterable<E>() {
            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {
                    private int currentIndex = startIndex;

                    @Override
                    public boolean hasNext() {
                        return currentIndex <= endIndex && currentIndex < array.length;
                    }

                    @Override
                    public E next() {
                        if (currentIndex > endIndex || currentIndex >= array.length) {
                            throw new NoSuchElementException();
                        }
                        return (E) array[currentIndex++];
                    }
                };
            }
        };
    }

    public Iterator<E> iterator() {
        return deque.iterator();
    }
}
