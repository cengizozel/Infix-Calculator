// Name: Cengiz Ozel

// This is where the algorithm does the work, it makes use of other methods I wrote in order to avoid repetition and confusion.
import java.io.File;

public class Algorithm {
	
	// String[] a is an array that hold a line of infix expression under each index.
	// File outputFile is saved for later, in order to print the answers on the output file.
	public void shuntingYard(String[] a, File outputFile) {
		
		// answerNumber keeps track of which index the code at.
		// arrayLength determines where the code should stop.
		//String[] answers is an array of answers.
		int answerNumber = 0;
		int arrayLength = a.length;
		String[] answers = new String[arrayLength];
		
		// Initiate required classes
		StackNode<String> stack = new StackNode<String>();		
        Queue q = new Queue();
        Priority p = new Priority();
        Calculate calc = new Calculate();
        WriteText w = new WriteText();
        IsOperatorLogical isOpLog = new IsOperatorLogical();
		
        
        // This is mainly used for resetting purposes, so no numbers or operators are carried over from the previous line.
		String currentStrNumber = "";
		boolean priorityCheck = true;
		boolean parenthesisCheck = true;
		char currentToken = ' ';
		int lastOpLogIndex = -1;
		char lastOpLog = ' ';
		
		// outerloop and outloop help with leaving the correct for loop when an error is found.
		outerloop:
		for (int i = 0; i < a.length; i++) {			
			// Indicates what expression number we are working on.
			String workingOn = a[i];
			workingOn = workingOn.replaceAll("\\s+", "");
			System.out.println("EXPRESSION " + String.valueOf(i+1) + ": " + workingOn);
			
			// Makes sure the line is not empty. This avoids crashing the rest of the program as it would have caused a stack underflow.
			if (workingOn.length() == 0) {
				System.out.println("Syntax Error: Empty line.");
				answers[answerNumber] = "ERROR";
				answerNumber++;
				isFinished(answerNumber, answers);
				continue;
			}
			
			int j = 0;
			lastOpLog = ' ';
			
			outloop:
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
				
				
				priorityCheck = true;
				currentToken = workingOn.charAt(j);

				String currentStrToken = Character.toString(currentToken);
				
				// Is the token an operand?
				if (Character.isDigit(currentToken) || currentToken == '.') {
					currentStrNumber = currentStrNumber + currentToken;
					if (j == workingOn.length() - 1) {
						q.enqueue(currentStrNumber);
					}
				}
				
				// If it came to this point, it must be an operator or logical
				else {
					boolean numeric = true;

					// We must check if the number we had before is actually a number, just in case there was a parenthesis
					try {
			            Double.parseDouble(currentStrNumber);
			        }
					catch (NumberFormatException e) {
			            numeric = false;
			        }
					
					if (numeric) {
						q.enqueue(currentStrNumber);
						currentStrNumber = "";
					}
					
					boolean firstTime = true;
					
					// This helps with finding out if there are multiple operators/logicals written next each other. For example, 2**3 and 2%|6.
					// Is it actually an operator? which operator?
					if (isOpLog.isOperator(currentToken) || isOpLog.isLogical(currentToken)) {
						// Parentheses and logicals can sometimes be next to each other, for example: !((2+4)*6)+2. We must distinguish correct syntax from the wrong.
						
						if (lastOpLog == ' ' && firstTime) {
							// System.out.println(lastOpLog + " and " + currentToken);
							// Nothing to do, just skip these if else statements
						}
						
						// This if statement covers many other potential syntax errors.
						// lastOpLogIndex == j - 1 is a way to determine if two operators/logicals came back to back.
						else if ((lastOpLogIndex == j - 1) && ((!isOpLog.isParenthesis(currentToken) && !isOpLog.isParenthesis(lastOpLog)) &&
								(((isOpLog.isOperator(currentToken) == true) && (isOpLog.isOperator(lastOpLog) == true)) ||
								((isOpLog.isLogical(currentToken) == true) && (isOpLog.isLogical(lastOpLog) == true))))) {
							
							System.out.println("Syntax error: Two operators/logicals cannot be next to each other.");
							System.out.println(lastOpLog + " and " + currentToken);
							answers[answerNumber] = "ERROR";
							answerNumber++;
							isFinished(answerNumber, answers);
							continue outerloop;
						}

						lastOpLogIndex = j;
						lastOpLog = currentToken;
						parenthesisCheck = true;

						// Check if we will deal with parenthesis first.
						while (parenthesisCheck) {

							// CASE 1: Token is '('
							// just add '(' regardless of what's on the stack.
							if (currentToken == '(') {
								stack.push(currentStrToken);
								parenthesisCheck = false;
								continue outloop;
							}

							// CASE 2: Token is ')' THIS MEANS STACK IS NOT EMPTY SINCE THERE IS A '('
							// if we get ')', pop stack, enqueue it until peek is '(', then remove both parenthesis and move on to the next token.
							else if (currentToken == ')') {
								
								// Is peek '(' ?
								// If not, do what's said above and try again.
								try {
									if (stack.peek().charAt(0) != '(') {
										String tempToken = stack.peek();
										q.enqueue(tempToken);
										stack.pop();
									}
									
									// If we get to '(', then we can simply remove both and move on (stop checking).
									else if (stack.peek().charAt(0) == '(') {
										stack.pop();
										parenthesisCheck = false;
										continue outloop;
									}
								}
								// What if we have the same number of '(' and ')' but the user actually entered "())1 + 5(" ?
								catch (Exception e) {
									System.out.println("Syntax Error: Parenthesis are not closed properly.");
									answers[answerNumber] = "ERROR";
									answerNumber++;
									isFinished(answerNumber, answers);
									continue outerloop;
								}
							}

							// CASE 3: Token is neither, however, there might be a parenthesis at peek.
							// Check if there is ')' at peek, so we can still just add what we have.
							else {
								// But first, we need to make sure, the stack is not empty, so we don't get a null.
								if (!stack.isEmpty()) {
									// Is it at peek?
									if (stack.peek().charAt(0) == '(') {
										stack.push(currentStrToken);
										parenthesisCheck = false;
										continue outloop;
									}
									// If not, move on to the next step regarding priority.
									else {
										parenthesisCheck = false;
									}
								}
								
								// Stack is empty
								else {
									parenthesisCheck = false;
								}
							}
						}
						
						// Keep checking for priority of operator.
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
					}
				}
			}
			
			// This is needed so, the lastly assigned number does not carry over to the next infix calculation.
			currentStrNumber = "";
			
			System.out.println("----------------------");
			if (!stack.isEmpty()) {
				// This is the stack before the postfix expression
				System.out.println("STACK:");
				stack.display();
			}
			else {
				// Stack is empty, must've ended with a parenthesis.
			}
			// This is the queue before the postfix expression
			System.out.println("QUEUE:");
			q.display();
			
			
			String tempStack = null;
			
			while (!stack.isEmpty()) {
				tempStack = stack.peek();
				stack.pop();
				q.enqueue(tempStack);
			}
			// Prints the postfix expressions which, soon, will be evaluated.
			System.out.println("\nPOSTFIX EXPRESSION:");
			q.display();
			System.out.println("\n----------------------");
			String peekQueue = null;
			double right = 0;
			double left = 0;

			
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
			
			// Format the outcome the way our sample postfix_eval_short.txt file was formatted.
			double answerDouble = Double.valueOf(stack.peek());
			String answerString = String.format("%.2f", answerDouble);
			System.out.println("ANSWER: " + answerString);
			
			
			// Lastly, we can dequeue and pop now.
			while (!q.isEmpty()) {
				q.dequeue();
			}
			while (!stack.isEmpty()) {
				stack.pop();
			}
			
			answers[answerNumber] = answerString;
			answerNumber++;
			
			isFinished(answerNumber, answers);
			
		}
		// outputFile is ready.
		w.writeFile(answers, outputFile);
	}
	
	// Determines if there are other lines of expressions to come or if every line has been covered.
	public void isFinished(int answerNumber, String[] answers) {
		if (answerNumber != answers.length) {
			System.out.println("Going again...\n\n");
		}
		else {
			System.out.println("FINISHED!");
		}
	}
}
