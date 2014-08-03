package com.lthorup.prolog;

public class NotEqual extends Logic {

	public NotEqual(Term a, Term b) {
		super(Symbol.lt, a, b);
	}
	
	@Override
	public boolean op(double a, double b) { return a != b; }

}
