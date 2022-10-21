package main.ui;

import main.data.IList;
import main.data.builder.UserTypeBuilder;

import java.io.*;

public class UISerialisation {

    public static  void saveToFile(String filename, IList list, UserTypeBuilder builder) throws FileNotFoundException {
        try (FileOutputStream fos = new FileOutputStream(filename)){
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(builder.typeName());
            out.writeObject(list.size());
            list.forEach(el -> {
                try {
                    out.writeObject(builder.toString(el));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  IList loadFromFile(String filename, UserTypeBuilder builder, IList list) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            String line;

            line = (String) (in.readObject());
            if (!builder.typeName().equals(line)) {
                throw new Exception("Wrong file structure");
            }
            var size = (Integer)in.readObject();
            for (int i = 0; i < size; i++) {
                line = (String)in.readObject();
                list.add(builder.createFromString(line));
            }
            return list;
        }
    }
}
