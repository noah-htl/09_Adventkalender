package at.htlsaafelden.adventkalender.File;

import java.io.*;

public class FileLoader {
    boolean[] values = new boolean[24];
    File file;

    private static FileLoader INSTANCE;
    private static FileLoader getInstance() {
        if(INSTANCE == null) INSTANCE = new FileLoader("save.txt");
        return INSTANCE;
    }

    private FileLoader(String name) {
        this.file = new File(name);
        if(!this.file.exists()) {
            try {
                this.saveAll();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.loadAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAll() throws IOException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 24; i++) {
            sb.append(values[i] ? 1 : 0);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
        writer.write(sb.toString());

        writer.close();
    }

    private void loadAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.file));

        char[] s = reader.readLine().toCharArray();

        for (int i = 0; i < 24; i++) {
            this.values[i] = s[i] == '1';
        }

        reader.close();
    }

    public static void save(int id, boolean open) {
        FileLoader loader = getInstance();
        loader.values[id-1] = open;
        try {
            loader.saveAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean load(int id) {
        FileLoader loader = getInstance();
        return loader.values[id-1];
    }
}
