package data.readers;

import data.loaded.Item;
import data.loaded.Region;
import data.loaded.Shop;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

public class ShopReader {

    XSSFWorkbook wb;
    public static final String SHEET_NAME = "Shops";

    public ShopReader(XSSFWorkbook wb) {
        this.wb = wb;
    }

    public ShopReader init() {
        readShops(wb.getSheet(SHEET_NAME), Shop.getGlobalShops());
        return this;
    }

    private void readShops(XSSFSheet sheet, List<Shop> destinationList) {
        boolean skipFirst = false;
        Shop shop;
        for (Row row : sheet) {
            if (row.getFirstCellNum() == -1) {
                break;
            }
            if (!skipFirst) {
                skipFirst = true;
                continue;
            }
            shop = readSingleShop(row);
            destinationList.add(shop);
        }
    }

    private Shop readSingleShop(Row row) {
        Shop.Builder builder = new Shop.Builder();

        final int shopName = 0;
        final int globalStock = 1;
        final int allowedRegions = 2;
        final int itemRolls = 3;
        final int regionalStock = 4;

        for (Cell cell : row) {
            switch (cell.getColumnIndex()) {
                case shopName -> {
                    if (cell.getStringCellValue().equalsIgnoreCase("none")) {
                        return builder.setName(cell.getStringCellValue())
                                .setItemRolls(-1)
                                .setGlobalStock(new ArrayList<>())
                                .setRegionalStock(new HashMap<>())
                                .setAllowedRegions(new ArrayList<>())
                                .build();
                    } else {
                        builder.setName(cell.getStringCellValue());
                    }
                }
                case globalStock -> builder.setGlobalStock(getPossibleStockFromCell(cell));
                case allowedRegions -> builder.setAllowedRegions(getPossibleRegionsFromCell(cell));
                case itemRolls -> builder.setItemRolls((int) cell.getNumericCellValue());
                case regionalStock, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 -> {
                    builder.setRegionalStock(readRegionalStock(cell.getColumnIndex(), row));
                    return builder.build();
                }
            }
        }

        return builder.build();
    }

    private List<Region> getPossibleRegionsFromCell(Cell cell) {
        if ("all".equalsIgnoreCase(cell.getStringCellValue())) {
            return Arrays.asList(Region.values());
        }
        String content = cell.getStringCellValue();
        String[] regionNames = content.split(",");
        List<Region> result = new ArrayList<>(regionNames.length);
        for (String s : regionNames) {
            result.add(Region.fromString(s));
        }
        return result;
    }

    private Map<Region, List<Item>> readRegionalStock(int targetColumn, Row row) {
        Map<Region, List<Item>> result = new HashMap<>();
        int currentColumn = 0;
        for (Cell c : row) {
            if (currentColumn < targetColumn) {
                currentColumn += c.getColumnIndex();
                continue;
            }
            Region region = Region.fromString(getTitleRowCell(c.getColumnIndex()));
            List<Item> items = getPossibleStockFromCell(c);
            result.put(region, items);
        }
        return result;
    }

    private String getTitleRowCell(int currCellIndex) {
        XSSFSheet sheet = wb.getSheet(SHEET_NAME);
        return sheet.getRow(0).getCell(currCellIndex).getStringCellValue();
    }

    private List<Item> getPossibleStockFromCell(Cell cell) {
        List<Item> stock = new ArrayList<>();
        if (cell.getCellTypeEnum() != CellType.STRING) {
            throw new RuntimeException("Was expecting to read a string in cell, instead read " + cell.getCellTypeEnum());
        }
        String[] splitNames = cell.getStringCellValue().split(",");

        for (String s : splitNames) {
            Item it = Item.getFromMasterByName(s.trim());
            stock.add(it);
        }
        return stock;
    }
}
