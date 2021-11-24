package gui;

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
import java.util.regex.Pattern;

public class GUI {
    public static void mainMenu(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {
        boolean closing = false;
        while(!closing) {
            System.out.println("Que souhaitez-vous faire? (1-9)");
            System.out.println("1) Lancer le chaînage avant");
            System.out.println("2) Lancer le chaînage arrière");
            System.out.println("3) Voir la liste des faits");
            System.out.println("4) Voir la liste des règles");
            System.out.println("5) Voir la base de faits");
            System.out.println("6) Modifier les faits");
            System.out.println("7) Modifier les règles");
            System.out.println("8) Modifier la base des faits");
            System.out.println("9) Quitter");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();

            System.out.println("---------------");
            switch (output) {
                case "1":
                    List<Fact> base = new ArrayList<>(baseFacts);
                    ForwardChaining.solve(rules, base);
                    System.out.println("Voici la base de faits en sortie des règles existantes:");
                    base.forEach(e -> System.out.println(facts.indexOf(e) + 1 + ") " + e));
                    System.out.println("---------------");
                    break;
                case "2":
                    System.out.println("Liste des faits existants:");
                    facts.forEach(e -> System.out.println(facts.indexOf(e)+1 + ") " + e));
                    System.out.println("Quel fait est votre objectif? (1-" + facts.size() + ")");
                    System.out.println("(0 pour revenir en arrière.)");
                    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                    output = scanner.next();
                    if(!output.equals("0") && pattern.matcher(output).matches()) {
                        int index = Integer.parseInt(output) - 1;
                        if(index >= 0 && index < facts.size()) {
                            Map<String, List<List<Fact>>> bChaining = BackwardChaining.solve(rules, facts.get(index));
                            BackwardChaining.readOutput(bChaining);
                        } else {
                            System.out.println("Veuillez entrer une valeur correcte!");
                        }
                    } else {
                        System.out.println("Veuillez entrer une valeur correcte!");
                    }
                    System.out.println("---------------");
                    break;
                case "3":
                    System.out.println("Liste des faits existants:");
                    facts.forEach(e -> System.out.println(facts.indexOf(e) + 1 + ") " + e));
                    break;
                case "4":
                    System.out.println("Liste des règles existantes:");
                    rules.forEach(e -> System.out.println(rules.indexOf(e) + 1 + ") " + e));
                    break;
                case "5":
                    System.out.println("Base de faits:");
                    baseFacts.forEach(e -> System.out.println(facts.indexOf(e) + 1 + ") " + e));
                    break;
                case "6":
                    modifyFacts(facts, rules, baseFacts);
                    break;
                case "7":
                    modifyRules(facts, rules, baseFacts);
                    break;
                case "8":
                    modifyBaseFacts(facts, rules, baseFacts);
                    break;
                case "9":
                    closing = true;
                    break;
                default:
                    System.out.println("Veuillez entrer une valeur correcte!");
                    System.out.println("---------------");
                    break;
            }
        }
    }

    public static void modifyFacts(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {
        boolean closing = false;
        while(!closing){
            System.out.println("Que souhaitez-vous faire? (1-3)");
            System.out.println("1) Ajouter un fait");
            System.out.println("2) Supprimer un fait");
            System.out.println("3) Retour");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();

            System.out.println("---------------");
            switch (output) {
                case "1":
                    addFact(facts, rules, baseFacts);
                    break;
                case "2":
                    removeFact(facts, rules, baseFacts);
                    break;
                case "3":
                    closing = true;
                    break;
                default:
                    System.out.println("Veuillez entrer une valeur correcte!");
                    System.out.println("---------------");
                    break;
            }
        }
    }

    public static void addFact(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException{
        boolean closing = false;
        while(!closing){
            System.out.println("Quel type de fait souhaitez-vous ajouter?");
            System.out.println("1) Fait sans valeur");
            System.out.println("2) Fait avec valeur");
            System.out.println("3) Annuler");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();
            System.out.println("---------------");
            String sentence;
            switch (output){
                case "1":
                    System.out.println("Veuillez écrire la phrase décrivant le fait");
                    System.out.print(">> ");
                    sentence = scanner.next();
                    if(sentence.length() > 2) {
                        facts.add(new PlainFact(sentence));
                        JsonProcess.exportToJson(facts, rules, baseFacts);
                        System.out.println("Votre fait a bien été créé!");
                        closing = true;
                    } else {
                        System.out.println("Veuillez entrer une valeur valide!");
                    }
                    break;
                case "2":
                    System.out.println("Veuillez écrire la phrase décrivant le fait");
                    System.out.print(">> ");
                    sentence = scanner.next();
                    if(sentence.length() > 2){
                        System.out.println("Veuillez choisir un opérateur ci-dessous. (1-" + Operator.values().length +
                                ")");
                        Arrays.stream(Operator.values()).forEach(e -> {
                            System.out.println(
                                    (new ArrayList<>(Arrays.asList(Operator.values())).indexOf(e)+1)
                                            + ") "
                                            + e.getSymbol());
                        });
                        System.out.print(">> ");
                        String operator = scanner.next();
                        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

                        if(pattern.matcher(operator).matches()){
                            int index = Integer.parseInt(operator)-1;
                            if(index >= 0 && index < Operator.values().length) {
                                Operator selectedOperator = Operator.values()[index];
                                System.out.println("Veuillez écrire la valeur du fait");
                                System.out.print(">> ");
                                String value = scanner.next();
                                if (pattern.matcher(value).matches()) {
                                    double selectedValue = Double.parseDouble(value);
                                    facts.add(new ValueFact(sentence, selectedOperator, selectedValue));
                                    JsonProcess.exportToJson(facts, rules, baseFacts);
                                    System.out.println("Votre fait a bien été créé!");
                                    closing = true;
                                } else {
                                    System.out.println("Veuillez entrer une valeur valide!");
                                    System.out.println("---------------");
                                }
                            } else{
                                System.out.println("Veuillez entrer une valeur valide!");
                                System.out.println("---------------");
                            }
                        } else {
                            System.out.println("Veuillez entrer une valeur valide!");
                            System.out.println("---------------");
                        }
                    } else {
                        System.out.println("Veuillez entrer une valeur valide!");
                        System.out.println("---------------");
                    }
                    break;
                case "3":
                    closing = true;
                    break;
                default:
                    System.out.println("Veuillez entrer une valeur correcte!");
                    break;
            }
        }
    }

    public static void removeFact(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {
        boolean closing = false;
        while(!closing) {
            System.out.println("Liste des faits existants:");
            facts.forEach(e -> System.out.println(facts.indexOf(e)+1 + ") " + e));
            System.out.println("Quel fait souhaitez-vous supprimer? (1-" + facts.size() + ")");
            System.out.println("(0 pour revenir en arrière.)");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();
            System.out.println("---------------");

            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if(output.equals("0")){
                closing = true;
            } else if(pattern.matcher(output).matches()){
                int index = Integer.parseInt(output)-1;
                if(index >= 0 && index < facts.size()) {
                    System.out.println("Fait: \"" + facts.get(index) + "\" supprimé");
                    System.out.println("---------------");
                    facts.remove(index);
                    JsonProcess.exportToJson(facts, rules, baseFacts);

                    closing = true;
                } else {
                    System.out.println("Veuillez entrer une valeur valide!");
                    System.out.println("---------------");
                }
            } else {
                System.out.println("Veuillez entrer une valeur valide!");
                System.out.println("---------------");
            }
        }
    }

    public static void modifyRules(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException{
        boolean isClosing = false;
        while(!isClosing){
            System.out.println("Que souhaitez-vous faire? (1-3)");
            System.out.println("1) Ajouter une règle");
            System.out.println("2) Supprimer une règle");
            System.out.println("3) Revenir en arrière");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();

            System.out.println("---------------");
            switch (output){
                case "1":
                    addRule(facts, rules, baseFacts);
                    break;
                case "2":
                    removeRule(facts, rules, baseFacts);
                    break;
                case "3":
                    isClosing = true;
                    break;
                default:
                    System.out.println("Veuillez entrer une valeur correcte!");
                    System.out.println("---------------");
                    break;
            }
        }
    }

    public static void addRule(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException{
        System.out.println("Voici la liste des faits existants:");
        facts.forEach(e -> System.out.println(facts.indexOf(e) + 1 + ") " + e));
        System.out.println("---------------");
        System.out.println("Veuillez entrez un par un les faits nécessaire pour valider la règle. (1-"+facts.size()+
                ")");
        System.out.println("(Entrez -1 pour valider)");

        List<Fact> neededFacts = new ArrayList<>();
        List<Fact> resultFacts = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        boolean isClosing = false;
        while(!isClosing){
            String output = scanner.next();
            if(output.equals("-1")){
                isClosing = true;
            } else if(pattern.matcher(output).matches()){
                int index = Integer.parseInt(output)-1;
                if(index >= 0 && index < facts.size()){
                    if(!neededFacts.contains(facts.get(index))){
                        neededFacts.add(facts.get(index));
                    }
                } else {
                    System.out.println("Veuillez entrer une valeur correcte!");
                }
            }
        }
        if(neededFacts.size() > 0) {
            System.out.println("---------------");
            System.out.println("Entrez maintenant un par un les faits obtenus en validant la règle. (1-" + facts.size() + ")");
            System.out.println("(Entrez -1 pour valider)");
            isClosing = false;
            while (!isClosing) {
                String output = scanner.next();
                if (output.equals("-1")) {
                    isClosing = true;
                } else if (pattern.matcher(output).matches()) {
                    int index = Integer.parseInt(output) - 1;
                    if (index >= 0 && index < facts.size()) {
                        if (!resultFacts.contains(facts.get(index))) {
                            resultFacts.add(facts.get(index));
                        }
                    } else {
                        System.out.println("Veuillez entrer une valeur correcte!");
                    }
                }
            }

            if(resultFacts.size() > 0){
                rules.add(new Rule(neededFacts, resultFacts));
                JsonProcess.exportToJson(facts, rules, baseFacts);
                System.out.println("Votre règle a bien été créée!");
            } else {
                System.out.println("Veuillez entrer une valeur correcte!");
                System.out.println("---------------");
            }
        } else {
            System.out.println("Veuillez entrer une valeur correcte!");
            System.out.println("---------------");
        }
    }

    public static void removeRule(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {
        boolean closing = false;
        while(!closing) {
            System.out.println("Liste des règles existantes:");
            rules.forEach(e -> System.out.println(rules.indexOf(e)+1 + ") " + e));
            System.out.println("Quelle règle souhaitez-vous supprimer? (1-" + rules.size() + ")");
            System.out.println("(0 pour revenir en arrière.)");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();
            System.out.println("---------------");

            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if(output.equals("0")){
                closing = true;
            } else if(pattern.matcher(output).matches()){
                int index = Integer.parseInt(output)-1;
                if(index >= 0 && index < rules.size()) {
                    System.out.println("Fait: \"" + rules.get(index) + "\" supprimé");
                    System.out.println("---------------");
                    rules.remove(index);
                    JsonProcess.exportToJson(facts, rules, baseFacts);

                    closing = true;
                } else {
                    System.out.println("Veuillez entrer une valeur valide!");
                    System.out.println("---------------");
                }
            } else {
                System.out.println("Veuillez entrer une valeur valide!");
                System.out.println("---------------");
            }
        }
    }

    public static void modifyBaseFacts(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {
        boolean closing = false;
        while(!closing){
            System.out.println("Que souhaitez-vous faire? (1-3)");
            System.out.println("1) Ajouter un fait dans la base de faits");
            System.out.println("2) Supprimer un fait de la base de faits");
            System.out.println("3) Retour");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();

            System.out.println("---------------");
            switch (output) {
                case "1":
                    addBaseFact(facts, rules, baseFacts);
                    break;
                case "2":
                    removeBaseFact(facts, rules, baseFacts);
                    break;
                case "3":
                    closing = true;
                    break;
                default:
                    System.out.println("Veuillez entrer une valeur correcte!");
                    System.out.println("---------------");
                    break;
            }
        }
    }

    public static void addBaseFact(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException{
        System.out.println("Voici la liste des faits existants:");
        facts.forEach(e -> System.out.println(facts.indexOf(e) + 1 + ") " + e));
        System.out.println("---------------");
        System.out.println("Veuillez entrez un par un les faits que vous souhaitez ajouter. (1-"+facts.size()+
                ")");
        System.out.println("(Entrez -1 pour valider)");

        List<Fact> newFacts = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        boolean isClosing = false;
        while(!isClosing){
            String output = scanner.next();
            if(output.equals("-1")){
                isClosing = true;
            } else if(pattern.matcher(output).matches()){
                int index = Integer.parseInt(output)-1;
                if(index >= 0 && index < facts.size()){
                    if(!newFacts.contains(facts.get(index))){
                        newFacts.add(facts.get(index));
                    }
                } else {
                    System.out.println("Veuillez entrer une valeur correcte!");
                }
            }
        }

        if(newFacts.size() > 0) {
            newFacts.stream().filter(e -> !baseFacts.contains(e)).forEach(baseFacts::add);
            JsonProcess.exportToJson(facts, rules, baseFacts);
            System.out.println("Les faits choisis ont bien été ajoutés!");
        } else {
            System.out.println("Veuillez entrer une valeur correcte!");
            System.out.println("---------------");
        }
    }

    public static void removeBaseFact(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {
        boolean closing = false;
        while(!closing) {
            System.out.println("Liste des faits dans la base de faits:");
            baseFacts.forEach(e -> System.out.println(baseFacts.indexOf(e)+1 + ") " + e));
            System.out.println("Quel fait souhaitez-vous supprimer? (1-" + baseFacts.size() + ")");
            System.out.println("(0 pour revenir en arrière.)");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String output = scanner.next();
            System.out.println("---------------");

            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if(output.equals("0")){
                closing = true;
            } else if(pattern.matcher(output).matches()){
                int index = Integer.parseInt(output)-1;
                if(index >= 0 && index < baseFacts.size()) {
                    System.out.println("Fait: \"" + baseFacts.get(index) + "\" supprimé");
                    System.out.println("---------------");
                    baseFacts.remove(index);
                    JsonProcess.exportToJson(facts, rules, baseFacts);

                    closing = true;
                } else {
                    System.out.println("Veuillez entrer une valeur valide!");
                    System.out.println("---------------");
                }
            } else {
                System.out.println("Veuillez entrer une valeur valide!");
                System.out.println("---------------");
            }
        }
    }
}
