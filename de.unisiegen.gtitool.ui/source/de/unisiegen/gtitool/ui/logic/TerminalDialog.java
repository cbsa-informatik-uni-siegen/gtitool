package de.unisiegen.gtitool.ui.logic;


import java.util.TreeSet;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.ui.netbeans.TerminalDialogForm;


/**
 * The logic class for the create new transition dialog.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class TerminalDialog
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
   * Create a new {@link TerminalDialog}
   * 
   * @param parent The parent frame.
   * @param grammar The {@link Grammar} of this dialog.
   */
  public TerminalDialog ( JFrame parent, Grammar grammar )
  {
    this.parent = parent;
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
    this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .addTerminalSymbolSetChangedListener ( new TerminalSymbolSetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void terminalSymbolSetChanged ( @SuppressWarnings ( "unused" )
          TerminalSymbolSet newTerminalSymbolSet )
          {
            setButtonStatus ();
          }

        } );

    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .addNonterminalSymbolSetChangedListener ( new NonterminalSymbolSetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void nonterminalSymbolSetChanged (
              @SuppressWarnings ( "unused" )
              NonterminalSymbolSet newNonterminalSymbolSet )
          {
            setButtonStatus ();
          }
        } );

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
    performNonterminalSymbolChange ( this.grammar.getNonterminalSymbolSet (),
        this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
            .getNonterminalSymbolSet () );
    performTerminalSymbolChange ( this.grammar.getTerminalSymbolSet (),
        this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .getTerminalSymbolSet () );
    this.gui.dispose ();
  }


  /**
   * Preforms the {@link Alphabet} change.
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
   * Preforms the {@link Alphabet} change.
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
  private final void setButtonStatus ()
  {
    if ( ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .getTerminalSymbolSet () == null )
        || ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
            .getNonterminalSymbolSet () == null ) )
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
