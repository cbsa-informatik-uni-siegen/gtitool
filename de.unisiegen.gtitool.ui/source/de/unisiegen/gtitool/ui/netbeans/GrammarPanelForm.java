package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.logic.GrammarPanel;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;

/**
 *
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class GrammarPanelForm extends javax.swing.JPanel implements EditorPanelForm{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -505151111103641215L;
    
    
    /** Creates new form GrammarPanelForm */
    public GrammarPanelForm() {
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

        jGTISplitPaneConsole = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIPanelGrammar = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIScrollPaneGrammar = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableGrammar = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTIPanelConsole = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTITabbedPaneConsole = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jGTIScrollPaneErrors = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableErrors = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTIScrollPaneWarnings = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableWarnings = new de.unisiegen.gtitool.ui.swing.JGTITable();

        setLayout(new java.awt.GridBagLayout());

        jGTISplitPaneConsole.setDividerLocation(200);
        jGTISplitPaneConsole.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneConsole.setResizeWeight(1.0);
        jGTIScrollPaneGrammar.setBorder(null);
        jGTITableGrammar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickedEvent(evt);
            }
        });

        jGTIScrollPaneGrammar.setViewportView(jGTITableGrammar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelGrammar.add(jGTIScrollPaneGrammar, gridBagConstraints);

        jGTISplitPaneConsole.setLeftComponent(jGTIPanelGrammar);

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

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jGTITabbedPaneConsole.addTab(bundle.getString("MachinePanel.Error"), new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/error.gif")), jGTIScrollPaneErrors); // NOI18N

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

        jGTITabbedPaneConsole.addTab(bundle.getString("MachinePanel.Warning"), new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/warning.gif")), jGTIScrollPaneWarnings); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelConsole.add(jGTITabbedPaneConsole, gridBagConstraints);

        jGTISplitPaneConsole.setRightComponent(jGTIPanelConsole);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jGTISplitPaneConsole, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jGTITableWarningsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableWarningsFocusLost
        this.logic.handleConsoleTableFocusLost(evt);
    }//GEN-LAST:event_jGTITableWarningsFocusLost

    private void jGTITableWarningsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableWarningsMouseExited
        this.logic.handleConsoleTableMouseExited( evt );
    }//GEN-LAST:event_jGTITableWarningsMouseExited

    private void jGTITableErrorsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableErrorsFocusLost
        this.logic.handleConsoleTableFocusLost(evt);
    }//GEN-LAST:event_jGTITableErrorsFocusLost

    private void jGTITableErrorsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableErrorsMouseExited
        this.logic.handleConsoleTableMouseExited( evt );
    }//GEN-LAST:event_jGTITableErrorsMouseExited

    private void mouseClickedEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClickedEvent
        this.logic.handleTableMouseClickedEvent(evt);
    }//GEN-LAST:event_mouseClickedEvent

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelConsole;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGrammar;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneErrors;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneGrammar;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneWarnings;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneConsole;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPaneConsole;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableErrors;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableGrammar;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableWarnings;
    // End of variables declaration//GEN-END:variables
    

    private GrammarPanel logic;
    
    public void setGrammarPanel (GrammarPanel window){
        logic = window;
    }
    
    public GrammarPanel getLogic(){
      return this.logic;
    }
}
