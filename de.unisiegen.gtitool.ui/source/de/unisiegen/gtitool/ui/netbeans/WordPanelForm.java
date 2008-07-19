package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;

/**
 * The {@link WordPanelForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class WordPanelForm extends JPanel implements LanguageChangedListener
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -7783353171815365705L;
    
    /**
     * The {@link Alphabet}.
     */
    private Alphabet alphabet = null ;
    
    /**
     * The push down {@link Alphabet}.
     */
    private Alphabet pushDownAlphabet = null ;
    
    /**
     * Allocates a new {@link WordPanelForm}.
     */
    public WordPanelForm() {
        initComponents();
        PreferenceManager.getInstance ().addLanguageChangedListener ( this );
    }
    
    /**
     * Sets the {@link Alphabet} of this {@link WordPanelForm}.
     *
     * @param alphabet The {@link Alphabet} to set.
     */
    public void setAlphabet (Alphabet alphabet)
    {
      this.alphabet = alphabet;
      this.styledAlphabetParserPanelInput.setText ( alphabet );
      this.styledWordParserPanel.setAlphabet ( alphabet );
    }
    
    /**
     * Sets the push down {@link Alphabet} of this {@link WordPanelForm}.
     *
     * @param pushDownAlphabet The push down {@link Alphabet} to set.
     */
    public void setPushDownAlphabet (Alphabet pushDownAlphabet)
    {
      this.pushDownAlphabet = pushDownAlphabet;
      this.styledAlphabetParserPanelPushDown.setText ( pushDownAlphabet );
      this.styledStackParserPanel.setPushDownAlphabet ( pushDownAlphabet );
    }
    
    /**
     * Returns the {@link Alphabet}.
     *
     * @return The {@link Alphabet}.
     */
    public Alphabet getAlphabet ()
    {
      return this.alphabet;
    }
    
    /**
     * Returns the push down {@link Alphabet}.
     *
     * @return The push down {@link Alphabet}.
     */
    public Alphabet getPushDownAlphabet ()
    {
      return this.pushDownAlphabet;
    }
    
    /**
     * {@inheritDoc}
     *
     * @see JComponent#requestFocus()
     */
    @Override
    public final void requestFocus()
    {
      this.styledWordParserPanel.requestFocus ();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see LanguageChangedListener#languageChanged()
     */
    public final void languageChanged ()
    {
      this.jGTILabelWord.setText ( Messages.getString ( "StyledWordParserPanel.Word" ) );
      this.jGTILabelStack.setText ( Messages.getString ( "StyledWordParserPanel.Stack" ) );
      this.jGTILabelAlphabet.setText ( Messages.getString ( "StyledWordParserPanel.Alphabet" ) );
      this.jGTILabelPushDownAlphabet.setText ( Messages.getString ( "StyledWordParserPanel.PushDownAlphabet" ) );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabelWord = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledWordParserPanel = new de.unisiegen.gtitool.ui.style.StyledWordParserPanel();
        jGTILabelAlphabet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledAlphabetParserPanelInput = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jGTILabelStack = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledStackParserPanel = new de.unisiegen.gtitool.ui.style.StyledStackParserPanel();
        jGTILabelPushDownAlphabet = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledAlphabetParserPanelPushDown = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jGTILabelStatus = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIColumn1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();

        setLayout(new java.awt.GridBagLayout());

        jGTILabelWord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTILabelWord.setText(bundle.getString("StyledWordParserPanel.Word")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTILabelWord, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        add(styledWordParserPanel, gridBagConstraints);

        jGTILabelAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelAlphabet.setText(bundle.getString("StyledWordParserPanel.Alphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTILabelAlphabet, gridBagConstraints);

        styledAlphabetParserPanelInput.setCopyable(true);
        styledAlphabetParserPanelInput.setEditable(false);
        styledAlphabetParserPanelInput.setSideBarVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(styledAlphabetParserPanelInput, gridBagConstraints);

        jGTILabelStack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelStack.setText(bundle.getString("StyledWordParserPanel.Stack")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTILabelStack, gridBagConstraints);

        styledStackParserPanel.setEditable(false);
        styledStackParserPanel.setRightAlignment(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        add(styledStackParserPanel, gridBagConstraints);

        jGTILabelPushDownAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelPushDownAlphabet.setText(bundle.getString("StyledWordParserPanel.PushDownAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTILabelPushDownAlphabet, gridBagConstraints);

        styledAlphabetParserPanelPushDown.setCopyable(true);
        styledAlphabetParserPanelPushDown.setEditable(false);
        styledAlphabetParserPanelPushDown.setSideBarVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(styledAlphabetParserPanelPushDown, gridBagConstraints);

        jGTILabelStatus.setText(bundle.getString("WordPanel.StatusEmpty")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 0, 16);
        add(jGTILabelStatus, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jGTIColumn0, gridBagConstraints);

        jGTIColumn1.setMinimumSize(new java.awt.Dimension(250, 5));
        jGTIColumn1.setPreferredSize(new java.awt.Dimension(250, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jGTIColumn1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIColumn0;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIColumn1;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPushDownAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelStack;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelStatus;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelWord;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelInput;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelPushDown;
    public de.unisiegen.gtitool.ui.style.StyledStackParserPanel styledStackParserPanel;
    public de.unisiegen.gtitool.ui.style.StyledWordParserPanel styledWordParserPanel;
    // End of variables declaration//GEN-END:variables
    
}
