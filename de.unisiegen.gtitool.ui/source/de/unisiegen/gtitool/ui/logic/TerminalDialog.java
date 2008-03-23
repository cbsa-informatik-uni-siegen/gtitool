package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
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
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.ui.Messages;
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
   * The {@link TerminalDialogForm}.
   */
  private TerminalDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link Grammar} of this dialog.
   */
  private Grammar grammar;


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
   * Returns the set of not removeable {@link NonterminalSymbol}s.
   * 
   * @param newNonterminalSymbolSet The new {@link NonterminalSymbolSet}.
   * @return The set of not removeable {@link NonterminalSymbol}s.
   */
  private final TreeSet < NonterminalSymbol > getNotRemoveableSymbolsFromNonterminals (
      NonterminalSymbolSet newNonterminalSymbolSet )
  {
    if ( newNonterminalSymbolSet == null )
    {
      throw new IllegalArgumentException ( "new nonterminalSymbolSet is null" ); //$NON-NLS-1$
    }
    TreeSet < NonterminalSymbol > notRemoveableSymbols = new TreeSet < NonterminalSymbol > ();
    TreeSet < NonterminalSymbol > symbolsToRemove = new TreeSet < NonterminalSymbol > ();
    for ( NonterminalSymbol current : this.grammar.getNonterminalSymbolSet () )
    {
      if ( !newNonterminalSymbolSet.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    for ( NonterminalSymbol current : symbolsToRemove )
    {
      if ( !this.grammar.isSymbolRemoveableFromNonterminalSymbols ( current ) )
      {
        notRemoveableSymbols.add ( current );
      }
    }
    return notRemoveableSymbols;
  }


  /**
   * Returns the set of not removeable {@link TerminalSymbolSet}s.
   * 
   * @param newTerminalSymbolSet The new {@link TerminalSymbolSet}.
   * @return The set of not removeable {@link TerminalSymbolSet}s.
   */
  private final TreeSet < TerminalSymbol > getNotRemoveableSymbolsFromTerminals (
      TerminalSymbolSet newTerminalSymbolSet )
  {
    if ( newTerminalSymbolSet == null )
    {
      throw new IllegalArgumentException ( "new nonterminalSymbolSet is null" ); //$NON-NLS-1$
    }
    TreeSet < TerminalSymbol > notRemoveableSymbols = new TreeSet < TerminalSymbol > ();
    TreeSet < TerminalSymbol > symbolsToRemove = new TreeSet < TerminalSymbol > ();
    for ( TerminalSymbol current : this.grammar.getTerminalSymbolSet () )
    {
      if ( !newTerminalSymbolSet.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    for ( TerminalSymbol current : symbolsToRemove )
    {
      if ( !this.grammar.isSymbolRemoveableFromTerminalSymbols ( current ) )
      {
        notRemoveableSymbols.add ( current );
      }
    }
    return notRemoveableSymbols;
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
    boolean buttonOkEnabled = true;

    this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .clearException ();
    if ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .getTerminalSymbolSet () == null )
    {
      buttonOkEnabled = false;
    }
    else
    {
      TreeSet < TerminalSymbol > notRemoveableSymbolsFromTerminalSymbols = getNotRemoveableSymbolsFromTerminals ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
          .getTerminalSymbolSet () );
      if ( notRemoveableSymbolsFromTerminalSymbols.size () > 0 )
      {
        buttonOkEnabled = false;
        ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
        for ( TerminalSymbol current : notRemoveableSymbolsFromTerminalSymbols )
        {
          exceptionList.add ( new ParserException ( 0, 0, Messages.getString (
              "TerminalPanel.SymbolUsed", current ) ) ); //$NON-NLS-1$
        }
        this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .setException ( exceptionList );
      }
    }

    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .clearException ();
    if ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .getNonterminalSymbolSet () == null )
    {
      buttonOkEnabled = false;
    }
    else
    {
      TreeSet < NonterminalSymbol > notRemoveableSymbolsFromNonterminalSymbols = getNotRemoveableSymbolsFromNonterminals ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
          .getNonterminalSymbolSet () );
      if ( notRemoveableSymbolsFromNonterminalSymbols.size () > 0 )
      {
        buttonOkEnabled = false;
        ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
        for ( NonterminalSymbol current : notRemoveableSymbolsFromNonterminalSymbols )
        {
          exceptionList.add ( new ParserException ( 0, 0, Messages.getString (
              "TerminalPanel.SymbolUsed", current ) ) ); //$NON-NLS-1$
        }
        this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
            .setException ( exceptionList );
      }
    }

    this.gui.jGTIButtonOk.setEnabled ( buttonOkEnabled );
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
