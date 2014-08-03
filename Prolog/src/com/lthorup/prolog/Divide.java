package com.lthorup.prolog;

public class Divide extends Arithmetic {

	public Divide(Term a, Term b) {
		super(Symbol.div, a, b);
	}
	
	@Override
	public double op(double a, double b) { return a / b; }
}
