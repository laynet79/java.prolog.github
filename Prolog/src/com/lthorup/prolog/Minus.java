package com.lthorup.prolog;

public class Minus extends Arithmetic {

	public Minus(Term a, Term b) {
		super(Symbol.minus, a, b);
	}
	
	@Override
	public double op(double a, double b) { return a - b; }
}
