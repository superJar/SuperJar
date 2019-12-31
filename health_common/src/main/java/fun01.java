import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author:superJar
 * @date:2019/12/20
 * @time:11:10
 * @details:
 */
public class fun01 {
    @Test
    public void fun001() throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook("F:/jar.xlsx");
//
//        XSSFSheet NbaStarSheet = workbook.createSheet("NBAStar");
//
//        XSSFRow row01 = NbaStarSheet.createRow(0);
//
//        row01.createCell(0).setCellValue("Name");
//        row01.createCell(1).setCellValue("Age");
//
//        XSSFRow row02 = NbaStarSheet.createRow(1);
//        row02.createCell(0).setCellValue("James");
//        row02.createCell(1).setCellValue("36");
//
//        XSSFRow row03 = NbaStarSheet.createRow(2);
//        row03.createCell(0).setCellValue("Davis");
//        row03.createCell(1).setCellValue("25");
//
//        FileOutputStream os = new FileOutputStream("F:/nbaStar.xlsx");
//        workbook.write(os);
//        os.flush();
//        os.close();
//
//        workbook.close();

//        String a = "abc.xlsx";
//        System.out.println(a.substring(a.lastIndexOf(".")+1));
    }
}
