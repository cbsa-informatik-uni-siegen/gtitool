package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The <code>NewDialogg</code>.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialog
{

  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogForm newDialogForm;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link Alphabet} for the new file
   */
  private Alphabet alphabet;
  
  /** The edit alphabet panel for the machine tab */
  private EditAlphabetPanel editMachineAlphabetPanel;
  
  /** The edit alphabet panel for the grammar tab */
  private EditAlphabetPanel editGrammarAlphabetPanel;


  /**
   * Creates a new <code>NewDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public NewDialog ( JFrame pParent )
  {
    this.parent = pParent;
    this.newDialogForm = new NewDialogForm ( pParent, true );
    this.newDialogForm.setLogic ( this );
    this.alphabet = PreferenceManager.getInstance ().getAlphabetItem ()
        .getAlphabet ().clone ();
    this.editMachineAlphabetPanel = new EditAlphabetPanel ();
    this.editMachineAlphabetPanel.styledAlphabetParserPanel.setAlphabet ( this.alphabet );
    this.editGrammarAlphabetPanel = new EditAlphabetPanel ();
    this.editGrammarAlphabetPanel.styledAlphabetParserPanel.setAlphabet ( this.alphabet );
    GridBagConstraints gridBagConstraints = new GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new Insets ( 0, 5, 0, 5 );
    this.newDialogForm.bodyPanelGrammar.add ( this.editGrammarAlphabetPanel.getPanel (), gridBagConstraints );
    this.newDialogForm.bodyPanelMachine.add ( this.editMachineAlphabetPanel.getPanel (), gridBagConstraints );
    

  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.newDialogForm.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.newDialogForm.getHeight () / 2 );
    this.newDialogForm.setBounds ( x, y, this.newDialogForm.getWidth (),
        this.newDialogForm.getHeight () );
    this.newDialogForm.setVisible ( true );
  }


  /**
   * Return a new EditorPanel
   * 
   * @return a new EditorPanel or null
   */
  public EditorPanel getEditorPanel ()
  {
    if ( this.newDialogForm.isCanceled () )
      return null;
      this.alphabet = this.editMachineAlphabetPanel.styledAlphabetParserPanel.getAlphabet ();
      if ( this.newDialogForm.tabbedPane.getSelectedComponent () == this.newDialogForm.machinesPanel )
        return new MachinePanel ( this.parent, this.alphabet );
      return new GrammarPanel ( this.parent, this.alphabet );
  }


  /**
   * Get the alphabet for the new file
   * 
   * @return the {@link Alphabet} for the new file
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }
}
