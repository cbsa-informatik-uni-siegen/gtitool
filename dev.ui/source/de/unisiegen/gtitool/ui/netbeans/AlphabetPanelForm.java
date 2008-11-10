package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * The {@link AlphabetPanelForm}.
 * 
 * @author Christian Fehler
 * @version $Id: AlphabetPanelForm.java 946 2008-05-30 14:27:24Z fehler $
 */
@SuppressWarnings ({ "all" })
public class AlphabetPanelForm extends JPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 945369117694612666L;


  /**
   * Allocates a new {@link AlphabetPanelForm}.
   */
  public AlphabetPanelForm ()
  {
    initComponents ();
  }


  /**
   * Handles the push down {@link Alphabet} item state changed event.
   */
  private void handlePushDownAlphabetItemStateChanged ()
  {
    if ( this.jGTICheckBoxPushDownAlphabet.isSelected () )
    {
      this.styledAlphabetParserPanelPushDown.setEnabled ( true );
      this.styledAlphabetParserPanelPushDown.synchronize ( null );
    }
    else
    {
      this.styledAlphabetParserPanelPushDown.setEnabled ( false );
      this.styledAlphabetParserPanelPushDown
          .synchronize ( this.styledAlphabetParserPanelInput );
    }
  }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabelInputAlphabet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledAlphabetParserPanelInput = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jGTICheckBoxPushDownAlphabet = new de.unisiegen.gtitool.ui.swing.JGTICheckBox();
        styledAlphabetParserPanelPushDown = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        styledRegexAlphabetParserPanelInput = new de.unisiegen.gtitool.ui.style.StyledRegexAlphabetParserPanel();

        setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTILabelInputAlphabet.setText(bundle.getString("PreferencesDialog.InputAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 5, 0);
        add(jGTILabelInputAlphabet, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        add(styledAlphabetParserPanelInput, gridBagConstraints);

        jGTICheckBoxPushDownAlphabet.setSelected(true);
        jGTICheckBoxPushDownAlphabet.setText(bundle.getString("PreferencesDialog.PushDownAlphabet")); // NOI18N
        jGTICheckBoxPushDownAlphabet.setToolTipText(bundle.getString("PreferencesDialog.PushDownAlphabetToolTip")); // NOI18N
        jGTICheckBoxPushDownAlphabet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTICheckBoxPushDownAlphabetItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 0);
        add(jGTICheckBoxPushDownAlphabet, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(styledAlphabetParserPanelPushDown, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        add(styledRegexAlphabetParserPanelInput, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTICheckBoxPushDownAlphabetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTICheckBoxPushDownAlphabetItemStateChanged
       handlePushDownAlphabetItemStateChanged ();
    }//GEN-LAST:event_jGTICheckBoxPushDownAlphabetItemStateChanged


  private void jCheckBoxPushDownAlphabetItemStateChanged (
      java.awt.event.ItemEvent evt )
  {// GEN-FIRST:event_jCheckBoxPushDownAlphabetItemStateChanged
    handlePushDownAlphabetItemStateChanged ();
  }// GEN-LAST:event_jCheckBoxPushDownAlphabetItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTICheckBox jGTICheckBoxPushDownAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelInputAlphabet;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelInput;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelPushDown;
    public de.unisiegen.gtitool.ui.style.StyledRegexAlphabetParserPanel styledRegexAlphabetParserPanelInput;
    // End of variables declaration//GEN-END:variables

}
