package utils;

import com.google.gson.Gson;
import fact.Fact;
import rule.Rule;

import java.io.*;
import java.util.List;

public class JsonProcess {

    public static void exportToJson(List<Fact> facts, List<Rule> rules, List<Fact> baseFacts) throws IOException {

        String json = "{" +
                "\"rules\":" +
                new Gson().toJson(facts) +
                "," +
                "\"facts\":" +
                new Gson().toJson(rules) +
                "," +
                "\"base_facts\":" +
                new Gson().toJson(baseFacts) +
                "}";

        File f = new File("./data.json");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(json);
        bw.close();
    }
}
