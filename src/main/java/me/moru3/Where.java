package me.moru3;

import me.moru3.utils.ObjConv;

public class Where extends ObjConv {
    private String before;
    private String after;
    private StringBuilder result = new StringBuilder();
    public Where(){}

    public Where addKey(String key) {
        if(before.equals("key") || before.equals("value")) return this;
        before = "key";
        after = " " + key;
        result.append(after);
        return this;
    }

    public Where addValue(Object value) {
        if(before.equals("key") || before.equals("value")) return this;
        before = "value";
        after = ObjToType(value).getConvM().apply(value);
        result.append(after);
        return this;
    }

    public Where addOperator(ComparativeOperator op) {
        if (before.equals("operator")) return this;
        before = "operator";
        after = " " + op.getOperator() + " ";
        result.append(after);
        return this;
    }
    public Where addOperator(LogicalOperator op) {
        if (before.equals("operator")) return this;
        before = "operator";
        after = " " + op.toString() + " ";
        result.append(after);
        return this;
    }

    public Where addLike(String key, String value) {
        if(before.equals("key") || before.equals("value")) return this;
        before = "key";
        after = "\"" + key + "\"";
        result.append(after);
        return this;
    }

    public String build() {
        String result = new String(this.result) + "<?>?";
        if(before.equals("operator")) return result.replace(after + "<?>?", "");
        return result.replace("<?>?", "");
    }
}
