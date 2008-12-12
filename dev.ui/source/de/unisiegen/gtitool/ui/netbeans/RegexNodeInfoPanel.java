/*
 * RegexNodeInfoPanel.java
 *
 * Created on 12. Dezember 2008, 10:31
 */

package de.unisiegen.gtitool.ui.netbeans;

/**
 *
 * @author  simon
 */
public class RegexNodeInfoPanel extends javax.swing.JPanel {

    /** Creates new form RegexNodeInfoPanel */
    public RegexNodeInfoPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabelNullable = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jScrollPaneNullable = new javax.swing.JScrollPane();
        jGTITextAreaNullable = new de.unisiegen.gtitool.ui.swing.JGTITextArea();
        jScrollPaneFirstpos = new javax.swing.JScrollPane();
        jGTITextAreaFirstpos = new de.unisiegen.gtitool.ui.swing.JGTITextArea();
        jGTILabelFirstpos = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelLastpos = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jScrollPaneLastpos = new javax.swing.JScrollPane();
        jGTITextAreaLastpos = new de.unisiegen.gtitool.ui.swing.JGTITextArea();
        jScrollPaneFollowpos = new javax.swing.JScrollPane();
        jGTITextAreaFollowpos = new de.unisiegen.gtitool.ui.swing.JGTITextArea();
        jGTILabelFollowpos = new de.unisiegen.gtitool.ui.swing.JGTILabel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("RegexPanel.InformationTitle"))); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        jGTILabelNullable.setText(bundle.getString("RegexNodeInfo.Nullable")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 10);
        add(jGTILabelNullable, gridBagConstraints);

        jGTITextAreaNullable.setColumns(20);
        jGTITextAreaNullable.setEditable(false);
        jGTITextAreaNullable.setRows(1);
        jScrollPaneNullable.setViewportView(jGTITextAreaNullable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        add(jScrollPaneNullable, gridBagConstraints);

        jGTITextAreaFirstpos.setColumns(20);
        jGTITextAreaFirstpos.setEditable(false);
        jGTITextAreaFirstpos.setRows(2);
        jScrollPaneFirstpos.setViewportView(jGTITextAreaFirstpos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        add(jScrollPaneFirstpos, gridBagConstraints);

        jGTILabelFirstpos.setText(bundle.getString("RegexNodeInfo.Firstpos")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        add(jGTILabelFirstpos, gridBagConstraints);

        jGTILabelLastpos.setText(bundle.getString("RegexNodeInfo.Lastpos")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        add(jGTILabelLastpos, gridBagConstraints);

        jGTITextAreaLastpos.setColumns(20);
        jGTITextAreaLastpos.setEditable(false);
        jGTITextAreaLastpos.setRows(2);
        jScrollPaneLastpos.setViewportView(jGTITextAreaLastpos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        add(jScrollPaneLastpos, gridBagConstraints);

        jGTITextAreaFollowpos.setColumns(20);
        jGTITextAreaFollowpos.setEditable(false);
        jGTITextAreaFollowpos.setRows(2);
        jScrollPaneFollowpos.setViewportView(jGTITextAreaFollowpos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        add(jScrollPaneFollowpos, gridBagConstraints);

        jGTILabelFollowpos.setText(bundle.getString("RegexNodeInfo.Followpos")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        add(jGTILabelFollowpos, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelFirstpos;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelFollowpos;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelLastpos;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelNullable;
    public de.unisiegen.gtitool.ui.swing.JGTITextArea jGTITextAreaFirstpos;
    public de.unisiegen.gtitool.ui.swing.JGTITextArea jGTITextAreaFollowpos;
    public de.unisiegen.gtitool.ui.swing.JGTITextArea jGTITextAreaLastpos;
    public de.unisiegen.gtitool.ui.swing.JGTITextArea jGTITextAreaNullable;
    public javax.swing.JScrollPane jScrollPaneFirstpos;
    public javax.swing.JScrollPane jScrollPaneFollowpos;
    public javax.swing.JScrollPane jScrollPaneLastpos;
    public javax.swing.JScrollPane jScrollPaneNullable;
    // End of variables declaration//GEN-END:variables

}
