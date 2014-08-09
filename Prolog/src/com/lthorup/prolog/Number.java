package com.lthorup.prolog;

public class Number extends Term {

	private double value;
	
	public Number(double value) {
		this.value = value;
	}
	
	public void negate() {
		value = -value;
	}
	
	public double value() { return value; }
	
	@Override
	public Binding unify(Term other, Binding env) {
		if (other instanceof Variable)
			return other.unify(this, env);
		if (! (other instanceof Number) || value != ((Number)other).value)
			return null;
		return env;
	}

	@Override
	public double eval(Binding env) throws Exception { return value; }
	
	@Override
	public String toString() { return toString(null); }
	@Override
	public String toString(Binding env) {
		if (((int)value - value) == 0)
			return String.format("%d",(int)value);
		else
			return String.valueOf(value); 
	}
}
