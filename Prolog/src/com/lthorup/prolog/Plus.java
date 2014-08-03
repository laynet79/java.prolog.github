package com.lthorup.prolog;

public class Plus extends Arithmetic {

	public Plus(Term a, Term b) {
		super(Symbol.plus, a, b);
	}
	
	@Override
	public double op(double a, double b) { return a + b; }

}
