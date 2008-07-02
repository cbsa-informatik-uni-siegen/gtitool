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
import de.unisiegen.gtitool.core.parser.nonterminalsymbolset.NonterminalSymbolSetParseable;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link NonterminalSymbolSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolSetParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledNonterminalSymbolSetParserPanel extends
    StyledParserPanel < NonterminalSymbolSet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -349502367082489237L;


  /**
   * The parsed {@link NonterminalSymbolSet} can not contain this
   * {@link NonterminalSymbol}s.
   */
  private TreeSet < NonterminalSymbol > notRemoveableNonterminalSymbols = null;


  /**
   * Every {@link NonterminalSymbol} in the {@link NonterminalSymbolSet} can not
   * be be in the {@link TerminalSymbolSet}.
   */
  private TerminalSymbolSet terminalSymbolSet = null;


  /**
   * The start {@link NonterminalSymbol}.
   */
  private NonterminalSymbol startNonterminalSymbol;


  /**
   * Allocates a new {@link StyledNonterminalSymbolSetParserPanel}.
   */
  public StyledNonterminalSymbolSetParserPanel ()
  {
    super ( new NonterminalSymbolSetParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final NonterminalSymbolSet checkParsedObject (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.terminalSymbolSet != null ) && ( nonterminalSymbolSet != null ) )
    {
      for ( NonterminalSymbol currentNonterminal : nonterminalSymbolSet )
      {
        for ( TerminalSymbol currentTerminal : this.terminalSymbolSet )
        {
          if ( currentNonterminal.getName ().equals (
              currentTerminal.getName () ) )
          {
            exceptionList.add ( new ParserException ( currentNonterminal
                .getParserOffset ().getStart (), currentNonterminal
                .getParserOffset ().getEnd (), Messages.getString (
                "TerminalPanel.AlreadyTerminalSymbol", //$NON-NLS-1$
                currentTerminal.getName (), this.terminalSymbolSet ) ) );
          }
        }
      }
    }

    if ( ( this.notRemoveableNonterminalSymbols != null )
        && ( nonterminalSymbolSet != null ) )
    {
      for ( NonterminalSymbol current : this.notRemoveableNonterminalSymbols )
      {
        if ( !nonterminalSymbolSet.contains ( current ) )
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

    return nonterminalSymbolSet;
  }


  /**
   * Sets the {@link NonterminalSymbol}s which should not be removeable.
   * 
   * @param notRemoveableNonterminalSymbols The {@link NonterminalSymbol}s which
   *          should not be removeable.
   * @see #notRemoveableNonterminalSymbols
   */
  public final void setNotRemoveableNonterminalSymbols (
      TreeSet < NonterminalSymbol > notRemoveableNonterminalSymbols )
  {
    this.notRemoveableNonterminalSymbols = notRemoveableNonterminalSymbols;
  }


  /**
   * Sets the start {@link NonterminalSymbol}.
   * 
   * @param startNonterminalSymbol The start {@link NonterminalSymbol} to set.
   */
  public final void setStartNonterminalSymbol (
      NonterminalSymbol startNonterminalSymbol )
  {
    this.startNonterminalSymbol = startNonterminalSymbol;

    // Set the overwritten style
    clearOverwrittenStyle ( Style.START_NONTERMINAL_SYMBOL );
    if ( this.startNonterminalSymbol != null )
    {
      addOverwrittenStyle ( this.startNonterminalSymbol.getName (),
          Style.START_NONTERMINAL_SYMBOL );
    }
  }


  /**
   * Sets the {@link TerminalSymbolSet}. Every {@link NonterminalSymbol} in the
   * {@link NonterminalSymbolSet} can not be be in the {@link TerminalSymbolSet}
   * .
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} to set.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    this.terminalSymbolSet = terminalSymbolSet;
  }
}
