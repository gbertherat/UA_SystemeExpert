package fact;

public enum Operator {
    EQ{
        @Override
        public String getSymbol() {
            return "=";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return value1 == value2;
        }
    },
    NEQ{
        @Override
        public String getSymbol() {
            return "!=";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return value1 != value2;
        }
    },
    GT{
        @Override
        public String getSymbol() {
            return ">";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return value1 > value2;
        }
    },
    GE{
        @Override
        public String getSymbol() {
            return ">=";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return value1 >= value2;
        }
    },
    LT{
        @Override
        public String getSymbol() {
            return "<";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return value1 < value2;
        }
    },
    LE{
        @Override
        public String getSymbol() {
            return "<=";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return value1 <= value2;
        }
    },
    NONE{
        @Override
        public String getSymbol() {
            return "";
        }

        @Override
        public boolean compareFactsValues(double value1, double value2) {
            return false;
        }
    };

    public abstract String getSymbol();
    public abstract boolean compareFactsValues(double value1, double value2);
}
