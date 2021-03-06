/*
 * Copyright (c) 2017. Eric Angeli
 *
 *  Permission is hereby granted, free of charge,
 *  to any person obtaining a copy of this software
 *  and associated documentation files (the "Software"),
 *  to deal in the Software without restriction,
 *  including without limitation the rights to use, copy,
 *  modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit
 *  persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission
 *  notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 *  AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 */
package com.thegoate.spreadsheets;

/**
 * SpreadSheet for interacting with a sheet.
 * get by (col#,row#) or by (col name,row#)
 * Created by Eric Angeli on 9/14/2017.
 */
public class SpreadSheet {
    public static String headers = "headers";
    public static String rowCount = "rowCount";
    public static String colCount = "colCount";
    public static String listSheets = "sheet list";

    public static String loadSheet(String sheetName) {
        return "load sheet:=" + sheetName;
    }

    public static String getRow(int rowNumber) {
        return getRow("" + rowNumber);
    }

    public static String getRow(String rowNumber) {
        return "get row#" + rowNumber;
    }

    public static String getCell(int row, int columnNumber){
        return getCell(""+row, ""+columnNumber);
    }

    public static String getCell(int row, String columnNumber){
        return getCell(""+row, columnNumber);
    }

    public static String getCell(String row, int columnNumber){
        return getCell(row, ""+columnNumber);
    }

    public static String getCell(String row, String column){
        return row + "." + column;
    }

    public static String getCell(String sheetName, int row, int columnNumber){
        return getCell(sheetName, ""+row, ""+columnNumber);
    }
    public static String getCell(String sheetName, int row, String columnNumber){
        return getCell(sheetName, ""+row, columnNumber);
    }
    public static String getCell(String sheetName, String row, int columnNumber){
        return getCell(sheetName, row, ""+columnNumber);
    }
    public static String getCell(String sheetName, String row, String columnNumber){
        return sheetName+":"+row+"."+columnNumber;
    }
}
