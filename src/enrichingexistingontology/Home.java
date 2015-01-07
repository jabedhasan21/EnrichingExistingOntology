/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrichingexistingontology;

import com.eeo.OpenNLP.OpenNLP;
import com.eeo.boilerpipe.Boilerpipe;

import com.eeo.jaws.*;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.xml.sax.SAXException;
/**
 *
 * @author jabed hasan
 */
public class Home extends javax.swing.JFrame {

    public String WebContent = "";
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title_lbl = new javax.swing.JLabel();
        edit_word_txt = new javax.swing.JTextField();
        search_btn = new javax.swing.JButton();
        result_scrollPane = new java.awt.ScrollPane();
        result_textArea = new java.awt.TextArea();
        go_wikipedia_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Enriching Existing Ontology");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title_lbl.setBackground(new java.awt.Color(0, 102, 102));
        title_lbl.setFont(new java.awt.Font("Al Nile", 1, 24)); // NOI18N
        title_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_lbl.setText("Enriching Existing Ontology");
        title_lbl.setEnabled(false);
        getContentPane().add(title_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 520, -1));
        title_lbl.getAccessibleContext().setAccessibleName("title_lbl");
        title_lbl.getAccessibleContext().setAccessibleDescription("");

        edit_word_txt.setToolTipText("Word");
        getContentPane().add(edit_word_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 182, -1));
        edit_word_txt.getAccessibleContext().setAccessibleName("edit_word_txt");

        search_btn.setText("Search");
        search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_btnActionPerformed(evt);
            }
        });
        getContentPane().add(search_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, -1));

        result_scrollPane.add(result_textArea);

        getContentPane().add(result_scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 650, 310));

        go_wikipedia_btn.setText("Go Wikipedia");
        go_wikipedia_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                go_wikipedia_btnActionPerformed(evt);
            }
        });
        getContentPane().add(go_wikipedia_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_btnActionPerformed
        String resutl = "";
        if( !edit_word_txt.getText().equals("") ) {
            result_textArea.setText(JawsAPI.getResult(edit_word_txt.getText()));
        }
        else{
            result_textArea.setText("");
            JOptionPane.showMessageDialog(rootPane, "Invalid Input", "Input Properly", NORMAL);
        }
    }//GEN-LAST:event_search_btnActionPerformed

    private void go_wikipedia_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_go_wikipedia_btnActionPerformed
     
        if( !edit_word_txt.getText().equals("") ) {
            try {
                WebContent = Boilerpipe.DefaultExtractor(edit_word_txt.getText());
                //System.out.println("ok : "+ WebContent);
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BoilerpipeProcessingException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            String sentences[] =  OpenNLP.SentenceDetect(WebContent);
            System.out.println("sentences Length : "+sentences.length);
            NewConceptList newConceptList = new NewConceptList(sentences,edit_word_txt.getText());
            newConceptList.setVisible(true);
            this.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_go_wikipedia_btnActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edit_word_txt;
    private javax.swing.JButton go_wikipedia_btn;
    private java.awt.ScrollPane result_scrollPane;
    private java.awt.TextArea result_textArea;
    private javax.swing.JButton search_btn;
    private javax.swing.JLabel title_lbl;
    // End of variables declaration//GEN-END:variables
}
