package me.moru3;

import me.moru3.utils.ObjConv;

public class Where extends ObjConv {
    private Boolean opera = false;
    private String after;
    private final StringBuilder result = new StringBuilder();
    public Where(){}

    public Where addKey(String key) {
        if(opera) return this;
        opera = true;
        after = " " + key;
        result.append(after);
        return this;
    }

    public Where addValue(Object value) {
        if(opera) return this;
        opera = true;
        after = ObjToType(value).getConvM().apply(value);
        result.append(after);
        return this;
    }

    public Where addOperator(ComparativeOperator op) {
        if (!opera) return this;
        opera = false;
        after = " " + op.getOperator() + " ";
        result.append(after);
        return this;
    }
    public Where addOperator(LogicalOperator op) {
        if (!opera) return this;
        opera = false;
        after = " " + op.toString() + " ";
        result.append(after);
        return this;
    }

    public Where addLike(String key, String value) {
        if(opera) return this;
        opera = true;
        after = "\"" + key + "\"";
        result.append(after);
        return this;
    }

    public String build() {
        String result = new String(this.result) + "<?>?";
        if(!opera) return result.replace(after + "<?>?", "");
        return result.replace("<?>?", "");
    }
}
