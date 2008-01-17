package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.ui.netbeans.NewDialogAlphabetForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The Panel used to enter the {@link Alphabet} for the new file
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialogAlphabet
{

  /** The {@link NewDialogAlphabetForm} */
  private NewDialogAlphabetForm gui;


  /** The parent Dialog containing this panel */
  private NewDialog parent;


  /**
   * Allocate a new <code>NewDialogAlphabet</code>
   * 
   * @param pParent The Dialog containing this panel
   */
  public NewDialogAlphabet ( NewDialog pParent )
  {
    this.parent = pParent;
    this.gui = new NewDialogAlphabetForm ();
    this.gui.setLogic ( this );
    Alphabet alphabet = PreferenceManager.getInstance ().getAlphabetItem ()
        .getAlphabet ().clone ();
    this.gui.styledAlphabetParserPanel.setAlphabet ( alphabet );

    /*
     * Alphabet changed listener
     */
    this.gui.styledAlphabetParserPanel
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( Alphabet pNewAlphabet )
          {
            if ( pNewAlphabet == null )
            {
              NewDialogAlphabet.this.gui.jButtonNext.setEnabled ( false );
            }
            else
            {
              NewDialogAlphabet.this.gui.jButtonNext.setEnabled ( true );
            }
          }
        } );
  }


  /**
   * Getter for the gui of this logic class
   * 
   * @return The {@link NewDialogAlphabetForm}
   */
  public NewDialogAlphabetForm getGui ()
  {
    return this.gui;
  }


  /**
   * Handle the cancel button pressed event
   */
  public void handleCancel ()
  {
    this.parent.getGui ().dispose ();

  }


  /**
   * Handle the finish button pressed event
   */
  public void handleFinish ()
  {
    this.parent.handleFinish ();

  }


  /**
   * Handle the previous button pressed event
   */
  public void handlePrevious ()
  {
    this.parent.handleAlphabetPrevious ();

  }


  /**
   * Get the alphabet for the new file
   * 
   * @return the {@link Alphabet} for the new file
   */
  public Alphabet getAlphabet ()
  {
    return this.gui.styledAlphabetParserPanel.getAlphabet ();
  }

}
