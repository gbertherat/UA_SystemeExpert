import fact.Fact;
import gui.GUI;
import org.json.simple.parser.ParseException;
import rule.Rule;
import utils.JsonProcess;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, ParseException {
        System.setProperty("file.encoding", "UTF-8");
        List<Fact> facts = JsonProcess.importFacts();
        List<Rule> rules = JsonProcess.importRules(facts);
        List<Fact> base = JsonProcess.importBaseFacts(facts);

        System.out.println("---------------");
        System.out.println("Système Expert");
        System.out.println("---------------");
        GUI.mainMenu(facts, rules, base);
    }
}
