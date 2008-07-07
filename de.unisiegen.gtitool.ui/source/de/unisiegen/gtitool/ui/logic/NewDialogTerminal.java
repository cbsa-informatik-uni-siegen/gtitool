package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.NewDialogAlphabetForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogTerminalForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The panel used to enter the {@link Alphabet} for the new file.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class NewDialogTerminal implements
    LogicClass < NewDialogTerminalForm >
{

  /**
   * The {@link NewDialogAlphabetForm}
   */
  private NewDialogTerminalForm gui;


  /**
   * The parent Dialog containing this panel
   */
  private NewDialog parent;


  /**
   * Allocate a new {@link NewDialogTerminal}.
   * 
   * @param parent The dialog containing this panel.
   */
  public NewDialogTerminal ( NewDialog parent )
  {
    this.parent = parent;
    this.gui = new NewDialogTerminalForm ( this );

    this.gui.terminalPanelForm.setNonterminalSymbolSet ( PreferenceManager
        .getInstance ().getNonterminalSymbolSetItem ()
        .getNonterminalSymbolSet () );

    this.gui.terminalPanelForm.setTerminalSymbolSet ( PreferenceManager
        .getInstance ().getTerminalSymbolSetItem ().getTerminalSymbolSet () );

    this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .setText ( PreferenceManager.getInstance ().getStartSymbolItem ()
            .getNonterminalSymbol () );

    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbolSet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) NonterminalSymbolSet newNonterminalSymbolSet )
          {
            setButtonStatus ();
          }
        } );
    this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < TerminalSymbolSet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) TerminalSymbolSet newTerminalSymbolSet )
          {
            setButtonStatus ();
          }
        } );

    this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbol > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) NonterminalSymbol newNonterminalSymbol )
          {
            setButtonStatus ();
          }
        } );
  }


  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return the {@link TerminalSymbolSet}
   */
  public TerminalSymbolSet geTerminalSymbolSet ()
  {
    return this.gui.terminalPanelForm.getTerminalSymbolSet ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final NewDialogTerminalForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return the {@link NonterminalSymbolSet}.
   */
  public NonterminalSymbolSet getNonterminalSymbolSet ()
  {
    return this.gui.terminalPanelForm.getNonterminalSymbolSet ();
  }


  /**
   * Returns the start symbol.
   * 
   * @return Returns the start symbol.
   */
  public NonterminalSymbol getStartSymbol ()
  {
    return this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .getParsedObject ();
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
    if ( ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .getParsedObject () == null )
        || ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .getParsedObject () == null )
        || ( this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
            .getParsedObject () == null ) )
    {
      this.gui.jGTIButtonFinished.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonFinished.setEnabled ( true );
    }
  }
}
