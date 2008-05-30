package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.TransitionDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The transition dialog.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class TransitionDialogForm extends JDialog implements GUIClass <TransitionDialog>
{
    
   /**
    * The serial version uid.
    */
    private static final long serialVersionUID = 8316002721568381761L;
    
    /**
     * The {@link TransitionDialog}.
     */
    private TransitionDialog logic;
    
    /**
     * Allocates a new {@link TransitionDialogForm}.
     * 
     * @param logic The {@link TransitionDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public TransitionDialogForm(TransitionDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final TransitionDialog getLogic ()
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
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabelNonterminalSymbol = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelTransitionSet = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelAlphabet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelChangeOverSet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelMoveButtons = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIScrollPaneAlphabet = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIListAlphabet = new de.unisiegen.gtitool.ui.swing.JGTIList();
        jGTIButtonMoveLeft = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonMoveRight = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIScrollPaneChangeOverSet = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIListChangeOverSet = new de.unisiegen.gtitool.ui.swing.JGTIList();
        jGTIPanelPushDown = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelPushDownRead = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelPushDownWrite = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledWordParserPanelRead = new de.unisiegen.gtitool.ui.style.StyledWordParserPanel();
        styledWordParserPanelWrite = new de.unisiegen.gtitool.ui.style.StyledWordParserPanel();
        jGTILabelPushDownAlphabet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledAlphabetParserPanelPushDownAlphabet = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jGTILabelTransitionSet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledTransitionParserPanel = new de.unisiegen.gtitool.ui.style.StyledTransitionParserPanel();
        jGTIPanelButtons = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("TransitionDialog.Title")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jGTILabelNonterminalSymbol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelNonterminalSymbol.setText(bundle.getString("TransitionDialog.Header")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTILabelNonterminalSymbol, gridBagConstraints);

        jGTILabelAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelAlphabet.setText(bundle.getString("TransitionDialog.Alphabet")); // NOI18N
        jGTILabelAlphabet.setMinimumSize(new java.awt.Dimension(150, 15));
        jGTILabelAlphabet.setPreferredSize(new java.awt.Dimension(150, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        jGTIPanelTransitionSet.add(jGTILabelAlphabet, gridBagConstraints);

        jGTILabelChangeOverSet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelChangeOverSet.setText(bundle.getString("TransitionDialog.TransitionSet")); // NOI18N
        jGTILabelChangeOverSet.setMinimumSize(new java.awt.Dimension(150, 15));
        jGTILabelChangeOverSet.setPreferredSize(new java.awt.Dimension(150, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        jGTIPanelTransitionSet.add(jGTILabelChangeOverSet, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIPanelTransitionSet, gridBagConstraints);

        jGTIListAlphabet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jGTIListAlphabet.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jGTIListAlphabetValueChanged(evt);
            }
        });

        jGTIScrollPaneAlphabet.setViewportView(jGTIListAlphabet);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelMoveButtons.add(jGTIScrollPaneAlphabet, gridBagConstraints);

        jGTIButtonMoveLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/moveLeft.png")));
        jGTIButtonMoveLeft.setEnabled(false);
        jGTIButtonMoveLeft.setMaximumHeight(32);
        jGTIButtonMoveLeft.setMaximumWidth(32);
        jGTIButtonMoveLeft.setMinimumHeight(32);
        jGTIButtonMoveLeft.setMinimumWidth(32);
        jGTIButtonMoveLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonMoveLeftActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelMoveButtons.add(jGTIButtonMoveLeft, gridBagConstraints);

        jGTIButtonMoveRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/moveRight.png")));
        jGTIButtonMoveRight.setEnabled(false);
        jGTIButtonMoveRight.setMaximumHeight(32);
        jGTIButtonMoveRight.setMaximumWidth(32);
        jGTIButtonMoveRight.setMinimumHeight(32);
        jGTIButtonMoveRight.setMinimumWidth(32);
        jGTIButtonMoveRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonMoveRightActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelMoveButtons.add(jGTIButtonMoveRight, gridBagConstraints);

        jGTIListChangeOverSet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jGTIListChangeOverSet.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jGTIListChangeOverSetValueChanged(evt);
            }
        });

        jGTIScrollPaneChangeOverSet.setViewportView(jGTIListChangeOverSet);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelMoveButtons.add(jGTIScrollPaneChangeOverSet, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIPanelMoveButtons, gridBagConstraints);

        jGTILabelPushDownRead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelPushDownRead.setText(bundle.getString("TransitionDialog.PushDownWordRead")); // NOI18N
        jGTILabelPushDownRead.setMinimumSize(new java.awt.Dimension(150, 15));
        jGTILabelPushDownRead.setPreferredSize(new java.awt.Dimension(150, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        jGTIPanelPushDown.add(jGTILabelPushDownRead, gridBagConstraints);

        jGTILabelPushDownWrite.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelPushDownWrite.setText(bundle.getString("TransitionDialog.PushDownWordWrite")); // NOI18N
        jGTILabelPushDownWrite.setMinimumSize(new java.awt.Dimension(150, 15));
        jGTILabelPushDownWrite.setPreferredSize(new java.awt.Dimension(150, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        jGTIPanelPushDown.add(jGTILabelPushDownWrite, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIPanelPushDown, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        getContentPane().add(styledWordParserPanelRead, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        getContentPane().add(styledWordParserPanelWrite, gridBagConstraints);

        jGTILabelPushDownAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelPushDownAlphabet.setText(bundle.getString("TransitionDialog.PushDownAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTILabelPushDownAlphabet, gridBagConstraints);

        styledAlphabetParserPanelPushDownAlphabet.setCopyable(true);
        styledAlphabetParserPanelPushDownAlphabet.setEditable(false);
        styledAlphabetParserPanelPushDownAlphabet.setSideBarVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(styledAlphabetParserPanelPushDownAlphabet, gridBagConstraints);

        jGTILabelTransitionSet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelTransitionSet.setText(bundle.getString("TransitionDialog.ResultingTransitionSet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTILabelTransitionSet, gridBagConstraints);

        styledTransitionParserPanel.setEditable(false);
        styledTransitionParserPanel.setSideBarVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(styledTransitionParserPanel, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("TransitionDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("TransitionDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("TransitionDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelButtons.add(jGTIButtonOk, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("TransitionDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("TransitionDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("TransitionDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelButtons.add(jGTIButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(jGTIPanelButtons, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-500)/2, 400, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonMoveRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonMoveRightActionPerformed
        this.logic.handleMoveRight();
    }//GEN-LAST:event_jGTIButtonMoveRightActionPerformed

    private void jGTIButtonMoveLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonMoveLeftActionPerformed
        this.logic.handleMoveLeft();
    }//GEN-LAST:event_jGTIButtonMoveLeftActionPerformed

    private void jGTIListChangeOverSetValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jGTIListChangeOverSetValueChanged
        this.logic.handleListSelection(evt);
    }//GEN-LAST:event_jGTIListChangeOverSetValueChanged

    private void jGTIListAlphabetValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jGTIListAlphabetValueChanged
        this.logic.handleListSelection(evt);
    }//GEN-LAST:event_jGTIListAlphabetValueChanged

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
        this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
        this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonMoveLeft;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonMoveRight;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelChangeOverSet;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelNonterminalSymbol;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPushDownAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPushDownRead;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPushDownWrite;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelTransitionSet;
    public de.unisiegen.gtitool.ui.swing.JGTIList jGTIListAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTIList jGTIListChangeOverSet;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelButtons;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMoveButtons;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelPushDown;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelTransitionSet;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneChangeOverSet;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelPushDownAlphabet;
    public de.unisiegen.gtitool.ui.style.StyledTransitionParserPanel styledTransitionParserPanel;
    public de.unisiegen.gtitool.ui.style.StyledWordParserPanel styledWordParserPanelRead;
    public de.unisiegen.gtitool.ui.style.StyledWordParserPanel styledWordParserPanelWrite;
    // End of variables declaration//GEN-END:variables
 
}
