package chaining;

import fact.Fact;
import rule.Rule;

import java.util.List;

public class ForwardChaining {
    public static void solve(List<Rule> rules, List<Fact> base){
        for(Rule rule : rules){
            if(base.containsAll(rule.getResultFacts())){
                continue;
            }

            if(rule.compareFacts(base)){
                base.addAll(rule.getResultFacts());
                solve(rules, base);
            }
        }
    }
}
