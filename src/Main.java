import data.loaded.Item;
import data.loaded.Shop;
import data.readers.ShopReader;
import data.readers.StockReader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
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
    }
}