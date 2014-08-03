package com.lthorup.prolog;


public class Unify extends Clause {

	public Unify(Term left, Term right) {
		super(Symbol.unify, null);
		addTerm(left);
		addTerm(right);
	}

	@Override
	public boolean isPrimative() { return true; }

	@Override
	public Binding unify(Binding env)
	{
		return terms.get(0).unify(terms.get(1),env);
	}
	
	@Override
	public String toString() {
		Variable var = (Variable)terms.get(0);
		Term value = terms.get(1);
		return String.format("%s = %s", var.toString(), value.toString());
	}
}
