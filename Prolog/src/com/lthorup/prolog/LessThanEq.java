package com.lthorup.prolog;

public class LessThanEq extends Logic {

	public LessThanEq(Term a, Term b) {
		super(Symbol.lt, a, b);
	}
	
	@Override
	public boolean op(double a, double b) { return a <= b; }
}
