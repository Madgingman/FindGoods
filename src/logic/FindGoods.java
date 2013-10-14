/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import db.CategoryMapper;
import webservice.TopQueriesServer;
import ui.FindGoodsJFrame;

/**
 *
 * @author M
 */
public class FindGoods {
    private static TopQueriesServer server = new TopQueriesServer();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	/* Set the System look and feel */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* 
	 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	 */
	try {
	    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(FindGoodsJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>
	
	server.start();

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new FindGoodsJFrame().setVisible(true);
	    }
	});
    }
    
    public static void exit() {
	server.stop();
	System.exit(0);
    }
}
