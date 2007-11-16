package de.unisiegen.gtitool.ui.logic;


import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.utils.AlphabetParser;


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
  Alphabet alphabet;


  /**
   * Creates a new <code>NewDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public NewDialog ( JFrame pParent )
  {
    this.parent = pParent;
    this.newDialogForm = new NewDialogForm ( pParent, true );
    try
    {
      this.newDialogForm.setIconImage ( ImageIO.read ( getClass ().getResource (
          "/de/unisiegen/gtitool/ui/icon/gtitool.png" ) ) ); //$NON-NLS-1$
    }
    catch ( Exception e )
    {
      // Do nothing
    }
    this.newDialogForm.setLogic ( this );
    this.alphabet = PreferenceManager.getInstance ().getAlphabetItem ()
        .getAlphabet ().clone ();
    this.newDialogForm.jTextPaneMachineAlphabet.setText ( AlphabetParser
        .createString ( this.alphabet ) );
    this.newDialogForm.jTextPaneGrammarAlphabet.setText ( AlphabetParser
        .createString ( this.alphabet ) );

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
    try
    {
      this.alphabet = AlphabetParser
          .createAlphabet ( this.newDialogForm.jTextPaneMachineAlphabet
              .getText () );
      if ( this.newDialogForm.tabbedPane.getSelectedComponent () == this.newDialogForm.machinesPanel )
        return new MachinePanel ( this.parent, this.alphabet );
      return new GrammarPanel ( this.parent, this.alphabet );
    }
    catch ( AlphabetException e )
    {
      /*
       * TODOBenny Handle the error. Happens if the user wants to add a symbol
       * more than one time.
       */
      return null;
    }
    catch ( SymbolException e )
    {
      /*
       * This should not happen. The SymbolException is thrown if a symbol with
       * an empty name should be created.
       */
      e.printStackTrace ();
      return null;
    }
  }


  /**
   * Handle the Key Typed Event for the Text Panes
   * 
   * @param pKeyEvent The fired {@link KeyEvent}
   */
  public void handleKeyTypedEvent ( KeyEvent pKeyEvent )
  {
    if ( !AlphabetParser.checkInput ( pKeyEvent.getKeyChar () ) )
    {
      pKeyEvent.setKeyChar ( '\u0000' );
    }
    if ( this.newDialogForm.jTextPaneMachineAlphabet.equals ( pKeyEvent
        .getSource () ) )
      this.newDialogForm.jTextPaneGrammarAlphabet
          .setText ( ( ( JTextPane ) pKeyEvent.getSource () ).getText ()
              + pKeyEvent.getKeyChar () );
    else
      this.newDialogForm.jTextPaneMachineAlphabet
          .setText ( ( ( JTextPane ) pKeyEvent.getSource () ).getText ()
              + pKeyEvent.getKeyChar () );
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
