package kiparo;

import kiparo.model.Root;

public class Main {
    public static void main(String[] args) {
        JsonSimpleParser parser = new JsonSimpleParser();
        Root root = parser.parse();

        System.out.println("Root " + root.toString());

    }

}
