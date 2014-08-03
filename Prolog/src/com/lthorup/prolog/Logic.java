package com.lthorup.prolog;

public class Logic extends Clause {

	public Logic(Symbol op, Term left, Term right) {
		super(op, null);
		addTerm(left);
		addTerm(right);
	}
	
	public static Logic create(Symbol op, Term left, Term right) {
		if (op == Symbol.lt)
			return new LessThan(left,right);
		if (op == Symbol.lte)
			return new LessThanEq(left,right);
		if (op == Symbol.eq)
			return new Equal(left,right);
		if (op == Symbol.neq)
			return new NotEqual(left,right);
		if (op == Symbol.gte)
			return new GreaterThanEq(left,right);
		else //(op == Symbol.gt)
			return new GreaterThan(left,right);
	}
	
	@Override
	public boolean isPrimative() { return true; }

	@Override
	public Binding unify(Binding env)
	{
		try {
			double a = terms.get(0).eval(env);
			double b = terms.get(1).eval(env);
			if (op(a, b))
				return env;
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	protected boolean op(double a, double b) { return true; }
	
	@Override
	public String toString() { return toString(null); }
	@Override
	public String toString(Binding env) {
		Term left = terms.get(0);
		Term right = terms.get(1);
		return String.format("%s %s %s", left.toString(env), functor.name(), right.toString(env));
	}
}
