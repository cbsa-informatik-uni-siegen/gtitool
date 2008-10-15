package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JPanel;

import de.unisiegen.gtitool.ui.logic.RegexPanel;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The {@link RegexPanelForm}.
 *
 * @author Simon Meurer
 * @version
 */
@SuppressWarnings({ "all" })
public class RegexPanelForm extends JPanel implements GUIClass <RegexPanel>
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -6408224101983628704L;

    /**
     * The {@link RegexPanel}.
     */
    private RegexPanel logic;
    
    /**
     * Allocates a new {@link RegexPanelForm}.
     * 
     * @param logic The {@link RegexPanel}.
     */
    public RegexPanelForm(RegexPanel logic) {
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final RegexPanel getLogic ()
    {
      return this.logic;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        toolbarButton = new javax.swing.ButtonGroup();
        jGTISplitPaneWord = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTISplitPaneConsole = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIPanelRegex = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTISplitPaneTable = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIPanelGraph = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIScrollPaneGraph = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIPanelTable = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTISplitPanePDATable = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIScrollPaneMachine = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableMachine = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTIScrollPaneMachinePDA = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableMachinePDA = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTIPanelRegexControl = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonChangeRegex = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTITextFieldRegex = new de.unisiegen.gtitool.ui.swing.JGTITextField();
        jGTIPanelConsole = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTITabbedPaneConsole = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jGTIScrollPaneErrors = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableErrors = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTIScrollPaneWarnings = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableWarnings = new de.unisiegen.gtitool.ui.swing.JGTITable();
        wordPanelForm = new de.unisiegen.gtitool.ui.netbeans.WordPanelForm();

        setLayout(new java.awt.GridBagLayout());

        jGTISplitPaneWord.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneWord.setResizeWeight(1.0);

        jGTISplitPaneConsole.setDividerLocation(200);
        jGTISplitPaneConsole.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneConsole.setResizeWeight(1.0);

        jGTISplitPaneTable.setDividerLocation(400);
        jGTISplitPaneTable.setResizeWeight(1.0);

        jGTIScrollPaneGraph.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelGraph.add(jGTIScrollPaneGraph, gridBagConstraints);

        jGTISplitPaneTable.setLeftComponent(jGTIPanelGraph);

        jGTIPanelTable.setMinimumSize(new java.awt.Dimension(200, 200));

        jGTISplitPanePDATable.setDividerLocation(100);
        jGTISplitPanePDATable.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jGTIScrollPaneMachine.setBorder(null);
        jGTIScrollPaneMachine.setMinimumSize(new java.awt.Dimension(150, 150));

        jGTITableMachine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jGTITableMachineMouseExited(evt);
            }
        });
        jGTITableMachine.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jGTITableMachineFocusLost(evt);
            }
        });
        jGTIScrollPaneMachine.setViewportView(jGTITableMachine);

        jGTISplitPanePDATable.setLeftComponent(jGTIScrollPaneMachine);

        jGTIScrollPaneMachinePDA.setBorder(null);
        jGTIScrollPaneMachinePDA.setMinimumSize(new java.awt.Dimension(150, 150));

        jGTITableMachinePDA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jGTITableMachinePDAMouseExited(evt);
            }
        });
        jGTITableMachinePDA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jGTITableMachinePDAFocusLost(evt);
            }
        });
        jGTIScrollPaneMachinePDA.setViewportView(jGTITableMachinePDA);

        jGTISplitPanePDATable.setRightComponent(jGTIScrollPaneMachinePDA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelTable.add(jGTISplitPanePDATable, gridBagConstraints);

        jGTISplitPaneTable.setRightComponent(jGTIPanelTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelRegex.add(jGTISplitPaneTable, gridBagConstraints);

        jGTIButtonChangeRegex.setText("Change Regex");
        jGTIButtonChangeRegex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonChangeRegexActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jGTIPanelRegexControl.add(jGTIButtonChangeRegex, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jGTIPanelRegexControl.add(jGTITextFieldRegex, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelRegex.add(jGTIPanelRegexControl, gridBagConstraints);

        jGTISplitPaneConsole.setLeftComponent(jGTIPanelRegex);

        jGTITabbedPaneConsole.setMinimumSize(new java.awt.Dimension(200, 200));

        jGTIScrollPaneErrors.setBorder(null);

        jGTITableErrors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jGTITableErrorsMouseExited(evt);
            }
        });
        jGTITableErrors.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jGTITableErrorsFocusLost(evt);
            }
        });
        jGTIScrollPaneErrors.setViewportView(jGTITableErrors);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTITabbedPaneConsole.addTab(bundle.getString("MachinePanel.Error"), new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/error.png")), jGTIScrollPaneErrors); // NOI18N

        jGTIScrollPaneWarnings.setBorder(null);

        jGTITableWarnings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jGTITableWarningsMouseExited(evt);
            }
        });
        jGTITableWarnings.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jGTITableWarningsFocusLost(evt);
            }
        });
        jGTIScrollPaneWarnings.setViewportView(jGTITableWarnings);

        jGTITabbedPaneConsole.addTab(bundle.getString("MachinePanel.Warning"), new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/warning.png")), jGTIScrollPaneWarnings); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelConsole.add(jGTITabbedPaneConsole, gridBagConstraints);

        jGTISplitPaneConsole.setRightComponent(jGTIPanelConsole);

        jGTISplitPaneWord.setLeftComponent(jGTISplitPaneConsole);
        jGTISplitPaneWord.setBottomComponent(wordPanelForm);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jGTISplitPaneWord, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTITableMachinePDAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableMachinePDAMouseExited
      
    }//GEN-LAST:event_jGTITableMachinePDAMouseExited

    private void jGTITableMachinePDAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableMachinePDAFocusLost
      
    }//GEN-LAST:event_jGTITableMachinePDAFocusLost

    private void jGTITableMachineMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableMachineMouseExited
      
    }//GEN-LAST:event_jGTITableMachineMouseExited

    private void jGTITableMachineFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableMachineFocusLost
      
    }//GEN-LAST:event_jGTITableMachineFocusLost

    private void jGTITableErrorsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableErrorsMouseExited
      
    }//GEN-LAST:event_jGTITableErrorsMouseExited

    private void jGTITableWarningsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableWarningsMouseExited
      
    }//GEN-LAST:event_jGTITableWarningsMouseExited

    private void jGTITableWarningsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableWarningsFocusLost
      
    }//GEN-LAST:event_jGTITableWarningsFocusLost

    private void jGTITableErrorsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableErrorsFocusLost
      
    }//GEN-LAST:event_jGTITableErrorsFocusLost

private void jGTIButtonChangeRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonChangeRegexActionPerformed
      this.logic.handleRegexChangeButtonClicked(evt);
}//GEN-LAST:event_jGTIButtonChangeRegexActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonChangeRegex;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelConsole;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelRegex;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelRegexControl;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelTable;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneErrors;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneMachine;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneMachinePDA;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneWarnings;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneConsole;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPanePDATable;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneTable;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneWord;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPaneConsole;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableErrors;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableMachine;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableMachinePDA;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableWarnings;
    public de.unisiegen.gtitool.ui.swing.JGTITextField jGTITextFieldRegex;
    public javax.swing.ButtonGroup toolbarButton;
    public de.unisiegen.gtitool.ui.netbeans.WordPanelForm wordPanelForm;
    // End of variables declaration//GEN-END:variables
    
}