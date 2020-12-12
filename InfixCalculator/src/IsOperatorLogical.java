// Determines if token is an operation, a logical, or parenthesis

public class IsOperatorLogical {
	
	boolean resultOp;
	boolean resultLog;
	boolean resultPar;
	
	public boolean isOperator (char currentToken) {
		if (currentToken == '+' || currentToken == '-' || currentToken == '*' || currentToken == '/'
				|| currentToken == '(' || currentToken == ')') {
			resultOp = true;
		}
		else {
			resultOp = false;
		}
		return resultOp;
	}
	
	public boolean isLogical (char currentToken) {
		if (currentToken == '<' || currentToken == '>'
			|| currentToken == '^' || currentToken == '%'
			|| currentToken == '=' || currentToken == '&'
			|| currentToken == '|' || currentToken == '!') {
			resultLog = true;
		}
		else {
			resultLog = false;
		}
		return resultLog;
	}
	
	public boolean isParenthesis (char currentToken) {
		if (currentToken == '(' || currentToken == ')') {
			resultPar = true;
		}
		else {
			resultPar = false;
		}
		return resultPar;
	}
	
}