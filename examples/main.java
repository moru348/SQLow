import me.moru3.Column;
import me.moru3.DataType;
import me.moru3.Table;
import me.moru3.exceptions.NoPropertyException;

public class Example {
    public static void main(String[] args) {
        Table table = new Table("test", new Column[]{new Column("id", DataType.BIGINT).setAutoIncrement(true).setPrimaryKey(true), new Column("name", DataType.VARCHAR).setNotNull(true).setDefault("ななしさん")});
        try {
            System.out.println(table.build(false));
        } catch (NoPropertyException e) {
            e.printStackTrace();
        }
    }
}
public class Example2 {
    public static void main(String[] args) {
        Table table = new Table("test", new Column[]{new Column("id", DataType.BIGINT).setAutoIncrement(true).setPrimaryKey(true), new Column("name", DataType.VARCHAR).setNotNull(true).setDefault("ななしさん")});
        try {
            System.out.println(table.build(false));
        } catch (NoPropertyException e) {
            e.printStackTrace();
        }
    }
}