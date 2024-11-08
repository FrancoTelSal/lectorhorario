/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaaccess;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExportarExcel {

    public void exportarExcel(JTable t, JTable t2) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Horarios Procesados");
                hoja.setDisplayGridlines(false);
                if (t.getRowCount() > 0) {
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row fila = hoja.createRow(f);
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (f == 0) {

                                celda.setCellValue(t.getColumnName(c));
                            }
                        }
                    }
                    int filaInicio = 1;
                    for (int f = 0; f < t.getRowCount(); f++) {
                        Row fila = hoja.createRow(filaInicio);
                        filaInicio++;
                        for (int c = 0; c < t.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (t.getValueAt(f, c) instanceof Double) {
                                celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                            } else if (t.getValueAt(f, c) instanceof Float) {
                                celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                            } else {
                                celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                            }
                        }
                    }
                    for (int i = 0; i < libro.getSheetAt(0).getRow(0)
                            .getLastCellNum(); i++) {
                        libro.getSheetAt(0).autoSizeColumn((short) i);
                    }
                }

                if (t2.getRowCount() > 0) {
                    Sheet hoja2 = libro.createSheet("Marcaciones por Persona");
                    hoja2.setDisplayGridlines(true);

                    for (int f = 0; f < t2.getRowCount(); f++) {
                        Row fila = hoja2.createRow(f);
                        for (int c = 0; c < t2.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (f == 0) {

                                celda.setCellValue(t2.getColumnName(c));
                            }
                        }
                    }
                    int filaInicio1 = 1;
                    for (int f = 0; f < t2.getRowCount(); f++) {
                        Row fila = hoja2.createRow(filaInicio1);
                        filaInicio1++;
                        System.out.println("Fila #" + f);
                        for (int c = 0; c < t2.getColumnCount(); c++) {
                            Cell celda = fila.createCell(c);
                            if (!(t2.getValueAt(f, c)==null))    {
                                celda.setCellValue(String.valueOf(t2.getValueAt(f, c)));
                            }
                            
                        }
                    }
                }

                for (int i = 0; i < libro.getSheetAt(1).getRow(0)
                        .getLastCellNum(); i++) {
                    libro.getSheetAt(0).autoSizeColumn((short) i);
                }
                colorearCeldas((HSSFWorkbook) libro, 5);
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;

            }
        }
    }

    public void colorearCeldas(Workbook fWorkbook, int nCelda) {
        Sheet mySheet = fWorkbook.getSheetAt(0);
        int opcion = 0;
        //HSSFSheet mySheet = fWorkbook.getSheetAt(0);

        // Creamos el estilo de celda del color ROJO = 1
        CellStyle styleGroup2 = fWorkbook.createCellStyle();
        styleGroup2.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleGroup2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Creamos el estilo de celda del color AMARILLO = 2
        CellStyle styleGroup1 = fWorkbook.createCellStyle();
        styleGroup1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleGroup1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Creamos el estilo de celda del color BLANCO = 3
        CellStyle styleGroup0 = fWorkbook.createCellStyle();
        styleGroup0.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        styleGroup0.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Creamos el estilo de celda del color NEGRO = 4
        CellStyle styleGroupBLS = fWorkbook.createCellStyle();
        styleGroupBLS.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        styleGroupBLS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Creamos el estilo de celda del color ROSA = 5
        CellStyle styleGroupPIN = fWorkbook.createCellStyle();
        styleGroupPIN.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        styleGroupPIN.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Recorrer cada columna del excel
        // Comenzamos en 1 porque la 0 es el header del excel
        for (int i = 1; i <= mySheet.getLastRowNum(); i++) {
            Row myRow = mySheet.getRow(i);

            // Recorremos sobre cada celda de la columna seleccionada
            Iterator cellIter = myRow.cellIterator();
            while (cellIter.hasNext()) {
                opcion = 0;
                Cell myCell = (HSSFCell) cellIter.next();
                // Cuando sea la celda correcta
                if (myCell.getColumnIndex() == nCelda) {
                    LocalTime hora = LocalTime.parse(myCell.toString());

                    int HoraIndex = 0;

                    if (hora.isAfter(LocalTime.of(20, 30))) { //21:00
                        HoraIndex = 1;
                    } else if (hora.isAfter(LocalTime.of(19, 45))) { //20:00
                        HoraIndex = 2;
                    } else if (hora.isAfter(LocalTime.of(19, 15))) { //19:30
                        HoraIndex = 3;
                    } else if (hora.isAfter(LocalTime.of(18, 30))) { //19:00
                        HoraIndex = 4;
                    } else if (hora.isAfter(LocalTime.of(17, 0))) { //18:00
                        HoraIndex = 5;
                    } else if (hora.isAfter(LocalTime.of(15, 45))) { //16:00
                        HoraIndex = 6;
                    } else if (hora.isAfter(LocalTime.of(15, 15))) { //15:30
                        HoraIndex = 7;
                    } else if (hora.isAfter(LocalTime.of(14, 30))) { //15:00
                        HoraIndex = 8;
                    } else if (hora.isAfter(LocalTime.of(13, 30))) { //14:00
                        HoraIndex = 9;
                    } else if (hora.isAfter(LocalTime.of(12, 45))) { //13:00
                        HoraIndex = 10;
                    } else if (hora.isAfter(LocalTime.of(12, 15))) { //12:30
                        HoraIndex = 11;
                    } else if (hora.isAfter(LocalTime.of(11, 45))) { //12:00
                        HoraIndex = 12;
                    } else if (hora.isAfter(LocalTime.of(11, 15))) { //11:30
                        HoraIndex = 13;
                    } else if (hora.isAfter(LocalTime.of(10, 40))) { //11:00
                        HoraIndex = 14;
                    } else if (hora.isAfter(LocalTime.of(9, 45))) { //10:00
                        HoraIndex = 15;
                    } else if (hora.isAfter(LocalTime.of(9, 15))) { //09:30
                        HoraIndex = 16;
                    } else if (hora.isAfter(LocalTime.of(8, 30))) { //09:00
                        HoraIndex = 17;
                    } else if (hora.isAfter(LocalTime.of(7, 45))) { //08:00
                        HoraIndex = 18;
                    } else if (hora.isAfter(LocalTime.of(7, 15))) { //07:30
                        HoraIndex = 19;
                    } else if (hora.isAfter(LocalTime.of(6, 40))) { //07:00
                        HoraIndex = 20;
                    } else if (hora.isAfter(LocalTime.of(6, 15))) { //06:30
                        HoraIndex = 21;
                    } else if (hora.isAfter(LocalTime.of(5, 45))) { //06:00
                        HoraIndex = 22;
                    } else if (hora.isAfter(LocalTime.of(5, 0))) { //05:30
                        HoraIndex = 23;
                    }

                    switch (HoraIndex) {
                        case 1: //21:00
                            if (hora.isAfter(LocalTime.of(21, 01)) && hora.isBefore(LocalTime.of(21, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                            } else if (hora.isAfter(LocalTime.of(21, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 2: //20:00
                            if (hora.isAfter(LocalTime.of(20, 01)) && hora.isBefore(LocalTime.of(20, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(20, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 3: //19:30
                            if (hora.isAfter(LocalTime.of(19, 31)) && hora.isBefore(LocalTime.of(19, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(19, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 4: //19:00
                            if (hora.isAfter(LocalTime.of(19, 01)) && hora.isBefore(LocalTime.of(19, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(19, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 5: //18:00
                            if (hora.isAfter(LocalTime.of(18, 01)) && hora.isBefore(LocalTime.of(18, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(18, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 6: //16:00
                            if (hora.isAfter(LocalTime.of(16, 01)) && hora.isBefore(LocalTime.of(16, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(16, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 7: //15:30
                            if (hora.isAfter(LocalTime.of(15, 31)) && hora.isBefore(LocalTime.of(15, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(15, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 8: //15:00
                            if (hora.isAfter(LocalTime.of(15, 01)) && hora.isBefore(LocalTime.of(15, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(15, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 9: //14:00
                            if (hora.isAfter(LocalTime.of(14, 01)) && hora.isBefore(LocalTime.of(14, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(14, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 10: //13:00
                            if (hora.isAfter(LocalTime.of(13, 01)) && hora.isBefore(LocalTime.of(13, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(13, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 11: //12:30
                            if (hora.isAfter(LocalTime.of(12, 31)) && hora.isBefore(LocalTime.of(12, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(12, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 12: //12:00
                            if (hora.isAfter(LocalTime.of(12, 01)) && hora.isBefore(LocalTime.of(12, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(12, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 13: //11:30
                            if (hora.isAfter(LocalTime.of(11, 31)) && hora.isBefore(LocalTime.of(11, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(11, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 14: //11:00
                            if (hora.isAfter(LocalTime.of(11, 01)) && hora.isBefore(LocalTime.of(11, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(11, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 15: //10:00
                            if (hora.isAfter(LocalTime.of(10, 01)) && hora.isBefore(LocalTime.of(10, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(10, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 16: //9:30
                            if (hora.isAfter(LocalTime.of(9, 31)) && hora.isBefore(LocalTime.of(9, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(9, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 17: //9:00
                            if (hora.isAfter(LocalTime.of(9, 01)) && hora.isBefore(LocalTime.of(9, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(9, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 18: //8:00
                            if (hora.isAfter(LocalTime.of(8, 01)) && hora.isBefore(LocalTime.of(8, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(8, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 19: //7:30
                            if (hora.isAfter(LocalTime.of(7, 31)) && hora.isBefore(LocalTime.of(7, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(7, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 20: //7:00
                            if (hora.isAfter(LocalTime.of(7, 01)) && hora.isBefore(LocalTime.of(7, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(7, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 21: //6:30
                            if (hora.isAfter(LocalTime.of(6, 31)) && hora.isBefore(LocalTime.of(6, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(6, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 22: //6:00
                            if (hora.isAfter(LocalTime.of(6, 01)) && hora.isBefore(LocalTime.of(6, 10))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(6, 10))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        case 23: //5:30
                            if (hora.isAfter(LocalTime.of(5, 31)) && hora.isBefore(LocalTime.of(5, 40))) {
                                //myCell.setCellStyle(styleGroup1);
                                opcion = 2;
                                //myRow.setRowStyle(styleGroup1);
                            } else if (hora.isAfter(LocalTime.of(5, 40))) {
                                //myCell.setCellStyle(styleGroup2);
                                opcion = 1;
                            }
                            break;

                        default:
                            throw new AssertionError();
                    }
                }

                if (myCell.toString().equals("ELIMINAR REGISTRO")) {
                    //myCell.setCellStyle(styleGroupBLS);
                    //myCell.setCellStyle(styleGroupBLS);
                    opcion = 4;
                }
                if (myCell.toString().equals("SE NECESITA VERIFICAR SI EXISTE HORARIO")) {
                    //myCell.setCellStyle(styleGroupPIN);
                    opcion = 5;
                    //myRow.setRowStyle(styleGroupPIN);
                }
                switch (opcion) {
                    case 1:
                        for (int l = 0; l < 10; l++) {
                            myRow.getCell(l).setCellStyle(styleGroup2);
                        }
                        break;
                    case 2:
                        for (int l = 0; l < 10; l++) {
                            myRow.getCell(l).setCellStyle(styleGroup1);
                        }
                        break;
                    case 4:
                        for (int l = 0; l < 10; l++) {
                            myRow.getCell(l).setCellStyle(styleGroupBLS);
                        }
                        break;
                    case 5:
                        for (int l = 0; l < 10; l++) {
                            myRow.getCell(l).setCellStyle(styleGroupPIN);
                        }
                        break;
                }
            }

        }

    }

}
