/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pruebaaccess.folderFrames;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author reto3
 */
public class guiaFrame extends javax.swing.JFrame {
    ImageIcon primera_P = new ImageIcon(getClass().getResource("/imagenes/primeraPantalla.png"));
    /**
     * Creates new form guiaFrame
     */
    public guiaFrame() {
        initComponents();  
        
    }

    public void imagenesPanel (ImageIcon icono, JPanel jPanel){
        Dimension d = jPanel.getSize();
        
        java.awt.Image img = icono.getImage();
        java.awt.Image nuevoImg = img.getScaledInstance(d.width, d.height, java.awt.Image.SCALE_REPLICATE);
        Icon nuevoIcon = new ImageIcon(nuevoImg);
        
        JLabel lbl = new JLabel();
        lbl.setIcon(nuevoIcon);
        jPanel.removeAll();
        lbl.setBounds(0, 0, d.width, d.height);
        jPanel.add(lbl);
        
        repaint();
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        label1.setText("GUIA");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/principal señal (3).jpg"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        jLabel2.setText("<html>\n<body>\n<CENTER>Explicacion de cada parte de la pantalla principal del programa</CENTER>\n<p>\n<br><br>&nbsp;&nbsp;&nbsp;<b>1.- Campos de Ingreso de fecha</b>, son utilizadas para señalar en que rango de fechas se desea obtener los registros, es necesario minimo ubicar la fecha de comienzo de rango (campo \"Desde\"), la fecha del sistema sera el final del rango en caso de no ubicar una fecha en el campo \"Hasta\". \n<br><br>&nbsp;&nbsp;&nbsp;<b>2.- Boton Procesar</b>, tal como su nombre menciona, procesa los registros encontrados en la base de datos, para su debido funcionamiento es necesario ubicar primeramente el rango de fechas, como minimo el comienzo del rango. Es necesario mencionar:\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Entre mas grande el rango de fechas, mas tiempo tomara en procesar los datos. Ej: Un mes de datos = Entre 30 segundos y 2 minutos. Un año de datos = Entre 10 y 15 minutos.\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cada vez que se presione el boton, cargara nuevos registros de la base de datos, por ello es preferible presionarlo una vez segun la necesidad, y en caso de desear otros datos a parte de los que ya se tienen primeramente guarde los ya obtenidos y procese los faltantes.\n<br><br>&nbsp;&nbsp;&nbsp;<b>3.- Tabla de Datos Biometricos</b>, aqui es donde se reflejaran todos los datos una vez procesados, en este mismo tambien mostraran los datos de una persona en especifico al usar el modulo de busqueda de personas. \n<br>&nbsp;&nbsp;&nbsp;Cabe mencionar que esta tabla no es editable, esto para evitar posibles errores humanos, si se desea editar los datos puede generar una copia de estos presionando el boton \"Generar Tabla Excel\".\n<br><br>&nbsp;&nbsp;&nbsp;<b>4.- Boton \"Generar Tabla Excel\"</b>, tal como su nombre menciona, generara un archivo excel de los registros encontrados en la actual tabla del programa, al precionar el boton mostrara una ventana flotante en donde el usuario puede elegir donde desea guardar el archivo excel y como llamarlo.\n<br><br>&nbsp;&nbsp;&nbsp;<b>5.- Listado de Nombres</b>, por defecto mostrara el texto \"Seleccione Nombre\" al usuario, al precioarlo mostrara un listado de todos los nombre de aquellas personas que trabajaron en el transcurso de tiempo señalado en el rango de fechas. Una vez seleccionado el nombre se puede generar los registros de esa persona en la tabla usando el boton \"Generar Registros\".\n<br><br>&nbsp;&nbsp;&nbsp;<b>6.- Boton \"Generar Registros\"</b>, al presionarlo generara los registros existentes de la persona la cual esta seleccionada en el listado de nombres encontrado encima del boton, en caso de no seleccionar un nombre el programa saltara un mensaje el cual pedira al usuario que elija un nombre.\n<br><br>&nbsp;&nbsp;&nbsp;<b>7.- Boton \"Tabla Completa\"</b>, dedicado a generar la tabla completa en caso de que se desea volver a ella si se genero otros registros, como por ejemplo haber generado los registros de una persona en si, pero se desea volver a la tabla con los registros completos.\n</p>\n</body>\n</html>");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 830, 680));

        jScrollPane1.setViewportView(jPanel2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 900, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(guiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(guiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(guiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(guiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new guiaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

    public class Image extends javax.swing.JPanel{
        public Image(){
            
        }
    }
}

