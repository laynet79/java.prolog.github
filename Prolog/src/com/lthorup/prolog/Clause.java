package com.lthorup.prolog;

import java.util.ArrayList;

public class Clause extends Term implements Cloneable {

	protected Symbol functor;
	protected ArrayList<Term> terms;
	protected boolean hasVars;
	
	public Clause(Symbol functor, ArrayList<Term> terms) {
		this.functor = functor;
		hasVars = false;
		if (terms == null)
			this.terms = new ArrayList<Term>();
		else {
			this.terms = terms;
			for (Term t : terms) {
				if (t.hasVars())
					hasVars = true;
			}
		}
	}
	
	public Symbol functor() { return functor; }
	public int arity() { return terms.size(); }
	
	public void addTerm(Term t) {
		if (t.hasVars())
			hasVars = true;
		terms.add(t);
	}
	
	public Clause Clone() {
        try {
            return (Clause) super.clone();
        } catch (CloneNotSupportedException e) {        
            e.printStackTrace();
            throw new RuntimeException();
        }
	}
	
	@Override
	public Term copy(int level) {
		if (! hasVars)
			return this;
		Clause n = Clone();
		n.terms = new ArrayList<Term>();
		for (Term t : terms)
			n.terms.add(t.copy(level));
		return n;
	}

	public boolean isPrimative() { return false; }
	
	@Override
	public Binding unify(Term other, Binding env) {
		if (other instanceof Variable)
			return other.unify(this,  env);
		if (! (other instanceof Clause))
			return null;
		Clause c = (Clause)other;
		if (functor != c.functor || terms.size() != c.terms.size())
			return null;
		for (int i = 0; i < terms.size(); i++) {
			env = terms.get(i).unify(c.terms.get(i), env);
			if (env == null)
				return null;
		}
		return env;
	}
	
	public Binding unify(Binding env) { return env; }
	
	@Override
	public boolean hasVars() {
		return hasVars;
	}
	
	@Override
	public String toString() { return toString(null); }
	@Override
	public String toString(Binding env) {
		if (terms.size() == 0)
			return functor.name();
		String s = functor.name() + "(";
		int cnt = 0;
		for (Term t : terms) {
			if (cnt > 0)
				s = s + ",";
			s = s + t.toString(env);
			cnt++;
		}
		s = s + ")";
		return s;
	}
}
