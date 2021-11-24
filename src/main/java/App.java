import fact.Fact;
import org.json.simple.parser.ParseException;
import rule.Rule;
import utils.JsonProcess;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, ParseException {
        List<Fact> facts = JsonProcess.importFacts();
        List<Rule> rules = JsonProcess.importRules(facts);
        List<Fact> base = JsonProcess.importBaseFacts(facts);
    }
}
