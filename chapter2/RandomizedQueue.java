import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;                           // size of Queue
    
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        a = (Item[]) new Object[2];
        n = 0;
    }
    public boolean isEmpty()                 // is the queue empty?
    {
        return n == 0;
    }
    public int size()                        // return the number of items on the queue
    {
        return n;
    }
    private void resize(int size)
    {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < n; i++)
        {
            temp[i] = a[i];
        }
        a = temp;
    }
    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new NullPointerException("");
        if (n == a.length) resize(2 * a.length);
        a[n] = item;
        n++;
    }
    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException("");
        int i = StdRandom.uniform(n); //get i between 0 and n
        Item item = a[i];
        a[i] = a[n - 1];
        a[n - 1] = null;
        n--;
        if (n < a.length / 4) {
            resize(a.length / 4);
        }
        return item;
    }
    public Item sample()                     // return (but do not remove) a random item
    {        
        if (isEmpty()) throw new NoSuchElementException("");
        int i = StdRandom.uniform(n);
        return a[i];
    }       
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueInterator();
    }
    private class RandomizedQueueInterator implements Iterator<Item> {
        private int i = n;
        public boolean hasNext() { return i > 0; }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("");
            int m = StdRandom.uniform(i);
            Item item = a[m];
            a[m] = a[i-1];
            a[i-1] = item;
            i--;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove is unsupported");
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++)
        {
            randomQueue.enqueue(i);
        }
        System.out.println("Below is iteration");
        for (Integer num : randomQueue)
        {
            System.out.printf("%d", num);
//            for (Integer num2 : randomQueue)
//            {
//                System.out.printf("%d", num2);
//            }
            System.out.println("");
        }
        System.out.println("Below Test Sample function"); 
        int i = 5;
        while (i-- > 0) {
            System.out.printf("%d\n", randomQueue.sample());
        }
        
        System.out.println("Below Test dequeue function"); 
        i = 5;
        while (i-- > 0)
        {
            System.out.printf("%d\n", randomQueue.dequeue());
        }
        System.out.println("After several dequeue, below are leftover"); 
        for (Integer num4 : randomQueue)
        {
            System.out.printf("%d\n", num4);
        }
        System.out.println("Continue Dequeue");
        i = 3;
        while (i-- > 0)
        {
            System.out.printf("%d\n", randomQueue.dequeue());
        }
        System.out.println("After all dequeue, nothing left");
        for (Integer num : randomQueue)
        {
            System.out.printf("%d\n", num);
        }
        System.out.println("");
    }
}
