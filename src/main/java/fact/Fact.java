package fact;

public interface Fact{
    boolean compareToFact(Fact fact);
    String getSentence();
    Operator getOperator();
    double getValue();
}
