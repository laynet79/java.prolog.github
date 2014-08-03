package com.lthorup.prolog;

public class GreaterThanEq extends Logic {

	public GreaterThanEq(Term a, Term b) {
		super(Symbol.lt, a, b);
	}
	
	@Override
	public boolean op(double a, double b) { return a >= b; }
}
