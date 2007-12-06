package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
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
    this.newDialogForm.styledAlphabetParserPanelGrammar.setAlphabet ( this.alphabet );
    this.newDialogForm.styledAlphabetParserPanelMachine.setAlphabet ( this.alphabet );
    this.newDialogForm.styledAlphabetParserPanelGrammar.synchronize ( this.newDialogForm.styledAlphabetParserPanelMachine );
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
    if ( this.newDialogForm.tabbedPane.getSelectedComponent () == this.newDialogForm.machinesPanel )
    {
      if ( this.newDialogForm.jRadioButtonDFA.isSelected ()  ) {
        
        return new MachinePanel ( this.parent, new DefaultMachineModel ( new DFA( this.newDialogForm.styledAlphabetParserPanelMachine.getAlphabet () ) ) );
      }
    }
    return new GrammarPanel ( this.parent, this.newDialogForm.styledAlphabetParserPanelGrammar.getAlphabet () );
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
