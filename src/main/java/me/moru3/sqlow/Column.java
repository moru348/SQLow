package me.moru3.sqlow;

import org.jetbrains.annotations.NotNull;

public class Column implements IColumn {
    private final String name;
    private final DataType<?> type;
    private boolean notnull;
    // private boolean unsigned;
    private boolean zerofill;
    private boolean autoincrement;
    private boolean primarykey;
    private Object defaultvalue;
    private Object property;

    public Column(@NotNull String name, @NotNull DataType<?> type) {
        this.name = name;
        this.type = type;
    }

    public String build() {
        StringBuilder result = new StringBuilder();
        result.append("`").append(name).append("` ");
        if(property!=null) {
            result.append(type.getTypeName()).append("(").append(property.toString()).append(")");
        } else if (type.getProperty()!=null) {
            result.append(type.getTypeName()).append("(").append(type.getProperty().toString()).append(")");
        } else {
            result.append(type.getTypeName());
        }
        if(zerofill&&type.getZeroFill()) result.append(" ZEROFILL");
        // if(unsigned&&type.getUnsigned()) result.append(" UNSIGNED");
        if(notnull) {
            result.append(" NOT");
        } else {
            if(primarykey) {
                result.append(" NOT");
            }
        }
        result.append(" NULL");
        if(autoincrement&&type.getAutoIncrement()) result.append(SQLow.getDatabaseType()==DatabaseType.SQLITE ? "AUTOINCREMENT" : " AUTO_INCREMENT");
        if(defaultvalue!=null&&type.getDefault()) {
            result.append(" DEFAULT ").append(type.getConvM().apply(defaultvalue));
        }
        return new String(result);
    }

    public String getName() {
        return name;
    }

    public Column setNotNull(boolean bool) {
        notnull = bool;
        return this;
    }

    public boolean isNotNull() {
        return notnull;
    }

    //public Column setUnsigned(boolean bool) {
    //    if(type.getUnsigned()) {
    //        this.unsigned = bool;
    //    }
    //    return this;
    //}

    //public boolean isUnsigned() {
    //    return unsigned;
    //}

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

    public Column setDefault(@NotNull Object obj) {
        if(type.getDefault()) {
            this.defaultvalue = obj;
        }
        return this;
    }

    public Column setProperty(@NotNull Object obj) {
        this.property = obj;
        return this;
    }
}
