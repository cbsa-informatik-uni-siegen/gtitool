package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.style.listener.AlphabetChangedListener;


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
  private NewDialogForm gui;


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
    this.gui = new NewDialogForm ( pParent, true );
    this.gui.setLogic ( this );
    this.alphabet = PreferenceManager.getInstance ().getAlphabetItem ()
        .getAlphabet ().clone ();
    this.gui.styledAlphabetParserPanelGrammar.setAlphabet ( this.alphabet );
    this.gui.styledAlphabetParserPanelMachine.setAlphabet ( this.alphabet );
    this.gui.styledAlphabetParserPanelGrammar.synchronize ( this.gui.styledAlphabetParserPanelMachine );
    
    /*
     * Alphabet changed listener
     */
    this.gui.styledAlphabetParserPanelGrammar
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( Alphabet pNewAlphabet )
          {
            if ( pNewAlphabet == null )
            {
              NewDialog.this.gui.jButtonOk.setEnabled ( false );
            }
            else
            {
              NewDialog.this.gui.jButtonOk.setEnabled ( true );
            }
          }
        } );
    
    /*
     * Alphabet changed listener
     */
    this.gui.styledAlphabetParserPanelGrammar
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( Alphabet pNewAlphabet )
          {
            if ( pNewAlphabet == null )
            {
              NewDialog.this.gui.jButtonOk.setEnabled ( false );
            }
            else
            {
              NewDialog.this.gui.jButtonOk.setEnabled ( true );
            }
          }
        } );
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (),
        this.gui.getHeight () );
    this.gui.setVisible ( true );
  }


  /**
   * Return a new EditorPanel
   * 
   * @return a new EditorPanel or null
   */
  public EditorPanel getEditorPanel ()
  {
    if ( this.gui.isCanceled () )
      return null;
    if ( this.gui.tabbedPane.getSelectedComponent () == this.gui.machinesPanel )
    {
      if ( this.gui.jRadioButtonDFA.isSelected ()  ) {
        
        return new MachinePanel ( this.parent, new DefaultMachineModel ( new DFA( this.gui.styledAlphabetParserPanelMachine.getAlphabet () ) ) );
      }
    }
    return new GrammarPanel ( this.parent, this.gui.styledAlphabetParserPanelGrammar.getAlphabet () );
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
