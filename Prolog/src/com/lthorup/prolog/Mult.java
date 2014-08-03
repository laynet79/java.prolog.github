package com.lthorup.prolog;

public class Mult extends Arithmetic {

	public Mult(Term a, Term b) {
		super(Symbol.mult, a, b);
	}
	
	@Override
	public double op(double a, double b) { return a * b; }

}
