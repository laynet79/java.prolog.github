package com.lthorup.prolog;

public class LessThan extends Logic {

	public LessThan(Term a, Term b) {
		super(Symbol.lt, a, b);
	}
	
	@Override
	public boolean op(double a, double b) { return a < b; }
}
