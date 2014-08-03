package com.lthorup.prolog;

public class Equal extends Logic {

	public Equal(Term a, Term b) {
		super(Symbol.lt, a, b);
	}
	
	@Override
	public boolean op(double a, double b) { return a == b; }
}
