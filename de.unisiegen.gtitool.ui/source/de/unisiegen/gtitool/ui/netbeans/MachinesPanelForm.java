/*
 * MachinesPanelForm.java
 *
 * Created on 21. Oktober 2007, 16:47
 */

package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.MachinePanel;

/**
 *
 * @author  Benjamin Mies
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
        mouse = new javax.swing.JToggleButton();
        addState = new javax.swing.JToggleButton();
        transition = new javax.swing.JToggleButton();
        deleteState = new javax.swing.JToggleButton();
        start = new javax.swing.JToggleButton();
        end = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        diagramm = new javax.swing.JPanel();
        diagrammContentPanel = new javax.swing.JScrollPane();
        tablePanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        toolbar.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Toolbar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        toolbar.add(jLabel1, gridBagConstraints);

        toolbarButton.add(mouse);
        mouse.setSelected(true);
        mouse.setText("mouse");
        mouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarMouse(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(mouse, gridBagConstraints);

        toolbarButton.add(addState);
        addState.setText("addState");
        addState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarAddState(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(addState, gridBagConstraints);

        toolbarButton.add(transition);
        transition.setText("transition");
        transition.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolBarTransition(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(transition, gridBagConstraints);

        toolbarButton.add(deleteState);
        deleteState.setText("delState");
        deleteState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarDelState(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(deleteState, gridBagConstraints);

        toolbarButton.add(start);
        start.setText("start");
        start.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarStart(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(start, gridBagConstraints);

        toolbarButton.add(end);
        end.setText("end");
        end.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleToolbarEnd(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        toolbar.add(end, gridBagConstraints);

        jButton1.setText("alphabet");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleToolbarAlphabet(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        toolbar.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(toolbar, gridBagConstraints);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
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
        add(jSplitPane1, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void handleToolbarMouse(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarMouse
        this.logic.handleToolbarMouse(this.mouse.isSelected());
    }//GEN-LAST:event_handleToolbarMouse

    private void handleToolbarAddState(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarAddState
        this.logic.handleToolbarAddState(this.addState.isSelected());
    }//GEN-LAST:event_handleToolbarAddState

    private void handleToolBarTransition(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolBarTransition
        this.logic.handleToolbarTransition(this.transition.isSelected());
    }//GEN-LAST:event_handleToolBarTransition

    private void handleToolbarDelState(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarDelState
        this.logic.handleToolbarDelState(this.deleteState.isSelected());
    }//GEN-LAST:event_handleToolbarDelState

    private void handleToolbarStart(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarStart
        this.logic.handleToolbarStart(this.start.isSelected());
    }//GEN-LAST:event_handleToolbarStart

    private void handleToolbarEnd(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleToolbarEnd
        this.logic.handleToolbarEnd(this.end.isSelected());
    }//GEN-LAST:event_handleToolbarEnd

    private void handleToolbarAlphabet(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleToolbarAlphabet
        this.logic.handleToolbarAlphabet();
    }//GEN-LAST:event_handleToolbarAlphabet
    
    
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    private javax.swing.JToggleButton addState;
    private javax.swing.JToggleButton deleteState;
    private javax.swing.JPanel diagramm;
    public javax.swing.JScrollPane diagrammContentPanel;
    private javax.swing.JToggleButton end;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToggleButton mouse;
    private javax.swing.JToggleButton start;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JPanel toolbar;
    private javax.swing.ButtonGroup toolbarButton;
    private javax.swing.JToggleButton transition;
    // Ende der Variablendeklaration//GEN-END:variables
    
    
        private MachinePanel logic;
    
    public void setMachinePanel (MachinePanel window){
        logic = window;
    }
}


