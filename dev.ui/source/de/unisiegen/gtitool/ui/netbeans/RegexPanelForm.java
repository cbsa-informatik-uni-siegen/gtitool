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

        jGTISplitPaneConsole = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIPanelRegex = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTISplitPaneTable = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIPanelRegexControl = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTITextFieldRegex = new de.unisiegen.gtitool.ui.swing.JGTITextField();
        jGTIButtonChangeRegex = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCoreSyntax = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonToLatex = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIScrollPaneGraph = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIPanelConsole = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTITabbedPaneConsole = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jGTIScrollPaneWarnings = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableWarnings = new de.unisiegen.gtitool.ui.swing.JGTITable();

        setLayout(new java.awt.GridBagLayout());

        jGTISplitPaneConsole.setDividerLocation(800);
        jGTISplitPaneConsole.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneConsole.setResizeWeight(1.0);

        jGTISplitPaneTable.setDividerLocation(250);
        jGTISplitPaneTable.setResizeWeight(0.5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelRegexControl.add(jGTITextFieldRegex, gridBagConstraints);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTIButtonChangeRegex.setText(bundle.getString("RegexPanel.ChangeRegex")); // NOI18N
        jGTIButtonChangeRegex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonChangeRegexActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelRegexControl.add(jGTIButtonChangeRegex, gridBagConstraints);

        jGTIButtonCoreSyntax.setText(bundle.getString("RegexPanel.ToCoreSyntax")); // NOI18N
        jGTIButtonCoreSyntax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCoreSyntaxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelRegexControl.add(jGTIButtonCoreSyntax, gridBagConstraints);

        jGTIButtonToLatex.setText("To Latex");
        jGTIButtonToLatex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonToLatexActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelRegexControl.add(jGTIButtonToLatex, gridBagConstraints);

        jGTISplitPaneTable.setLeftComponent(jGTIPanelRegexControl);
        jGTISplitPaneTable.setRightComponent(jGTIScrollPaneGraph);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelRegex.add(jGTISplitPaneTable, gridBagConstraints);

        jGTISplitPaneConsole.setLeftComponent(jGTIPanelRegex);

        jGTITabbedPaneConsole.setMinimumSize(new java.awt.Dimension(200, 200));

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
        gridBagConstraints.weighty = 0.1;
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

    private void jGTITableWarningsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITableWarningsMouseExited
      
    }//GEN-LAST:event_jGTITableWarningsMouseExited

    private void jGTITableWarningsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jGTITableWarningsFocusLost
      
    }//GEN-LAST:event_jGTITableWarningsFocusLost

private void jGTIButtonChangeRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonChangeRegexActionPerformed
      this.logic.handleRegexChangeButtonClicked(evt);
}//GEN-LAST:event_jGTIButtonChangeRegexActionPerformed

private void jGTIButtonCoreSyntaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCoreSyntaxActionPerformed
      this.logic.handleToCoreSyntaxButtonClicked(evt);
}//GEN-LAST:event_jGTIButtonCoreSyntaxActionPerformed

private void jGTIButtonToLatexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonToLatexActionPerformed
    this.logic.handleToLatexButtonClicked(evt);
}//GEN-LAST:event_jGTIButtonToLatexActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonChangeRegex;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCoreSyntax;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonToLatex;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelConsole;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelRegex;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelRegexControl;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneWarnings;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneConsole;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneTable;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPaneConsole;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableWarnings;
    public de.unisiegen.gtitool.ui.swing.JGTITextField jGTITextFieldRegex;
    // End of variables declaration//GEN-END:variables
    
}