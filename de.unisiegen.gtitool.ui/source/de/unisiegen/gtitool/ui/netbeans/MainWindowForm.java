package de.unisiegen.gtitool.ui.netbeans;



import de.unisiegen.gtitool.ui.logic.MainWindow;

/**
 *
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class MainWindowForm extends javax.swing.JFrame {
 
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -5068944082893352104L;


    /**
     * The {@link MainWindow}.
     */ 
    private MainWindow logic;
    
    
    /**
     * Creates new form MainWindow
     * 
     * @param logic The {@link MainWindow}.
     */
    public MainWindowForm(MainWindow logic) {
        this.logic = logic;
        initComponents();
    }
    
    /**
     * 
     * Get the logic class for this gui class
     *
     * @return the logic class for this gui class
     */
    public MainWindow getLogic()
    {
      return this.logic;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        modeSettingsGroup = new javax.swing.ButtonGroup();
        toolbarButton = new javax.swing.ButtonGroup();
        jToolBarMain = new javax.swing.JToolBar();
        jToolBarFile = new javax.swing.JToolBar();
        jButtonNew = new javax.swing.JButton();
        jButtonOpen = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonSaveAs = new javax.swing.JButton();
        jSeparatorMain = new javax.swing.JSeparator();
        jToolBarEdit = new javax.swing.JToolBar();
        jButtonMouse = new javax.swing.JToggleButton();
        jButtonAddState = new javax.swing.JToggleButton();
        jButtonAddTransition = new javax.swing.JToggleButton();
        jButtonStartState = new javax.swing.JToggleButton();
        jButtonFinalState = new javax.swing.JToggleButton();
        jButtonEditAlphabet = new javax.swing.JButton();
        jSeparatorMain1 = new javax.swing.JSeparator();
        jButtonPrevious = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        jButtonNextStep = new javax.swing.JButton();
        jButtonAutoStep = new javax.swing.JToggleButton();
        jButtonStop = new javax.swing.JButton();
        jGTITabbedPaneMain = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jSeparatorFile1 = new javax.swing.JSeparator();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jMenuItemSaveAll = new javax.swing.JMenuItem();
        jSeparatorFile2 = new javax.swing.JSeparator();
        jMenuRecentlyUsed = new javax.swing.JMenu();
        jSeparatorFile3 = new javax.swing.JSeparator();
        jMenuItemQuit = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemUndo = new javax.swing.JMenuItem();
        jMenuItemRedo = new javax.swing.JMenuItem();
        jSeparatorEdit1 = new javax.swing.JSeparator();
        jMenuItemCut = new javax.swing.JMenuItem();
        jMenuItemCopy = new javax.swing.JMenuItem();
        jMenuItemPaste = new javax.swing.JMenuItem();
        jSeparatorEdit2 = new javax.swing.JSeparator();
        jMenuItemPreferences = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jCheckBoxMenuItemConsole = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemTable = new javax.swing.JCheckBoxMenuItem();
        jMenuExecute = new javax.swing.JMenu();
        jMenuItemValidate = new javax.swing.JMenuItem();
        jMenuItemEnterWord = new javax.swing.JMenuItem();
        jMenuItemEditMachine = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("mainframe");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBarMain.setFloatable(false);
        jToolBarMain.setBorderPainted(false);
        jToolBarFile.setBorder(null);
        jToolBarFile.setFloatable(false);
        jToolBarFile.setBorderPainted(false);
        jToolBarFile.setOpaque(false);
        jButtonNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new24.png")));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jButtonNew.setToolTipText(bundle.getString("MainWindow.NewToolTip")); // NOI18N
        jButtonNew.setBorderPainted(false);
        jButtonNew.setFocusPainted(false);
        jButtonNew.setFocusable(false);
        jButtonNew.setOpaque(false);
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleNew(evt);
            }
        });

        jToolBarFile.add(jButtonNew);

        jButtonOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/open24.png")));
        jButtonOpen.setToolTipText(bundle.getString("MainWindow.OpenToolTip")); // NOI18N
        jButtonOpen.setBorderPainted(false);
        jButtonOpen.setFocusPainted(false);
        jButtonOpen.setFocusable(false);
        jButtonOpen.setOpaque(false);
        jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleOpen(evt);
            }
        });

        jToolBarFile.add(jButtonOpen);

        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/save24.png")));
        jButtonSave.setToolTipText(bundle.getString("MainWindow.SaveToolTip")); // NOI18N
        jButtonSave.setBorderPainted(false);
        jButtonSave.setFocusPainted(false);
        jButtonSave.setFocusable(false);
        jButtonSave.setOpaque(false);
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSave(evt);
            }
        });

        jToolBarFile.add(jButtonSave);

        jButtonSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveas24.png")));
        jButtonSaveAs.setToolTipText(bundle.getString("MainWindow.SaveAsToolTip")); // NOI18N
        jButtonSaveAs.setBorderPainted(false);
        jButtonSaveAs.setFocusPainted(false);
        jButtonSaveAs.setFocusable(false);
        jButtonSaveAs.setOpaque(false);
        jButtonSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSaveAs(evt);
            }
        });

        jToolBarFile.add(jButtonSaveAs);

        jToolBarMain.add(jToolBarFile);

        jSeparatorMain.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorMain.setMaximumSize(new java.awt.Dimension(5, 32));
        jToolBarMain.add(jSeparatorMain);

        jToolBarEdit.setFloatable(false);
        jToolBarEdit.setBorderPainted(false);
        jToolBarEdit.setOpaque(false);
        toolbarButton.add(jButtonMouse);
        jButtonMouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_mouse.gif")));
        jButtonMouse.setSelected(true);
        jButtonMouse.setToolTipText(bundle.getString("MachinePanel.Mouse")); // NOI18N
        jButtonMouse.setBorderPainted(false);
        jButtonMouse.setFocusPainted(false);
        jButtonMouse.setFocusable(false);
        jButtonMouse.setMaximumSize(new java.awt.Dimension(36, 36));
        jButtonMouse.setMinimumSize(new java.awt.Dimension(36, 36));
        jButtonMouse.setOpaque(false);
        jButtonMouse.setPreferredSize(new java.awt.Dimension(36, 36));
        jButtonMouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarMouse(evt);
            }
        });

        jToolBarEdit.add(jButtonMouse);

        toolbarButton.add(jButtonAddState);
        jButtonAddState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_add.gif")));
        jButtonAddState.setToolTipText(bundle.getString("MachinePanel.AddState")); // NOI18N
        jButtonAddState.setBorderPainted(false);
        jButtonAddState.setFocusPainted(false);
        jButtonAddState.setFocusable(false);
        jButtonAddState.setMaximumSize(new java.awt.Dimension(36, 36));
        jButtonAddState.setMinimumSize(new java.awt.Dimension(36, 36));
        jButtonAddState.setOpaque(false);
        jButtonAddState.setPreferredSize(new java.awt.Dimension(36, 36));
        jButtonAddState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarAddState(evt);
            }
        });

        jToolBarEdit.add(jButtonAddState);

        toolbarButton.add(jButtonAddTransition);
        jButtonAddTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_transition.gif")));
        jButtonAddTransition.setToolTipText(bundle.getString("MachinePanel.AddTransition")); // NOI18N
        jButtonAddTransition.setBorderPainted(false);
        jButtonAddTransition.setFocusPainted(false);
        jButtonAddTransition.setFocusable(false);
        jButtonAddTransition.setMaximumSize(new java.awt.Dimension(36, 36));
        jButtonAddTransition.setMinimumSize(new java.awt.Dimension(36, 36));
        jButtonAddTransition.setOpaque(false);
        jButtonAddTransition.setPreferredSize(new java.awt.Dimension(36, 36));
        jButtonAddTransition.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarAddTransition(evt);
            }
        });

        jToolBarEdit.add(jButtonAddTransition);

        toolbarButton.add(jButtonStartState);
        jButtonStartState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_start.gif")));
        jButtonStartState.setToolTipText(bundle.getString("MachinePanel.StartState")); // NOI18N
        jButtonStartState.setBorderPainted(false);
        jButtonStartState.setFocusPainted(false);
        jButtonStartState.setFocusable(false);
        jButtonStartState.setMaximumSize(new java.awt.Dimension(36, 36));
        jButtonStartState.setMinimumSize(new java.awt.Dimension(36, 36));
        jButtonStartState.setOpaque(false);
        jButtonStartState.setPreferredSize(new java.awt.Dimension(36, 36));
        jButtonStartState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarAddStartState(evt);
            }
        });

        jToolBarEdit.add(jButtonStartState);

        toolbarButton.add(jButtonFinalState);
        jButtonFinalState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_final.gif")));
        jButtonFinalState.setToolTipText(bundle.getString("MachinePanel.FinalState")); // NOI18N
        jButtonFinalState.setBorderPainted(false);
        jButtonFinalState.setFocusPainted(false);
        jButtonFinalState.setFocusable(false);
        jButtonFinalState.setMaximumSize(new java.awt.Dimension(36, 36));
        jButtonFinalState.setMinimumSize(new java.awt.Dimension(36, 36));
        jButtonFinalState.setOpaque(false);
        jButtonFinalState.setPreferredSize(new java.awt.Dimension(36, 36));
        jButtonFinalState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarAddFinalState(evt);
            }
        });

        jToolBarEdit.add(jButtonFinalState);

        jButtonEditAlphabet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_alphabet.png")));
        jButtonEditAlphabet.setToolTipText(bundle.getString("MachinePanel.EditAlphabet")); // NOI18N
        jButtonEditAlphabet.setBorderPainted(false);
        jButtonEditAlphabet.setFocusable(false);
        jButtonEditAlphabet.setOpaque(false);
        jButtonEditAlphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleToolbarEditAlphabet(evt);
            }
        });

        jToolBarEdit.add(jButtonEditAlphabet);

        jSeparatorMain1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorMain1.setMaximumSize(new java.awt.Dimension(5, 32));
        jToolBarEdit.add(jSeparatorMain1);

        jButtonPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/toolbar_previous.png")));
        jButtonPrevious.setToolTipText(bundle.getString("MachinePanel.WordModePreviousStep")); // NOI18N
        jButtonPrevious.setBorderPainted(false);
        jButtonPrevious.setFocusable(false);
        jButtonPrevious.setOpaque(false);
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleWordPreviousStep(evt);
            }
        });

        jToolBarEdit.add(jButtonPrevious);

        jButtonStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/toolbar_start.png")));
        jButtonStart.setToolTipText(bundle.getString("MachinePanel.WordModeStart")); // NOI18N
        jButtonStart.setBorderPainted(false);
        jButtonStart.setFocusable(false);
        jButtonStart.setOpaque(false);
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleWordStart(evt);
            }
        });

        jToolBarEdit.add(jButtonStart);

        jButtonNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/toolbar_next.png")));
        jButtonNextStep.setToolTipText(bundle.getString("MachinePanel.WordModeNextStep")); // NOI18N
        jButtonNextStep.setBorderPainted(false);
        jButtonNextStep.setFocusable(false);
        jButtonNextStep.setOpaque(false);
        jButtonNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleWordNextStep(evt);
            }
        });

        jToolBarEdit.add(jButtonNextStep);

        jButtonAutoStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/toolbar_autostep.png")));
        jButtonAutoStep.setToolTipText(bundle.getString("MachinePanel.WordModeAutoStep")); // NOI18N
        jButtonAutoStep.setBorderPainted(false);
        jButtonAutoStep.setEnabled(false);
        jButtonAutoStep.setFocusPainted(false);
        jButtonAutoStep.setFocusable(false);
        jButtonAutoStep.setMaximumSize(new java.awt.Dimension(36, 36));
        jButtonAutoStep.setMinimumSize(new java.awt.Dimension(36, 36));
        jButtonAutoStep.setOpaque(false);
        jButtonAutoStep.setPreferredSize(new java.awt.Dimension(36, 36));
        jButtonAutoStep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleWordAutoStep(evt);
            }
        });

        jToolBarEdit.add(jButtonAutoStep);

        jButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/toolbar_stop.png")));
        jButtonStop.setToolTipText(bundle.getString("MachinePanel.WordModeStop")); // NOI18N
        jButtonStop.setBorderPainted(false);
        jButtonStop.setFocusable(false);
        jButtonStop.setOpaque(false);
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleWordStop(evt);
            }
        });

        jToolBarEdit.add(jButtonStop);

        jToolBarMain.add(jToolBarEdit);

        getContentPane().add(jToolBarMain, java.awt.BorderLayout.NORTH);

        jGTITabbedPaneMain.setFocusable(false);
        jGTITabbedPaneMain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jGTITabbedPaneMainStateChanged(evt);
            }
        });

        getContentPane().add(jGTITabbedPaneMain, java.awt.BorderLayout.CENTER);

        jMenuFile.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.FileMnemonic").charAt(0));
        jMenuFile.setText(bundle.getString("MainWindow.File")); // NOI18N
        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new16.gif")));
        jMenuItemNew.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.NewMnemonic").charAt(0));
        jMenuItemNew.setText(bundle.getString("MainWindow.New")); // NOI18N
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleNew(evt);
            }
        });

        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/open16.png")));
        jMenuItemOpen.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.OpenMnemonic").charAt(0));
        jMenuItemOpen.setText(bundle.getString("MainWindow.Open")); // NOI18N
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleOpen(evt);
            }
        });

        jMenuFile.add(jMenuItemOpen);

        jMenuItemClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuItemClose.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CloseMnemonic").charAt(0));
        jMenuItemClose.setText(bundle.getString("MainWindow.Close")); // NOI18N
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleClose(evt);
            }
        });

        jMenuFile.add(jMenuItemClose);

        jMenuFile.add(jSeparatorFile1);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/save16.png")));
        jMenuItemSave.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveMnemonic").charAt(0));
        jMenuItemSave.setText(bundle.getString("MainWindow.Save")); // NOI18N
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSave(evt);
            }
        });

        jMenuFile.add(jMenuItemSave);

        jMenuItemSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveas16.png")));
        jMenuItemSaveAs.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveAsMnemonic").charAt(0));
        jMenuItemSaveAs.setText(bundle.getString("MainWindow.SaveAs")); // NOI18N
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSaveAs(evt);
            }
        });

        jMenuFile.add(jMenuItemSaveAs);

        jMenuItemSaveAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSaveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveAll16.gif")));
        jMenuItemSaveAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveAllMnemonic").charAt(0));
        jMenuItemSaveAll.setText(bundle.getString("MainWindow.SaveAll")); // NOI18N
        jMenuFile.add(jMenuItemSaveAll);

        jMenuFile.add(jSeparatorFile2);

        jMenuRecentlyUsed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuRecentlyUsed.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.RecentlyUsedMnemonic").charAt(0));
        jMenuRecentlyUsed.setText(bundle.getString("MainWindow.RecentlyUsed")); // NOI18N
        jMenuFile.add(jMenuRecentlyUsed);

        jMenuFile.add(jSeparatorFile3);

        jMenuItemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuItemQuit.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.QuitMnemonic").charAt(0));
        jMenuItemQuit.setText(bundle.getString("MainWindow.Quit")); // NOI18N
        jMenuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleQuit(evt);
            }
        });

        jMenuFile.add(jMenuItemQuit);

        jMenuBarMain.add(jMenuFile);

        jMenuEdit.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.EditMnemonic").charAt(0));
        jMenuEdit.setText(bundle.getString("MainWindow.Edit")); // NOI18N
        jMenuItemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/undo16.gif")));
        jMenuItemUndo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.UndoMnemonic").charAt(0));
        jMenuItemUndo.setText(bundle.getString("MainWindow.Undo")); // NOI18N
        jMenuEdit.add(jMenuItemUndo);

        jMenuItemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/redo16.gif")));
        jMenuItemRedo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.RedoMnemonic").charAt(0));
        jMenuItemRedo.setText(bundle.getString("MainWindow.Redo")); // NOI18N
        jMenuEdit.add(jMenuItemRedo);

        jMenuEdit.add(jSeparatorEdit1);

        jMenuItemCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/cut16.gif")));
        jMenuItemCut.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CutMnemonic").charAt(0));
        jMenuItemCut.setText(bundle.getString("MainWindow.Cut")); // NOI18N
        jMenuEdit.add(jMenuItemCut);

        jMenuItemCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/copy16.gif")));
        jMenuItemCopy.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CopyMnemonic").charAt(0));
        jMenuItemCopy.setText(bundle.getString("MainWindow.Copy")); // NOI18N
        jMenuEdit.add(jMenuItemCopy);

        jMenuItemPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/paste16.gif")));
        jMenuItemPaste.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.PasteMnemonic").charAt(0));
        jMenuItemPaste.setText(bundle.getString("MainWindow.Paste")); // NOI18N
        jMenuEdit.add(jMenuItemPaste);

        jMenuEdit.add(jSeparatorEdit2);

        jMenuItemPreferences.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/preferences16.png")));
        jMenuItemPreferences.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.PreferencesMnemonic").charAt(0));
        jMenuItemPreferences.setText(bundle.getString("MainWindow.Preferences")); // NOI18N
        jMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreferencesActionPerformed(evt);
            }
        });

        jMenuEdit.add(jMenuItemPreferences);

        jMenuBarMain.add(jMenuEdit);

        jMenuView.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ViewMnemonic").charAt(0));
        jMenuView.setText(bundle.getString("MainWindow.View")); // NOI18N
        jCheckBoxMenuItemConsole.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ConsoleMnemonic").charAt(0));
        jCheckBoxMenuItemConsole.setSelected(true);
        jCheckBoxMenuItemConsole.setText(bundle.getString("MainWindow.Console")); // NOI18N
        jCheckBoxMenuItemConsole.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemConsoleItemStateChanged(evt);
            }
        });

        jMenuView.add(jCheckBoxMenuItemConsole);

        jCheckBoxMenuItemTable.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.TableMnemonic").charAt(0));
        jCheckBoxMenuItemTable.setSelected(true);
        jCheckBoxMenuItemTable.setText(bundle.getString("MainWindow.Table")); // NOI18N
        jCheckBoxMenuItemTable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemTableItemStateChanged(evt);
            }
        });

        jMenuView.add(jCheckBoxMenuItemTable);

        jMenuBarMain.add(jMenuView);

        jMenuExecute.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ExecuteMnemonic").charAt(0));
        jMenuExecute.setText(bundle.getString("MainWindow.Execute")); // NOI18N
        jMenuItemValidate.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ValidateMnemonic").charAt(0));
        jMenuItemValidate.setText(bundle.getString("MainWindow.Validate")); // NOI18N
        jMenuItemValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemValidateActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemValidate);

        jMenuItemEnterWord.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.EnterWordMnemonic").charAt(0));
        jMenuItemEnterWord.setText(bundle.getString("MainWindow.EnterWord")); // NOI18N
        jMenuItemEnterWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEnterWordActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemEnterWord);

        jMenuItemEditMachine.setText(bundle.getString("MainWindow.EditMachine")); // NOI18N
        jMenuItemEditMachine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditMachineActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemEditMachine);

        jMenuBarMain.add(jMenuExecute);

        jMenuHelp.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.HelpMnemonic").charAt(0));
        jMenuHelp.setText(bundle.getString("MainWindow.Help")); // NOI18N
        jMenuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAbout.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.AboutMnemonic").charAt(0));
        jMenuItemAbout.setText(bundle.getString("MainWindow.About")); // NOI18N
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });

        jMenuHelp.add(jMenuItemAbout);

        jMenuBarMain.add(jMenuHelp);

        setJMenuBar(jMenuBarMain);

        setBounds(0, 0, 600, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTITabbedPaneMainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jGTITabbedPaneMainStateChanged
        this.logic.handleTabbedPaneStateChanged();
    }//GEN-LAST:event_jGTITabbedPaneMainStateChanged

    private void handleWordStop(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleWordStop
        this.logic.handleWordStop();
    }//GEN-LAST:event_handleWordStop

    private void handleWordNextStep(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleWordNextStep
        this.logic.handleWordNextStep();
    }//GEN-LAST:event_handleWordNextStep

    private void handleWordStart(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleWordStart
        this.logic.handleWordStart();
    }//GEN-LAST:event_handleWordStart

    private void handleWordPreviousStep(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleWordPreviousStep
        this.logic.handleWordPreviousStep();
    }//GEN-LAST:event_handleWordPreviousStep

    private void handleToolbarEditAlphabet(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleToolbarEditAlphabet
        this.logic.handleEditAlphabet();
    }//GEN-LAST:event_handleToolbarEditAlphabet

    private void jMenuItemEditMachineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditMachineActionPerformed
        this.logic.handleEditMachine();
    }//GEN-LAST:event_jMenuItemEditMachineActionPerformed

    private void handleWordAutoStep(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleWordAutoStep
        this.logic.handleWordAutoStep(evt);
    }//GEN-LAST:event_handleWordAutoStep

    private void handleClose(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleClose
        this.logic.handleClose();
    }//GEN-LAST:event_handleClose

    private void jCheckBoxMenuItemTableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemTableItemStateChanged
        this.logic.handleTableStateChanged();
    }//GEN-LAST:event_jCheckBoxMenuItemTableItemStateChanged

    private void jCheckBoxMenuItemConsoleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemConsoleItemStateChanged
        this.logic.handleConsoleStateChanged();
    }//GEN-LAST:event_jCheckBoxMenuItemConsoleItemStateChanged

    private void handleSaveAs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleSaveAs
        this.logic.handleSaveAs();
    }//GEN-LAST:event_handleSaveAs

    private void handleToolbarAddFinalState(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarAddFinalState
        this.logic.handleToolbarEnd(this.jButtonFinalState.isSelected());
    }//GEN-LAST:event_handleToolbarAddFinalState

    private void handleToolbarAddStartState(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarAddStartState
        this.logic.handleToolbarStart(this.jButtonStartState.isSelected());
    }//GEN-LAST:event_handleToolbarAddStartState

    private void handleToolbarAddTransition(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarAddTransition
        this.logic.handleToolbarTransition(this.jButtonAddTransition.isSelected());
    }//GEN-LAST:event_handleToolbarAddTransition

    private void handleToolbarAddState(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarAddState
        this.logic.handleToolbarAddState(this.jButtonAddState.isSelected());
    }//GEN-LAST:event_handleToolbarAddState

    private void handleToolbarMouse(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarMouse
        this.logic.handleToolbarMouse(this.jButtonMouse.isSelected());
    }//GEN-LAST:event_handleToolbarMouse

    private void handleSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleSave
        logic.handleSave();
    }//GEN-LAST:event_handleSave

    private void jMenuItemValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemValidateActionPerformed
      this.logic.handleValidate();
    }//GEN-LAST:event_jMenuItemValidateActionPerformed

    private void jMenuItemEnterWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEnterWordActionPerformed
      this.logic.handleEnterWord();
    }//GEN-LAST:event_jMenuItemEnterWordActionPerformed

    private void handleNew(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleNew
      this.logic.handleNew();
    }//GEN-LAST:event_handleNew

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleQuit();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
      this.logic.handleAbout(); 
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

    private void jMenuItemPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPreferencesActionPerformed
      this.logic.handlePreferences();
    }//GEN-LAST:event_jMenuItemPreferencesActionPerformed

    private void handleQuit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleQuit
      this.logic.handleQuit();
    }//GEN-LAST:event_handleQuit

    private void handleOpen(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleOpen
      this.logic.handleOpen(); 
    }//GEN-LAST:event_handleOpen
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JToggleButton jButtonAddState;
    public javax.swing.JToggleButton jButtonAddTransition;
    public javax.swing.JToggleButton jButtonAutoStep;
    public javax.swing.JButton jButtonEditAlphabet;
    public javax.swing.JToggleButton jButtonFinalState;
    public javax.swing.JToggleButton jButtonMouse;
    public javax.swing.JButton jButtonNew;
    public javax.swing.JButton jButtonNextStep;
    public javax.swing.JButton jButtonOpen;
    public javax.swing.JButton jButtonPrevious;
    public javax.swing.JButton jButtonSave;
    public javax.swing.JButton jButtonSaveAs;
    public javax.swing.JButton jButtonStart;
    public javax.swing.JToggleButton jButtonStartState;
    public javax.swing.JButton jButtonStop;
    public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemConsole;
    public javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemTable;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPaneMain;
    public javax.swing.JMenuBar jMenuBarMain;
    public javax.swing.JMenu jMenuEdit;
    public javax.swing.JMenu jMenuExecute;
    public javax.swing.JMenu jMenuFile;
    public javax.swing.JMenu jMenuHelp;
    public javax.swing.JMenuItem jMenuItemAbout;
    public javax.swing.JMenuItem jMenuItemClose;
    public javax.swing.JMenuItem jMenuItemCopy;
    public javax.swing.JMenuItem jMenuItemCut;
    public javax.swing.JMenuItem jMenuItemEditMachine;
    public javax.swing.JMenuItem jMenuItemEnterWord;
    public javax.swing.JMenuItem jMenuItemNew;
    public javax.swing.JMenuItem jMenuItemOpen;
    public javax.swing.JMenuItem jMenuItemPaste;
    public javax.swing.JMenuItem jMenuItemPreferences;
    public javax.swing.JMenuItem jMenuItemQuit;
    public javax.swing.JMenuItem jMenuItemRedo;
    public javax.swing.JMenuItem jMenuItemSave;
    public javax.swing.JMenuItem jMenuItemSaveAll;
    public javax.swing.JMenuItem jMenuItemSaveAs;
    public javax.swing.JMenuItem jMenuItemUndo;
    public javax.swing.JMenuItem jMenuItemValidate;
    public javax.swing.JMenu jMenuRecentlyUsed;
    public javax.swing.JMenu jMenuView;
    public javax.swing.JSeparator jSeparatorEdit1;
    public javax.swing.JSeparator jSeparatorEdit2;
    public javax.swing.JSeparator jSeparatorFile1;
    public javax.swing.JSeparator jSeparatorFile2;
    public javax.swing.JSeparator jSeparatorFile3;
    public javax.swing.JSeparator jSeparatorMain;
    public javax.swing.JSeparator jSeparatorMain1;
    public javax.swing.JToolBar jToolBarEdit;
    public javax.swing.JToolBar jToolBarFile;
    public javax.swing.JToolBar jToolBarMain;
    public javax.swing.ButtonGroup modeSettingsGroup;
    public javax.swing.ButtonGroup toolbarButton;
    // End of variables declaration//GEN-END:variables

}
