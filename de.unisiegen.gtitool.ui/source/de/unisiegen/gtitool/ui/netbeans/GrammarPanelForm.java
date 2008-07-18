package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JPanel;

import de.unisiegen.gtitool.ui.logic.GrammarPanel;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The {@link GrammarPanelForm}
 *
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class GrammarPanelForm extends JPanel implements GUIClass <GrammarPanel>
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -505151111103641215L;
    
    /**
     * The {@link GrammarPanel}.
     */
    private GrammarPanel logic;
    
    /**
     * Allocates a new {@link GrammarPanelForm}.
     */
    public GrammarPanelForm(GrammarPanel logic) {
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final GrammarPanel getLogic ()
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

        styledTerminalSymbolSetParserPanel = new de.unisiegen.gtitool.ui.style.StyledTerminalSymbolSetParserPanel();
        jGTILabelTerminalSymbols = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledStartNonterminalSymbolParserPanel = new de.unisiegen.gtitool.ui.style.StyledStartNonterminalSymbolParserPanel();
        jGTILabelStartSymbol = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledNonterminalSymbolSetParserPanel = new de.unisiegen.gtitool.ui.style.StyledNonterminalSymbolSetParserPanel();
        jGTILabelNonterminalSymbols = new de.unisiegen.gtitool.ui.swing.JGTILabel();
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

        styledTerminalSymbolSetParserPanel.setCopyable(true);
        styledTerminalSymbolSetParserPanel.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 16, 16);
        add(styledTerminalSymbolSetParserPanel, gridBagConstraints);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTILabelTerminalSymbols.setText(bundle.getString("TerminalPanel.TerminalSymbols")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTILabelTerminalSymbols, gridBagConstraints);

        styledStartNonterminalSymbolParserPanel.setCopyable(true);
        styledStartNonterminalSymbolParserPanel.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        add(styledStartNonterminalSymbolParserPanel, gridBagConstraints);

        jGTILabelStartSymbol.setText(bundle.getString("TerminalPanel.StartSymbol")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTILabelStartSymbol, gridBagConstraints);

        styledNonterminalSymbolSetParserPanel.setCopyable(true);
        styledNonterminalSymbolSetParserPanel.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        add(styledNonterminalSymbolSetParserPanel, gridBagConstraints);

        jGTILabelNonterminalSymbols.setText(bundle.getString("TerminalPanel.NonterminalSymbols")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        add(jGTILabelNonterminalSymbols, gridBagConstraints);

        jGTISplitPaneConsole.setDividerLocation(200);
        jGTISplitPaneConsole.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneConsole.setResizeWeight(1.0);

        jGTIScrollPaneGrammar.setBorder(null);

        jGTITableGrammar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickedEvent(evt);
            }
        });
        jGTITableGrammar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jGTITableGrammarKeyReleased(evt);
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
        gridBagConstraints.gridy = 101;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelConsole.add(jGTITabbedPaneConsole, gridBagConstraints);

        jGTISplitPaneConsole.setRightComponent(jGTIPanelConsole);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jGTISplitPaneConsole, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTITableGrammarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jGTITableGrammarKeyReleased
        this.logic.handleGrammarTableKeyReleased(evt);
    }//GEN-LAST:event_jGTITableGrammarKeyReleased

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
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelNonterminalSymbols;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelStartSymbol;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelTerminalSymbols;
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
    public de.unisiegen.gtitool.ui.style.StyledNonterminalSymbolSetParserPanel styledNonterminalSymbolSetParserPanel;
    public de.unisiegen.gtitool.ui.style.StyledStartNonterminalSymbolParserPanel styledStartNonterminalSymbolParserPanel;
    public de.unisiegen.gtitool.ui.style.StyledTerminalSymbolSetParserPanel styledTerminalSymbolSetParserPanel;
    // End of variables declaration//GEN-END:variables

}
