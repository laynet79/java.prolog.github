package com.lthorup.prolog;


public class Variable extends Term {

	private Symbol symbol;
	private int level;
	
	public Variable(Symbol symbol, int level) {
		this.symbol = symbol;
		this.level = level;
	}
	
	public Symbol symbol() { return symbol; }
	public int level() { return level; }
	
	public boolean equals(Variable v) { return v.symbol == symbol && v.level == level; }
	
	@Override
	public Term copy(int level) {
		if (this.level == level)
			return this;
		return new Variable(symbol, level);
	}
	
	@Override
	public boolean hasVars() { return true; }
	
	@Override
	public Binding unify(Term other, Binding env) {
		Binding b = env.lookup(this);
		if (b != null)
			return other.unify(b.value(), env);
		return Binding.bind(this, other, env);
	}

	@Override
	public double eval(Binding env) throws Exception {
		Binding b = env.lookup(this);
		return b.value().eval(env);
	}

	@Override
	public String toString() { return toString(null); }
	@Override
	public String toString(Binding env) {
		if (env == null)
			return symbol.name();
		Binding b = env.lookup(this);
		if (b == null)
			return symbol.name();
		return b.value().toString(env);
	}
}
