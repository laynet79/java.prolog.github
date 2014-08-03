package com.lthorup.prolog;

public class GreaterThan extends Logic {

	public GreaterThan(Term a, Term b) {
		super(Symbol.lt, a, b);
	}
	
	@Override
	public boolean op(double a, double b) { return a > b; }
}
