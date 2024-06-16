package com.programming.bookservice.common;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.domain.Category;
import com.programming.bookservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelUtility {

    private static CategoryRepository categoryRepository;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//    public static String TYPE = "application/vnd.oasis.opendocument.spreadsheet";
    static String[] HEADERs = { "Book Name", "Publisher Name", "Publish Date", "Author Name", "Description", "Price" };
    static String SHEET = "book";

    public static void setCategoryRepository(CategoryRepository repository) {
        categoryRepository = repository;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        log.info(file.getContentType());
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Book> excelToBookList(InputStream inputStream){
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Book> bookList = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Book book = new Book();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx){
                        case 0:
                            book.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            book.setPublisherName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                book.setPublishDate(sdf.get2DigitYearStart());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        case 3:
                            book.setAuthorName(currentCell.getStringCellValue());
                            break;
                        case 4:
                            book.setDescription(currentCell.getStringCellValue());
                            break;
                        case 5:
                            book.setPrice(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 6:
                            book.setCategoryId((long) currentCell.getNumericCellValue());
                        default:
                            break;
                    }
                    cellIdx++;
                }
                bookList.add(book);
            }
            workbook.close();
            return bookList;
        }catch (IOException e){
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
