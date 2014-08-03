package com.lthorup.prolog;

public class Mod extends Arithmetic {

	public Mod(Term a, Term b) {
		super(Symbol.mod, a, b);
	}
	
	@Override
	public double op(double a, double b) { return a % b; }
}
