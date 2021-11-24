import chaining.BackwardChaining;
import chaining.ForwardChaining;
import fact.Fact;
import fact.Operator;
import fact.PlainFact;
import fact.ValueFact;
import rule.Rule;
import utils.JsonProcess;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        // EXEMPLE POUR EXERCICE 1 TD 1
        // FACTS
        Fact majeur = new PlainFact("Majeur");
        Fact poste = new PlainFact("Poste à responsabilité");
        Fact facilitesLangues = new PlainFact("Facilités à apprendre les langues");
        Fact neerlandais = new PlainFact("Parle néerlandais");
        Fact anglais = new PlainFact("Parle anglais");
        Fact dynamique = new PlainFact("Dynamique");
        Fact adaptabilite = new PlainFact("Bonne adaptabilité");
        Fact slave = new PlainFact("Être slave");
        Fact leadership = new PlainFact("Capacité de leadership");
        Fact accepte = new PlainFact("Être accepté");

        ArrayList<Fact> facts = new ArrayList<>(Arrays.asList(slave, poste, poste, facilitesLangues, neerlandais,
                anglais, dynamique, adaptabilite, slave, leadership, accepte));

        // RULES              IF                                                            THEN         Arrays.asList(majeur));
        Rule rule1 = new Rule(Arrays.asList(majeur, poste, facilitesLangues, neerlandais),  Arrays.asList(dynamique));
        Rule rule2 = new Rule(Arrays.asList(facilitesLangues, anglais),                     Arrays.asList(adaptabilite));
        Rule rule3 = new Rule(Arrays.asList(slave, dynamique),                              Arrays.asList(adaptabilite));
        Rule rule4 = new Rule(Arrays.asList(majeur, poste),                                 Arrays.asList(leadership));
        Rule rule5 = new Rule(Arrays.asList(facilitesLangues),                              Arrays.asList(neerlandais));
        Rule rule6 = new Rule(Arrays.asList(majeur, adaptabilite, leadership),              Arrays.asList(accepte));
        Rule rule7 = new Rule(Arrays.asList(slave),                                         Arrays.asList(facilitesLangues));
        Rule rule8 = new Rule(Arrays.asList(leadership, slave),                             Arrays.asList(adaptabilite));
        Rule rule9 = new Rule(Arrays.asList(majeur),                                        Arrays.asList(poste));

        List<Rule> rules = Arrays.asList(rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9);

        // BASE DE FAITS
        List<Fact> baseFacts = new ArrayList<>(Arrays.asList(slave, poste));

        JsonProcess.exportToJson(facts, rules, baseFacts);
    }
}
