package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JComponent;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;

/**
 * The {@link WordPanelForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class WordPanelForm extends javax.swing.JPanel implements LanguageChangedListener{
    
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
     * Creates new form WordPanelForm.
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
      this.styledAlphabetParserPanelInput.setAlphabet ( alphabet );
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
      this.styledAlphabetParserPanelPushDown.setAlphabet ( pushDownAlphabet );
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
      this.jLabelWord.setText ( Messages.getString ( "StyledWordParserPanel.Word" ) );
      this.jLabelStack.setText ( Messages.getString ( "StyledWordParserPanel.Stack" ) );
      this.JLabelAlphabet.setText ( Messages.getString ( "StyledWordParserPanel.Alphabet" ) );
      this.jLabelPushDownAlphabet.setText ( Messages.getString ( "StyledWordParserPanel.PushDownAlphabet" ) );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelWord = new javax.swing.JLabel();
        styledWordParserPanel = new de.unisiegen.gtitool.ui.style.StyledWordParserPanel();
        jLabelStack = new javax.swing.JLabel();
        styledStackParserPanel = new de.unisiegen.gtitool.ui.style.StyledStackParserPanel();
        JLabelAlphabet = new javax.swing.JLabel();
        styledAlphabetParserPanelInput = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jLabelPushDownAlphabet = new javax.swing.JLabel();
        styledAlphabetParserPanelPushDown = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();

        setLayout(new java.awt.GridBagLayout());

        jLabelWord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jLabelWord.setText(bundle.getString("StyledWordParserPanel.Word")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jLabelWord, gridBagConstraints);

        styledWordParserPanel.setMinimumSize(new java.awt.Dimension(200, 53));
        styledWordParserPanel.setPreferredSize(new java.awt.Dimension(200, 53));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        add(styledWordParserPanel, gridBagConstraints);

        jLabelStack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelStack.setText(bundle.getString("StyledWordParserPanel.Stack")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jLabelStack, gridBagConstraints);

        styledStackParserPanel.setEditable(false);
        styledStackParserPanel.setMinimumSize(new java.awt.Dimension(200, 53));
        styledStackParserPanel.setPreferredSize(new java.awt.Dimension(200, 53));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 16, 16);
        add(styledStackParserPanel, gridBagConstraints);

        JLabelAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelAlphabet.setText(bundle.getString("StyledWordParserPanel.Alphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(JLabelAlphabet, gridBagConstraints);

        styledAlphabetParserPanelInput.setCopyable(true);
        styledAlphabetParserPanelInput.setEditable(false);
        styledAlphabetParserPanelInput.setMinimumSize(new java.awt.Dimension(100, 53));
        styledAlphabetParserPanelInput.setPreferredSize(new java.awt.Dimension(100, 53));
        styledAlphabetParserPanelInput.setSideBarVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(styledAlphabetParserPanelInput, gridBagConstraints);

        jLabelPushDownAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPushDownAlphabet.setText(bundle.getString("StyledWordParserPanel.PushDownAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jLabelPushDownAlphabet, gridBagConstraints);

        styledAlphabetParserPanelPushDown.setCopyable(true);
        styledAlphabetParserPanelPushDown.setEditable(false);
        styledAlphabetParserPanelPushDown.setMinimumSize(new java.awt.Dimension(100, 53));
        styledAlphabetParserPanelPushDown.setPreferredSize(new java.awt.Dimension(100, 53));
        styledAlphabetParserPanelPushDown.setSideBarVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        add(styledAlphabetParserPanelPushDown, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel JLabelAlphabet;
    public javax.swing.JLabel jLabelPushDownAlphabet;
    public javax.swing.JLabel jLabelStack;
    public javax.swing.JLabel jLabelWord;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelInput;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanelPushDown;
    public de.unisiegen.gtitool.ui.style.StyledStackParserPanel styledStackParserPanel;
    public de.unisiegen.gtitool.ui.style.StyledWordParserPanel styledWordParserPanel;
    // End of variables declaration//GEN-END:variables
    
}
