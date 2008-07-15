package de.unisiegen.gtitool.ui.logic;


import java.util.TreeSet;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.TerminalDialogForm;
import de.unisiegen.gtitool.ui.redoundo.GrammarSymbolsChangedItem;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The logic class for the create new transition dialog.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class TerminalDialog implements LogicClass < TerminalDialogForm >
{

  /**
   * The {@link Grammar} of this dialog.
   */
  private Grammar grammar;


  /**
   * The {@link TerminalDialogForm}.
   */
  private TerminalDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link GrammarPanel}.
   */
  private GrammarPanel grammarPanel;


  /**
   * Create a new {@link TerminalDialog}
   * 
   * @param parent The parent frame.
   * @param grammarPanel The {@link GrammarPanel}.
   * @param grammar The {@link Grammar} of this dialog.
   */
  public TerminalDialog ( JFrame parent, GrammarPanel grammarPanel,
      Grammar grammar )
  {
    this.parent = parent;
    this.grammarPanel = grammarPanel;
    this.grammar = grammar;
    this.gui = new TerminalDialogForm ( this, parent );

    this.gui.terminalPanelForm.setTerminalSymbolSet ( this.grammar
        .getTerminalSymbolSet () );

    this.gui.terminalPanelForm.setNonterminalSymbolSet ( this.grammar
        .getNonterminalSymbolSet () );

    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .setNotRemoveableNonterminalSymbols ( this.grammar
            .getNotRemoveableNonterminalSymbolsFromNonterminalSymbol () );

    this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .setNotRemoveableTerminalSymbols ( this.grammar
            .getNotRemoveableTerminalSymbolsFromTerminalSymbol () );

    this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .setText ( this.grammar.getStartSymbol () );

    this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < TerminalSymbolSet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) TerminalSymbolSet newTerminalSymbolSet )
          {
            setButtonStatus ();
          }
        } );

    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbolSet > ()
        {

          public void parseableChanged (
              @SuppressWarnings ( "unused" ) NonterminalSymbolSet newNonterminalSymbolSet )
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
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final TerminalDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Handles cancel button pressed.
   */
  public final void handleCancel ()
  {
    this.gui.dispose ();
  }


  /**
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    this.gui.setVisible ( false );
    if ( !this.grammar.getNonterminalSymbolSet ().equals (
        this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
            .getParsedObject () )
        || !this.grammar.getTerminalSymbolSet ().equals (
            this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
                .getParsedObject () )
        || !this.grammar.getStartSymbol ().equals (
            this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
                .getParsedObject () ) )
    {
      this.grammarPanel
          .getRedoUndoHandler ()
          .addItem (
              new GrammarSymbolsChangedItem (
                  this.grammar,
                  this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
                      .getParsedObject (),
                  this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
                      .getParsedObject (),
                  this.grammar.getStartSymbol (),
                  this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
                      .getParsedObject () ) );
    }
    performNonterminalSymbolChange ( this.grammar.getNonterminalSymbolSet (),
        this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
            .getParsedObject () );
    performTerminalSymbolChange ( this.grammar.getTerminalSymbolSet (),
        this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .getParsedObject () );
    this.grammar
        .setStartSymbol ( this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
            .getParsedObject () );
    this.gui.dispose ();
  }


  /**
   * Preforms the {@link Symbol} change.
   * 
   * @param oldNonterminalSymbols The old {@link NonterminalSymbolSet}.
   * @param newNonterminalSymbols The new {@link NonterminalSymbolSet}.
   */
  private final void performNonterminalSymbolChange (
      NonterminalSymbolSet oldNonterminalSymbols,
      NonterminalSymbolSet newNonterminalSymbols )
  {
    TreeSet < NonterminalSymbol > symbolsToAdd = new TreeSet < NonterminalSymbol > ();
    TreeSet < NonterminalSymbol > symbolsToRemove = new TreeSet < NonterminalSymbol > ();
    for ( NonterminalSymbol current : newNonterminalSymbols )
    {
      if ( !oldNonterminalSymbols.contains ( current ) )
      {
        symbolsToAdd.add ( current );
      }
    }
    for ( NonterminalSymbol current : oldNonterminalSymbols )
    {
      if ( !newNonterminalSymbols.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    try
    {
      oldNonterminalSymbols.add ( symbolsToAdd );
      oldNonterminalSymbols.remove ( symbolsToRemove );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Performs the {@link Symbol} change.
   * 
   * @param oldTerminalSymbols The old {@link TerminalSymbolSet}.
   * @param newTerminalSymbols The new {@link TerminalSymbolSet}.
   */
  private final void performTerminalSymbolChange (
      TerminalSymbolSet oldTerminalSymbols, TerminalSymbolSet newTerminalSymbols )
  {
    TreeSet < TerminalSymbol > symbolsToAdd = new TreeSet < TerminalSymbol > ();
    TreeSet < TerminalSymbol > symbolsToRemove = new TreeSet < TerminalSymbol > ();
    for ( TerminalSymbol current : newTerminalSymbols )
    {
      if ( !oldTerminalSymbols.contains ( current ) )
      {
        symbolsToAdd.add ( current );
      }
    }
    for ( TerminalSymbol current : oldTerminalSymbols )
    {
      if ( !newTerminalSymbols.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    try
    {
      oldTerminalSymbols.add ( symbolsToAdd );
      oldTerminalSymbols.remove ( symbolsToRemove );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * Sets the status of the buttons.
   */
  public final void setButtonStatus ()
  {
    if ( ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .getParsedObject () == null )
        || ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
            .getParsedObject () == null ) )
    {
      this.gui.jGTIButtonOk.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonOk.setEnabled ( true );
    }
  }


  /**
   * Show the dialog for creating a new transition
   */
  public final void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
