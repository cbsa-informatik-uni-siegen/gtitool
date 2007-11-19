/*
 * MachinesPanelForm.java
 *
 * Created on 21. Oktober 2007, 16:47
 */

package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.MachinePanel;

/**
 * The Forms file for the {@link MachinePanel}
 *
 * @author Benjamin Mies
 * @version $Id$
 */
public class MachinesPanelForm extends javax.swing.JPanel {
    
    /** Creates new form MachinesPanelForm */
    public MachinesPanelForm() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Erzeugter Quelltext ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        toolbarButton = new javax.swing.ButtonGroup();
        toolbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonMouse = new javax.swing.JToggleButton();
        jButtonAddState = new javax.swing.JToggleButton();
        jButtonAddTransition = new javax.swing.JToggleButton();
        jButtonStartState = new javax.swing.JToggleButton();
        jButtonFinalState = new javax.swing.JToggleButton();
        jButtonEditAlphabet = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        top = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        diagramm = new javax.swing.JPanel();
        diagrammContentPanel = new javax.swing.JScrollPane();
        tablePanel = new javax.swing.JPanel();
        bottom = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        toolbar.setLayout(new java.awt.GridBagLayout());

        toolbar.setToolTipText("Mouse");
        toolbar.setMinimumSize(new java.awt.Dimension(60, 309));
        toolbar.setPreferredSize(new java.awt.Dimension(60, 309));
        jLabel1.setText("Toolbar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 20, 0);
        toolbar.add(jLabel1, gridBagConstraints);

        toolbarButton.add(jButtonMouse);
        jButtonMouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_mouse.png")));
        jButtonMouse.setSelected(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jButtonMouse.setToolTipText(bundle.getString("MachinePanel.Mouse")); // NOI18N
        jButtonMouse.setFocusable(false);
        jButtonMouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarMouse(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        toolbar.add(jButtonMouse, gridBagConstraints);

        toolbarButton.add(jButtonAddState);
        jButtonAddState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_add.png")));
        jButtonAddState.setToolTipText(bundle.getString("MachinePanel.AddState")); // NOI18N
        jButtonAddState.setFocusable(false);
        jButtonAddState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarAddState(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(jButtonAddState, gridBagConstraints);

        toolbarButton.add(jButtonAddTransition);
        jButtonAddTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_transition.png")));
        jButtonAddTransition.setToolTipText(bundle.getString("MachinePanel.AddTransition")); // NOI18N
        jButtonAddTransition.setFocusable(false);
        jButtonAddTransition.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolBarTransition(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(jButtonAddTransition, gridBagConstraints);

        toolbarButton.add(jButtonStartState);
        jButtonStartState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_start.png")));
        jButtonStartState.setToolTipText(bundle.getString("MachinePanel.StartState")); // NOI18N
        jButtonStartState.setFocusable(false);
        jButtonStartState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarStart(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(jButtonStartState, gridBagConstraints);

        toolbarButton.add(jButtonFinalState);
        jButtonFinalState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_stop.png")));
        jButtonFinalState.setToolTipText(bundle.getString("MachinePanel.FinalState")); // NOI18N
        jButtonFinalState.setFocusable(false);
        jButtonFinalState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarEnd(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(jButtonFinalState, gridBagConstraints);

        jButtonEditAlphabet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_alphabet.png")));
        jButtonEditAlphabet.setToolTipText(bundle.getString("MachinePanel.EditAlphabet")); // NOI18N
        jButtonEditAlphabet.setFocusable(false);
        jButtonEditAlphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleToolbarAlphabet(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        toolbar.add(jButtonEditAlphabet, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(toolbar, gridBagConstraints);

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setOneTouchExpandable(true);
        top.setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setDividerLocation(700);
        jSplitPane1.setOneTouchExpandable(true);
        diagramm.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        diagramm.add(diagrammContentPanel, gridBagConstraints);

        jSplitPane1.setLeftComponent(diagramm);

        jSplitPane1.setRightComponent(tablePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        top.add(jSplitPane1, gridBagConstraints);

        jSplitPane2.setLeftComponent(top);

        bottom.setName("Console");
        jSplitPane2.setRightComponent(bottom);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane2, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void handleToolbarMouse(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarMouse
        this.logic.handleToolbarMouse(this.jButtonMouse.isSelected());
    }//GEN-LAST:event_handleToolbarMouse

    private void handleToolbarAddState(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarAddState
        this.logic.handleToolbarAddState(this.jButtonAddState.isSelected());
    }//GEN-LAST:event_handleToolbarAddState

    private void handleToolBarTransition(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolBarTransition
        this.logic.handleToolbarTransition(this.jButtonAddTransition.isSelected());
    }//GEN-LAST:event_handleToolBarTransition

    private void handleToolbarStart(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarStart
        this.logic.handleToolbarStart(this.jButtonStartState.isSelected());
    }//GEN-LAST:event_handleToolbarStart

    private void handleToolbarEnd(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarEnd
        this.logic.handleToolbarEnd(this.jButtonFinalState.isSelected());
    }//GEN-LAST:event_handleToolbarEnd

    private void handleToolbarAlphabet(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleToolbarAlphabet
        this.logic.handleToolbarAlphabet();
    }//GEN-LAST:event_handleToolbarAlphabet
    
    
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    private javax.swing.JPanel bottom;
    public javax.swing.JPanel diagramm;
    public javax.swing.JScrollPane diagrammContentPanel;
    public javax.swing.JToggleButton jButtonAddState;
    public javax.swing.JToggleButton jButtonAddTransition;
    public javax.swing.JButton jButtonEditAlphabet;
    public javax.swing.JToggleButton jButtonFinalState;
    public javax.swing.JToggleButton jButtonMouse;
    public javax.swing.JToggleButton jButtonStartState;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JPanel toolbar;
    private javax.swing.ButtonGroup toolbarButton;
    public javax.swing.JPanel top;
    // Ende der Variablendeklaration//GEN-END:variables
    
    
        private MachinePanel logic;
    
    public void setMachinePanel (MachinePanel window){
        logic = window;
    }
}


