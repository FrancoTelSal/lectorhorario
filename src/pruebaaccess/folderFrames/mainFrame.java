/*0
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pruebaaccess.folderFrames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import pruebaaccess.con_Access;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.*;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import pruebaaccess.Array_Excel;
import pruebaaccess.ExportarExcel;
import pruebaaccess.datosMostrar;
import pruebaaccess.datosPersona;
import pruebaaccess.registroBD;
import pruebaaccess.tableRender;

/**
 *
 * @author reto3
 */
public class mainFrame extends javax.swing.JFrame {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat fechaformato = new SimpleDateFormat("yyyy-MM-dd");
    //SimpleDateFormat horaformato = new SimpleDateFormat("HH:mm:ss");

    //DateTimeFormatter nuevoFormat = DateTimeFormatter.ofPattern("EEE dd LLL uuu HH:mm:ss");
    DateTimeFormatter fechaNuFor = DateTimeFormatter.ofPattern("EEE dd LLL uuu");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("EEE dd LLL uuu HH:mm:ss");
    DateTimeFormatter formatFecha1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel modeloProce = new DefaultTableModel();
    DefaultTableModel tableProcesada = new DefaultTableModel();
    DefaultTableModel nomTabla = new DefaultTableModel();
    TableColumnModel columnModel2;
    int max = 0;

    File directorio = new File("\\RegistroHorario");
    File txtFile = new File(directorio.getAbsolutePath() + "\\ruta.txt");
    String cont_ruta = "";
    FileReader fileReader;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;

    con_Access conexion = new con_Access();
    //String[] titulos = {"Badgenumber", "Name", "Cedula", "Cargo", "Fecha y Hora"};
    Connection con;
    Statement s;
    ResultSet rs;
    //List<datoFila> datosLista = new ArrayList<>();
    List<registroBD> registros = new ArrayList<>();
    //List<datosEmpleado> personalLista = new ArrayList<>();
    //List<registroBio> bioLista = new ArrayList<>();
    List<datosMostrar> listaordenada = new ArrayList<>();
    List<registroBD> listaordenada_ = new ArrayList<>();
    List<datosPersona> dPersonna = new ArrayList<>();

