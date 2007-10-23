/*
 * MainWindowForm.java
 *
 * Created on May 29, 2007, 3:26 PM
 */
package de.unisiegen.gtitool.ui.netbeans;



import de.unisiegen.gtitool.ui.logic.MainWindow;

/**
 *
 * @author  Benjamin Mies
 * @version $Id$
 */
public class MainWindowForm extends javax.swing.JFrame {
    
    /** Creates new form MainWindowForm */
    public MainWindowForm() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Erzeugter Quelltext ">//GEN-BEGIN:initComponents
    private void initComponents() {
        javax.swing.JMenuBar MainMenuBar;
        javax.swing.JMenu editMenu;
        javax.swing.JSeparator editMenuSeparator1;
        javax.swing.JSeparator editMenuSeperator;
        javax.swing.JToolBar editToolBar;
        javax.swing.JMenu fileMenu;
        javax.swing.JSeparator fileMenuSeperator1;
        javax.swing.JSeparator fileMenuSerpator2;
        javax.swing.JMenu helpMenu;
        javax.swing.JToolBar mainToolbar;
        javax.swing.JButton newButton;
        javax.swing.JMenuItem newItem;
        javax.swing.JButton openButton;
        javax.swing.JMenuItem openItem;
        javax.swing.JMenuItem quitItem;

        modeSettingsGroup = new javax.swing.ButtonGroup();
        mainToolbar = new javax.swing.JToolBar();
        jToolBar1 = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        saveAsButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        editToolBar = new javax.swing.JToolBar();
        cutButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        pasteButton = new javax.swing.JButton();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        MainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newItem = new javax.swing.JMenuItem();
        openItem = new javax.swing.JMenuItem();
        closeItem = new javax.swing.JMenuItem();
        fileMenuSeperator1 = new javax.swing.JSeparator();
        saveItem = new javax.swing.JMenuItem();
        saveAsItem = new javax.swing.JMenuItem();
        saveAllItem = new javax.swing.JMenuItem();
        fileMenuSerpator2 = new javax.swing.JSeparator();
        recentFilesMenu = new javax.swing.JMenu();
        fileMenuSeperator3 = new javax.swing.JSeparator();
        quitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        editMenuSeparator1 = new javax.swing.JSeparator();
        cutItem = new javax.swing.JMenuItem();
        copyItem = new javax.swing.JMenuItem();
        pasteItem = new javax.swing.JMenuItem();
        editMenuSeperator = new javax.swing.JSeparator();
        preferencesItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("mainframe");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabChange(evt);
            }
        });

        mainToolbar.setFloatable(false);
        mainToolbar.setBorderPainted(false);
        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setOpaque(false);
        newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new24.png")));
        newButton.setBorderPainted(false);
        newButton.setFocusPainted(false);
        newButton.setFocusable(false);
        newButton.setOpaque(false);
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(newButton);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/open24.png")));
        openButton.setBorderPainted(false);
        openButton.setFocusPainted(false);
        openButton.setFocusable(false);
        openButton.setOpaque(false);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(openButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/save24.png")));
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        saveButton.setFocusable(false);
        saveButton.setOpaque(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        saveButton.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                saveButtonAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jToolBar1.add(saveButton);

        saveAsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveas24.png")));
        saveAsButton.setBorderPainted(false);
        saveAsButton.setFocusPainted(false);
        saveAsButton.setFocusable(false);
        saveAsButton.setOpaque(false);
        saveAsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(saveAsButton);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(5, 32));
        jToolBar1.add(jSeparator1);

        mainToolbar.add(jToolBar1);

        editToolBar.setFloatable(false);
        editToolBar.setBorderPainted(false);
        editToolBar.setOpaque(false);
        cutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/cut24.gif")));
        cutButton.setBorderPainted(false);
        cutButton.setFocusPainted(false);
        cutButton.setFocusable(false);
        cutButton.setOpaque(false);
        cutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutButtonActionPerformed(evt);
            }
        });

        editToolBar.add(cutButton);

        copyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/copy24.gif")));
        copyButton.setBorderPainted(false);
        copyButton.setFocusPainted(false);
        copyButton.setFocusable(false);
        copyButton.setOpaque(false);
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        editToolBar.add(copyButton);

        pasteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/paste24.gif")));
        pasteButton.setBorderPainted(false);
        pasteButton.setFocusPainted(false);
        pasteButton.setFocusable(false);
        pasteButton.setOpaque(false);
        pasteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteButtonActionPerformed(evt);
            }
        });

        editToolBar.add(pasteButton);

        undoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/undo24.gif")));
        undoButton.setBorderPainted(false);
        undoButton.setFocusPainted(false);
        undoButton.setFocusable(false);
        undoButton.setOpaque(false);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        editToolBar.add(undoButton);

        redoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/redo24.gif")));
        redoButton.setBorderPainted(false);
        redoButton.setFocusPainted(false);
        redoButton.setFocusable(false);
        redoButton.setOpaque(false);
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
            }
        });

        editToolBar.add(redoButton);

        mainToolbar.add(editToolBar);

        getContentPane().add(mainToolbar, java.awt.BorderLayout.NORTH);

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });
        tabbedPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabbedPaneKeyPressed(evt);
            }
        });

        getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        fileMenu.setText(bundle.getString("MainWindow.File")); // NOI18N
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        newItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new16.gif")));
        newItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.NewMnemonic").charAt(0));
        newItem.setText(bundle.getString("MainWindow.New")); // NOI18N
        newItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newItemActionPerformed(evt);
            }
        });

        fileMenu.add(newItem);

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/open16.png")));
        openItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.OpenMnemonic").charAt(0));
        openItem.setText(bundle.getString("MainWindow.Open")); // NOI18N
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });

        fileMenu.add(openItem);

        closeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        closeItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        closeItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CloseMnemonic").charAt(0));
        closeItem.setText(bundle.getString("MainWindow.Close")); // NOI18N
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeItemActionPerformed(evt);
            }
        });

        fileMenu.add(closeItem);

        fileMenu.add(fileMenuSeperator1);

        saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/save16.png")));
        saveItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveMnemonic").charAt(0));
        saveItem.setText(bundle.getString("MainWindow.Save")); // NOI18N
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveItem);

        saveAsItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveas16.png")));
        saveAsItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveAsMnemonic").charAt(0));
        saveAsItem.setText(bundle.getString("MainWindow.Save_As")); // NOI18N
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveAsItem);

        saveAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAllItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveAll16.gif")));
        saveAllItem.setText(bundle.getString("MainWindow.Save_All")); // NOI18N
        saveAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAllItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveAllItem);

        fileMenu.add(fileMenuSerpator2);

        recentFilesMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        recentFilesMenu.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.RecentlyUsedMnemonic").charAt(0));
        recentFilesMenu.setText(bundle.getString("MainWindow.RecentlyUsed")); // NOI18N
        fileMenu.add(recentFilesMenu);

        fileMenuSeperator3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                fileMenuSeperator3AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        fileMenu.add(fileMenuSeperator3);

        quitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        quitItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        quitItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.QuitMnemonic").charAt(0));
        quitItem.setText(bundle.getString("MainWindow.Quit")); // NOI18N
        quitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitItemActionPerformed(evt);
            }
        });

        fileMenu.add(quitItem);

        MainMenuBar.add(fileMenu);

        editMenu.setText(bundle.getString("MainWindow.Edit")); // NOI18N
        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/undo16.gif")));
        undoItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.UndoMnemonic").charAt(0));
        undoItem.setText(bundle.getString("MainWindow.Undo")); // NOI18N
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });

        editMenu.add(undoItem);

        redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/redo16.gif")));
        redoItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.RedoMnemonic").charAt(0));
        redoItem.setText(bundle.getString("MainWindow.Redo")); // NOI18N
        redoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoItemActionPerformed(evt);
            }
        });

        editMenu.add(redoItem);

        editMenu.add(editMenuSeparator1);

        cutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/cut16.gif")));
        cutItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CutMnemonic").charAt(0));
        cutItem.setText(bundle.getString("MainWindow.Cut")); // NOI18N
        cutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutItemActionPerformed(evt);
            }
        });

        editMenu.add(cutItem);

        copyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/copy16.gif")));
        copyItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CopyMnemonic").charAt(0));
        copyItem.setText(bundle.getString("MainWindow.Copy")); // NOI18N
        copyItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyItemActionPerformed(evt);
            }
        });

        editMenu.add(copyItem);

        pasteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/paste16.gif")));
        pasteItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.PasteMnemonic").charAt(0));
        pasteItem.setText(bundle.getString("MainWindow.Paste")); // NOI18N
        pasteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteItemActionPerformed(evt);
            }
        });

        editMenu.add(pasteItem);

        editMenu.add(editMenuSeperator);

        preferencesItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        preferencesItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.PreferencesMnemonic").charAt(0));
        preferencesItem.setText(bundle.getString("MainWindow.Preferences")); // NOI18N
        preferencesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferencesItemActionPerformed(evt);
            }
        });

        editMenu.add(preferencesItem);

        MainMenuBar.add(editMenu);

        helpMenu.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.HelpMnemonic").charAt(0));
        helpMenu.setText(bundle.getString("MainWindow.Help")); // NOI18N
        aboutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        aboutItem.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.AboutMnemonic").charAt(0));
        aboutItem.setText(bundle.getString("MainWindow.About")); // NOI18N
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });

        helpMenu.add(aboutItem);

        MainMenuBar.add(helpMenu);

        setJMenuBar(MainMenuBar);

        setBounds(0, 0, 706, 561);
    }// </editor-fold>//GEN-END:initComponents

    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_fileMenuActionPerformed

    private void tabChange(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabChange
// NOTICE add your handling code here:
    }//GEN-LAST:event_tabChange

    private void tabbedPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabbedPaneKeyPressed
