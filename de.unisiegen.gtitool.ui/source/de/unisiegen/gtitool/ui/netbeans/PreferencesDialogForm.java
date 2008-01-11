package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.PreferencesDialog;


/**
 * The <code>PreferencesDialogForm</code>.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class PreferencesDialogForm extends javax.swing.JDialog {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -8194161182214638312L;
    
    /**
     * The {@link PreferencesDialog}.
     */
    private PreferencesDialog logic ;
    
    /**
     * Creates new form PreferenceDialog
     * 
     * @param logic The {@link PreferencesDialog}.
     * @param parent The parent {@link Frame}.
     */
    public PreferencesDialogForm(PreferencesDialog logic, java.awt.Frame parent) {
        super(parent, true);
        this.logic = logic;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelGeneral = new javax.swing.JPanel();
        jLabelLanguage = new javax.swing.JLabel();
        jComboBoxLanguage = new javax.swing.JComboBox();
        jLabelLookAndFeel = new javax.swing.JLabel();
        jComboBoxLookAndFeel = new javax.swing.JComboBox();
        jLabelZoom = new javax.swing.JLabel();
        jSliderZoom = new javax.swing.JSlider();
        jLabelMouseSelection = new javax.swing.JLabel();
        jComboBoxMouseSelection = new javax.swing.JComboBox();
        jPanelGeneralSpace = new javax.swing.JPanel();
        jButtonRestore = new javax.swing.JButton();
        jPanelColors = new javax.swing.JPanel();
        jScrollPaneColor = new javax.swing.JScrollPane();
        jListColor = new javax.swing.JList();
        jTextPaneDescription = new javax.swing.JTextPane();
        jPanelAlphabet = new javax.swing.JPanel();
        jLabelInputAlphabet = new javax.swing.JLabel();
        styledAlphabetParserPanelInput = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jCheckBoxPushDownAlphabet = new javax.swing.JCheckBox();
        styledAlphabetParserPanelPushDown = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonAccept = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        setTitle(bundle.getString("PreferencesDialog.Title")); // NOI18N
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane.setFocusable(false);
        jPanelGeneral.setLayout(new java.awt.GridBagLayout());

        jLabelLanguage.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.LanguageMnemonic").charAt(0));
        jLabelLanguage.setLabelFor(jComboBoxLanguage);
        jLabelLanguage.setText(bundle.getString("PreferencesDialog.Language")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 5);
        jPanelGeneral.add(jLabelLanguage, gridBagConstraints);

        jComboBoxLanguage.setToolTipText(bundle.getString("PreferencesDialog.LanguageToolTip")); // NOI18N
        jComboBoxLanguage.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 5, 5, 16);
        jPanelGeneral.add(jComboBoxLanguage, gridBagConstraints);

        jLabelLookAndFeel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.LookAndFeelMnemonic").charAt(0));
        jLabelLookAndFeel.setLabelFor(jComboBoxLookAndFeel);
        jLabelLookAndFeel.setText(bundle.getString("PreferencesDialog.LookAndFeel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jPanelGeneral.add(jLabelLookAndFeel, gridBagConstraints);

        jComboBoxLookAndFeel.setToolTipText(bundle.getString("PreferencesDialog.LookAndFeelToolTip")); // NOI18N
        jComboBoxLookAndFeel.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jPanelGeneral.add(jComboBoxLookAndFeel, gridBagConstraints);

        jLabelZoom.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.ZoomMnemonic").charAt(0));
        jLabelZoom.setLabelFor(jSliderZoom);
        jLabelZoom.setText(bundle.getString("PreferencesDialog.Zoom")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jPanelGeneral.add(jLabelZoom, gridBagConstraints);

        jSliderZoom.setMajorTickSpacing(50);
        jSliderZoom.setMaximum(200);
        jSliderZoom.setMinimum(50);
        jSliderZoom.setMinorTickSpacing(25);
        jSliderZoom.setPaintLabels(true);
        jSliderZoom.setPaintTicks(true);
        jSliderZoom.setSnapToTicks(true);
        jSliderZoom.setToolTipText(bundle.getString("PreferencesDialog.ZoomToolTip")); // NOI18N
        jSliderZoom.setValue(100);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jPanelGeneral.add(jSliderZoom, gridBagConstraints);

        jLabelMouseSelection.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.MouseSelectionMnemonic").charAt(0));
        jLabelMouseSelection.setLabelFor(jComboBoxMouseSelection);
        jLabelMouseSelection.setText(bundle.getString("PreferencesDialog.MouseSelection")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jPanelGeneral.add(jLabelMouseSelection, gridBagConstraints);

        jComboBoxMouseSelection.setToolTipText(bundle.getString("PreferencesDialog.MouseSelectionToolTip")); // NOI18N
        jComboBoxMouseSelection.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jPanelGeneral.add(jComboBoxMouseSelection, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelGeneral.add(jPanelGeneralSpace, gridBagConstraints);

        jButtonRestore.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.RestoreMnemonic").charAt(0));
        jButtonRestore.setText(bundle.getString("PreferencesDialog.Restore")); // NOI18N
        jButtonRestore.setToolTipText(bundle.getString("PreferencesDialog.RestoreToolTip")); // NOI18N
        jButtonRestore.setFocusPainted(false);
        jButtonRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRestoreActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        jPanelGeneral.add(jButtonRestore, gridBagConstraints);

        jTabbedPane.addTab(bundle.getString("PreferencesDialog.TabGeneral"), null, jPanelGeneral, bundle.getString("PreferencesDialog.TabGeneralToolTip")); // NOI18N

        jPanelColors.setLayout(new java.awt.GridBagLayout());

        jScrollPaneColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jListColor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListColor.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jListColorMouseMoved(evt);
            }
        });
        jListColor.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListColorValueChanged(evt);
            }
        });
        jListColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jListColorMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jListColorMouseReleased(evt);
            }
        });

        jScrollPaneColor.setViewportView(jListColor);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        jPanelColors.add(jScrollPaneColor, gridBagConstraints);

        jTextPaneDescription.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextPaneDescription.setEditable(false);
        jTextPaneDescription.setFocusable(false);
        jTextPaneDescription.setMaximumSize(new java.awt.Dimension(6, 46));
        jTextPaneDescription.setMinimumSize(new java.awt.Dimension(6, 46));
        jTextPaneDescription.setPreferredSize(new java.awt.Dimension(6, 46));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        jPanelColors.add(jTextPaneDescription, gridBagConstraints);

        jTabbedPane.addTab(bundle.getString("PreferencesDialog.TabColors"), null, jPanelColors, bundle.getString("PreferencesDialog.TabColorsToolTip")); // NOI18N

        jPanelAlphabet.setLayout(new java.awt.GridBagLayout());

        jLabelInputAlphabet.setLabelFor(styledAlphabetParserPanelInput);
        jLabelInputAlphabet.setText(bundle.getString("PreferencesDialog.InputAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        jPanelAlphabet.add(jLabelInputAlphabet, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 16);
        jPanelAlphabet.add(styledAlphabetParserPanelInput, gridBagConstraints);

        jCheckBoxPushDownAlphabet.setSelected(true);
        jCheckBoxPushDownAlphabet.setText(bundle.getString("PreferencesDialog.PushDownAlphabet")); // NOI18N
        jCheckBoxPushDownAlphabet.setToolTipText(bundle.getString("PreferencesDialog.PushDownAlphabetToolTip")); // NOI18N
        jCheckBoxPushDownAlphabet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jCheckBoxPushDownAlphabet.setFocusPainted(false);
        jCheckBoxPushDownAlphabet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxPushDownAlphabetItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jPanelAlphabet.add(jCheckBoxPushDownAlphabet, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 16);
        jPanelAlphabet.add(styledAlphabetParserPanelPushDown, gridBagConstraints);

        jTabbedPane.addTab(bundle.getString("PreferencesDialog.TabAlphabet"), null, jPanelAlphabet, bundle.getString("PreferencesDialog.TabAlphabetToolTip")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane, gridBagConstraints);

        jButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.OkMnemonic").charAt(0));
        jButtonOk.setText(bundle.getString("PreferencesDialog.Ok")); // NOI18N
        jButtonOk.setToolTipText(bundle.getString("PreferencesDialog.OkToolTip")); // NOI18N
        jButtonOk.setFocusPainted(false);
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 16, 5);
        getContentPane().add(jButtonOk, gridBagConstraints);

        jButtonAccept.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.AcceptMnemonic").charAt(0));
        jButtonAccept.setText(bundle.getString("PreferencesDialog.Accept")); // NOI18N
        jButtonAccept.setToolTipText(bundle.getString("PreferencesDialog.AcceptToolTip")); // NOI18N
        jButtonAccept.setFocusPainted(false);
        jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAcceptActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 16, 5);
        getContentPane().add(jButtonAccept, gridBagConstraints);

        jButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("PreferencesDialog.CancelMnemonic").charAt(0));
        jButtonCancel.setText(bundle.getString("PreferencesDialog.Cancel")); // NOI18N
        jButtonCancel.setToolTipText(bundle.getString("PreferencesDialog.CancelToolTip")); // NOI18N
        jButtonCancel.setFocusPainted(false);
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 16, 16);
        getContentPane().add(jButtonCancel, gridBagConstraints);

        setBounds(0, 0, 401, 333);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxPushDownAlphabetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxPushDownAlphabetItemStateChanged
       this.logic.handlePushDownAlphabetItemStateChanged(evt);
    }//GEN-LAST:event_jCheckBoxPushDownAlphabetItemStateChanged

    private void jListColorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListColorMouseExited
       this.logic.handleColorListMouseExited(evt);
    }//GEN-LAST:event_jListColorMouseExited

    private void jListColorValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListColorValueChanged
       this.logic.handleColorListValueChanged(evt);
    }//GEN-LAST:event_jListColorValueChanged

    private void jListColorMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListColorMouseMoved
        this.logic.handleColorListMouseMoved(evt);
    }//GEN-LAST:event_jListColorMouseMoved

    private void jListColorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListColorMouseReleased
        this.logic.handleColorListMouseReleased(evt);
    }//GEN-LAST:event_jListColorMouseReleased

    private void jButtonRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRestoreActionPerformed
        this.logic.handleRestore();
    }//GEN-LAST:event_jButtonRestoreActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.logic.handleCancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.logic.handleOk();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    private void jButtonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAcceptActionPerformed
        this.logic.handleAccept();
    }//GEN-LAST:event_jButtonAcceptActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonAccept;
    public javax.swing.JButton jButtonCancel;
    public javax.swing.JButton jButtonOk;
    public javax.swing.JButton jButtonRestore;
    public javax.swing.JCheckBox jCheckBoxPushDownAlphabet;
    public javax.swing.JComboBox jComboBoxLanguage;
    public javax.swing.JComboBox jComboBoxLookAndFeel;
    public javax.swing.JComboBox jComboBoxMouseSelection;
    public javax.swing.JLabel jLabelInputAlphabet;
    public javax.swing.JLabel jLabelLanguage;
    public javax.swing.JLabel jLabelLookAndFeel;
    public javax.swing.JLabel jLabelMouseSelection;
    public javax.swing.JLabel jLabelZoom;
    public javax.swing.JList jListColor;
    public javax.swing.JPanel jPanelAlphabet;
    public javax.swing.JPanel jPanelColors;
    public javax.swing.JPanel jPanelGeneral;
    public javax.swing.JPanel jPanelGeneralSpace;
    public javax.swing.JScrollPane jScrollPaneColor;
    public javax.swing.JSlider jSliderZoom;
    public javax.swing.JTabbedPane jTabbedPane;
    public javax.swing.JTextPane jTextPaneDescription;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelInput;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelPushDown;
    // End of variables declaration//GEN-END:variables
    
}
