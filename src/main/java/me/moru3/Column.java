package me.moru3;

import com.sun.istack.internal.NotNull;
import me.moru3.exceptions.NoPropertyException;
import me.moru3.utils.Obj2Str;

public class Column extends Obj2Str implements IColumn {
    private final String name;
    private final DataType<?> type;
    private boolean notnull;
    private boolean unsigned;
    private boolean zerofill;
    private boolean autoincrement;
    private boolean primarykey;
    private Object defaultvalue;
    private Object property;

    public Column(@NotNull String name, @NotNull DataType<?> type) {
        this.name = name;
        this.type = type;
    }

    public String build() throws NoPropertyException {
        StringBuilder result = new StringBuilder();
        result.append("`").append(name).append("` ");
        switch (type.getTypeName()) {
            case "ENUM":
                if(property!=null) { result.append("ENUM(").append(obj2Str(property)).append(")"); } else { throw new NoPropertyException("ENUM property is not set."); }
                break;
            case "SET":
                if(property!=null) { result.append("SET(").append(obj2Str(property)).append(")"); } else { throw new NoPropertyException("ENUM property is not set."); }
                break;
            default:
                if(property!=null) {
                    result.append(type.getTypeName()).append("(").append(obj2Str(property)).append(")");
                } else if (type.getProperty()!=null) {
                    result.append(type.getTypeName()).append("(").append(obj2Str(type.getProperty())).append(")");
                } else {
                    result.append(type.getTypeName());
                }
        }
        if(zerofill&&type.getZeroFill()) result.append(" ZEROFILL");
        if(unsigned&&type.getUnsigned()) result.append(" UNSIGNED");
        if(notnull) {
            result.append(" NOT");
        } else {
            if(primarykey) {
                result.append(" NOT");
            }
        }
        result.append(" NULL");
        if(autoincrement&&type.getAutoIncrement()) result.append(" AUTO_INCREMENT");
        if(defaultvalue!=null&&type.getDefault()) {
            result.append(" DEFAULT ").append(obj2Str(defaultvalue));
        }
        return new String(result);
    }

    public Column setNotNull(boolean bool) {
        notnull = bool;
        return this;
    }

    public boolean isNotNull() {
        return notnull;
    }

    public Column setUnsigned(boolean bool) {
        if(type.getUnsigned()) {
            this.unsigned = bool;
        }
        return this;
    }

    public boolean isUnsigned() {
        return unsigned;
    }

    public Column setZeroFill(boolean bool) {
        if(type.getZeroFill()) {
            this.zerofill = bool;
        }
        return this;
    }

    public boolean isZeroFill() {
        return zerofill;
    }

    public Column setAutoIncrement(boolean bool) {
        if(type.getAutoIncrement()) {
            this.autoincrement = bool;
        }
        return this;
    }

    public boolean isAutoIncrement() {
        return autoincrement;
    }

    public Column setPrimaryKey(boolean bool) {
        if(type.getPrimaryKey()) {
            this.primarykey = bool;
        }
        return this;
    }

    public boolean isPrimaryKey() {
        return primarykey;
    }

    public Column setDefaultValue(Object obj) {
        return this;
    }

    public Column setDefault(Object obj) {
        if(type.getDefault()) {
            this.defaultvalue = obj;
        }
        return this;
    }

    public Column setProperty(Object obj) {
        this.property = obj;
        return this;
    }
}
