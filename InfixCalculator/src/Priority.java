// Simply the priority of each token
// Numbers don't have a "priority" but the -1 output helps determining if the token is indeed a number.

public class Priority {
	
	int priorityNumber = 0;
	
	public int priority(char a) {
		if (a == '(') {
			priorityNumber = 0;
		}
		else if (a == '!') {
			priorityNumber = 1;
		}
		else if (a == '|') {
			priorityNumber = 2;
		}
		else if (a == '&') {
			priorityNumber = 3;
		}
		else if (a == '<' || a == '>' || a == '=') {
			priorityNumber = 4;
		}
		else if (a == '+' || a == '-') {
			priorityNumber = 5;
		}
		else if (a == '*' || a == '/' || a == '%') {
			priorityNumber = 6;
		}
		else if (a == '^') {
			priorityNumber = 7;
		}
		else {
			priorityNumber = -1;
		}
		
		return priorityNumber;
	}
}