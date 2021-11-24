package fact;
public class ValueFact implements Fact{
    private final String sentence;
    private final Operator operator;
    private final double value;

    public ValueFact(String sentence, Operator operator, double value){
        this.sentence = sentence;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String getSentence() {
        return this.sentence;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.sentence + " " + this.operator.getSymbol() + " " + this.value;
    }

    public boolean compareToFact(Fact fact) {
        return fact.getOperator().compareFactsValues(this.value, fact.getValue());
    }
}
