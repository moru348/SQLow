# SQLowForJava
最近JavaにハマってたのでGo向けに書いたSQLowを書き直してます。
## 特徴
### ・簡単
<b>SQLに簡単に接続し、構文などをミスなく生成できます。</b><br><br>
例: ```Table table = new Table("test", new Column[]{new Column("id", DataType.BIGINT).setAutoIncrement(true).setPrimaryKey(true), new Column("name", DataType.TEXT).setNotNull(true)});```
<br>
<br>
### ・拡張性
<b>オリジナルの型を追加しString, Intなど以外に独自の型も使用できます。</b><br><br>
例: ここでは Conv クラス上に String fromItemStack(Object itemStack), ItemStack toItemStack(Object itemStack) メソッドがあると仮定しています。<br>※ItemStackはBukkitPluginのオリジナルの型です
<br>
```DataType<?> ITEMSTACK = new DataType<>("LONGTEXT", false, false, false, false, false, 2147483647, Conv::fromItemStack, Conv::toItemStack, ItemStack.class, 1);```
<br><br>
SQLiteについて。<br>
SQLiteはDatabaseTypeを使用せずにnew SQLow("sqlow", "root")のように使用してください。