// NOTICE add your handling code here:
    }//GEN-LAST:event_tabbedPaneKeyPressed

    private void saveButtonAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_saveButtonAncestorAdded
// NOTICE add your handling code here:
    }//GEN-LAST:event_saveButtonAncestorAdded

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
// NOTICE add your handling code here:
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
// NOTICE add your handling code here:
       logic.handleAbout(); 
    }//GEN-LAST:event_aboutItemActionPerformed

    private void preferencesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferencesItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_preferencesItemActionPerformed

    private void pasteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_pasteItemActionPerformed

    private void copyItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_copyItemActionPerformed

    private void cutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_cutItemActionPerformed

    private void redoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_redoItemActionPerformed

    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_undoItemActionPerformed

    private void quitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitItemActionPerformed
// NOTICE add your handling code here:
        logic.handleQuit();
    }//GEN-LAST:event_quitItemActionPerformed

    private void fileMenuSeperator3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_fileMenuSeperator3AncestorAdded
// NOTICE add your handling code here:
    }//GEN-LAST:event_fileMenuSeperator3AncestorAdded

    private void saveAllItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAllItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_saveAllItemActionPerformed

    private void saveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_saveItemActionPerformed

    private void closeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeItemActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_closeItemActionPerformed

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
// NOTICE add your handling code here:
        logic.handleOpen();
    }//GEN-LAST:event_openItemActionPerformed

    private void newItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newItemActionPerformed
