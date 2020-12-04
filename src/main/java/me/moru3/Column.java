package me.moru3;

import com.sun.istack.internal.NotNull;
import me.moru3.exceptions.NoPropertyException;
import me.moru3.utils.Obj2Str;

public class Column extends Obj2Str {
    private final String name;
    private final DataType<?> type;
    private Boolean notnull;
    private Boolean unsigned;
    private Boolean zerofill;
    private Boolean autoincrement;
    private Boolean primarykey;
    private Object defaultvalue;
    private Boolean property;

    public Column(@NotNull String name, @NotNull DataType<?> type) {
        this.name = name;
        this.type = type;
    }

    public String build() throws NoPropertyException {
        StringBuilder result = new StringBuilder();
        result.append("`").append(name).append("` ");
        switch (type.getTypeName()) {
            case "ENUM":
                if(property!=null) {
                    result.append("ENUM(").append(obj2Str(property)).append(")");
                } else {
                    throw new NoPropertyException("ENUM property is not set.");
                }
                break;
            case "SET":
                if(property!=null) {
                    result.append("SET(").append(obj2Str(property)).append(")");
                } else {
                    throw new NoPropertyException("ENUM property is not set.");
                }
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
        if(zerofill) {
            if(type.getZeroFill()) {
                result.append(" ZEROFILL");
            }
        }
        if(unsigned) {
            if(type.getUnsigned()) {
                result.append(" UNSIGNED");
            }
        }
        if(notnull) {
            result.append(" NOT");
        } else {
            if(primarykey) {
                result.append(" NOT");
            }
        }
        result.append(" NULL");
        if(autoincrement) {
            if(type.getAutoIncrement()) {
                result.append(" AUTO_INCREMENT");
            }
        }
        return new String(result);
    }

    public Column setNotNull(boolean sel) {
        return this;
    }

    public Column setUnsigned(boolean sel) {
        if(type.getUnsigned()) {
            this.unsigned = sel;
        }
        return this;
    }

    public Column setZeroFill(boolean sel) {
        if(type.getZeroFill()) {
            this.zerofill = sel;
        }
        return this;
    }

    public Column setAutoIncrement(Boolean sel) {
        if(type.getAutoIncrement()) {
            this.autoincrement = sel;
        }
        return this;
    }

    public Column setPrimaryKey(Boolean sel) {
        if(type.getPrimaryKey()) {
            this.primarykey = sel;
        }
        return this;
    }

    public Column setDefault(Object obj) {
        if(type.getDefault()) {
            this.defaultvalue = obj;
        }
        return this;
    }

    public Column setProperty(Boolean sel) {
        this.property = sel;
        return this;
    }
}
