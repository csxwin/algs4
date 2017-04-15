import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;
    private class Node
    {
        private Item elem;
        private Node next;
    }
    public Deque()                           // construct an empty deque
    {
        n = 0;
        first = null;
        last = null;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return n == 0;        
    }
    public int size()                        // return the number of items on the deque
    {
        return n;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new NullPointerException("");
        Node oldFirst = first;
        first = new Node();
        first.elem = item;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        n++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new NullPointerException("");
        Node oldLast = last;
        last = new Node();
        last.elem = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("");
        Item elem = first.elem;
        first = first.next;
        n--;
        return elem;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException("");   
        n--;
        Item elem = last.elem;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            Node temp = first;
            while (temp.next != last) temp = temp.next;
            temp.next = null;
            last = temp;
        }
        return elem;
    }
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>{
        private Node ptr = first;
        public boolean hasNext()
        {
            return ptr != null;
        }
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            Item val = ptr.elem;
            ptr = ptr.next;
            return val;    
        }
        public void remove() {
            throw new UnsupportedOperationException("remove is unsupported");
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<Integer> dequeInstance = new Deque<Integer>();
        dequeInstance.addFirst(1);
        dequeInstance.addFirst(2);
        dequeInstance.addFirst(3);
        dequeInstance.addFirst(4);
        
        dequeInstance.addLast(5);
        dequeInstance.addLast(6);
        dequeInstance.addLast(7);
        dequeInstance.addLast(8);
                
        Iterator<Integer> i = dequeInstance.iterator();
        while (i.hasNext())
        {
            System.out.printf("%d", i.next());
        }
        System.out.println("");
        
        System.out.printf("%d", dequeInstance.removeLast());
        System.out.printf("%d", dequeInstance.removeFirst());
        System.out.println("");
        
        Iterator<Integer> i2 = dequeInstance.iterator();
        while (i2.hasNext())
        {
            System.out.printf("%d", i2.next());
        }
        System.out.println("");
        
        for (Integer item : dequeInstance) {
            System.out.printf("%d", item);
            for (Integer s : dequeInstance) {
                System.out.printf("%d", s);
            }
            System.out.println("");
        }
        
    }
}
