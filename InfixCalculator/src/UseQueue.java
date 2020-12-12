import static java.lang.System.exit;

class QueueNode {
	// key is the value inside of the queue
	// next is the reference of the key
	String key;
	QueueNode next;

    // This constructs a queue by creating the first key and reference.
	public QueueNode(String key) { 
		this.key = key;
		this.next = null;
	} 
} 

class Queue { 
	QueueNode front, rear;
	
	// This makes moving through each key in the queue easier by assigning appropriate references.
	public Queue() { 
		this.front = this.rear = null;
	}

	// This adds values to the queue
	void enqueue(String key) {
		QueueNode temp = new QueueNode(key);
		if (this.rear == null) { 
			this.front = this.rear = temp;
			return;
		}

		this.rear.next = temp;
		this.rear = temp;
	}
	
	// Checks if the queue is empty.
	public boolean isEmpty() {
        return this.front == null;
    }
	
	// Returns the value at the beginning of the queue.
	String peek;
	public String peek() {
        if (!isEmpty()) {
            peek = this.front.key;
        }
        else {
            return null;
        }
		return peek;
    } 
	
	// Removes the value from the beginning of the queue.
	void dequeue() {
		if (this.front == null) 
			return;

		@SuppressWarnings("unused")
		QueueNode temp = this.front;
		this.front = this.front.next;

		if (this.front == null) 
			this.rear = null;
	}
	
	// It returns what's in the queue.
	// We have to make sure it's not empty first.
	void display() {
		if (this.front == null) { 
            System.out.printf("\nStack Underflow");
            exit(1);
        } 
        else { 
        	QueueNode temp = this.front;
            while (temp != null) { 
            	if (temp.next != null) {
            		System.out.printf("%s -> ", temp.key);
            	}
            	else if (temp.next == null) {
            		System.out.printf("%s", temp.key);
            	}
  
                temp = temp.next;
            } 
        }
	}
}