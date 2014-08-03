package com.lthorup.prolog;


public class Is extends Clause {

	public Is(Term var, Term value) {
		super(Symbol.is, null);
		addTerm(var);
		addTerm(value);
	}

	@Override
	public boolean isPrimative() { return true; }

	@Override
	public Binding unify(Binding env)
	{
		if (! (terms.get(0) instanceof Variable))
			return null;
		try {
			double value = terms.get(1).eval(env);
			Number num = new Number(value);
			return Binding.bind((Variable)(terms.get(0)), num, env);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public String toString(Binding env) {
		Term var = terms.get(0);
		Term value = terms.get(1);
		return String.format("%s is %s", var.toString(env), value.toString(env));
	}
}
