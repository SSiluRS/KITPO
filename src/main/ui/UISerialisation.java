package main.ui;

import main.data.IList;
import main.data.builder.UserTypeBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class UISerialisation {

    public static  void saveToFile(String filename, IList list, UserTypeBuilder builder) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(builder.typeName());
            list.forEach(el -> writer.println(builder.toString(el)));
        }
    }

    public static  IList loadFromFile(String filename, UserTypeBuilder builder, IList list) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            line = br.readLine();
            if (!builder.typeName().equals(line)) {
                throw new Exception("Wrong file structure");
            }

            while ((line = br.readLine()) != null) {
                list.add(builder.createFromString(line));
            }
            return list;
        }
    }
}
