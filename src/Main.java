import data.loaded.Region;
import data.loaded.instances.ShopInstance;
import data.readers.ShopReader;
import data.readers.StockReader;
import generators.MarketGenerator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
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

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Market Generator");
            System.out.println("0) Quit");
            System.out.println("1) Generate Market");
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Select a region: \n (P)aperkind, (D)usgar, (M)irrimam, Ba(Z)aar, (Y)ackrix, (S)hattered Island, (U)underix, (O)rterix, (A)scensia, (B)aktix, (W)ortsmar, (K)roaka, (T)ralarry, (C)oar, (G)ranvidas, (F)lamka");
                    Region reg = Region.fromCharacter(sc.next().charAt(0));
                    if (reg == null) {
                        System.out.println("ERROR: REGION NOT RECOGNIZED");
                        break;
                    }
                    List<ShopInstance> market = generator.generateMarket(reg);
                    for (ShopInstance shop : market) {
                        System.out.println(shop);
                    }
                }
            }
        } while (true);
    }
}