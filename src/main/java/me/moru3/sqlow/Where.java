package me.moru3.sqlow;

import me.moru3.sqlow.utils.ObjConv;

public class Where extends ObjConv {
    private Boolean opera = false;
    private String after;
    private final StringBuilder result = new StringBuilder();
    public Where(){}

    public Where addKey(String key) {
        if(opera) return this;
        opera = true;
        after = key;
        result.append(after);
        return this;
    }

    public Where addValue(Object value) {
        if(opera) return this;
        opera = true;
        if(value instanceof Select) {
            after = ((Select) value).build();
        } else {
            after = ObjToType(value).getConvM().apply(value);
        }
        result.append(after);
        return this;
    }

    public Where and() {
        if (!opera) return this;
        opera = false;
        after = " AND ";
        result.append(after);
        return this;
    }

    public Where or() {
        if (!opera) return this;
        opera = false;
        after = " OR ";
        result.append(after);
        return this;
    }

    public Where equals() {
        if (!opera) return this;
        opera = false;
        after = " = ";
        result.append(after);
        return this;
    }

    public Where lessThan() {
        if (!opera) return this;
        opera = false;
        after = " < ";
        result.append(after);
        return this;
    }

    public Where greaterThan() {
        if (!opera) return this;
        opera = false;
        after = " > ";
        result.append(after);
        return this;
    }

    public Where lessThanOrEquals() {
        if (!opera) return this;
        opera = false;
        after = " <= ";
        result.append(after);
        return this;
    }

    public Where greaterThanOrEquals() {
        if (!opera) return this;
        opera = false;
        after = " >= ";
        result.append(after);
        return this;
    }

    public Where addLike(String key, String value) {
        if(opera) return this;
        opera = true;
        after = "key LIKE \"" + value + "\"";
        result.append(after);
        return this;
    }

    public String build() {
        String result = " WHERE " + new String(this.result) + "<?>?";
        if(this.result.length()<=0) {
            return "";
        }
        if(!opera) return result.replace(after + "<?>?", "");
        return result.replace("<?>?", "");
    }
}
