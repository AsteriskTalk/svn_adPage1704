package util;

public class ASTKLogManager {
	public static String getMethodName_setLength(int endTarget) {
		if (endTarget <= 0) { endTarget = 1; }
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String methodNames = "";
		for (int i=1 ; i<endTarget+1 ; i++) {
			methodNames += stacks[i].getMethodName();
			if (i+1 < endTarget+1) { methodNames += " - "; }
		}
		return methodNames;
	}
	
	public static String getMethodName_now() {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String methodName = stacks[1].getMethodName(); // 0 : print => getMethodName_now
		return methodName;
	}
	
	public static String getMethodName_withClassName() {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String methodName = stacks[1].getMethodName();
		String className = stacks[1].getClassName();
		return className + "." + methodName;
	}
	
	public static String getMethodName_withClassName(int target) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		StackTraceElement stack = stacks[target+1];
		String methodName = stack.getMethodName();
		String className = stack.getClassName();
		return className + "." + methodName;
		
	}
	
	public static String getClassName_now() {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String className = stacks[1].getClassName();
		return className;
	}
	
}
