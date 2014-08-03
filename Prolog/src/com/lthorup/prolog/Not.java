package com.lthorup.prolog;

public class Not extends Clause {

	public Not(Term term) {
		super(Symbol.unify, null);
		addTerm(term);
	}
	
	@Override
	public String toString(Binding env) {
		Term term = terms.get(0);
		return String.format("not %s", term.toString(env));
	}
}
