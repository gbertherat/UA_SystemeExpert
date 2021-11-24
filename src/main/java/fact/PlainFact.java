package fact;

public class PlainFact implements Fact{
    private final String sentence;

    public PlainFact(String sentence){
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    @Override
    public Operator getOperator() {
        return Operator.NONE;
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public String toString() {
        return this.sentence;
    }

    public boolean compareToFact(Fact fact){
        return this.sentence.equals(fact.getSentence());
    }
}
