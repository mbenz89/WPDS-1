package boomerang.jimple;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

import soot.SootMethod;
import soot.jimple.Stmt;
import wpds.interfaces.Location;

public class Statement implements Location {
	private static Statement epsilon;
	private Stmt delegate;
	private SootMethod method;
	private String rep;

	public Statement(Stmt delegate, SootMethod m) {
		this.delegate = delegate;
		this.method = m;
	}

	private Statement(String rep) {
		this.rep = rep;
		this.delegate = null;
	}

	public Optional<Stmt> getUnit() {
		if (delegate == null)
			return Optional.absent();
		return Optional.of(delegate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statement other = (Statement) obj;
		if (delegate == null) {
			if (other.delegate != null)
				return false;
		} else if (!delegate.equals(other.delegate))
			return false;
		return true;
	}

	public static Statement epsilon() {
		if (epsilon == null) {
			epsilon = new Statement("eps_s");
		}
		return epsilon;
	}

	@Override
	public String toString() {
		if (delegate == null) {
			return rep;
		}
		if(DEBUG)
			return method +" " + delegate;
		return "[" + Integer.toString(methodToInt(method)) + "]" + Integer.toString(stmtToInt(delegate));
	}

	private static boolean DEBUG = true;
	private static Map<SootMethod, Integer> methodToInteger = new HashMap<>();
	private static Map<Stmt, Integer> statementToInteger = new HashMap<>();

	public int stmtToInt(Stmt s) {
		if (!statementToInteger.containsKey(s)) {
			statementToInteger.put(s, statementToInteger.size());
		}
		return statementToInteger.get(s);
	}

	public int methodToInt(SootMethod method) {
		if (!methodToInteger.containsKey(method)) {
			methodToInteger.put(method, methodToInteger.size());
		}
		return methodToInteger.get(method);
	}

	public SootMethod getMethod() {
		return method;
	}
}