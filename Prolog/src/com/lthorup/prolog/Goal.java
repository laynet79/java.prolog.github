package com.lthorup.prolog;

public class Goal {

	private Clause clause;
	private Goal rest;
	
	public Goal(Clause clause, Goal rest) {
		this.clause = clause;
		this.rest = rest;
	}
	
	public Clause clause() { return clause; }
	public Goal rest() { return rest; }
	
	public Binding unify(Goal other, Binding env) {
		return clause.unify(other.clause, env);
	}
	
	public String toString() {
		String s = clause.toString();
		if (rest == null)
			return s;
		return s + "," + rest.toString();
	}
	
}
