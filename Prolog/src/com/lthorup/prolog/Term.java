package com.lthorup.prolog;


public class Term {

	public Term copy(int level) { return this; }
	
	public Binding unify(Term other, Binding env) { return null; }
	
	public double eval(Binding env) throws Exception { throw new Exception("eval on non-expression"); }
	
	public boolean hasVars() { return false; }
	
	public String toString(Binding env) { return ""; }
}
