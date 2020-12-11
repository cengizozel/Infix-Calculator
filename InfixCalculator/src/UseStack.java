import static java.lang.System.exit;

class StackNode<T> {
    private class Node {
    	
    	// data is the value inside of the stack
    	// link is the reference of the data
        T data;
        Node link;
    } 
    
    // top is the data on top
    // it helps with reaching other data variables using the following chain of references.
    Node top;
    
    // This constructs a stack by creating the first reference
    StackNode() {
        this.top = null;
    }
    
    
    // This means we add values on top of a vertical set of values.
    @SuppressWarnings("unused")
	public void push(T x) {
        Node temp = new Node();

        if (temp == null) {
            System.out.print("\nHeap Overflow");
            return;
        } 
        
        temp.data = x;
        temp.link = top;
        top = temp;
    }
  
    // If top of the stack is empty, then there is nothing else left to check. Thus, we say it must be empty.
    public boolean isEmpty() {
        return top == null;
    } 
  
    // It returns what's on top of the stack.
	public T peek() {
        if (!isEmpty()) {
            return top.data;
        }
        else {
            // Stack is empty
            return (T) null;
        } 
    } 
	
	// This removes the value on top of the stack.
    public void pop() {
        if (top == null) {
            System.out.print("\nStack Underflow");
            return;
        } 
  
        top = (top).link;
    } 
  
    // This prints every value in each data in a vertical order, accurately representing it's "stack" structure.
    // We have to make sure it's not empty first.
    public void display() {
        if (top == null) {
            exit(1);
        	System.out.printf("\nStack is empty, must've ended with a parenthesis.");
        } 
        else {
            Node temp = top;
            while (temp != null) {
                System.out.printf("%s\n", temp.data);
                temp = temp.link;
            } 
        } 
    } 
}