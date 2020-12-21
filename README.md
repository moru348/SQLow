## Currently in beta. There may be bugs.

# SQLow
I've been addicted to Java lately, so I'm rewriting the SQLow I wrote for Go.
## Feature
### ・Easy
<b>Easily connect to SQL and generate syntax etc. without mistakes.</b><br><br>
example: ```Table table = new Table("test").addColumn(new Column("id", DataType.BIGINT).setAutoIncrement(true).setPrimaryKey(true)).addColumn(new Column("id", DataType.VARCHAR).setNotNull(true).setProperty(32));```
<br>
<br>
### ・Scalability
<b>Add own types and use your own types as well as Strings, Int, etc.</b><br><br>
example: It is assumed here that there are String fromItemStack(Object itemStack) and ItemStack toItemStack(Object itemStack) methods on the Conv class. <br> * ItemStack is the original type of Bukkit Plugin.
<br>
```DataType<?> ITEMSTACK = new DataType<>("LONGTEXT", false, false, false, false, false, 2147483647, Conv::fromItemStack, Conv::toItemStack, ItemStack.class, 1);```
<br>
<br>
```https://repo.moru3.dev/```<br>
```me.moru3:sqlow:2.15.8```
