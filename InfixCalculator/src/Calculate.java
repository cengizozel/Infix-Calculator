// Three arguments needed, the operator/logical, what's on it's left, and what's on it's right.
public class Calculate {
	
	public String calculate(double left, double right, char c) {
		
		// After having char c, we can easily determine which operation to do.
		double resultNum = 0;
		boolean logicalError = false;
		String result = null;
		
		if (c == '+') {
			resultNum = left + right;
		}
		
		else if (c == '-') {
			resultNum = left - right;
		}
		
		else if (c == '*') {
			resultNum = left * right;
		}
		
		else if (c == '/') {
			resultNum = left / right;
		}
		
		else if (c == '<') {
			if (left < right) {
				resultNum = 1;
			}
			else if (left >= right) {
				resultNum = 0;
			}
		}
		
		else if (c == '>') {
			if (left > right) {
				resultNum = 1;
			}
			else if (left <= right) {
				resultNum = 0;
			}
		}
		
		else if (c == '=') {
			if (left == right) {
				resultNum = 1;
			}
			else if (left != right) {
				resultNum = 0;
			}
		}
		
		// Make sure to deal with possible syntax errors.
		else if (c == '&') {
			if (left == 1 && right == 1) {
				resultNum = 1;
			}
			else if (left == 0 || right == 0 || left == 1 || right == 1) {
				resultNum = 0;
			}
			else {
				logicalError = true;
			}
		}
		
		else if (c == '|') {
			if (left == 0 && right == 0) {
				resultNum = 0;
			}
			else if (left == 0 || right == 0 || left == 1 || right == 1) {
				resultNum = 1;
			}
			else {
				logicalError = true;
			}
		}
		
		else if (c == '!') {
			if (right == 1) {
				resultNum = 0;
			}
			else if (right == 0) {
				resultNum = 1;
			}
			else {
				logicalError = true;
			}
		}
		
		else if (c == '%') {
			resultNum = left % right;
		}
		
		else if (c == '^') {
			resultNum = Math.pow(left, right);
		}
		
		if (!logicalError) {
			result = Double.toString(resultNum);
		}
		else if (logicalError) {
			result = "logical error";
		}
		return result;
	}
}