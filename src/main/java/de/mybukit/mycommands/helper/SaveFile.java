package de.mybukkit.mycommands.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class SaveFile {

    public File file;
    public String name;
    public String path;
    public ArrayList<String> data = new ArrayList<>();

    String mask = "what you see is not what it is.";


    String sign = ".(OAo)o ";


    public SaveFile(String name, String path) {
        this.name = name;
        this.path = path;
        this.file = new File(path + name);
    }

    protected SaveFile() {
    }

    public void createFile() {
        if (this.file.exists()) {
            return;
        }
        File pathFile = new File(this.path);

        pathFile.mkdirs();

        try {
            this.file.createNewFile();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public void save(boolean hide) {
        createFile();

        try {
            PrintWriter out = new PrintWriter(this.file);

            if (hide) {
                out.println(this.sign);
            }

            int index = 0;
            for (String line : this.data) {

                for (int i = 0; i < line.length(); i++) {

                    char character = line.charAt(i);

                    if (hide) {

                        character = (char) ((character + this.mask.charAt(index) - 64) % 95);
                        character = (char) (character + 32);

                        index++;
                        index %= this.mask.length();
                    }

                    out.print(character);
                }
                out.println();
                out.flush();
            }
            out.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }


    public void load() {
        createFile();
        clear();
        boolean hide = false;
        int index = 0;

        try {
            Scanner scan = new Scanner(this.file);

            while (scan.hasNext()) {

                String rawString = scan.nextLine();
                String line = "";

                if (rawString.equals(this.sign)) {

                    hide = true;

                    continue;
                }
                if (hide) {

                    for (int i = 0; i < rawString.length(); i++) {

                        char character = rawString.charAt(i);

                        if (hide) {

                            character = (char) (character - 32);
                            character = (char) ((character - this.mask.charAt(index) - 32 + 95) % 95
                                + 32);
                        }

                        line = line + character;
                        index++;
                        index %= this.mask.length();
                    }

                } else {

                    line = rawString;
                }

                this.data.add(line);
            }

            scan.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }


    public void clear() {
        this.data.clear();
    }


    public boolean exists() {
        return this.file.exists();
    }

    public String getSingleData(String name) {
        for (String aData : this.data) {
            if (aData.contains(name)) {
                return aData;
            }
        }
        return null;
    }

    public boolean isBoolean(String name) {
      return name.contains("true") || name.contains("false");
    }

    public boolean getBoolean(String name) {
        String aData = getSingleData(name);
        String[] split = null;
        if (isBoolean(aData)) {
            split = aData.split("=");
        }
        return Boolean.parseBoolean(split[1]);
    }

    public String getString(String name) {
        String aData = getSingleData(name);
        String[] split = aData.split("=");
        return split[1];
    }
}
