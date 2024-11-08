/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaaccess;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
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

/**
 *
 * @author reto3
 */
public class Array_Excel {

    DateTimeFormatter fechaNuFor = DateTimeFormatter.ofPattern("EEE dd LLL uuu");
    DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void excelArray(String[] cabecera, List<datosMostrar> array, File archivoXLS) throws IOException {
        try {
            Workbook libro = new HSSFWorkbook();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            Sheet hoja = libro.createSheet("Horarios Procesados");
            hoja.setDisplayGridlines(false);
            for (int f = 0; f < 1; f++) {
                Row fila = hoja.createRow(f);
                for (int c = 0; c < cabecera.length; c++) {
                    Cell celda = fila.createCell(c);
                    celda.setCellValue(cabecera[c]);
                }
            }

            int filaInicio = 1;
            Cell celda;
            for (int f = 0; f < array.size(); f++) {
                if ((filaInicio - 1) <= array.size()) {
                    Row fila = hoja.createRow(filaInicio);

                    String[] a = {array.get(filaInicio - 1).getBadgenum(), array.get(filaInicio - 1).getName(),
                        array.get(filaInicio - 1).getCedula(), array.get(filaInicio - 1).getCargo(),
                        fechaNuFor.format(array.get(filaInicio - 1).getHyf_primera()),
                        formatHora.format(array.get(filaInicio - 1).getHyf_primera()),
                        fechaNuFor.format(array.get(filaInicio - 1).getHyf_ultima()),
                        formatHora.format(array.get(filaInicio - 1).getHyf_ultima()),
                        array.get(filaInicio - 1).getHorario(), array.get(filaInicio - 1).getCalculo()};

                    for (int i = 0; i < cabecera.length; i++) {
                        celda = fila.createCell(i);
                        celda.setCellValue(a[i]);

                    }
                    filaInicio++;
                }
            }

            for (int i = 0; i < libro.getSheetAt(0).getRow(0)
                    .getLastCellNum(); i++) {
                libro.getSheetAt(0).autoSizeColumn((short) i);
            }
            colorearCeldas((HSSFWorkbook) libro, 5);
            libro.write(archivo);
            archivo.close();
            Desktop.getDesktop().open(archivoXLS);
        } catch (Exception e) {
            throw e;
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
