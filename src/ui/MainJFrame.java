/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business.Administrator;
import business.Finder;
import business.Product;
import business.Store;
import db.StoreMapper;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import service.Currencies;
import service.HTTPClient;
import service.TopQueriesServer;
import service.XMLReader;


/**
 *
 * @author M
 */
public class MainJFrame extends JFrame {

    private TrayIcon trayIcon;
    private boolean administratorMode;
    private List<Store> selectedStores;
    private Finder finder;
    
    
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
	initComponents();
	
	this.setLocationRelativeTo(null);
	this.setIconImage(new ImageIcon("src\\ui\\images\\mainIcon.png").getImage());
	resultsTable.setVisible(false);
	resTableScrPane.setVisible(false);
	resDescrLabel.setText("");
	
	// Set tray icon
	trayIcon = new TrayIcon(new ImageIcon("src\\ui\\images\\mainIcon.png").getImage(), "FindGoods");
        trayIcon.setImageAutoSize(true);
	
	// Create tray popup menu
	PopupMenu popupMenu = new PopupMenu();
        MenuItem item = new MenuItem("Show");
        item.addActionListener(new ActionListener() {
	    @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                setState(JFrame.NORMAL);
            }
        });
        popupMenu.add(item);
        popupMenu.addSeparator();
	
	item = new MenuItem("Exit");
	item.addActionListener(new ActionListener() {
	    @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        popupMenu.add(item);
	trayIcon.setPopupMenu(popupMenu);
	
	// Add new tray to system tray
        try {
            java.awt.SystemTray.getSystemTray().add(trayIcon);
        } catch(AWTException ex) {
            JOptionPane.showMessageDialog(null, "Can't show tray icon.",
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        }
	
	// Listeners for tray
	trayIcon.addActionListener(new ActionListener() {
	    @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                setState(JFrame.NORMAL);
            }
        });
        this.addWindowStateListener(new WindowStateListener() {
	    @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState() == JFrame.ICONIFIED) {
                    setVisible(false);
                    trayIcon.displayMessage("FindGoods by Ez", 
			    "Double-click to show window", TrayIcon.MessageType.INFO);
                }
            }
        });
	
	selectedStores = new ArrayList<>();
	administratorMode = false;
	setUserElementsEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        queryTField = new javax.swing.JTextField();
        findButton = new javax.swing.JButton();
        resTableScrPane = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        tempListScrPane = new javax.swing.JScrollPane();
        tempList = new javax.swing.JList(new javax.swing.DefaultListModel());
        resDescrLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tmpRateLabel = new javax.swing.JLabel();
        mainJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        loginJMItem = new javax.swing.JMenuItem();
        logoutJMItem = new javax.swing.JMenuItem();
        registerMItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitJMItem = new javax.swing.JMenuItem();
        optionsJMenu = new javax.swing.JMenu();
        preferencesJMItem = new javax.swing.JMenuItem();

        setTitle("FindGoods by Ez");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(600, 400));

        mainPanel.setBackground(new java.awt.Color(153, 204, 255));
        mainPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        mainPanel.setName(""); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(600, 300));

        queryTField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        queryTField.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        queryTField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        queryTField.setEnabled(false);
        queryTField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                queryTFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                queryTFieldFocusLost(evt);
            }
        });
        queryTField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                queryTFieldKeyPressed(evt);
            }
        });

        findButton.setBackground(new java.awt.Color(153, 204, 255));
        findButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        findButton.setText("Найти");
        findButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        findButton.setEnabled(false);
        findButton.setPreferredSize(new java.awt.Dimension(73, 17));
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        resTableScrPane.setEnabled(false);

        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultsTable.setEnabled(false);
        resTableScrPane.setViewportView(resultsTable);

        tempListScrPane.setEnabled(false);

        tempList.setEnabled(false);
        tempListScrPane.setViewportView(tempList);

        resDescrLabel.setText("jLabel1");
        resDescrLabel.setEnabled(false);

        jLabel1.setText(" RUB/USD rate:");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tempListScrPane)
                            .addComponent(resTableScrPane, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(resDescrLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(queryTField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tmpRateLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(queryTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resDescrLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tempListScrPane, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tmpRateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resTableScrPane, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        fileJMenu.setText("File");

        loginJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        loginJMItem.setText("Log in...");
        loginJMItem.setName(""); // NOI18N
        loginJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(loginJMItem);
        loginJMItem.getAccessibleContext().setAccessibleName("loginJMenuItem");

        logoutJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        logoutJMItem.setText("Log out");
        logoutJMItem.setVisible(false);
        logoutJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(logoutJMItem);

        registerMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        registerMItem.setText("Register...");
        registerMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(registerMItem);
        fileJMenu.add(jSeparator1);

        exitJMItem.setText("Exit");
        exitJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMItem);

        mainJMenuBar.add(fileJMenu);

        optionsJMenu.setText("Options");

        preferencesJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        preferencesJMItem.setText("Preferences...");
        preferencesJMItem.setEnabled(false);
        preferencesJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesJMItemActionPerformed(evt);
            }
        });
        optionsJMenu.add(preferencesJMItem);

        mainJMenuBar.add(optionsJMenu);

        setJMenuBar(mainJMenuBar);
        mainJMenuBar.getAccessibleContext().setAccessibleName("");
        mainJMenuBar.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMItemActionPerformed
        this.dispose();
	System.exit(0);
    }//GEN-LAST:event_exitJMItemActionPerformed

    private void queryTFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_queryTFieldFocusGained
	queryTField.setBorder(new javax.swing.border.SoftBevelBorder(
		javax.swing.border.BevelBorder.LOWERED, 
		new java.awt.Color(51, 153, 255), 
		new java.awt.Color(51, 153, 255), 
		new java.awt.Color(51, 153, 255), null));
    }//GEN-LAST:event_queryTFieldFocusGained

    private void queryTFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_queryTFieldFocusLost
        queryTField.setBorder(new javax.swing.border.SoftBevelBorder(
		javax.swing.border.BevelBorder.LOWERED));
    }//GEN-LAST:event_queryTFieldFocusLost

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
	this.repaint();
	find(queryTField.getText());
	setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_findButtonActionPerformed

    private void queryTFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_queryTFieldKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
	    setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
	    this.repaint();
	    find(queryTField.getText());
	    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	}
    }//GEN-LAST:event_queryTFieldKeyPressed

    private void loginJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginJMItemActionPerformed
        // Some authorization stuff
	AuthorizationJDialog authDlg = new AuthorizationJDialog(this, true);
	authDlg.setVisible(true);
	
	if (authDlg.user != null) {
	    setUserElementsEnabled(true);
	    if (authDlg.user instanceof Administrator) {
		// Enable some admin features
		administratorMode = true;
	    }
	    try {
		double exchangeRate = HTTPClient.getExchangeRate(Currencies.USD);
		tmpRateLabel.setText(new Double(exchangeRate).toString());
	    } catch (ParserConfigurationException | SAXException | IOException ex) {
		JOptionPane.showMessageDialog(null, "Can't load exchange rate.", "Error",
			JOptionPane.ERROR_MESSAGE, null);
	    }
	}
    }//GEN-LAST:event_loginJMItemActionPerformed

    private void logoutJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutJMItemActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Logging out", 
		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	if (result == JOptionPane.NO_OPTION) {
	    return;
	}
	
	// Some anti-authorization stuff
	this.queryTField.setText("");
	this.resDescrLabel.setText("");
	((DefaultListModel) this.tempList.getModel()).clear();
	DefaultTableModel model = (DefaultTableModel) this.resultsTable.getModel();
	model.getDataVector().removeAllElements();
	model.fireTableDataChanged();
	
	setUserElementsEnabled(false);
	this.administratorMode = false;
    }//GEN-LAST:event_logoutJMItemActionPerformed

    private void preferencesJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesJMItemActionPerformed
	StoresJDialog prefDlg = new StoresJDialog(this, true);
	prefDlg.setVisible(true);
	updateSelectedStores(prefDlg.stores);
    }//GEN-LAST:event_preferencesJMItemActionPerformed

    private void registerMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerMItemActionPerformed
        // Some registration stuff
	RegistrationJDialog regDlg = new RegistrationJDialog(this, true);
	regDlg.setVisible(true);
	
	if (regDlg.user != null) {
	    // Some authorization stuff
	    setUserElementsEnabled(true);
	}
    }//GEN-LAST:event_registerMItemActionPerformed

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
	} catch (ClassNotFoundException | InstantiationException | 
		IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	java.awt.EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		TopQueriesServer server = new service.TopQueriesServer();
		server.start();
	    }
	});

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new MainJFrame().setVisible(true);
	    }
	});
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exitJMItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JButton findButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem loginJMItem;
    private javax.swing.JMenuItem logoutJMItem;
    private javax.swing.JMenuBar mainJMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenu optionsJMenu;
    private javax.swing.JMenuItem preferencesJMItem;
    private javax.swing.JTextField queryTField;
    private javax.swing.JMenuItem registerMItem;
    private javax.swing.JLabel resDescrLabel;
    private javax.swing.JScrollPane resTableScrPane;
    private javax.swing.JTable resultsTable;
    private javax.swing.JList tempList;
    private javax.swing.JScrollPane tempListScrPane;
    private javax.swing.JLabel tmpRateLabel;
    // End of variables declaration//GEN-END:variables

    private void find(final String text) {
	if (text.isEmpty()) {
	    JOptionPane.showMessageDialog(null,
		    "Enter query to search!",
		    "Empty query", JOptionPane.INFORMATION_MESSAGE, null);
	    resDescrLabel.setText("");
	    ((javax.swing.DefaultListModel) tempList.getModel()).clear();
	    return;
	}
	
	if (selectedStores.isEmpty()) {
	    try {
		updateSelectedStores(XMLReader.loadSettings("src\\settings.xml"));
	    } catch (ParserConfigurationException | IOException | SAXException ex) {
		JOptionPane.showMessageDialog(null, "Can't load settings.", "Error", 
			JOptionPane.ERROR_MESSAGE, null);
		resDescrLabel.setText("");
		((javax.swing.DefaultListModel) tempList.getModel()).clear();
		return;
	    }
	}
	
	// добавить расширенный поиск
	for (Store store : selectedStores) {
	    try {
		finder = new Finder(store, text, null, null, Long.MAX_VALUE, 0);
		java.util.ArrayList<Product> result;
		if (!finder.find()) {
		    resDescrLabel.setText("Ничего не найдено");
		    return;
		}
		result = finder.getResult();
		for (Product product : result) {
		    ((javax.swing.DefaultListModel) tempList.getModel()).addElement(
			    "<html><a href=\"" + product.getUrl() + "\">"
			    + product.getName() + "</a></html>  -  " + product.getPrice());
		}

		resDescrLabel.setText("Найдено товаров: " + result.size());
	    } catch (java.io.IOException ex) {
		JOptionPane.showMessageDialog(null,
			"Unexpected IO error. Search is cancelled.\n\nError: " + ex.getMessage(),
			"Error", JOptionPane.ERROR_MESSAGE, null);
		resDescrLabel.setText("");
		return;
	    }
	}
    }

    private void updateSelectedStores(Map<String, Boolean> stores) {
	for (Map.Entry<String, Boolean> entry : stores.entrySet()) {
	    if (!entry.getValue()) {
		continue;
	    }
	    try {
		StoreMapper smapper = new StoreMapper();
		Store store = smapper.findByParam(StoreMapper.StoreParams.Name, entry.getKey());
		if (store != null) {
		    selectedStores.add(store);
		}
	    } catch (SQLException ex) {
		JOptionPane.showMessageDialog(null,
			"Database access error. Stores can't be loaded.\n\nError "
			+ ex.getErrorCode() + ": " + ex.getMessage(),
			"Error", JOptionPane.ERROR_MESSAGE, null);
	    }
	}
    }

    private void setUserElementsEnabled(boolean value) {
	this.loginJMItem.setVisible(!value);
	this.registerMItem.setVisible(!value);
	this.logoutJMItem.setVisible(value);
	
	this.preferencesJMItem.setEnabled(value);
	
	Component[] components = this.mainPanel.getComponents();
	for (Component comp : components) {
	    comp.setEnabled(value);
	    comp.setVisible(value);
	}
	tempList.setEnabled(value);
    }
}
