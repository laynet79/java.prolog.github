package com.lthorup.prolog;

public class Arithmetic extends Clause {

	public Arithmetic(Symbol op, Term left, Term right) {
		super(op, null);
		addTerm(left);
		addTerm(right);
	}
	
	public static Arithmetic create(Symbol op, Term left, Term right) {
		if (op == Symbol.plus)
			return new Plus(left,right);
		if (op == Symbol.minus)
			return new Minus(left,right);
		if (op == Symbol.mult)
			return new Mult(left,right);
		if (op == Symbol.div)
			return new Divide(left,right);
		else //(op == Symbol.mod)
			return new Mod(left,right);
	}
	
	public double eval(Binding env) throws Exception
	{
		double a = terms.get(0).eval(env);
		double b = terms.get(1).eval(env);
		return op(a, b);
	}
	
	protected double op(double a, double b) { return 0; }
	
	@Override
	public String toString() { return toString(null); }
	@Override
	public String toString(Binding env) {
		Term left = terms.get(0);
		Term right = terms.get(1);
		String leftString = (left instanceof Arithmetic) ? String.format("(%s)", left.toString(env)) : left.toString(env);
		String rightString = (right instanceof Arithmetic) ? String.format("(%s)", right.toString(env)) : right.toString(env);
		return String.format("%s%s%s", leftString, functor.name(), rightString);
	}	
}
