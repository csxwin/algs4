public class Stack<item>{
    private Node top;
    private class Node {
        item num;
        Node next;
    }
    public void push(item N) {
        Node temp = new Node();
        temp.num = N;
        temp.next = top;
        top = temp;
    }
    public item pop() {
        item topElem = top.num;
        top = top.next;
        return topElem;
    }
    public static void main(String[] args)
    {
        Stack<Integer> s = new Stack<Integer>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        s.push(5);
        
        System.out.printf("%d",s.pop());
        System.out.printf("%d",s.pop());
        System.out.printf("%d",s.pop());
        System.out.printf("%d",s.pop());
        System.out.printf("%d",s.pop());
        
        Stack<String> s2 = new Stack<String>();
        s2.push("abc");
        s2.push("def");
        s2.push("ghi");
        s2.push("jkl");
        s2.push("mno");
        
        System.out.printf("%s",s2.pop());
        System.out.printf("%s",s2.pop());
        System.out.printf("%s",s2.pop());
        System.out.printf("%s",s2.pop());
        System.out.printf("%s",s2.pop());
    }
}
        
        
        