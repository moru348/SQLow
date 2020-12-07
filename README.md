# SQLowForJava
最近JavaにハマってたのでGo向けに書いたSQLowを書き直してます。
## 特徴
### ・簡単
<b>SQLに簡単に接続し、構文などをミスなく生成できます。</b><br><br>
例: ```Table("UserData", new ArrayList<Column>(Column("id", DataType.INT).setAutoIncrement(true).setPrimaryKey(true), Column("name", DataType.TEXT).setNotNull(true)))```
<br>
<br>
### ・拡張性
<b>オリジナルの型を追加しString, Intなど以外に独自の型も使用できます。</b><br><br>
例: ここでは Conv クラス上に String itemStack2SQLObj(Object itemStack) メソッドがあると仮定しています。<br>※ItemStackはBukkitPluginのオリジナルの型です
<br>
```DataType<ItemStack> = DataType<>(TEXT", false, false, false, false, false, Conv::itemStack2SQLObj);```
<br>※オリジナルの型の定義方法はいつか変える予定です。