/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business.User;
import db.UserMapper;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author M
 */
public class AuthorizationJDialog extends javax.swing.JDialog {

    public String login;
    public String password;
    public User user;
    
    /**
     * Creates new form AuthorizationJDialog
     */
    public AuthorizationJDialog(java.awt.Frame parent, boolean modal) {
	super(parent, modal);
	initComponents();
	setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainJPanel = new javax.swing.JPanel();
        loginTField = new javax.swing.JTextField();
        loginLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordPField = new javax.swing.JPasswordField();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Авторизация");
        setMinimumSize(new java.awt.Dimension(100, 50));
        setResizable(false);

        loginLabel.setText("Login:");

        passwordLabel.setText("Password:");

        javax.swing.GroupLayout MainJPanelLayout = new javax.swing.GroupLayout(MainJPanel);
        MainJPanel.setLayout(MainJPanelLayout);
        MainJPanelLayout.setHorizontalGroup(
            MainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordLabel)
                    .addComponent(loginLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginTField)
                    .addComponent(passwordPField))
                .addContainerGap())
        );
        MainJPanelLayout.setVerticalGroup(
            MainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainJPanelLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(MainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginLabel)
                    .addComponent(loginTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordPField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        cancelButton.setText("Cancel");
        cancelButton.setMaximumSize(new java.awt.Dimension(75, 25));
        cancelButton.setMinimumSize(new java.awt.Dimension(75, 25));
        cancelButton.setPreferredSize(new java.awt.Dimension(75, 25));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.setMaximumSize(new java.awt.Dimension(75, 25));
        okButton.setMinimumSize(new java.awt.Dimension(75, 25));
        okButton.setPreferredSize(new java.awt.Dimension(75, 25));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MainJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.login = loginTField.getText();
	this.password = String.valueOf(passwordPField.getPassword());
	
	UserMapper umapper = new UserMapper();
	try {
	    user = umapper.findByParam(UserMapper.UserParams.Login, login);
	    if (user == null || !user.comparePassword(password)) {
		JOptionPane.showMessageDialog(null, "Wrong login or password!", "Error",
			JOptionPane.ERROR_MESSAGE, null);
		return;
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Unexpected database error.", "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	}
	this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        user = null;
	this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainJPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JTextField loginTField;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordPField;
    // End of variables declaration//GEN-END:variables
}
