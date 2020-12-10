package me.moru3.sqlow;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DataTypes {
    public static final TreeMap<Integer, List<DataType<?>>> dataTypes = new TreeMap<>();

    public static void add(DataType<?> type) {
        if(dataTypes.get(type.getPriority())!=null) {
            List<DataType<?>> temp = dataTypes.get(type.getPriority());
            temp.add(type);
            dataTypes.put(type.getPriority(), temp);
        } else {
            List<DataType<?>> temp = new ArrayList<>();
            temp.add(type);
            dataTypes.put(type.getPriority(), temp);
        }
    }

    public static TreeMap<Integer, List<DataType<?>>> get() {
        if(dataTypes.size()<1) {
            new DataType<>();
        }
        return dataTypes;
    }
}
