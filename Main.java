import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc;
        try {
            sc = new Scanner(Paths.get("parser.txt"));
            while(sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] splitBloudem = s.split("\uD83D\uDC2A");
                if (splitBloudem.length == 1) { // TODO toto neznamena ze tam neni bloud
                    System.out.println(s);
                }
                else {
                    String[] splitPousti = splitBloudem[1].split("\uD83C\uDFDC");
                    if(splitPousti.length > 1)
                        System.out.println(splitPousti[1]);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
