package chaining;

import fact.Fact;
import rule.Rule;

import java.util.*;


public class BackwardChaining {
    public static Map<String, List<List<Fact>>> solve(List<Rule> rules, Fact target){
        Map<String, List<List<Fact>>> result = new LinkedHashMap<>();
        result.put(target.getSentence(), new ArrayList<>());
        for(Rule rule: rules){
            if(rule.getResultFacts().stream().anyMatch(e -> e.getSentence().equals(target.getSentence()))){
                result.get(target.getSentence()).add(new ArrayList<>(rule.getRuleFacts()));
                for(Fact fact: rule.getRuleFacts()) {
                    result.putAll(solve(rules, fact));
                }
            }
        }

        return result;
    }

    public static void readOutput(Map<String, List<List<Fact>>> output){
        for(String key: output.keySet()){
            if(output.get(key).size() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Pour avoir ").append(key).append(", il faut: ");
                for (List<Fact> list : output.get(key)) {
                    sb.append("(");
                    for(Fact fact: list){
                        sb.append(fact);
                        if(list.indexOf(fact) != list.size() -1) {
                            sb.append(" et ");
                        }
                    }
                    sb.append(")");
                    if(output.get(key).indexOf(list) != output.get(key).size()-1){
                        sb.append(" ou ");
                    }
                }
                System.out.println(sb);
            }
        }
    }
}
