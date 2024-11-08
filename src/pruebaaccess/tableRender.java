/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaaccess;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalTime;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author reto3
 */
public class tableRender extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String X = (String) table.getValueAt(row, 5);
        LocalTime hora = LocalTime.parse(X);
        String y = (String) table.getValueAt(row, 8);
        
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
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(20, 40))) {
                    this.setBackground(Color.red);
                } else
                    this.setBackground(Color.white);
                /*if (table.getValueAt(row, column).equals("ELIMINAR REGISTRO")) {
                    
                }*/
                break;
                
            case 2: //20:00
                if (hora.isAfter(LocalTime.of(20, 01)) && hora.isBefore(LocalTime.of(20, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(20, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 3: //19:30
                if (hora.isAfter(LocalTime.of(19, 31)) && hora.isBefore(LocalTime.of(19, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(19, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 4: //19:00
                if (hora.isAfter(LocalTime.of(19, 01)) && hora.isBefore(LocalTime.of(19, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(19, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 5: //18:00
                if (hora.isAfter(LocalTime.of(18, 01)) && hora.isBefore(LocalTime.of(18, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(18, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 6: //16:00
                if (hora.isAfter(LocalTime.of(16, 01)) && hora.isBefore(LocalTime.of(16, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(16, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 7: //15:30
                if (hora.isAfter(LocalTime.of(15, 31)) && hora.isBefore(LocalTime.of(15, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(15, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 8: //15:00
                if (hora.isAfter(LocalTime.of(15, 01)) && hora.isBefore(LocalTime.of(15, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(15, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 9: //14:00
                if (hora.isAfter(LocalTime.of(14, 01)) && hora.isBefore(LocalTime.of(14, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(14, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 10: //13:00
                if (hora.isAfter(LocalTime.of(13, 01)) && hora.isBefore(LocalTime.of(13, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(13, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 11: //12:30
                if (hora.isAfter(LocalTime.of(12, 31)) && hora.isBefore(LocalTime.of(12, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(12, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 12: //12:00
                if (hora.isAfter(LocalTime.of(12, 01)) && hora.isBefore(LocalTime.of(12, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(12, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 13: //11:30
                if (hora.isAfter(LocalTime.of(11, 31)) && hora.isBefore(LocalTime.of(11, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(11, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 14: //11:00
                if (hora.isAfter(LocalTime.of(11, 01)) && hora.isBefore(LocalTime.of(11, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(11, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 15: //10:00
                if (hora.isAfter(LocalTime.of(10, 01)) && hora.isBefore(LocalTime.of(10, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(10, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 16: //9:30
                if (hora.isAfter(LocalTime.of(9, 31)) && hora.isBefore(LocalTime.of(9, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(9, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 17: //9:00
                if (hora.isAfter(LocalTime.of(9, 01)) && hora.isBefore(LocalTime.of(9, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(9, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 18: //8:00
                if (hora.isAfter(LocalTime.of(8, 01)) && hora.isBefore(LocalTime.of(8, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(8, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 19: //7:30
                if (hora.isAfter(LocalTime.of(7, 31)) && hora.isBefore(LocalTime.of(7, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(7, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 20: //7:00
                if (hora.isAfter(LocalTime.of(7, 01)) && hora.isBefore(LocalTime.of(7, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(7, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 21: //6:30
                if (hora.isAfter(LocalTime.of(6, 31)) && hora.isBefore(LocalTime.of(6, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(6, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 22: //6:00
                if (hora.isAfter(LocalTime.of(6, 01)) && hora.isBefore(LocalTime.of(6, 10))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(6, 10))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);
                break;
                
            case 23: //5:30
                if (hora.isAfter(LocalTime.of(5, 31)) && hora.isBefore(LocalTime.of(5, 40))) {
                    this.setBackground(Color.yellow);
                } else if (hora.isAfter(LocalTime.of(5, 40))) {
                    this.setBackground(Color.red);
                }else
                    this.setBackground(Color.white);    
                break;
                
            
            default:
                
        }
        
        if (y.equals("ELIMINAR REGISTRO")) {
            this.setBackground(Color.black);
         }
        
        if (y.equals("SE NECESITA VERIFICAR SI EXISTE HORARIO")) {
            this.setBackground(Color.pink);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
