package com.lthorup.prolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Database {

	Map<Symbol,ArrayList<Rule>> ruleMap;
	
	public Database() {
		ruleMap = new HashMap<Symbol,ArrayList<Rule>>();
	}
	
	public void addRule(Rule rule) {
		ArrayList<Rule> ruleList = ruleMap.get(rule.thenPart().functor());
		if (ruleList == null)
			ruleList = new ArrayList<Rule>();
		ruleList.add(rule);
		ruleMap.put(rule.functor(), ruleList);
	}
	
	public ArrayList<Rule> getRules(Symbol key) {
		return ruleMap.get(key);
	}
	
	public void print() {
		Iterator<Map.Entry<Symbol, ArrayList<Rule>>> iterator = ruleMap.entrySet().iterator();
		while(iterator.hasNext()) {
		   Map.Entry<Symbol, ArrayList<Rule>> entry = iterator.next();
		   ArrayList<Rule> rules = entry.getValue();
		   System.out.printf("-------------------------------------------------------\n");
		   for (Rule rule : rules)
			   System.out.printf("%s\n", rule.toString());
		}
	}
}
