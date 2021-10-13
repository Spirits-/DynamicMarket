import data.loaded.Region;
import data.loaded.instances.ShopInstance;
import data.readers.ShopReader;
import data.readers.StockReader;
import generators.MarketGenerator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static final int ASCII_OFFSET_NUM = 48;

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("ERROR: Please specify a data source file.");
            System.exit(1);
        }
        if (!args[0].endsWith(".xlsx")) {
            System.out.println("ERROR: Please specify a Excel (2007 or later) source file.");
            System.exit(2);
        }

        FileInputStream fis = new FileInputStream(args[0]);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        StockReader stockReader = new StockReader(wb).init();
        ShopReader shopReader = new ShopReader(wb).init();
        MarketGenerator generator = new MarketGenerator();

        //System.exit(69);

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        int choice;

        do {
            System.out.println("Market Generator");
            System.out.println("0) Quit");
            System.out.println("1) Generate Market");
            System.out.print("Please enter your choice: ");
            choice = consoleInput.readLine().charAt(0) - ASCII_OFFSET_NUM;
            if (choice > 1) {
                System.out.println("Please enter a valid number. Value entered: " + choice);
                continue;
            }

            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Select a region: \n (P)aperkind, (D)usgar, (M)irrimam, Ba(Z)aar, (Y)ackrix, (S)hattered Island, (U)underix, (O)rterix, (A)scensia, (B)aktix, (W)ortsmar, (K)roaka, (T)ralarry, (C)oar, (G)ranvidas, (F)lamka");
                    String input = consoleInput.readLine();
                    List<Region> regs = readRegionsFromInput(input);
                    System.out.println("Region(s) selected: " + Arrays.toString(regs.toArray()));
                    if (regs.isEmpty()) {
                        System.out.println("ERROR: REGION NOT RECOGNIZED");
                        break;
                    }
                    List<ShopInstance> market = generator.generateMarket(regs);
                    for (ShopInstance shop : market) {
                        System.out.println(shop);
                    }
                }
            }
        } while (true);
    }

    private static List<Region> readRegionsFromInput(String in) {
        String processedIn = in.replace(", ", "").replace(",", "");
        char[] inArr = processedIn.toCharArray();
        List<Region> result = new ArrayList<>();
        for (char c : inArr) {
            if (Region.isViableLetter(c)) {
                result.add(Region.fromCharacter(c));
            }
        }
        return result;
    }
}