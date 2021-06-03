import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;

public class Table extends DBConnection {
    static String pathname = "hashTable.xlsx";

    public static void writeIntoExcel(String key, Date value) throws IOException, InvalidFormatException {
        try {
            DBConnection.add(key, value);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return;
        }

        FileInputStream inputStream = new FileInputStream(pathname);
        Workbook book = WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int lastRow = sheet.getLastRowNum();

        // Нумерация начинается с нуля
        Row row = sheet.createRow(lastRow + 1);

        // Мы запишем имя и дату в два столбца
        // имя будет String, а дата рождения --- Date,
        // формата dd.mm.yyyy
        Cell name = row.createCell(0);
        CellStyle style = book.createCellStyle();

        name.setCellValue(key);
        name.setCellStyle(style);

        Cell birthdate = row.createCell(1);

        DataFormat format = book.createDataFormat();
        CellStyle dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
        birthdate.setCellStyle(dateStyle);
        birthdate.setCellValue(value);

        // Меняем размер(ширину) столбца
        sheet.autoSizeColumn(1);

        // Записываем всё в файл
        sheet.setZoom(5, 2);
        FileOutputStream outputStream = new FileOutputStream(pathname);
        book.write(outputStream);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(pathname));

        book.close();
    }

    public static void getTable(String parameter, String name) throws IOException {

        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(pathname));
        XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
        HashTable<String, Date> map = null;
        try {
            map = DBConnection.getAllBirthdays();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        assert map != null;


        switch (parameter) {
            case "all" -> map.getAll();
            case "get" -> {
                Date date = map.get(name);
                if (date == null) {
                    System.out.println(name + "`s birthday wasn't found");
                    break;
                }
                System.out.println(name + "`s birthday is " + date);
            }
            case "remove" -> {
                Date remove = map.remove(name);
                try {
                    DBConnection.remove(name);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                    return;
                }


                XSSFRow row;
                for (int i = 1; i < myExcelSheet.getLastRowNum(); i++) {
                    row = myExcelSheet.getRow(i);
                    if(row.getCell(0).toString().equals(name)){
                        myExcelSheet.removeRow(row);
                    }
                }
                if(remove == null){
                    System.out.println(name+" wasn't found");
                    return;
                }
                System.out.println(name + "`s birthday(" + remove + ") was removed");
            }
        }

        FileOutputStream outputStream = new FileOutputStream(pathname);
        myExcelBook.write(outputStream);
        myExcelBook.close();
        outputStream.close();
    }
}
