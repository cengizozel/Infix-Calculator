# Infix Calculator

Using my own stack and queue implementations, this program takes an input file consisting of several lines of infix notation mathematical calculations, convert them to postfix notation, and evaluate them.  

## Demo

### Running and getting the output file with results
![github-infix](https://user-images.githubusercontent.com/60388555/104799336-50614a00-579c-11eb-8809-bd247b7ff1b7.png)

### Secondary output file with more in-depth explanation with data structures
![4](https://user-images.githubusercontent.com/60388555/104799344-6d961880-579c-11eb-86a9-f7fadd4569c1.PNG)

## File Descriptions  
  
- **Algorithm:** After taking the necessary arguments, with the help of other classes, this will do the shunting-yard algorithm in order to get the postfix expression and get the answer.  
- **Calculate:** During the postfix evaluation in Algorithm.java if the token is an operator/logical and there already at least two numbers on the stack, this source code will take the necessary input and return the answer which will be added back to the stack (except '!' requires only one number in the stack).  
- **InfixCalculator:** This is where the whole program starts running, it takes the infix expressions as input with the use of ReadText.java, and it is sent to Algorithm.java to start applying the algorithm.  
- **IsOperatorLogical:** This takes a token as an argument and decides if it is an operator, a logical, or parenthesis. This is used when determining if there might be a syntax error caused my having an expression such as "3*/5" by comparing the current token with the previous one.  
- **Priority:** This is used when an operator/logical is being added to the stack. It is needed to know the priority, so the stack can be popped if necessary before pushing anything new.  
- **ReadText:** This converts the input text file into an array of strings where each string is a line of infix expression.  
- **UseQueue:** This is a queue using linked lists. This is created, so I could do queue operations without the use of the Java library.  
- **UseStack:** This is a stack using linked lists. This is created, so I could do stack operations without the use of the Java library.  
- **WriteText:** In the end of Algorithm.java, we get an array of all the answers. WriteText prints each index of answer to a corresponding line of the output text file.  


## Installation and Usage

- Download
[project](https://github.com/cengizozel/Infix-Calculator/archive/main.zip).
- Open \InfixCalculator\bin, and run CMD from there.
- In CMD, enter the command below.

```batch
java InfixCalculator infix_expr_short.txt my_eval.txt
```

- The first argument will be taken as the text file that has all the infix expressions. After each line gets converted into their postfix expression and gets evaluated to find the answer, they are printed on the text file provided by the user on the second argument.  
  
- For convenience, this program can be run with only one argument as well. If the user enters the command below in CMD, the first argument will be taken as the input text file as usual.
```
java InfixCalculator infix_expr_short.txt
```
- However, the output file will be automatically created by the program, named "my_eval.txt" if it doesn't exist already, and it will be written on it. This way, the user does not have to write a second argument every time.  
  
- After InfixCalculator is run on CMD, the program will use ReadText to create an array of all the infix operations. This will be sent to Algorithm. In Algorithm, we first check if the line is empty, so the program doesn't have to deal with an stack underflow problem. After that, the program removes all the spaces from the infix expression, so that (1 + 3) becomes (1+3). This saves time by not having to figure what a 'space token' would mean in every run.
```java
if (workingOn.length() == 0) {
	System.out.println("Syntax Error: Empty line.");
	answers[answerNumber] = "ERROR";
	answerNumber++;
	isFinished(answerNumber, answers);
	continue;
}
```
- Next thing it does is counting the number of '(' and ')'s in order to make sure the user did not enter something like ((1 + 5). Any error reasons will be written in OUTPUT.txt, and it will say "ERROR" for the current line in the output text file where the answers are stored.
```java
for (j = 0; j < workingOn.length(); j++) {
	// Counts the number of each parenthesis in order to avoid dealing with this later on.
	int openPar = 0;
	int closedPar = 0;

	for (int m = 0; m < workingOn.length(); m++) {
		currentToken = workingOn.charAt(m);

		if (currentToken == '(') {
			openPar++;
		}
		else if (currentToken == ')') {
			closedPar++;
		}
		else if ((currentToken >= 'a' && currentToken <= 'z') || (currentToken >= 'A' && currentToken <= 'Z')) {
			System.out.println("Syntax Error: Cannot have letters.");
			answers[answerNumber] = "ERROR";
			answerNumber++;
			isFinished(answerNumber, answers);
			continue outerloop;
		}
	}
	// It prints an error message and moves on to the next line.
	if (openPar < closedPar) {
		System.out.println("Syntax Error: Too many closing parentheses.");
		answers[answerNumber] = "ERROR";
		answerNumber++;
		isFinished(answerNumber, answers);
		continue outerloop;
	}
	else if (openPar > closedPar) {
		System.out.println("Syntax Error: Too many opening parentheses.");
		answers[answerNumber] = "ERROR";
		answerNumber++;
		isFinished(answerNumber, answers);
		continue outerloop;
	}
```
- Once the line passes through error tests like these, the program starts working on the infix expression. If the token is an operand, it is added to the queue. If it is an operator/logical, we pop any tokens in the stack with a higher or equal priority, then the current token is pushed onto the stack. This is done with the help of Priority.java.
```java
while (priorityCheck == true && parenthesisCheck == false) {
	// If not empty, check priority first
	if (!stack.isEmpty()) {

		// If new operator is lower or same (in priority), you can't add, so keep swapping until it's enough.
		// Don't do priorityCheck false yet because we might need to do it again.
		if (p.priority(currentToken) <= p.priority(stack.peek().charAt(0))) {
			q.enqueue(stack.peek());
			stack.pop();
		}

		// If it is higher, then just push it to the stack and move on.
		// This will happen if previous operation has been done enough as well.
		else if (p.priority(currentToken) > p.priority(stack.peek().charAt(0))) {
			stack.push(currentStrToken);
			priorityCheck = false;
		}
	}
	//if stack was empty all along, just add :)
	else if (stack.isEmpty()) {
		stack.push(currentStrToken);
		priorityCheck = false;
	}
}
```
- Once this process is complete. OUTPUT.txt will show the final stack and queue. Anything left on the stack is popped and added to the queue. This is the postfix expression and will also be shown in OUTPUT.txt.  
  
- Once the program has the postfix expression, Algorithm leaves a big for loop in order to start evaluating the expression. The program dequeues a token. If it is a number, it is added to stack; if it is an operator/logical, the top value of the stack is added to the right, the next top value is added to the left, and the operator/logical in the middle in Calculate.java.
```java
while (!q.isEmpty()) {
	// Check if it's a number. If so, simply push it to the stack. I set numbers' "priority" to be -1.
	if (p.priority(q.peek().charAt(0)) == -1) {
		peekQueue = q.peek();
		stack.push(peekQueue);
		q.dequeue();
	}
	// It's an operator/logical
	else {					
		right = Double.valueOf(stack.peek());					
		stack.pop();
		if (!stack.isEmpty()) {
			left = Double.valueOf(stack.peek());
			stack.pop();
		}
		// Special case for '!' since it does not need a number on it's left in order to be evaluated.
		else if (q.peek().charAt(0) == '!') {
			//System.out.println("!");
		}

		String result = null;

		char operand = q.peek().charAt(0);
		q.dequeue();
		if (right == 0 && operand == '/') {
			System.out.println("Syntax error: Cannot divide by zero.");
			answers[answerNumber] = "ERROR";
			while (!q.isEmpty()) {
				q.dequeue();
			}
			while (!stack.isEmpty()) {
				stack.pop();
			}
			answerNumber++;
			isFinished(answerNumber, answers);
			continue outerloop;
		}
		else {
			// Syntax error for expressions such as !3
			result = calc.calculate(left, right, operand);
			if (result == "logical error") {
				System.out.println("Syntax error: Logical expressions can only be 1 (true) or 0 (false).");
				answers[answerNumber] = "ERROR";
				answerNumber++;
				isFinished(answerNumber, answers);
				continue outerloop;
			}
		}
		stack.push(result);
	}
}
```
- The answer is returned and pushed back to the stack. This is repeated until there is only one number left in the stack. That is the answer and will be added to the array of answers. Then Algorithm repeats the bigger for loop it was in for the next line.
- Once every line is calculated, the answer array is sent to WriteText where each index is printed to a new line in the output text. Once the program is terminated, the user can check everything that happened in OUTPUT.txt since it was cut and pasted from the command line.  
  
  
## Features  

 - Supports the exponentiation [^] and modulo [%] operators.  
 - The ability to choose between entering one or two arguments in CMD.  
 - Error Handling (in OUTPUT.txt)  
    - **Empty Line:**  Empty line.  
    - **(2+3)):**      Too many closing parentheses.  
    - **((2+3):**      Too many opening parentheses.  
    - __(2*-5):__      Two operators/logicals cannot be next to each other.  
    - **()2/4)(:**     Parenthesis are not closed properly.  
    - **(5/0):**       Cannot divide by zero.  
    - **(!2):**        Logical expressions can only be 1 (true) or 0 (false).  
    - **(2 & 3):**     Logical expressions can only be 1 (true) or 0 (false).  
    - **(2 | 3):**     Logical expressions can only be 1 (true) or 0 (false).  
    - **(one + two):** Cannot have letters.  
  
The files described below can be found in InfixCalculator\bin  
  
Extra Test Cases         (extra_test_cases.txt)  
Expected Results         (expected_results.txt)  
Expected Results OUTPUT  (expected_results OUTPUT.txt)  
