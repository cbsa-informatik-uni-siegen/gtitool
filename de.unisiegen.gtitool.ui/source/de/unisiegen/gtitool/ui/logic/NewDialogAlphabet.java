package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.ui.netbeans.NewDialogAlphabetForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The panel used to enter the {@link Alphabet} for the new file.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class NewDialogAlphabet
{

  /**
   * The {@link NewDialogAlphabetForm}
   */
  private NewDialogAlphabetForm gui;


  /**
   * The parent Dialog containing this panel
   */
  private NewDialog parent;


  /**
   * Allocate a new {@link NewDialogAlphabet}.
   * 
   * @param parent The dialog containing this panel.
   */
  public NewDialogAlphabet ( NewDialog parent )
  {
    this.parent = parent;
    this.gui = new NewDialogAlphabetForm ();
    this.gui.setLogic ( this );
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .setText ( PreferenceManager.getInstance ().getAlphabetItem ()
            .getAlphabet () );
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .setText ( PreferenceManager.getInstance ().getPushDownAlphabetItem ()
            .getAlphabet () );
    this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
        .setSelected ( PreferenceManager.getInstance ()
            .getUsePushDownAlphabet () );

    /*
     * Alphabet changed listener
     */
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( @SuppressWarnings ( "unused" )
          Alphabet newAlphabet )
          {
            setButtonStatus ();
          }
        } );
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( @SuppressWarnings ( "unused" )
          Alphabet newAlphabet )
          {
            setButtonStatus ();
          }
        } );
  }


  /**
   * Returns the {@link Alphabet} of the new file.
   * 
   * @return The {@link Alphabet} of the new file.
   */
  public final Alphabet getAlphabet ()
  {
    return this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getAlphabet ();
  }


  /**
   * Getter for the gui of this logic class.
   * 
   * @return The {@link NewDialogAlphabetForm}.
   */
  public final NewDialogAlphabetForm getGui ()
  {
    return this.gui;
  }


  /**
   * Returns the push down {@link Alphabet} of the new file.
   * 
   * @return The push down {@link Alphabet} of the new file.
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .getAlphabet ();
  }


  /**
   * Returns the use push down {@link Alphabet} of the new file.
   * 
   * @return The use push down {@link Alphabet} of the new file.
   */
  public final boolean getUsePushDownAlphabet ()
  {
    return this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
        .isSelected ();
  }


  /**
   * Handle the cancel button pressed event.
   */
  public final void handleCancel ()
  {
    this.parent.getGui ().dispose ();
  }


  /**
   * Handle the finish button pressed event.
   */
  public final void handleFinish ()
  {
    this.parent.handleFinish ();

  }


  /**
   * Handle the previous button pressed event.
   */
  public final void handlePrevious ()
  {
    this.parent.handleAlphabetPrevious ();
  }


  /**
   * Sets the status of the buttons.
   */
  private final void setButtonStatus ()
  {
    if ( ( this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getAlphabet () == null )
        || ( this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .getAlphabet () == null ) )
    {
      this.gui.jGTIButtonFinished.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonFinished.setEnabled ( true );
    }
  }
}
