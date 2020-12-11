Name: Cengiz Ozel

# InfixCalculator
Using my own stack and queue implementations, this program program takes an input file consisting of several lines of infix notation mathematical calculations, convert them to postfix notation, and evaluate them.
  
Source code files (The .java files inside of InfixCalculator\src folder):  

Algorithm - After taking the necessary arguments, with the help of other classes, this will do the shunting-yard algorithm in order to get the postfix expression and get the answer.
Calculate - During the postfix evaluation in Algorithm.java if the token is an operator/logical and there already at least two numbers on the stack, this source code will take the necessary input and return the answer which will be added back to the stack (except '!' requires only one number in the stack).
InfixCalculator - This is where the whole program starts running, it takes the infix expressions as input with the use of ReadText.java, and it is sent to Algorithm.java to start applying the algorithm.
IsOperatorLogical - This takes a token as an argument and decides if it is an operator, a logical, or parenthesis. This is used when determining if there might be a syntax error caused my having an expression such as "3*/5" by comparing the current token with the previous one.
Priority - This is used when an operator/logical is being added to the stack. It is needed to know the priority, so the stack can be popped if necessary before pushing anything new.
ReadText - This converts the input text file into an array of strings where each string is a line of infix expression.
UseQueue - This is a queue using linked lists. This is created, so I could do queue operations without the use of the Java library.
UseStack - This is a stack using linked lists. This is created, so I could do stack operations without the use of the Java library.
WriteText - In the end of Algorithm.java, we get an array of all the answers. WriteText prints each index of answer to a corresponding line of the output text file.


How It Runs

In order to run this program, open \InfixCalculator\bin, and run CMD from there. An easy way for that (in Windows 10) is to click on the address bar on the left of the search bar in File Explorer, type "cmd" and hit enter.
In CMD, type: java InfixCalculator infix_expr_short.txt my_eval.txt

The first argument will be taken as the text file that has all the infix expressions. After each line gets converted into their postfix expression and gets evaluated to find the answer, they are printed on the text file provided by the user on the second argument.

For convenience, this program can be run with only one argument as well. If the user enters: "java InfixCalculator infix_expr_short.txt" in CMD, the first argument will be taken as the input text file as usual. However, the output file will be automatically created by the program, named "my_eval.txt" if it doesn't exist already, and it will be written on it. This way, the user does not have to write a second argument every time.

After InfixCalculator is run on CMD, the program will use ReadText to create an array of all the infix operations. This will be sent to Algorithm. In Algorithm, we first check if the line is empty, so the program doesn't have to deal with an stack underflow problem. After that, the program removes all the spaces from the infix expression, so that (1 + 3) becomes (1+3). This saves time by not having to figure what a 'space token' would mean in every time. Next thing it does is counting the number of '(' and ')'s in order to make sure the user did not enter something like ((1 + 5). Any error reasons will be written in OUTPUT.txt, and it will say "ERROR" for the current line in the output text file where the answers are stored. Once the line passes through error tests like these, the program starts working on the infix expression. If the token is an operand, it is added to the queue. If it is an operator/logical, we pop any tokens in the stack with a higher or equal priority, then the current token is pushed onto the stack. This is done with the help of Priority.java. Once this process is complete. OUTPUT.txt will show the final stack and queue. Anything left on the stack is popped and added to the queue. This is the postfix expression and will also be shown in OUTPUT.txt. The console used to say everything it does including adding and removing things to/from the stack and queues for debugging purposes, but the TA said this was not needed for the OUTPUT.txt. So I removed that part to make the output more brief and easy to read.

Once the program has the postfix expression, Algorithm leaves a big for loop in order to start evaluating the expression. The program dequeues a token. If it is a number, it is added to stack; if it is an operator/logical, the top value of the stack is added to the right, the next top value is added to the left, and the operator/logical in the middle in Calculate.java. The answer is returned and pushed back to the stack. This is repeated until there is only one number left in the stack. That is the answer and will be added to the array of answers. Then Algorithm repeats the bigger for loop it was in for the next line. Once every line is calculated, the answer array is sent to WriteText where each index is printed to a new line in the output text. Once the program is terminated, the user can check everything that happened in OUTPUT.txt since it was cut and pasted from the command line.


Some Other Features

 - Supports the exponentiation [^] and modulo [%] operators.
 - The ability to choose between entering one or two arguments in CMD.
 - Error Handling (in OUTPUT.txt)
    * Empty Line:  Empty line.
    * (2+3)):      Too many closing parentheses.
    * ((2+3):      Too many opening parentheses.
    * (2*-5):      Two operators/logicals cannot be next to each other.
    * ()2/4)(:     Parenthesis are not closed properly.
    * (5/0):       Cannot divide by zero.
    * (!2):        Logical expressions can only be 1 (true) or 0 (false).
    * (2 & 3):     Logical expressions can only be 1 (true) or 0 (false).
    * (2 | 3):     Logical expressions can only be 1 (true) or 0 (false).
    * (one + two): Cannot have letters.

The files described below can be found in InfixCalculator\bin

Extra Test Cases         (extra_test_cases.txt)
Expected Results         (expected_results.txt)
Expected Results OUTPUT  (expected_results OUTPUT.txt)