    /**
     * Creates new form mainFrame
     */
    public mainFrame() {
        initComponents();
        cargarFrame();
        jTable1.setDefaultEditor(Object.class, null);
        comboBoxSuggestion1.addItem("Seleccione Nombre");

        try {
            //Verificar si existe o no
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {  //En caso de no existir, se crea la nueva carpeta con el nombre de RegistroHorario
                    System.out.println("Directorio creado");

                    //Crear el archivo .txt donde se guardara la ruta de la base de datos
                    if (!txtFile.exists()) { //Si no existe, se crea el archivo .txt
                        txtFile.createNewFile();
                    }
                } else {                    //En caso de que ocurra un problema o sean necesario permiso de administrador (Solucionar)
                    System.out.println("Error al crear directorio");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error l crear el directorio", "ERROR", 1);
            System.out.println(e);
        }

        bdConexion();

        this.jTable1.setDefaultRenderer(Object.class, new tableRender());
        //jComboBox1.addItem(nom_personal.get(0));

    }

    public void bdConexion() {
        //Crear el archivo tipo carpeta donde se guardara el .txt
        //directorio = new File("C:\\RegistroHorario");

        try {

            fileReader = new FileReader(txtFile);
            br = new BufferedReader(fileReader);
            //fw = new FileWriter(txtFile);
            //bw = new BufferedWriter(fw);

            cont_ruta = br.readLine();
            File rtdb = new File(cont_ruta);
            if (rtdb.exists()) {
                conexion.setBd(rtdb);
                con = conexion.conectarBD();
                s = con.createStatement();
                textCarga.setText("Base de datos Cargada con exito.");
            } else {
                System.out.println("No existe base de datos");
            }

            /*if (txtFile.length()==0) {
                cont_ruta = br.readLine();
                //File rtdb = new File(cont_ruta);
                conexion.setBd(rtdb);
                con = conexion.conectarBD();
                s = con.createStatement();
            } else {
                bw.write(cont_ruta);
                bw.close();
                System.out.println("Nueva ruta ingresada"+cont_ruta);
            }*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No existe conexion a base de datos", "ERROR", 1);
            System.out.println(e);
        }

        /*String[] a= {"EXAMINAR","CERRAR"};
        var option = JOptionPane.showOptionDialog(null, "Seleccione archivo access(.mdb,.accdb)", "Archivo ACCESS", WIDTH, -1, null, a, a[1]);
        if (option == 0) {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);
            try {
                conexion.setBd(fc.getSelectedFile());
                con = conexion.conectarBD();
                s = con.createStatement();
            } catch (Exception e) {
                System.out.println("Error al conectar la aplicacion: "+ e);
            }
        } else{
            System.exit(0);
        }*/
    }

    public void existeDatoActual() {
        String stringSQL = "SELECT TOP 1 CHECKTIME FROM CHECKINOUT ORDER BY CHECKTIME DESC;";
        System.out.println(stringSQL);
        String hastaF = "";
        Date fec = null;
        try {
        if (hastaFecha.getDate()==null) {
            hastaF = LocalDateTime.now().format(formatFecha1);
        } else {
            fec = hastaFecha.getDate();
            hastaF = fechaformato.format(fec);
        }
            rs = s.executeQuery(stringSQL);
            LocalDateTime date = LocalDateTime.parse(rs.getString("CHECKTIME"));
            System.out.println(date);
            LocalDateTime dateF = LocalDateTime.parse(hastaF);
            if (dateF.isBefore(date)) {
                JOptionPane.showMessageDialog(null, "Fecha de ultimo dato existente: " + date);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la fecha del ultimo dato");
        }
        datos_BD();
    }

    public void datos_BD() {
        String desdeF = "";
        String hastaF = "";
        Date df = null;

        if (desdeFecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Por favor elija desde que fecha realizar la carga de datos", "ERROR", 0);
        } else if (hastaFecha.getDate() == null) {
            hastaF = LocalDateTime.now().format(formatFecha1);
            df = desdeFecha.getDate();
            desdeF = fechaformato.format(df);
        } else {
            df = desdeFecha.getDate();
            desdeF = fechaformato.format(df);
            df = hastaFecha.getDate();
            hastaF = fechaformato.format(df);
        }

        //OffsetDateTime odt;
        //String stringSQL = "SELECT * FROM temporal";
        String stringSQL = "SELECT temporal.Badgenumber, temporal.Name, temporal.cedula, temporal.cargo, CHECKINOUT.CHECKTIME"
                + " FROM (CHECKINOUT INNER JOIN USERINFO ON CHECKINOUT.USERID = USERINFO.USERID) INNER JOIN temporal ON USERINFO.Badgenumber = temporal.Badgenumber"
                + " WHERE CHECKINOUT.CHECKTIME BETWEEN #" + desdeF + "# AND #" + hastaF + "#;";

        try {
            //jButton3.setText("Buscando datos");
            System.out.println("Buscando datos");
            rs = s.executeQuery(stringSQL);
            //jButton3.setText("Procesando");
            System.out.println("Procesando");
            while (rs.next()) {

                //badge = rs.getString("Badgenumber");
                //name = rs.getString("Name");
                //cedula = rs.getString("cedula");
                //cargo = rs.getString("cargo");
                //checktime = dateFormat.parse(rs.getString("CHECKTIME"));
                //String date = dateFormat.format(checktime);
                String date = dateFormat.format(dateFormat.parse(rs.getString("CHECKTIME")));
                String datemod = date.replace("T", " ");
                //fechayhora = LocalDateTime.parse(datemod, timeFormatter);
                //LocalDate.parse(rs.getString("CHECKTIME"), formatFecha);
                //hora = LocalTime.parse(rs.getString("CHECKTIME"), formatHora);

                //registros.add(new registroBD(badge, name, cedula, cargo, fechayhora));
                registros.add(new registroBD(rs.getString("Badgenumber"), rs.getString("Name"), rs.getString("cedula"),
                        rs.getString("cargo"),
                        LocalDateTime.parse(datemod, timeFormatter)));
                //datosLista.add(new datoFila(badge, name, cedula, cargo, checktime));

            }
            //System.out.println("Registros Guardados");

            //System.out.println(registros.size());
            ordenar();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Ruta de la base de datos no encontrada", "ERROR", 1);

        }
        identificar_horario();
        procesar_datos();

    }

    public void cargarFrame() {
        jTable1.setModel(model);

    }

    public void procesar_datos() {
        if (tableProcesada.getRowCount() > 0) {
            tableProcesada.setRowCount(0);
        }

        //String[] cabecera = {"Badge", "Nombre", "Cedula" ,"Cargo","F. y H. Entrada", "F. y H. Salida", "Horas a Laborar", "Total Horas"};
        String[] cabecera = {"Badge", "Nombre", "Cedula", "Cargo", "Fecha de Entrada", "Hora de Entrada",
            "Fecha de Salida", "Hora de Salida", "Horas a Laborar", "N° Horas Trabajadas"};
        String[] dato = new String[10];

        for (int i = 0; i < cabecera.length; i++) {
            tableProcesada.addColumn(cabecera[i]);
        }
        //List<datosProcesado> listProce= new ArrayList<>();

        //For, comienzo del guardado de datos en la clase datosEmpleado
        for (int i = 0; i < listaordenada.size(); i++) {
            //dato[0] = listaordenada.get(i).getBadgenum();
            //dato[1] = listaordenada.get(i).getName();
            //String datePrimera =  timeFormatter.format(listaordenada.get(i).getHyf_primera());
            //String dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
            //dato[2] = datePrimera;
            //dato[3] = dateUltima;

            //LocalTime primeraHora = listaordenada.get(i).getPHora();
            //LocalTime ultimaHora = listaordenada.get(i).getUHora();
            LocalDateTime primeraHora = listaordenada.get(i).getHyf_primera();
            LocalDateTime ultimaHora = listaordenada.get(i).getHyf_ultima();

            String horario = "No existe horario";
            String calculo = "No existe horario";

            int HoraIndex = 0;

            if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 20, 30))) { //21:00
                HoraIndex = 1;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 45))) { //20:00
                HoraIndex = 2;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 15))) { //19:30
                HoraIndex = 3;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 30))) { //19:00
                HoraIndex = 4;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 17, 0))) { //18:00
                HoraIndex = 5;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 45))) { //16:00
                HoraIndex = 6;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 15))) { //15:30
                HoraIndex = 7;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 14, 30))) { //15:00
                HoraIndex = 8;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 13, 30))) { //14:00
                HoraIndex = 9;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 12, 45))) { //13:00
                HoraIndex = 10;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 12, 15))) { //12:30
                HoraIndex = 11;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 11, 45))) { //12:00
                HoraIndex = 12;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 11, 15))) { //11:30
                HoraIndex = 13;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 10, 40))) { //11:00
                HoraIndex = 14;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 9, 45))) { //10:00
                HoraIndex = 15;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 9, 15))) { //09:30
                HoraIndex = 16;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 8, 30))) { //09:00
                HoraIndex = 17;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 7, 45))) { //08:00
                HoraIndex = 18;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 7, 15))) { //07:30
                HoraIndex = 19;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 6, 40))) { //07:00
                HoraIndex = 20;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 6, 15))) { //06:30
                HoraIndex = 21;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 5, 45))) { //06:00
                HoraIndex = 22;
            } else if (primeraHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 5, 0))) { //05:30
                HoraIndex = 23;
            }

            //boolean existe = false;
            switch (HoraIndex) {
                case 1: //21:00
                    if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "21:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    }
                    if (i + 1 < listaordenada.size()) {
                        if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {
                            //Dia Siguiente - 1 es igual al Dia Actual
                            if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 10, 0, 0))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "21:00 - 09:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "21:00 - ??:??";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                //i++;

                            }

                        }
                    }
                    break;
                case 2: //20:00
                    if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "20:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    }
                    if (i + 1 < listaordenada.size()) {
                        if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear() || (listaordenada.get(i).getHyf_primera().equals(listaordenada.get(i).getHyf_ultima())) //Dia Siguiente - 1 es igual al Dia Actual
                                || (listaordenada.get(i).getHyf_primera().equals(listaordenada.get(i).getHyf_ultima()))) {
                            if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 9, 0, 0))) {

                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "20:00 - 08:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 13, 0, 0))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "20:00 - 12:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "20:00 - ??:??";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                //i++;

                            }
                        }
                    }
                    break;
                case 3: //19:30
                    if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "07:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    }
                    if (i + 1 < listaordenada.size()) {
                        if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                            if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 9, 0, 0))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "19:30 - 07:30";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "19:30 - ??:??";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                //i++;

                            }
                        }
                    }
                    break;
                case 4: //19:00
                    if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "19:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    }
                    if (i + 1 < listaordenada.size()) {
                        if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                            if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(),
                                    listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 8, 0, 0))) {

                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "19:00 - 07:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 20, 0, 0))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "19:00 - 19:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "19:00 - ??:??";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                //i++;

                            }
                        }
                    }
                    break;
                case 5: //18:00
                    if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(),
                                ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "18:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    }
                    if (i + 1 < listaordenada.size()) {
                        if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                            if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 07, 0, 0))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "18:00 - 06:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "18:00 - ??:??";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                //i++;

                            }
                        }
                    }
                    break;
                case 6: //16:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 21, 0, 0))) {
                        horario = "16:00 - 20:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);
                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "16:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        /*else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(),                             ultimaHora.getDayOfMonth(),23, 59, 59))) {
                        horario = "16:00 - ??:??";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);
                    }*/
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 1, 0, 0))
                                        || ultimaHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 0))) {
                                    horario = "16:00 - 00:00";
                                    if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(),
                                            ultimaHora.getDayOfMonth(), 1, 0, 0))) {

                                        listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                        calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    }
                                    if (ultimaHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 0))) {

                                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                                    }
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 9, 0, 0))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "16:00 - 08:00";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "16:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }
                    break;
                case 7: //15:30

                    if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "15:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else if (i + 1 < listaordenada.size()) {
                        if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                            if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(),
                                    ultimaHora.getDayOfMonth(), 10, 0, 0))) {

                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "15:30 - 09:00";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 16, 0, 0))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "15:30 - 15:30";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                //i++;

                            } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                horario = "15:30 - ??:??";
                                calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                //dato[3] = dateUltima;
                                //dato[4] = horario;
                                //dato[5] = calculo;
                                listaordenada.get(i).setHorario(horario);
                                listaordenada.get(i).setCalculo(calculo);
                                //i++;

                            }
                        }
                    }
                    break;
                case 8: //15:00
                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 22, 30, 0))) {
                        horario = "15:00 - 21:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "15:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "15:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 9: //14:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 0, 0))) {
                        horario = "14:00 - 18:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 30, 0))) {
                        horario = "14:00 - 22:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "14:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 15, 0, 0))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "14:00 - 14:00";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "14:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 10: //13:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 20, 0, 0))) {
                        horario = "13:00 - 19:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 21, 15, 0))) {
                        horario = "13:00 - 21:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 22, 30, 0))) {
                        horario = "13:00 - 21:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "13:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(),
                                        ultimaHora.getDayOfMonth(), 14, 0, 0))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "13:00 - 13:00";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "13:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 11: //12:30

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 22, 0, 0))) {
                        horario = "12:30 - 21:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "12:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "12:30 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 12: //12:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 0, 0))) {
                        horario = "12:00 - 18:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 20, 15, 0))) {
                        horario = "12:00 - 20:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 21, 30, 0))) {
                        horario = "12:00 - 20:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "12:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "12:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 13: //11:30

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 21, 30, 0))) {
                        horario = "11:30 - 20:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);
                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "11:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "11:30 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 14: //11:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 16, 30, 0))) {
                        horario = "11:00 - 15:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 30, 0))) {
                        horario = "11:00 - 17:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 20, 0))) {
                        horario = "11:00 - 19:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 20, 30, 0))) {
                        horario = "11:00 - 19:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);
                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "11:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "11:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 15: //10:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 30, 0))) {
                        horario = "10:00 - 14:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 20, 0))) {
                        horario = "10:00 - 18:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 45, 0))) {
                        horario = "10:00 - 18:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "10:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "10:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 16: //9:30

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 17, 50, 0))) {
                        horario = "09:30 - 17:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 19, 30, 0))) {
                        horario = "09:30 - 18:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "09:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "09:30 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 17: //9:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 14, 0, 0))) {
                        horario = "09:00 - 13:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 17, 20, 0))) {
                        horario = "09:00 - 17:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 30, 0))) {
                        horario = "09:00 - 17:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "09:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "09:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 18: //8:00
                    /*if (ultimaHora.equals(primeraHora)) {
                        horario = "NO ES POSIBLE CALCULAR";
                        calculo = "";
                    } else*/
                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 11, 0, 0))) {
                        horario = "08:00 - 10:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(),
                            ultimaHora.getDayOfMonth(), 13, 0, 0))) {
                        horario = "08:00 - 12:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 18, 0))) {
                        horario = "08:00 - 15:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 50, 0))) {
                        horario = "08:00 - 15:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 16, 15, 0))) {
                        horario = "08:00 - 16:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 16, 45, 0))) {
                        horario = "08:00 - 16:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 17, 15, 0))) {
                        horario = "08:00 - 17:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 45, 0))) {
                        horario = "08:00 - 17:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 21, 30, 0))) {
                        horario = "08:00 - 20:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "08:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 1, 0, 0))
                                        || ultimaHora.isAfter(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 0))) {
                                    horario = "08:00 - 00:00";

                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 9, 0, 0))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "08:00 - 08:00";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    // dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "08:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 19: //7:30

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 14, 0, 0))) {
                        horario = "07:30 - 11:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 16, 15, 0))) {
                        horario = "07:30 - 16:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 17, 30, 0))) {
                        horario = "07:30 - 16:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "07:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual

                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 8, 30, 0))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "07:30 - 07:30";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "07:30 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    //i++;                      
                                }
                            }
                        }
                    }

                    break;
                case 20: //7:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 14, 0, 0))) {
                        horario = "07:00 - 13:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 15, 0))) {
                        horario = "07:00 - 15:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 15, 50, 0))) {
                        horario = "07:00 - 15:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 17, 0, 0))) {
                        horario = "07:00 - 16:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 30, 0))) {
                        horario = "07:00 - 17:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 20, 00, 0))) {
                        horario = "07:00 - 19:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "07:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual

                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 6, 30, 0))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "07:00 - 06:00";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i + 1).setHorario("ELIMINAR REGISTRO");
                                    listaordenada.get(i + 1).setCalculo("ELIMINAR REGISTRO");
                                    //i++;

                                } else if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 7, 30, 0))) {
                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "07:00 - 07:00";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    //i++;                      
                                }
                            }
                        }
                    }

                    break;
                case 21: //6:30

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 13, 30, 0))) {
                        horario = "06:30 - 12:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 14, 50, 0))) {
                        horario = "06:30 - 14:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 16, 30, 0))) {
                        horario = "06:30 - 15:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "06:30 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "06:30 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }

                    break;
                case 22: //6:00

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 13, 0, 0))) {
                        horario = "06:00 - 12:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 14, 20, 0))) {
                        horario = "06:00 - 14:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 16, 0, 0))) {
                        horario = "06:00 - 14:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 18, 30, 0))) {
                        horario = "06:00 - 18:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 20, 30, 0))) {
                        horario = "06:00 - 19:00";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "06:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "06:00 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }

                    }

                    break;
                case 23: //5:30

                    if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 12, 30, 0))) {
                        horario = "05:30 - 11:30";
                        calculo = diferenciadehoras(primeraHora, ultimaHora);
                        //dato[4] = horario;
                        //dato[5] = calculo;
                        listaordenada.get(i).setHorario(horario);
                        listaordenada.get(i).setCalculo(calculo);

                    } else if (!ultimaHora.equals(primeraHora)) {
                        if (ultimaHora.isBefore(LocalDateTime.of(ultimaHora.getYear(), ultimaHora.getMonthValue(), ultimaHora.getDayOfMonth(), 23, 59, 59))) {
                            horario = "05:00 - ??:??";
                            calculo = diferenciadehoras(primeraHora, ultimaHora);
                            //dato[4] = horario;
                            //dato[5] = calculo;
                            listaordenada.get(i).setHorario(horario);
                            listaordenada.get(i).setCalculo(calculo);
                        }
                    } else {
                        if (i + 1 < listaordenada.size()) {
                            if (listaordenada.get(i + 1).getHyf_ultima().getDayOfYear() - 1 == listaordenada.get(i).getHyf_ultima().getDayOfYear()) {//Dia Siguiente - 1 es igual al Dia Actual
                                if (listaordenada.get(i + 1).getHyf_ultima().isBefore(LocalDateTime.of(listaordenada.get(i + 1).getHyf_ultima().getYear(), listaordenada.get(i + 1).getHyf_ultima().getMonthValue(), listaordenada.get(i + 1).getHyf_ultima().getDayOfMonth(), 23, 59, 59))) {

                                    listaordenada.get(i).setHyf_ultima(listaordenada.get(i + 1).getHyf_ultima());
                                    //dateUltima = timeFormatter.format(listaordenada.get(i).getHyf_ultima());
                                    horario = "05:30 - ??:??";
                                    calculo = diferenciadehoras(listaordenada.get(i).getHyf_ultima(), primeraHora);
                                    //dato[3] = dateUltima;
                                    //dato[4] = horario;
                                    //dato[5] = calculo;
                                    listaordenada.get(i).setHorario(horario);
                                    listaordenada.get(i).setCalculo(calculo);
                                    //i++;

                                }
                            }
                        }
                    }
                    break;
                default:

                    horario = "NO ES POSIBLE CALCULAR";
                    calculo = "NO ES POSIBLE CALCULAR";
                    //dato[4] = horario;
                    //dato[5] = calculo;
                    listaordenada.get(i).setHorario(horario);
                    listaordenada.get(i).setCalculo(calculo);

            }
            /*if (primeraHora.equals(ultimaHora)) {
                dato[4] = "NO MARCO ENTRADA/SALIDA";
                dato[5] = "NO MARCO ENTRADA/SALIDA";
                listaordenada.get(i).setHorario("NO MARCO ENTRADA/SALIDA");
                listaordenada.get(i).setCalculo("NO MARCO ENTRADA/SALIDA");
            }*/
            //dato[4] = HoraIndex+"";
            //dato[5] = ultimaHora.toString();
            if (listaordenada.get(i).getHorario().isBlank()) {
                listaordenada.get(i).setHorario("SE NECESITA VERIFICAR SI EXISTE HORARIO");
            }
            if (listaordenada.get(i).getCalculo().equals("0:0:0")) {
                listaordenada.get(i).setHorario("ELIMINAR REGISTRO");
            }

            dato[0] = listaordenada.get(i).getBadgenum();
            dato[1] = listaordenada.get(i).getName();
            dato[2] = listaordenada.get(i).getCedula();
            dato[3] = listaordenada.get(i).getCargo();
            //dato[2] = listaordenada.get(i).getHyf_primera().toString();
            //dato[4] = nuevoFormat.format(listaordenada.get(i).getHyf_primera());
            dato[4] = fechaNuFor.format(listaordenada.get(i).getHyf_primera());
            //dato[3] = listaordenada.get(i).getHyf_ultima().toString();
            //dato[5] = nuevoFormat.format(listaordenada.get(i).getHyf_ultima());
            dato[5] = formatHora.format(listaordenada.get(i).getHyf_primera());
            dato[6] = fechaNuFor.format(listaordenada.get(i).getHyf_ultima());
            dato[7] = formatHora.format(listaordenada.get(i).getHyf_ultima());
            dato[8] = listaordenada.get(i).getHorario();
            dato[9] = listaordenada.get(i).getCalculo();

            if (!dato[8].equals("ELIMINAR REGISTRO")) {
                tableProcesada.addRow(dato);
            }

            //tableProcesada.addRow(dato);
        }

        jTable1.removeAll();
        if (jTable1.getColumnCount() > 0) {
            jTable1.removeColumnSelectionInterval(0, 9);
        }

        jTable1.setModel(tableProcesada);

        TableColumnModel columnModel = jTable1.getColumnModel();

        columnModel.getColumn(0).setMinWidth(30);
        columnModel.getColumn(1).setMinWidth(250);
        columnModel.getColumn(2).setMinWidth(90);
        columnModel.getColumn(3).setMinWidth(200);
        columnModel.getColumn(4).setMinWidth(95);
        columnModel.getColumn(5).setMinWidth(95);
        columnModel.getColumn(6).setMinWidth(95);
        columnModel.getColumn(7).setMinWidth(95);
        columnModel.getColumn(8).setMinWidth(150);
        columnModel.getColumn(9).setMinWidth(150);
        columnModel.setColumnSelectionAllowed(false);
        jTable1.setColumnModel(columnModel);

        cargaNombres();
        guardar_horas();
        procesar_datos2();

        JOptionPane.showMessageDialog(null, registros.size() + " Registros cargados y procesados con exito", "EXITO", 3);
    }

    public void cargaNombres() {
        List<String> nom_personal = new ArrayList<>();

        boolean existe = false;
        String nom = "";
        for (int i = 0; i < listaordenada.size(); i++) {
            for (int j = 0; j < nom_personal.size(); j++) {
                existe = false;
                if (nom_personal.isEmpty()) {
                    nom_personal.add(listaordenada.get(i).getName());
                    existe = true;
                }
                if (nom_personal.get(j).equalsIgnoreCase(listaordenada.get(i).getName())) {
                    existe = true;
                } else {
                    nom = listaordenada.get(i).getName();
                }
            }
            if (existe == false) {
                nom_personal.add(nom);
            }
        }
        Collections.sort(nom_personal);
        for (int i = 0; i < nom_personal.size(); i++) {
            comboBoxSuggestion1.addItem(nom_personal.get(i));
        }
    }

    public String diferenciadehoras(LocalDateTime pHora, LocalDateTime uHora) {
        Duration diferencia;

        if (uHora.getDayOfYear() == pHora.getDayOfYear()) {
            diferencia = Duration.between(pHora, uHora);
            return diferencia.toHours() + ":" + diferencia.toMinutesPart() + ":" + diferencia.toSecondsPart();
        } else {
            //h = (23 - pHora.getHour()) + (uHora.getHour()+1);
            diferencia = Duration.between(uHora, pHora);
            return /*h*(-1)*/ diferencia.toHours() + ":" + diferencia.toMillisPart() + ":" + diferencia.toSecondsPart();
        }

        /*diferencia = Duration.between(pHora, uHora);
        return diferencia.toHoursPart() + ":" + diferencia.toMinutesPart() + ":" + diferencia.toSecondsPart();
         */
 /*Date pHora_ = null;
        Date uHora_ = null; 
        String hora = "";
        try {
            pHora_ = dateFormat.parse(pHora.toString());
            uHora_ = dateFormat.parse(uHora.toString());

            long diff = uHora_.getTime() - pHora_.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            hora = diffHours + ":" + diffMinutes + ":" + diffSeconds;

        } catch (Exception e) {
            System.out.println("Error al realizar la resta: " + e);
        }

        return hora;*/
    }

    public void ordenar() {
        listaordenada_ = registros.stream().sorted(Comparator.comparing(registroBD::getNombre)).collect(Collectors.toList());
    }

    public void identificar_horario() {
        //int count = 0;

        for (registroBD r : listaordenada_) {
            boolean existe = false;
            //count++;
            while (!existe) {
                for (datosMostrar dMostrar : listaordenada) {

                    if (r.getNombre().equals(dMostrar.getName()) && r.getFecha().equals(dMostrar.getFecha())) {
                        existe = true;

                        if (r.getHora().isBefore(dMostrar.getPHora())) {
                            dMostrar.setHyf_primera(r.getFechayhora());
                        }
                        if (r.getHora().isAfter(dMostrar.getUHora())) {
                            dMostrar.setHyf_ultima(r.getFechayhora());
                        }

                    }
                }
                if (!existe) {
                    listaordenada.add(new datosMostrar(r.getBadge(), r.getNombre(), r.getCedula(),
                            r.getCargo(), r.getFechayhora(), r.getFechayhora(), "", ""));
                }
            }

            //System.out.println("Identificando... T - " + count%registros.size());
        }
        //listaordenada = dMostrarLista.stream().sorted(Comparator.comparing(datosMostrar::getName)).collect(Collectors.toList());
        //dMostrarLista.clear();
    }

    public void guardar_horas() {
        int count = 0;
        String fechaActual = "";
        for (registroBD datos : listaordenada_) {
            boolean existe = false;
            if (!(fechaActual.equals(datos.getFecha()))) {
                count = 0;
            }
            fechaActual = datos.getFecha();
            while (!existe) {
                for (datosPersona p : dPersonna) {

                    if (p.getNombre().equals(datos.getNombre()) && p.getFecha().equals(datos.getFecha())) {
                        existe = true;
                        p.agregarHora(datos.getHora().toString());
                        //p.agregarHora(datos.getFecha());
                        count++;
                    }
                }
                if (!existe) {
                    dPersonna.add(new datosPersona(datos.getNombre(), datos.getFecha(), new ArrayList<>()));
                }
            }
            if (max < count) {
                max = count;
            }
        }
    }

    public void procesar_datos2() {
        String[] cabecera = new String[max + 1];

        cabecera[0] = "Fecha";
        for (int i = 1; i <= max; i++) {
            cabecera[i] = "Hora# " + i;
            //System.out.println(cabecera[i]);
        }

        for (int i = 0; i < cabecera.length; i++) {
            modeloProce.addColumn(cabecera[i]);
        }

        //System.out.println(Arrays.toString(cabecera));
        jTable2.setModel(model);
        jTable2.setModel(modeloProce);
        columnModel2 = jTable2.getColumnModel();
        for (int i = 0; i < cabecera.length; i++) {
            columnModel2.getColumn(i).setMinWidth(30);
        }

        jTable2.setColumnModel(columnModel2);
        //for (datosProce p : dProce) {        }
    }

    public void obtenerDatos(String selected) {
        modeloProce.setNumRows(0);
        String[] datosArray = new String[max + 1];
        for (datosPersona p : dPersonna) {
            //int j = 0;
            if (selected.equals(p.getNombre())) {
                datosArray[0] = p.getFecha();
                for (int i = 0; i < p.getHora().size(); i++) {
                    datosArray[i + 1] = p.getHora().get(i);
                }
                //System.out.println("Nombre: " + p.getNombre() + " Fecha: " + p.getFecha() + " Horarios: " + p.getHora());
                modeloProce.addRow(datosArray);
                for (int i = 0; i < datosArray.length; i++) {
                    datosArray[i] = "";
                }
            }

        }

        jTable2.setModel(modeloProce);
        jTable2.setColumnModel(columnModel2);
    }

    public void nom_Tabla(String nombre) {

        while (nomTabla.getRowCount() > 0) {
            nomTabla.removeRow(nomTabla.getRowCount() - 1);
        }

        //String[] cabecera = {"Badge", "Nombre", "Cedula" ,"Cargo","F. y H. Entrada", "F. y H. Salida", "Horas a Laborar", "Total Horas"};
        String[] cabecera = {"Badge", "Nombre", "Cedula", "Cargo", "Fecha de Entrada", "Hora de Entrada",
            "Fecha de Salida", "Hora de Salida", "Horas a Laborar", "N° Horas Trabajadas"};
        String[] dato = new String[10];

        if (nomTabla.getColumnCount() == 0) {
            for (int i = 0; i < cabecera.length; i++) {
                nomTabla.addColumn(cabecera[i]);
            }
        }

        for (datosMostrar mostrar : listaordenada) {
            if (mostrar.getName().equals(nombre)) {
                dato[0] = mostrar.getBadgenum();
                dato[1] = mostrar.getName();
                //String datePrimera =  nuevoFormat.format(mostrar.getHyf_primera());
                //String dateUltima = nuevoFormat.format(mostrar.getHyf_ultima());
                dato[2] = mostrar.getCedula();
                dato[3] = mostrar.getCargo();
                //dato[4] = datePrimera;
                dato[4] = fechaNuFor.format(mostrar.getHyf_primera());
                //dato[5] = dateUltima;
                dato[5] = formatHora.format(mostrar.getHyf_primera());
                dato[6] = fechaNuFor.format(mostrar.getHyf_ultima());
                dato[7] = formatHora.format(mostrar.getHyf_ultima());
                dato[8] = mostrar.getHorario();
                dato[9] = mostrar.getCalculo();
                if (!dato[8].equals("ELIMINAR REGISTRO")) {
                    nomTabla.addRow(dato);
                }
            }
        }
        jTable1.removeAll();

        jTable1.setModel(nomTabla);
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setMinWidth(30);
        columnModel.getColumn(1).setMinWidth(250);
        columnModel.getColumn(2).setMinWidth(100);
        columnModel.getColumn(3).setMinWidth(150);
        columnModel.getColumn(4).setMinWidth(95);
        columnModel.getColumn(5).setMinWidth(95);
        columnModel.getColumn(6).setMinWidth(95);
        columnModel.getColumn(7).setMinWidth(95);
        columnModel.getColumn(8).setMinWidth(150);
        columnModel.getColumn(9).setMinWidth(150);
        jTable1.setColumnModel(columnModel);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        comboBoxSuggestion1 = new combo_suggestion.ComboBoxSuggestion();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        desdeFecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        hastaFecha = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        textCarga = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GENERADOR DE REPORTES");
        setBackground(new java.awt.Color(204, 204, 204));
        setLocation(new java.awt.Point(0, 0));
        setMaximumSize(new java.awt.Dimension(900, 700));
        setMinimumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(930, 660));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("BUSQUEDA POR PERSONA");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel3.setText("NOMBRE");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 30));

        jButton2.setText("GENERAR REGISTROS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 170, 40));

        jButton1.setText("TABLA COMPLETA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 40));

        comboBoxSuggestion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSuggestion1ActionPerformed(evt);
            }
        });
        jPanel2.add(comboBoxSuggestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 270, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, 130));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("INGRESE RANGO");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 110, 30));

        desdeFecha.setDateFormatString("dd-MM-yyyy");
        desdeFecha.setDoubleBuffered(false);
        jPanel3.add(desdeFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 170, -1));

        jLabel5.setText("DESDE");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 50, 20));

        jLabel6.setText("HASTA");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        hastaFecha.setDateFormatString("dd-MM-yyyy");
        jPanel3.add(hastaFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 170, -1));

        jButton3.setText("PROCESAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 150, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 250, 130));

        jButton5.setText("<html><center>GENERAR</center></p><p>TABLA EXCEL</html>");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 120, 130));

        jButton6.setText("<html><<center>CAMBIAR</center></p><p>ARCHIVO DB</html>");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 120, 130));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 890, 150));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Datos Biometricos");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 210, -1));

        textCarga.setText("Base de datos no Conectada");
        getContentPane().add(textCarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, -1, -1));

        jTabbedPane1.setName(""); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAlignmentX(0.0F);
        jTable1.setAlignmentY(0.0F);
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("Datos Completos", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTabbedPane1.addTab("Marcaciones Individuales por Persona", jScrollPane2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 880, 340));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jLabel7.setText("Creado por: Frank Tello Salvador");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 580, -1, -1));

        jMenu1.setText("Ayuda");

        jMenuItem1.setText("Guia");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String seleccionado = comboBoxSuggestion1.getSelectedItem().toString();
        if (seleccionado.equals("Seleccione Nombre") || seleccionado.equals("")) {
            JOptionPane.showMessageDialog(null, "                      Elija un Nombre\nSi no existen nombres, procese la tabla porfavor.");
        } else {
            nom_Tabla(seleccionado);
            obtenerDatos(seleccionado);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        if (desdeFecha.getDate() == null || desdeFecha.getDate().toString().isBlank()) {
            JOptionPane.showMessageDialog(null, "Seleccione minimo comienzo del rango de fecha (Opcion Desde)", "ERROR", 1);
        } else {
            textCarga.setText("Procesando datos...");
            jTable1.removeAll();
            jTable2.removeAll();
            try {
                existeDatoActual();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: Ruta de la base de datos no encontrada", "ERROR", 1);
            }

            textCarga.setText("Datos Procesados");
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTable1.selectAll();
        if (jTable1.getSelectedRowCount() != 0 || jTable2.getSelectedRowCount() != 0) {
            jTable1.removeAll();
            jTable1.setModel(tableProcesada);

            TableColumnModel columnModel = jTable1.getColumnModel();
            columnModel.getColumn(0).setMinWidth(30);
            columnModel.getColumn(1).setMinWidth(250);
            columnModel.getColumn(2).setMinWidth(150);
            columnModel.getColumn(3).setMinWidth(150);
            columnModel.getColumn(4).setMinWidth(150);
            columnModel.getColumn(5).setMinWidth(150);
            jTable1.setColumnModel(columnModel);
        } else {
            JOptionPane.showMessageDialog(null, new JLabel("La tabla no tiene elementos", JLabel.CENTER), "ERROR", 0);
            System.out.println("La tabla no tiene elementos");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jTable1.selectAll();
        if (jTable1.getSelectedRowCount() != 0 || jTable2.getSelectedRowCount() != 0) {
            //jTable1.clearSelection();
            //System.out.println("La tabla tiene elementos");
            ExportarExcel ex_cel;
            /*String[] cabecera = {"Badge", "Nombre", "Cedula", "Cargo", "Fecha de Entrada", "Hora de Entrada",
                "Fecha de Salida", "Hora de Salida", "Horas a Laborar", "N° Horas Trabajadas"};
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar archivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String ruta = chooser.getSelectedFile().toString().concat(".xls");*/
            try {
                /*File archivoXLS = new File(ruta);
                    if (archivoXLS.exists()) {
                        archivoXLS.delete();
                    }
                    archivoXLS.createNewFile();*/
                //ex_cel = new ExportarExcel();
                ex_cel = new ExportarExcel();
                //ex_cel.excelArray(cabecera, listaordenada, archivoXLS);
                ex_cel.exportarExcel(jTable1, jTable2);

                //JOptionPane.showMessageDialog(null, "Archivo guardado con exito");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e);
                e.printStackTrace();
                //}
            }
        } else {
            JOptionPane.showMessageDialog(null, "La tabla no tiene elementos", "ERROR", 1);
            //System.out.println("La tabla no tiene elementos");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        guiaFrame f = new guiaFrame();
        f.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        try {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);
            cont_ruta = fc.getSelectedFile().getAbsolutePath();
            //System.out.println(cont_ruta);
             FileWriter fw = new FileWriter(txtFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cont_ruta);
            bw.close();
            //System.out.println("Nueva ruta ingresada"+cont_ruta+"\nRuta carpeta: "+txtFile.getAbsolutePath());
            textCarga.setText("Cargando...");
            bdConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo");
            //System.out.println("Error al leer el archivo: "+e.getMessage());
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void comboBoxSuggestion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSuggestion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSuggestion1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combo_suggestion.ComboBoxSuggestion comboBoxSuggestion1;
    private com.toedter.calendar.JDateChooser desdeFecha;
    private com.toedter.calendar.JDateChooser hastaFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel textCarga;
    // End of variables declaration//GEN-END:variables
}
