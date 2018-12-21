package com.github.upcraftlp.glasspane.api.util.serialization.datareader;

import java.io.*;
import java.util.Scanner;

public class TextDataReader implements DataReader<String> {

    @Override
    public String readData(InputStream in) {
        StringBuilder builder = new StringBuilder();
        if(in != null) {
            Scanner sc = new Scanner(new BufferedInputStream(in));
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if(line.startsWith("//")) continue;
                if(!line.trim().isEmpty()) builder.append(line);
                if(sc.hasNextLine()) builder.append("\n");
            }
        }
        return builder.toString();
    }
}
