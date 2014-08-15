package com.lthorup.prolog;

public class Neg extends Arithmetic {

	public Neg(Term a) {
		super(Symbol.minus, a);
	}
	
	@Override
	public double op(double a) { return -a; }
}
