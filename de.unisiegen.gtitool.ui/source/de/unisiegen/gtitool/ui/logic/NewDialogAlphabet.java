package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.NewDialogAlphabetForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The panel used to enter the {@link Alphabet} for the new file.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class NewDialogAlphabet implements
    LogicClass < NewDialogAlphabetForm >
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
    this.gui = new NewDialogAlphabetForm ( this );
    /*
     * Alphabet changed listener
     */
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) Alphabet newAlphabet )
          {
            setButtonStatus ();
          }
        } );
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) Alphabet newAlphabet )
          {
            setButtonStatus ();
          }
        } );
    this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) Alphabet newAlphabet )
          {
            setButtonStatus ();
          }
        } );
  }


  /**
   * Changes GUI elements
   */
  public void changeGui ()
  {
    if ( this.parent.getNewDialogChoice ().getUserChoice ().equals (
        NewDialogChoice.Choice.REGEX ) )
    {
      getGUI ().alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setVisible ( false );
      getGUI ().alphabetPanelForm.styledAlphabetParserPanelPushDown
          .setVisible ( false );
      getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
          .setVisible ( false );
      getGUI ().alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setVisible ( true );
      getGUI ().alphabetPanelForm.jGTILabelRegexAlphabet.setVisible ( false );
      this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setText ( ( ( DefaultRegexAlphabet ) PreferenceManager
              .getInstance ().getRegexAlphabetItem ().getAlphabet () )
              .toClassPrettyString () );
    }
    else
    {
      getGUI ().alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setVisible ( true );
      getGUI ().alphabetPanelForm.styledAlphabetParserPanelPushDown
          .setVisible ( true );
      getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
          .setVisible ( true );
      getGUI ().alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setVisible ( false );
      getGUI ().alphabetPanelForm.jGTILabelRegexAlphabet.setVisible ( false );
      this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
          .setText ( PreferenceManager.getInstance ().getAlphabetItem ()
              .getAlphabet () );
      this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
          .setText ( PreferenceManager.getInstance ()
              .getPushDownAlphabetItem ().getAlphabet () );
      this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setSelected ( PreferenceManager.getInstance ()
              .getUsePushDownAlphabet () );
    }
  }


  /**
   * Returns the {@link Alphabet} of the new file.
   * 
   * @return The {@link Alphabet} of the new file.
   */
  public final Alphabet getAlphabet ()
  {
    return this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getParsedObject ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final NewDialogAlphabetForm getGUI ()
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
        .getParsedObject ();
  }


  /**
   * Returns the {@link DefaultRegexAlphabet} of the new file.
   * 
   * @return The {@link DefaultRegexAlphabet} of the new file.
   */
  public final DefaultRegexAlphabet getRegexAlphabet ()
  {
    return ( DefaultRegexAlphabet ) this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
        .getParsedObject ();
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
    this.parent.getGUI ().dispose ();
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
  public final void setButtonStatus ()
  {
    boolean pushDownOk = this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .getParsedObject () != null;
    boolean normalOk = this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getParsedObject () != null;
    boolean regexOk = this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
        .getParsedObject () != null;
    if ( this.parent.getNewDialogChoice ().getUserChoice ().equals (
        NewDialogChoice.Choice.REGEX ) )
    {
      if ( !regexOk )
      {
        this.gui.jGTIButtonFinished.setEnabled ( false );
      }
      else
      {
        this.gui.jGTIButtonFinished.setEnabled ( true );
      }
    }
    else
    {
      if ( !pushDownOk || !normalOk )
      {
        this.gui.jGTIButtonFinished.setEnabled ( false );
      }
      else
      {
        this.gui.jGTIButtonFinished.setEnabled ( true );
      }
    }
    if ( !this.parent.getMachineChoice ().equals (
        NewDialogMachineChoice.Choice.PDA ) )
    {
      if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.SHOW ) )
      {
        // do nothing
      }
      else if ( PreferenceManager.getInstance ().getPDAModeItem ().equals (
          PDAModeItem.HIDE ) )
      {
        this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
            .setEnabled ( false );
        this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .setEnabled ( false );
      }
      else
      {
        throw new RuntimeException ( "unsupported pda mode" ); //$NON-NLS-1$
      }
    }
    else
    {
      this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setEnabled ( true );
      if ( this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .isSelected () )
      {
        this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .setEnabled ( true );
      }
    }
  }
}
