package me.moru3;

public enum ComparativeOperator {
    equals("="),
    lessThan("<"),
    greaterThan(">"),
    lennThanOrEqual("<="),
    greaterThanOrEqual("=>"),
    ;
    String operator;
    ComparativeOperator(String s) {
        operator = s;
    }

    public String getOperator() {
        return operator;
    }
}
