/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.ProductFilter;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import logic.Administrator;
import logic.Product;
import logic.Store;
import logic.User;
import org.xml.sax.SAXException;
import service.CategoryService;
import service.HistoryService;
import service.ProductService;
import service.StoreService;
import service.UserService;
import service.XMLBuilder;
import service.XMLReader;
import ui.resultsTable.CurrencyCellRenderer;
import ui.resultsTable.ImageCellRenderer;
import ui.resultsTable.JEditorPaneCellRenderer;
import ui.resultsTable.ResultsTableModel;
import utils.ProductUtils;
import webservice.HTTPClient;


/**
 *
 * @author M
 */
public class FindGoodsJFrame extends javax.swing.JFrame
	implements MouseListener, ListSelectionListener {
    private User user;
    
    private static final int RESULTS_TABLE_ROW_HEIGHT = 70;

    private TrayIcon trayIcon;
    private HashMap<String, Double> availableCurrencies;
    private ArrayList<Store> selectedStores;
    private List<String> categories;
    private List<Product> searchResults;
    
    /**
     * Creates new form FindGoodsJFrame
     */
    public FindGoodsJFrame() {
	initComponents();
	this.setLocationRelativeTo(null);
	this.setIconImage(new ImageIcon("src\\ui\\images\\mainIcon.png").getImage());
	
	enableElements(false);
	selectedStores = new ArrayList<>();
	
	// Setting results table parameters
	ResultsTableModel resTableModel = new ResultsTableModel();
	resultsTable.setModel(resTableModel);
	resultsTable.setRowHeight(RESULTS_TABLE_ROW_HEIGHT);
	
	    // Adding row sorter
	TableRowSorter<ResultsTableModel> sorter = new TableRowSorter<>(resTableModel);
	sorter.setSortable(0, false);
	sorter.setSortable(1, false);
	sorter.setSortsOnUpdates(true);
	resultsTable.setRowSorter(sorter);
	    // Setting column models
	resultsTable.getTableHeader().setReorderingAllowed(false);
	resultsTable.getColumnModel().getColumn(1).setCellRenderer(new ImageCellRenderer());
	resultsTable.getColumnModel().getColumn(2).setCellRenderer(new JEditorPaneCellRenderer());
	resultsTable.getColumnModel().getColumn(3).setCellRenderer(new CurrencyCellRenderer(Locale.getDefault()));
	    // Setting columns parameters
	int tableWidth = resultsTable.getWidth();
	resultsTable.getColumnModel().getColumn(0).setPreferredWidth(tableWidth/20);
	resultsTable.getColumnModel().getColumn(1).setPreferredWidth(tableWidth/10);
	resultsTable.getColumnModel().getColumn(2).setPreferredWidth(tableWidth - 6 * tableWidth / 20);
	resultsTable.getColumnModel().getColumn(3).setPreferredWidth(tableWidth/10);
	resultsTable.getColumnModel().getColumn(4).setPreferredWidth(tableWidth/20);
	for (int i = 0; i < resultsTable.getColumnModel().getColumnCount(); i++) {
	    resultsTable.getColumnModel().getColumn(i).setResizable(false);
	}
	resultsTable.getColumnModel().setColumnMargin(0);
	    // Add selection listener
	resultsTable.getSelectionModel().addListSelectionListener(this);
	    // Add mouse listener
	resultsTable.addMouseListener(this);
	
	// Setting tray menu
	    // Adding tray icon
	trayIcon = new TrayIcon(new ImageIcon(getClass().getResource("images/mainIcon.png")).getImage(), "FindGoods");
        trayIcon.setImageAutoSize(true);
	
	    // Creating tray popup menu
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
	
	    // Attaching new tray to system tray
        try {
            java.awt.SystemTray.getSystemTray().add(trayIcon);
        } catch(AWTException ex) {
            JOptionPane.showMessageDialog(null, "Can't show tray icon.",
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        }
	
	    // Listeners for tray menu
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScrollPane = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        filterPane = new javax.swing.JSplitPane();
        mandatoryPropsFilterPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        producerTField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lowerDateTField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        upperDateTField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lowerPriceTField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        upperPriceTField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        categoryСBox = new javax.swing.JComboBox();
        optionalPropsFilterPanel = new javax.swing.JPanel();
        applyFilter = new javax.swing.JButton();
        queryTField = new javax.swing.JTextField();
        findBtn = new javax.swing.JButton();
        resultsScrollPane = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        currencyLabel = new javax.swing.JLabel();
        currencyCBox = new javax.swing.JComboBox();
        likeProductBtn = new javax.swing.JButton();
        favoriteProductTBtn = new javax.swing.JToggleButton();
        productInfoBtn = new javax.swing.JButton();
        browseBtn = new javax.swing.JButton();
        mainJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        loginJMItem = new javax.swing.JMenuItem();
        logoutJMItem = new javax.swing.JMenuItem();
        registerMItem = new javax.swing.JMenuItem();
        importJMItem = new javax.swing.JMenuItem();
        exportJMItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitJMItem = new javax.swing.JMenuItem();
        optionsJMenu = new javax.swing.JMenu();
        showFavoritesJMItem = new javax.swing.JMenuItem();
        updateProductsJMItem = new javax.swing.JMenuItem();
        preferencesJMItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find goods by EZ");
        setBackground(new java.awt.Color(153, 204, 255));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setName("UserJFrame"); // NOI18N

        mainScrollPane.setBackground(new java.awt.Color(153, 204, 255));

        mainPanel.setBackground(new java.awt.Color(153, 204, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(100, 100));

        filterPane.setBackground(new java.awt.Color(153, 204, 255));
        filterPane.setBorder(null);
        filterPane.setDividerLocation(40);
        filterPane.setDividerSize(0);
        filterPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        filterPane.setMinimumSize(new java.awt.Dimension(106, 25));
        filterPane.setPreferredSize(new java.awt.Dimension(644, 25));

        mandatoryPropsFilterPanel.setBackground(new java.awt.Color(153, 204, 255));
        mandatoryPropsFilterPanel.setMinimumSize(new java.awt.Dimension(100, 1));
        mandatoryPropsFilterPanel.setPreferredSize(new java.awt.Dimension(632, 145));

        jLabel2.setText("Производитель");

        jLabel1.setText("Дата выпуска с ");

        jLabel3.setText("по");

        jLabel4.setText("Цена от");

        jLabel5.setText("до");

        jLabel6.setText("Категория товара:");

        javax.swing.GroupLayout mandatoryPropsFilterPanelLayout = new javax.swing.GroupLayout(mandatoryPropsFilterPanel);
        mandatoryPropsFilterPanel.setLayout(mandatoryPropsFilterPanelLayout);
        mandatoryPropsFilterPanelLayout.setHorizontalGroup(
            mandatoryPropsFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mandatoryPropsFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryСBox, 0, 346, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(producerTField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowerDateTField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upperDateTField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowerPriceTField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upperPriceTField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mandatoryPropsFilterPanelLayout.setVerticalGroup(
            mandatoryPropsFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mandatoryPropsFilterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mandatoryPropsFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(producerTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(lowerDateTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(upperDateTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(lowerPriceTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upperPriceTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(categoryСBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        filterPane.setLeftComponent(mandatoryPropsFilterPanel);

        optionalPropsFilterPanel.setBackground(new java.awt.Color(153, 204, 255));
        optionalPropsFilterPanel.setMinimumSize(new java.awt.Dimension(100, 24));
        optionalPropsFilterPanel.setPreferredSize(new java.awt.Dimension(638, 24));

        applyFilter.setText("Применить фильтр");
        applyFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout optionalPropsFilterPanelLayout = new javax.swing.GroupLayout(optionalPropsFilterPanel);
        optionalPropsFilterPanel.setLayout(optionalPropsFilterPanelLayout);
        optionalPropsFilterPanelLayout.setHorizontalGroup(
            optionalPropsFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionalPropsFilterPanelLayout.createSequentialGroup()
                .addContainerGap(1139, Short.MAX_VALUE)
                .addComponent(applyFilter)
                .addContainerGap())
        );
        optionalPropsFilterPanelLayout.setVerticalGroup(
            optionalPropsFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionalPropsFilterPanelLayout.createSequentialGroup()
                .addComponent(applyFilter)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        filterPane.setRightComponent(optionalPropsFilterPanel);

        queryTField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        queryTField.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        queryTField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
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

        findBtn.setBackground(new java.awt.Color(153, 204, 255));
        findBtn.setText("Найти");
        findBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBtnActionPerformed(evt);
            }
        });

        resultsTable.setBackground(new java.awt.Color(226, 239, 255));
        resultsTable.setToolTipText("");
        resultsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        resultsTable.setShowHorizontalLines(false);
        resultsTable.setShowVerticalLines(false);
        resultsScrollPane.setViewportView(resultsTable);

        currencyLabel.setText("Валюта:");

        currencyCBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                currencyCBoxItemStateChanged(evt);
            }
        });

        likeProductBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/like.png"))); // NOI18N
        likeProductBtn.setToolTipText("Оценить товар");
        likeProductBtn.setBorder(null);
        likeProductBtn.setBorderPainted(false);
        likeProductBtn.setContentAreaFilled(false);
        likeProductBtn.setEnabled(false);
        likeProductBtn.setFocusPainted(false);
        likeProductBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/likeGrayscale.png"))); // NOI18N
        likeProductBtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/likeHighlighted.png"))); // NOI18N
        likeProductBtn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/likeHighlightedGrayscale.png"))); // NOI18N
        likeProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeProductBtnActionPerformed(evt);
            }
        });

        favoriteProductTBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/star.png"))); // NOI18N
        favoriteProductTBtn.setToolTipText("Добавить в избранное");
        favoriteProductTBtn.setBorder(null);
        favoriteProductTBtn.setBorderPainted(false);
        favoriteProductTBtn.setContentAreaFilled(false);
        favoriteProductTBtn.setDisabledIcon(null);
        favoriteProductTBtn.setEnabled(false);
        favoriteProductTBtn.setFocusPainted(false);
        favoriteProductTBtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/starHighlighted.png"))); // NOI18N
        favoriteProductTBtn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/starHighlightedGrayscale.png"))); // NOI18N
        favoriteProductTBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/starGrayscale.png"))); // NOI18N
        favoriteProductTBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoriteProductTBtnActionPerformed(evt);
            }
        });

        productInfoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/info.png"))); // NOI18N
        productInfoBtn.setToolTipText("Информация о товаре");
        productInfoBtn.setBorderPainted(false);
        productInfoBtn.setContentAreaFilled(false);
        productInfoBtn.setEnabled(false);
        productInfoBtn.setFocusPainted(false);
        productInfoBtn.setMaximumSize(new java.awt.Dimension(24, 24));
        productInfoBtn.setMinimumSize(new java.awt.Dimension(24, 24));
        productInfoBtn.setPreferredSize(new java.awt.Dimension(24, 24));
        productInfoBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/infoDark.png"))); // NOI18N
        productInfoBtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/infoHighlighted.png"))); // NOI18N
        productInfoBtn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/infoHighlighted.png"))); // NOI18N
        productInfoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productInfoBtnActionPerformed(evt);
            }
        });

        browseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/browse.png"))); // NOI18N
        browseBtn.setToolTipText("Перейти на страницу описания товара");
        browseBtn.setBorder(null);
        browseBtn.setBorderPainted(false);
        browseBtn.setContentAreaFilled(false);
        browseBtn.setEnabled(false);
        browseBtn.setFocusPainted(false);
        browseBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/browseDark.png"))); // NOI18N
        browseBtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/browseHighlighted.png"))); // NOI18N
        browseBtn.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/browseHighlighted.png"))); // NOI18N
        browseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filterPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1278, Short.MAX_VALUE)
            .addComponent(resultsScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(queryTField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(currencyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(currencyCBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(productInfoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(browseBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(favoriteProductTBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(likeProductBtn)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(queryTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterPane, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(favoriteProductTBtn)
                    .addComponent(likeProductBtn)
                    .addComponent(productInfoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currencyCBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyLabel))
                .addContainerGap())
        );

        //resultsScrollPane.getVerticalScrollBar().setVisible(false);
        //resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        mainScrollPane.setViewportView(mainPanel);

        fileJMenu.setText("Файл");

        loginJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        loginJMItem.setText("Войти...");
        loginJMItem.setName(""); // NOI18N
        loginJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(loginJMItem);

        logoutJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        logoutJMItem.setText("Выйти");
        logoutJMItem.setVisible(false);
        logoutJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(logoutJMItem);

        registerMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        registerMItem.setText("Регистрация...");
        registerMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(registerMItem);

        importJMItem.setText("Импорт...");
        importJMItem.setEnabled(false);
        importJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(importJMItem);

        exportJMItem.setText("Экспорт...");
        exportJMItem.setEnabled(false);
        exportJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exportJMItem);
        fileJMenu.add(jSeparator1);

        exitJMItem.setText("Закрыть");
        exitJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMItem);

        mainJMenuBar.add(fileJMenu);

        optionsJMenu.setText("Опции");

        showFavoritesJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        showFavoritesJMItem.setText("Показать избранное");
        showFavoritesJMItem.setEnabled(false);
        showFavoritesJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFavoritesJMItemActionPerformed(evt);
            }
        });
        optionsJMenu.add(showFavoritesJMItem);

        updateProductsJMItem.setText("Обновить базу товаров");
        updateProductsJMItem.setVisible(false);
        optionsJMenu.add(updateProductsJMItem);

        preferencesJMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        preferencesJMItem.setText("Настройки...");
        preferencesJMItem.setEnabled(false);
        preferencesJMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesJMItemActionPerformed(evt);
            }
        });
        optionsJMenu.add(preferencesJMItem);

        mainJMenuBar.add(optionsJMenu);

        setJMenuBar(mainJMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void queryTFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_queryTFieldFocusGained
        queryTField.setBorder(new javax.swing.border.SoftBevelBorder(
            javax.swing.border.BevelBorder.LOWERED,
            new java.awt.Color(51, 153, 255),
            new java.awt.Color(51, 153, 255),
            new java.awt.Color(51, 153, 255), null));
	
	if (queryTField.getForeground() == Color.LIGHT_GRAY) {
	    queryTField.setText("");
	    queryTField.setForeground(Color.BLACK);
	}
    }//GEN-LAST:event_queryTFieldFocusGained

    private void queryTFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_queryTFieldFocusLost
        queryTField.setBorder(new javax.swing.border.SoftBevelBorder(
            javax.swing.border.BevelBorder.LOWERED));
	
	if (queryTField.getText().isEmpty()) {
	    queryTField.setText("Введите название продукта");
	    queryTField.setForeground(Color.LIGHT_GRAY);
	}
    }//GEN-LAST:event_queryTFieldFocusLost

    private void queryTFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_queryTFieldKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            find();
        }
    }//GEN-LAST:event_queryTFieldKeyPressed

    private void findBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBtnActionPerformed
	find();
    }//GEN-LAST:event_findBtnActionPerformed

    private void loginJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginJMItemActionPerformed
        // Some authorization stuff
	//setAuthorizedUser(true);
        AuthorizationJDialog authDlg = new AuthorizationJDialog(this, true);
        authDlg.setVisible(true);
        if (authDlg.user != null) {
	    user = authDlg.user;
	    enableElements(true);
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
        ((ResultsTableModel) this.resultsTable.getModel()).clear();
	this.user = null;
	enableElements(false);
    }//GEN-LAST:event_logoutJMItemActionPerformed

    private void registerMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerMItemActionPerformed
        // Some registration stuff
        RegistrationJDialog regDlg = new RegistrationJDialog(this, true);
        regDlg.setVisible(true);
    }//GEN-LAST:event_registerMItemActionPerformed

    private void exitJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMItemActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitJMItemActionPerformed

    private void preferencesJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesJMItemActionPerformed
        PreferencesJDialog prefDlg = new PreferencesJDialog(this, 
		this.user instanceof Administrator, true);
        prefDlg.setVisible(true);
        updateSelectedStores(prefDlg.getStores());
    }//GEN-LAST:event_preferencesJMItemActionPerformed

    private void currencyCBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_currencyCBoxItemStateChanged
	if (evt.getStateChange() == ItemEvent.SELECTED) {
	    CurrencyCellRenderer renderer = (CurrencyCellRenderer) resultsTable.getColumnModel().getColumn(3).getCellRenderer();
	    String currency = Currency.getInstance(renderer.getLocale()).getCurrencyCode();
	    String newCurrency = evt.getItem().toString();
	    setCurrencyRenderer(newCurrency);
	    
	    if (!newCurrency.equalsIgnoreCase(currency)) {
		double rate = availableCurrencies.get(newCurrency) / availableCurrencies.get(currency);
		((ResultsTableModel) resultsTable.getModel()).recalculatePrices(rate);
	    }
	    resultsTable.repaint();
	}
    }//GEN-LAST:event_currencyCBoxItemStateChanged

    private void likeProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeProductBtnActionPerformed
	if (!likeProductBtn.isEnabled()) {
	    return;
	}
	
	int selectedRow = resultsTable.getSelectedRow();
	Long productId = (Long) ((ResultsTableModel) resultsTable.getModel()).getValueAt(selectedRow, 0);
	Product product = ProductUtils.getProductFromList(searchResults, productId);
	
	try {
	    Double rating = ProductService.rateProduct(productId, this.user.getId(), 1);
	    product.addRatedUser(this.user.getId());
	    
	    ((ResultsTableModel) resultsTable.getModel()).setValueAt(rating, selectedRow, 4);
	    likeProductBtn.setEnabled(false);
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Unexpected database error: can't increment product rating."
		    + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_likeProductBtnActionPerformed

    private void favoriteProductTBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoriteProductTBtnActionPerformed
	int selectedRow = resultsTable.getSelectedRow();
	Long productId = (Long) ((ResultsTableModel) resultsTable.getModel()).getValueAt(selectedRow, 0);
	
	try {
	    if (favoriteProductTBtn.isSelected()) {
		UserService.addProductToFavorites(this.user.getId(), productId);
		this.user.addProductToFavorites(productId);
	    } else {
		UserService.removeProductFromFavorites(this.user.getId(), productId);
		this.user.removeProductFromFavorites(productId);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Unexpected database error: can't increment product rating."
		    + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_favoriteProductTBtnActionPerformed

    private void browseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBtnActionPerformed
        int selectedRow = resultsTable.getSelectedRow();
	Long productId = (Long) ((ResultsTableModel) resultsTable.getModel()).getValueAt(selectedRow, 0);

	try {
	    Product product = ProductUtils.getProductFromList(searchResults, productId);
	    if (product.getUrl() != null) {
		Desktop.getDesktop().browse(product.getUrl().toURI());
	    }
	} catch (IOException | URISyntaxException ex) {
	    JOptionPane.showMessageDialog(null, "Browsing failed.",
		"Error", JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_browseBtnActionPerformed

    private void productInfoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productInfoBtnActionPerformed
        showProductInfo();
    }//GEN-LAST:event_productInfoBtnActionPerformed

    private void showFavoritesJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFavoritesJMItemActionPerformed
	Set<Long> favoriteProductIds = this.user.getFavoriteProducts();
	if (favoriteProductIds == null || favoriteProductIds.isEmpty()) {
	    return;
	}
	
	try {
	    ArrayList<Product> products = new ArrayList<>();
	    for (Long productId : favoriteProductIds) {
		Product product = ProductService.getProduct(productId);
		if (product != null) {
		    products.add(product);
		}
	    }
	    
	    searchResults.clear();
	    searchResults.addAll(products);
	    setDataToResultsTable(products);
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Unexpected database error: " + ex.getLocalizedMessage(), "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	}
	
    }//GEN-LAST:event_showFavoritesJMItemActionPerformed

    private void exportJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportJMItemActionPerformed
        if (searchResults.isEmpty()) {
	    return;
	}

	try {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    fileChooser.addChoosableFileFilter(new XmlFileFilter("xml", "*.xml"));
	    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
		File file = fileChooser.getSelectedFile();
		if (file.getName().length() < 5
			|| !file.getName().substring(file.getName().length() - 4).equals(".xml")) {
		    String path = file.getPath() + ".xml";
		    file = new File(path);
		}
		XMLBuilder.saveProducts(searchResults, file);
	    }
	} catch (ParserConfigurationException | TransformerException | IOException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Export fails: " + ex.getLocalizedMessage(), "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_exportJMItemActionPerformed

    private void importJMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importJMItemActionPerformed
	try {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    fileChooser.addChoosableFileFilter(new XmlFileFilter("xml", "*.xml"));
	    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
		searchResults.clear();
		searchResults.addAll(XMLReader.loadProducts(fileChooser.getSelectedFile()));
		setDataToResultsTable(searchResults);
	    }
	} catch (ParserConfigurationException | SAXException | IOException | ParseException | SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Import fails: " + ex.getLocalizedMessage(), "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_importJMItemActionPerformed

    private void applyFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyFilterActionPerformed
	checkStoresAreLoaded();
	
	searchResults = ProductUtils.filterProducts(searchResults, getFilter());
	try {
	    setDataToResultsTable(searchResults);
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null,
		    "Unexpected database error: " + ex.getLocalizedMessage(), "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	}
    }//GEN-LAST:event_applyFilterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyFilter;
    private javax.swing.JButton browseBtn;
    private javax.swing.JComboBox categoryСBox;
    private javax.swing.JComboBox currencyCBox;
    private javax.swing.JLabel currencyLabel;
    private javax.swing.JMenuItem exitJMItem;
    private javax.swing.JMenuItem exportJMItem;
    private javax.swing.JToggleButton favoriteProductTBtn;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JSplitPane filterPane;
    private javax.swing.JButton findBtn;
    private javax.swing.JMenuItem importJMItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton likeProductBtn;
    private javax.swing.JMenuItem loginJMItem;
    private javax.swing.JMenuItem logoutJMItem;
    private javax.swing.JTextField lowerDateTField;
    private javax.swing.JTextField lowerPriceTField;
    private javax.swing.JMenuBar mainJMenuBar;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JPanel mandatoryPropsFilterPanel;
    private javax.swing.JPanel optionalPropsFilterPanel;
    private javax.swing.JMenu optionsJMenu;
    private javax.swing.JMenuItem preferencesJMItem;
    private javax.swing.JTextField producerTField;
    private javax.swing.JButton productInfoBtn;
    private javax.swing.JTextField queryTField;
    private javax.swing.JMenuItem registerMItem;
    private javax.swing.JScrollPane resultsScrollPane;
    private javax.swing.JTable resultsTable;
    private javax.swing.JMenuItem showFavoritesJMItem;
    private javax.swing.JMenuItem updateProductsJMItem;
    private javax.swing.JTextField upperDateTField;
    private javax.swing.JTextField upperPriceTField;
    // End of variables declaration//GEN-END:variables

    private void find() {
	((ResultsTableModel) resultsTable.getModel()).clear();
	this.mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
	this.repaint();

	checkStoresAreLoaded();
	
	try {
	    ProductFilter filter = getFilter();
	    searchResults = ProductService.findProducts(filter, 1.5);

	    setDataToResultsTable(searchResults);
	    HistoryService.addHistoryEntries(Arrays.asList(queryTField.getText().trim().split(" ")));
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Unexpected database error." + ex.getLocalizedMessage(), "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	} catch (IllegalArgumentException ex) {
	    JOptionPane.showMessageDialog(null, "Illegal filter parameter values.", "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	}
	
	resultsScrollPane.setVisible(true);
	this.mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

	this.repaint();
    }

    private void setCurrencyRenderer(String currencyCode) {
	Locale[] locales = Locale.getAvailableLocales();
	for (Locale l : locales) {
	    Currency currency = java.text.NumberFormat.getCurrencyInstance(l).getCurrency();
	    if (currency.getCurrencyCode().equalsIgnoreCase(currencyCode)) {
		CurrencyCellRenderer rend = (CurrencyCellRenderer) resultsTable.getColumnModel().getColumn(3).getCellRenderer();
		rend.setLocale(l);
		break;
	    }
	}
    }

    private void updateSelectedStores(Map<String, Boolean> stores) {
	selectedStores.clear();
	for (Map.Entry<String, Boolean> entry : stores.entrySet()) {
	    if (!entry.getValue()) {
		continue;
	    }
	    try {
		Store store = StoreService.getStore(entry.getKey());
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

    private void enableElements(boolean isEnabled) {
	// Show/hide some user stuff
	loginJMItem.setVisible(!isEnabled);
	registerMItem.setVisible(!isEnabled);
	logoutJMItem.setVisible(isEnabled);
	preferencesJMItem.setEnabled(isEnabled);
	exportJMItem.setEnabled(isEnabled);
	importJMItem.setEnabled(isEnabled);
	showFavoritesJMItem.setEnabled(isEnabled);
	
	for (Component comp : this.mainPanel.getComponents()) {
	    comp.setEnabled(isEnabled);
	    comp.setVisible(isEnabled);
	}
	
	productInfoBtn.setEnabled(false);
	browseBtn.setEnabled(false);
	favoriteProductTBtn.setEnabled(false);
	likeProductBtn.setEnabled(false);

	// Show/hide some admin stuff
	if (this.user instanceof Administrator) {
	    enableAdminElements(isEnabled);
	}

	if (!isEnabled) {
	    return;
	}
	
	searchResults = new ArrayList<>();
	
	try {
	    categories = new ArrayList<>();
	    categories.add("-");
	    categories.addAll(CategoryService.getCategoryNames());
	    categoryСBox.setModel(new javax.swing.DefaultComboBoxModel(
		    categories.toArray(new String [this.categories.size()])));
	    categoryСBox.setSelectedIndex(0);
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Can't load product categories.", "Error", 
		    JOptionPane.ERROR_MESSAGE, null);
	    return;
	}
	
	try {
	    availableCurrencies = (HashMap<String, Double>) HTTPClient.getExchangeRateMap();
	    currencyCBox.removeAllItems();
	    for (String key : availableCurrencies.keySet()) {
		currencyCBox.addItem(key);
	    }
	    currencyCBox.setSelectedItem(Currency.getInstance(Locale.getDefault()).getCurrencyCode());
	} catch (ParserConfigurationException | SAXException | IOException ex) {
	    JOptionPane.showMessageDialog(null, "Can't load exchange rate map.", "Error",
		    JOptionPane.ERROR_MESSAGE, null);
	    currencyCBox.removeAllItems();
	    currencyCBox.addItem(NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode());
	}
    }

    private void enableAdminElements(boolean isEnabled) {
	// Enabling some admin stuff
	this.updateProductsJMItem.setVisible(isEnabled);
	this.updateProductsJMItem.setEnabled(isEnabled);
    }

    private ProductFilter getFilter() {
	String name = queryTField.getText();
	if (name.equals("Введите название продукта")) {
	    name = "";
	}
	
	if (categoryСBox.getSelectedItem().toString().equals("-") && producerTField.getText().isEmpty()
		&& lowerDateTField.getText().isEmpty() && upperDateTField.getText().isEmpty()) {
	    return new ProductFilter(selectedStores, name, new Long(-1), null,
		    null, null, -1.0, -1.0);
	}
	
	long categoryId = -1;
	if (!categoryСBox.getSelectedItem().toString().equals("-")) {
	    try {
		categoryId = CategoryService.findCategory(categoryСBox.getSelectedItem().toString()).getId();
	    } catch (NullPointerException | SQLException ex) {
		JOptionPane.showMessageDialog(null, "Can't resolve category from database."
			+ " Category'll be ignored.", "Warning", JOptionPane.WARNING_MESSAGE, null);
	    }
	}
	
	final String producer = producerTField.getText();
	
	final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	Date ldate = null, udate = null;
	if (!lowerDateTField.getText().isEmpty()) {
	    try {
		ldate = sdf.parse(lowerDateTField.getText());
	    } catch (ParseException ex) {
		JOptionPane.showMessageDialog(null, "Wrong date format. Lower date'll be ignored."
			+ "\nUsage: dd.MM.yyyy", "Warning", JOptionPane.WARNING_MESSAGE, null);
	    }
	}
	if (!upperDateTField.getText().isEmpty()) {
	    try {
		udate = sdf.parse(upperDateTField.getText());
	    } catch (ParseException ex) {
		JOptionPane.showMessageDialog(null, "Wrong date format. Upper date'll be ignored."
			+ "\nUsage: dd.MM.yyyy", "Warning", JOptionPane.WARNING_MESSAGE, null);
	    }
	}
	
	String lowerPrice = lowerPriceTField.getText();
	String upperPrice = upperPriceTField.getText();
	double lprice = lowerPrice.isEmpty() ? -1.0 : Double.valueOf(lowerPrice);
	double uprice = upperPrice.isEmpty() ? -1.0 : Double.valueOf(upperPrice);
	
	return new ProductFilter(selectedStores, name, categoryId, producer, ldate, udate, lprice, uprice);
    }

    private void setDataToResultsTable(List<Product> result) throws SQLException {
	((ResultsTableModel) resultsTable.getModel()).clear();
	
	for (Product product : result) {
	    ImageIcon imgIcon = prepareImage(new ImageIcon(product.getImage()),
		    resultsTable.getRowHeight(),
		    resultsTable.getColumnModel().getColumn(1).getWidth());
	    Double rating = ProductService.getRating(product.getId());

	    ((ResultsTableModel) resultsTable.getModel()).addRow(
		    product.getId(), imgIcon,
		    formatProductDescription(product), product.getPrice(), rating);
	}
    }
    
    private JEditorPane formatProductDescription(Product product) {
	JEditorPane pane = new JEditorPane();
	pane.setContentType("text/html");
	
	StringBuilder content = new StringBuilder();
	content.append("<html><b>").append(product.getName()).append("</b> - ");
	content.append(product.getStore().getName()).append("<br>");
	content.append(product.getProducer()).append("<br>");
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	content.append(sdf.format(product.getProductionDate())).append("</html>");
	
	pane.setText(content.toString());

	return pane;
    }
    
    /**
     * Checks that an image was loaded successfully and scales it to the cell size if needed.
     * @return the original scaled image <b>OR</b> default scaled image if the image was not loaded
     */
    private ImageIcon prepareImage(ImageIcon imgIcon, int cellWidth, int cellHeight) {
	if (imgIcon == null || imgIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
	    imgIcon = new ImageIcon(getClass().getResource("images/defaultProductImage.png"));
	}
	
	return new ImageIcon(scaleImage(imgIcon, cellWidth, cellHeight));
    }
    
    /**
     * Scales an image to the cell size.
     * @return the original image if it is smaller than the cell <b>OR</b> the scaled image otherwise
     */
    private Image scaleImage(ImageIcon img, int cellWidth, int cellHeight) {
	int height = img.getIconHeight();
	int width = img.getIconWidth();
	if (height < cellHeight && width < cellWidth) {
	    return img.getImage();
	}
	
	double scale = 1.0;
	if (height > cellHeight) {
	    scale = ((double) cellHeight) / height;
	    height = cellHeight;
	}

	width = (int) (scale * width);
	if (width > cellWidth) {
	    scale = ((double) cellWidth) / width;
	    height = (int) (scale * height);
	    width = cellWidth;
	}
	
	return img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void showProductInfo() {
	int selectedRow = resultsTable.getSelectedRow();
	
	if (selectedRow == -1) {
	    return;
	}
	
	Long productId = (Long) ((ResultsTableModel) resultsTable.getModel()).getValueAt(selectedRow, 0);
	Product product = ProductUtils.getProductFromList(searchResults, productId);
	
	ProductInfoJDialog infoDlg = new ProductInfoJDialog(this, true, product);
	infoDlg.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
	int selectedRow = resultsTable.getSelectedRow();
	
	if (selectedRow == -1) {
	    productInfoBtn.setEnabled(false);
	    browseBtn.setEnabled(false);
	    favoriteProductTBtn.setEnabled(false);
	    likeProductBtn.setEnabled(false);
	    return;
	}
	
	Long productId = (Long) ((ResultsTableModel) resultsTable.getModel()).getValueAt(selectedRow, 0);
	Product product = ProductUtils.getProductFromList(searchResults, productId);

	
	productInfoBtn.setEnabled(true);
	browseBtn.setEnabled(true);
	favoriteProductTBtn.setEnabled(true);
	favoriteProductTBtn.setSelected(this.user.getFavoriteProducts().contains(productId));
	likeProductBtn.setEnabled(!product.getRatedUsers().contains(this.user.getId()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	if (e.getClickCount() == 2) {
	    showProductInfo();
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void checkStoresAreLoaded() {
	if (selectedStores.isEmpty()) {
	    try {
		updateSelectedStores(XMLReader.loadSettings("src\\settings.xml"));
	    } catch (ParserConfigurationException | IOException | SAXException ex) {
		JOptionPane.showMessageDialog(null, "Can't load store's settings.", "Error", 
			JOptionPane.ERROR_MESSAGE, null);
		return;
	    }
	}
    }
    
}
