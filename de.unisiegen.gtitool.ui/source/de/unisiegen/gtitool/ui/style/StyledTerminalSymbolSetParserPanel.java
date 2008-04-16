package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.terminalsymbolset.TerminalSymbolSetParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link TerminalSymbolSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTerminalSymbolSetParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledTerminalSymbolSetParserPanel extends
    StyledParserPanel < TerminalSymbolSet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -928492541925565704L;


  /**
   * Every {@link TerminalSymbol} in the {@link TerminalSymbolSet} can not be be
   * in the {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet = null;


  /**
   * The parsed {@link TerminalSymbolSet} can not contain this
   * {@link TerminalSymbol}s.
   */
  private TreeSet < TerminalSymbol > notRemoveableTerminalSymbols = null;


  /**
   * Allocates a new {@link StyledTerminalSymbolSetParserPanel}.
   */
  public StyledTerminalSymbolSetParserPanel ()
  {
    super ( new TerminalSymbolSetParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final TerminalSymbolSet checkParsedObject (
      TerminalSymbolSet terminalSymbolSet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.nonterminalSymbolSet != null ) && ( terminalSymbolSet != null ) )
    {
      for ( TerminalSymbol currentTerminal : terminalSymbolSet )
      {
        for ( NonterminalSymbol currentNonterminal : this.nonterminalSymbolSet )
        {
          if ( currentTerminal.getName ().equals (
              currentNonterminal.getName () ) )
          {
            exceptionList.add ( new ParserException ( currentTerminal
                .getParserOffset ().getStart (), currentTerminal
                .getParserOffset ().getEnd (), Messages.getString (
                "TerminalPanel.AlreadyNonterminalSymbol", //$NON-NLS-1$
                currentTerminal.getName (), this.nonterminalSymbolSet ) ) );
          }
        }
      }
    }

    if ( ( this.notRemoveableTerminalSymbols != null )
        && ( terminalSymbolSet != null ) )
    {
      for ( TerminalSymbol current : this.notRemoveableTerminalSymbols )
      {
        if ( !terminalSymbolSet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "TerminalPanel.SymbolUsed", current ) ) ); //$NON-NLS-1$
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }
    return terminalSymbolSet;
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. Every {@link TerminalSymbol} in the
   * {@link TerminalSymbolSet} can not be be in the {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
  }


  /**
   * Sets the {@link TerminalSymbol}s which should not be removeable.
   * 
   * @param notRemoveableTerminalSymbols The {@link TerminalSymbol}s which
   *          should not be removeable.
   * @see #notRemoveableTerminalSymbols
   */
  public final void setNotRemoveableTerminalSymbols (
      TreeSet < TerminalSymbol > notRemoveableTerminalSymbols )
  {
    this.notRemoveableTerminalSymbols = notRemoveableTerminalSymbols;
  }
}
