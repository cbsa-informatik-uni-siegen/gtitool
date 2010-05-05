package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultProductionWordSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.ProductionWordSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.productionword.ProductionWordParseable;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link ProductionWord} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledProductionWordParserPanel.java 532 2008-02-04 23:54:55Z
 *          fehler $
 */
public final class StyledProductionWordParserPanel extends
    StyledParserPanel < ProductionWordSet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2385625169180684832L;


  /**
   * Every parsed symbol in this set is a {@link NonterminalSymbol}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet = null;


  /**
   * Every parsed symbol in this set is a {@link TerminalSymbol}.
   */
  private TerminalSymbolSet terminalSymbolSet = null;


  /**
   * The start {@link NonterminalSymbol}.
   */
  private NonterminalSymbol startNonterminalSymbol;


  /**
   * Allocates a new {@link StyledProductionWordParserPanel}.
   */
  public StyledProductionWordParserPanel ()
  {
    super ( new ProductionWordParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final ProductionWordSet checkParsedObject (
      final ProductionWordSet productionWordSet )
  {
    if ( this.nonterminalSymbolSet == null )
    {
      throw new RuntimeException ( "nonterminal symbol set is not set" ); //$NON-NLS-1$
    }
    if ( this.terminalSymbolSet == null )
    {
      throw new RuntimeException ( "terminal symbol set is not set" ); //$NON-NLS-1$
    }

    if ( productionWordSet == null )
      return null;

    ProductionWordSet newProductionWordSet = null;

    final ArrayList < ProductionWord > wordList = new ArrayList < ProductionWord > ();

    final ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    for ( ProductionWord productionWord : productionWordSet )
    {
      final ArrayList < ProductionWordMember > memberList = new ArrayList < ProductionWordMember > ();
      for ( ProductionWordMember current : productionWord )
      {
        // Nonterminal
        boolean foundNonterminal = false;
        nonterminalLoop : for ( NonterminalSymbol currentNonterminal : this.nonterminalSymbolSet )
        {
          if ( current.getName ().equals ( currentNonterminal.getName () ) )
          {
            foundNonterminal = true;
            NonterminalSymbol newNonterminalSymbol = new DefaultNonterminalSymbol (
                current.getName () );
            newNonterminalSymbol.setParserOffset ( current.getParserOffset () );
            memberList.add ( newNonterminalSymbol );
            break nonterminalLoop;
          }
        }
        // Terminal
        boolean foundTerminal = false;
        terminalLoop : for ( TerminalSymbol currentTerminal : this.terminalSymbolSet )
        {
          if ( current.getName ().equals ( currentTerminal.getName () ) )
          {
            foundTerminal = true;
            TerminalSymbol newTerminalSymbol = new DefaultTerminalSymbol (
                current.getName () );
            newTerminalSymbol.setParserOffset ( current.getParserOffset () );
            memberList.add ( newTerminalSymbol );
            break terminalLoop;
          }
        }

        if ( ( !foundNonterminal ) && ( !foundTerminal ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getPrettyString ( "ProductionWord.SymbolNotFound", //$NON-NLS-1$
                  current.toPrettyString () ).toHTMLString () ) );
        }
      }
      wordList.add ( new DefaultProductionWord ( memberList ) );
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    newProductionWordSet = new DefaultProductionWordSet ( wordList );
    newProductionWordSet
        .setParserOffset ( productionWordSet.getParserOffset () );

    return newProductionWordSet;
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. Every parsed symbol in this set is a
   * {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    if ( this.terminalSymbolSet != null )
    {
      for ( TerminalSymbol currentTerminal : this.terminalSymbolSet )
      {
        for ( NonterminalSymbol currentNonterminal : nonterminalSymbolSet )
        {
          if ( currentTerminal.getName ().equals (
              currentNonterminal.getName () ) )
          {
            throw new RuntimeException (
                "nonterminals and terminals are not disjunct: " + currentTerminal.getName () ); //$NON-NLS-1$
          }
        }
      }
    }
    this.nonterminalSymbolSet = nonterminalSymbolSet;

    // Set the overwritten style
    clearOverwrittenStyle ( Style.NONTERMINAL_SYMBOL );
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      addOverwrittenStyle ( current.getName (), Style.NONTERMINAL_SYMBOL );
    }
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
   * Sets the {@link TerminalSymbolSet}. Every parsed symbol in this set is a
   * {@link TerminalSymbol}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} to set.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    if ( this.nonterminalSymbolSet != null )
    {
      for ( NonterminalSymbol currentNonterminal : this.nonterminalSymbolSet )
      {
        for ( TerminalSymbol currentTerminal : terminalSymbolSet )
        {
          if ( currentNonterminal.getName ().equals (
              currentTerminal.getName () ) )
          {
            throw new RuntimeException (
                "nonterminals and terminals are not disjunct: " + currentNonterminal.getName () ); //$NON-NLS-1$
          }
        }
      }
    }
    this.terminalSymbolSet = terminalSymbolSet;

    // Set the overwritten style
    clearOverwrittenStyle ( Style.TERMINAL_SYMBOL );
    for ( TerminalSymbol current : this.terminalSymbolSet )
    {
      addOverwrittenStyle ( current.getName (), Style.TERMINAL_SYMBOL );
    }
  }
}
