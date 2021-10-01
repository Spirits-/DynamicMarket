package data.readers;

import data.loaded.Item;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class StockReader {

    XSSFWorkbook wb;

    public StockReader(XSSFWorkbook wb) {
        this.wb = wb;
    }

    public StockReader init() {
        readStocks("Stocks", Item.getMaster());
        readStocks("id_Carving", Item.getNecklace());
        readStocks("id_StrangersList", Item.getStranger());
        readStocks("id_Wondrous", Item.getWonders());
        return this;
    }

    private void readStocks(String sheetName, List<Item> destinationList) {
        XSSFSheet sheet = wb.getSheet(sheetName);
        boolean skipFirst = false;
        Item item;
        for (Row row : sheet) {
            int firstCellNum = row.getFirstCellNum();
            if (firstCellNum == -1) {
                break;
            }
            if (!skipFirst) {
                skipFirst = true;
                continue;
            }
            item = readItem(row);
            destinationList.add(item);
        }
    }

    private Item readItem(Row row) {
        Item.Builder builder = new Item.Builder();

        for (Cell cell : row) {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case NUMERIC -> determineAndSetMaxMinRollBound(builder, cell);
                case STRING -> determineAndSetNameMultipleRolls(builder, cell);
            }
        }

        return builder.build();
    }

    private void determineAndSetNameMultipleRolls(Item.Builder builder, Cell cell) {
        String cellVal = cell.getStringCellValue();
        boolean isStringName = isStringName(cellVal);
        if (isStringName) {
            builder.setName(cellVal);
        } else {
            boolean multipleRolls = cellVal.equalsIgnoreCase("yes");
            builder.setMultipleRolls(multipleRolls);
        }
    }

    private void determineAndSetMaxMinRollBound(Item.Builder builder, Cell cell) {
        int cellNum = (int) cell.getNumericCellValue();
        if (cellNum < builder.getMinRolls()) {
            builder.setMinRolls(cellNum);
        }
        if (cellNum > builder.getMaxRolls()) {
            builder.setMaxRolls(cellNum);
        }
    }

    private boolean isStringName(String stringCellValue) {
        return !stringCellValue.equalsIgnoreCase("yes") && !stringCellValue.equalsIgnoreCase("no");
    }
}
