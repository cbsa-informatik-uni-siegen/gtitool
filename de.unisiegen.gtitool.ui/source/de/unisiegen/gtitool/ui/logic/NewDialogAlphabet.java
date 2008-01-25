package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.ui.netbeans.NewDialogAlphabetForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The Panel used to enter the {@link Alphabet} for the new file
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class NewDialogAlphabet
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( PreferencesDialog.class );


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
    this.gui.styledAlphabetParserPanelInput.setAlphabet ( PreferenceManager
        .getInstance ().getAlphabetItem ().getAlphabet () );
    this.gui.styledAlphabetParserPanelPushDown.setAlphabet ( PreferenceManager
        .getInstance ().getPushDownAlphabetItem ().getAlphabet () );

    /*
     * Alphabet changed listener
     */
    this.gui.styledAlphabetParserPanelInput
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( @SuppressWarnings ( "unused" )
          Alphabet newAlphabet )
          {
            setButtonStatus ();
          }
        } );
    this.gui.styledAlphabetParserPanelPushDown
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
    return this.gui.styledAlphabetParserPanelInput.getAlphabet ();
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
   * Returns the {@link Alphabet} of the new file.
   * 
   * @return The {@link Alphabet} of the new file.
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.gui.styledAlphabetParserPanelPushDown.getAlphabet ();
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
   * Handles the push down {@link Alphabet} item state changed.
   * 
   * @param event The item event.
   */
  public final void handlePushDownAlphabetItemStateChanged (
      @SuppressWarnings ( "unused" )
      ItemEvent event )
  {
    logger.debug ( "handle push down alphabet state changed" ); //$NON-NLS-1$
    if ( this.gui.jCheckBoxPushDownAlphabet.isSelected () )
    {
      this.gui.styledAlphabetParserPanelPushDown.setEnabled ( true );
      this.gui.styledAlphabetParserPanelPushDown.synchronize ( null );
    }
    else
    {
      this.gui.styledAlphabetParserPanelPushDown.setEnabled ( false );
      this.gui.styledAlphabetParserPanelPushDown
          .synchronize ( this.gui.styledAlphabetParserPanelInput );
    }
  }


  /**
   * Sets the status of the buttons.
   */
  private final void setButtonStatus ()
  {
    if ( ( this.gui.styledAlphabetParserPanelInput.getAlphabet () == null )
        || ( this.gui.styledAlphabetParserPanelPushDown.getAlphabet () == null ) )
    {
      this.gui.jGTIButtonFinished.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonFinished.setEnabled ( true );
    }
  }
}
