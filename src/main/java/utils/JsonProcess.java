package utils;

import com.google.gson.Gson;
import fact.Fact;

import fact.Operator;
import fact.PlainFact;
import fact.ValueFact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rule.Rule;

import java.io.*;
import java.util.*;

public class JsonProcess {

    public static void exportToJson(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {

        String json = "{" +
                "\"facts\":" +
                new Gson().toJson(facts) +
                "," +
                "\"rules\":" +
                new Gson().toJson(rules) +
                "," +
                "\"base_facts\":" +
                new Gson().toJson(baseFacts) +
                "}";

        File f = new File("data.json");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(json);
        bw.close();
    }

    public static JSONArray importData(String key) throws IOException, ParseException {
        File f = new File("./data.json");
        if(f.createNewFile()){
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{}");
            bw.close();
        }
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder jsonString = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            jsonString.append(line).append("\n");
        }
        br.close();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString.toString());
        return (JSONArray) json.get(key);
    }

    private static void getFacts(List<Fact> facts, JSONArray resultArr, List<Fact> result) {
        for(Object o : resultArr){
            JSONObject resultObj = (JSONObject) o;
            Optional<Fact> fact = facts.stream().filter(e -> e.getSentence().equals(resultObj.get("sentence"))).findFirst();
            fact.ifPresent(result::add);
        }
    }

    public static List<Fact> importFacts() throws IOException, ParseException {
        JSONArray arr = importData("facts");
        List<Fact> facts = new ArrayList<>();

        if(arr != null) {
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                if (obj.get("operator") != null) {
                    String sentence = (String) obj.get("sentence");
                    Optional<Operator> operator = Arrays.stream(Operator.values())
                            .filter(e -> e.toString().equals(obj.get("operator")))
                            .findFirst();
                    double value = (double) obj.get("value");

                    operator.ifPresent(oper -> facts.add(new ValueFact(sentence, oper, value)));
                } else {
                    facts.add(new PlainFact((String) obj.get("sentence")));
                }
            }
        }
        return facts;
    }

    public static List<Rule> importRules(List<Fact> facts) throws IOException, ParseException {
        JSONArray arr = importData("rules");
        List<Rule> rules = new ArrayList<>();
        if(arr != null) {
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;

                JSONArray rulesArr = (JSONArray) obj.get("ruleFacts");
                List<Fact> ruleFacts = new ArrayList<>();
                getFacts(facts, rulesArr, ruleFacts);

                JSONArray resultArr = (JSONArray) obj.get("resultFacts");
                List<Fact> resultFacts = new ArrayList<>();
                getFacts(facts, resultArr, resultFacts);

                rules.add(new Rule(ruleFacts, resultFacts));
            }
        }
        return rules;
    }

    public static List<Fact> importBaseFacts(List<Fact> facts) throws IOException, ParseException {
        List<Fact> baseFacts = new ArrayList<>();
        JSONArray arr = importData("base_facts");
        if(arr != null) {
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                Optional<Fact> fact = facts.stream().filter(e -> e.getSentence().equals(obj.get("sentence"))).findFirst();
                fact.ifPresent(baseFacts::add);
            }
        }
        return baseFacts;
    }
}
