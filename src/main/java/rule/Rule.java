package rule;

import fact.Fact;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
public class Rule {
    private List<Fact> ruleFacts;
    private List<Fact> resultFacts;

    public Rule(List<Fact> ruleFacts, List<Fact> resultFacts) {
        this.ruleFacts = ruleFacts;
        this.resultFacts = resultFacts;
    }

    public boolean compareFacts(List<Fact> facts) {
        boolean everyFactsAreValid = true;
        for(Fact fact : ruleFacts){
            Optional<Fact> filterFact = facts.stream().filter(e -> e.getSentence().equals(fact.getSentence())).findFirst();
            if(filterFact.isPresent()){
                everyFactsAreValid = filterFact.get().compareToFact(fact);
            } else {
                return false;
            }
        }
        return everyFactsAreValid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("If (");
        for(Fact fact : ruleFacts){
            sb.append(fact);
            if(ruleFacts.indexOf(fact) != ruleFacts.size()-1){
                sb.append(" ^ ");
            }
        }
        sb.append(") Then (");
        for(Fact fact: resultFacts){
            sb.append(fact);
            if(resultFacts.indexOf(fact) != resultFacts.size()-1){
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
