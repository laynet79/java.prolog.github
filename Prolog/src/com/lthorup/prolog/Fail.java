package com.lthorup.prolog;

public class Fail extends Clause {

	public Fail() {
		super(Symbol.fail, null);
	}
	@Override
	public boolean isPrimative() { return true; }

	@Override
	public Binding unify(Binding env)
	{
		return null;
	}
	
}