// NOTICE add your handling code here:
        logic.handleNew();
    }//GEN-LAST:event_newItemActionPerformed

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_redoButtonActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_undoButtonActionPerformed

    private void pasteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_pasteButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_copyButtonActionPerformed

    private void cutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_cutButtonActionPerformed

    private void saveAsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsButtonActionPerformed
// NOTICE add your handling code here:
    }//GEN-LAST:event_saveAsButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
// NOTICE add your handling code here:
        logic.handleOpen(); 
    }//GEN-LAST:event_openButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
// NOTICE add your handling code here:
         logic.handleNew();
    }//GEN-LAST:event_newButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindowForm().setVisible(true);
            }
        });
    }
    
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    public javax.swing.JMenuItem aboutItem;
    public javax.swing.JMenuItem closeItem;
    public javax.swing.JButton copyButton;
    public javax.swing.JMenuItem copyItem;
    public javax.swing.JButton cutButton;
    public javax.swing.JMenuItem cutItem;
    public javax.swing.JSeparator fileMenuSeperator3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.ButtonGroup modeSettingsGroup;
    public javax.swing.JButton pasteButton;
    public javax.swing.JMenuItem pasteItem;
    public javax.swing.JMenuItem preferencesItem;
    public javax.swing.JMenu recentFilesMenu;
    public javax.swing.JButton redoButton;
    public javax.swing.JMenuItem redoItem;
    public javax.swing.JMenuItem saveAllItem;
    public javax.swing.JButton saveAsButton;
    public javax.swing.JMenuItem saveAsItem;
    public javax.swing.JButton saveButton;
    public javax.swing.JMenuItem saveItem;
    public javax.swing.JTabbedPane tabbedPane;
    public javax.swing.JButton undoButton;
    public javax.swing.JMenuItem undoItem;
    // Ende der Variablendeklaration//GEN-END:variables
    
    private MainWindow logic;
    
    public void setMainWindow (MainWindow window){
        logic = window;
    }
    
    public void handleNew(){
      logic.handleNew();
    }
}
