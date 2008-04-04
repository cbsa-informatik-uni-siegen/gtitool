package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ProductionChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.production.ProductionParseable;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link Production} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledProductionParserPanel.java 532 2008-02-04 23:54:55Z
 *          fehler $
 */
public final class StyledProductionParserPanel extends StyledParserPanel
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
   * Allocates a new {@link StyledProductionParserPanel}.
   */
  public StyledProductionParserPanel ()
  {
    super ( new ProductionParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireProductionChanged ( ( Production ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link ProductionChangedListener}.
   * 
   * @param listener The {@link ProductionChangedListener}.
   */
  public final synchronized void addProductionChangedListener (
      ProductionChangedListener listener )
  {
    this.listenerList.add ( ProductionChangedListener.class, listener );
  }


  /**
   * Checks the given {@link Production}.
   * 
   * @param production The {@link Production} to check.
   * @return The input {@link Production} with the right
   *         {@link NonterminalSymbol}s and {@link TerminalSymbol}s.
   */
  private final Production checkProduction ( Production production )
  {
    if ( this.nonterminalSymbolSet == null )
    {
      throw new RuntimeException ( "nonterminal symbol set is not set" ); //$NON-NLS-1$
    }
    if ( this.terminalSymbolSet == null )
    {
      throw new RuntimeException ( "terminal symbol set is not set" ); //$NON-NLS-1$
    }

    if ( production != null )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

      /*
       * NonterminalSymbol
       */
      if ( !this.nonterminalSymbolSet.contains ( production
          .getNonterminalSymbol () ) )
      {
        exceptionList.add ( new ParserException ( production
            .getNonterminalSymbol ().getParserOffset ().getStart (), production
            .getNonterminalSymbol ().getParserOffset ().getEnd (), Messages
            .getString ( "Production.NonterminalSymbol", production //$NON-NLS-1$
                .getNonterminalSymbol ().getName () ) ) );
      }

      /*
       * ProductionWord
       */
      ProductionWord newProductionWord = null;
      try
      {
        ArrayList < ProductionWordMember > memberList = new ArrayList < ProductionWordMember > ();

        for ( ProductionWordMember current : production.getProductionWord () )
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
              newNonterminalSymbol
                  .setParserOffset ( current.getParserOffset () );
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
            exceptionList.add ( new ParserException ( current
                .getParserOffset ().getStart (), current.getParserOffset ()
                .getEnd (), Messages.getString (
                "ProductionWord.SymbolNotFound", //$NON-NLS-1$
                current.getName () ) ) );
          }
        }

        newProductionWord = new DefaultProductionWord ( memberList );
        newProductionWord.setParserOffset ( production.getProductionWord ()
            .getParserOffset () );
      }
      catch ( NonterminalSymbolException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( TerminalSymbolException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }

      if ( exceptionList.size () > 0 )
      {
        setException ( exceptionList );
        return null;
      }
      Production newProduction = new DefaultProduction ( production
          .getNonterminalSymbol (), newProductionWord );
      return newProduction;
    }
    return production;
  }


  /**
   * Let the listeners know that the {@link Production} has changed.
   * 
   * @param newProduction The new {@link Production}.
   */
  private final void fireProductionChanged ( Production newProduction )
  {
    Production checkedProduction = checkProduction ( newProduction );
    ProductionChangedListener [] listeners = this.listenerList
        .getListeners ( ProductionChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].productionChanged ( checkedProduction );
    }
  }


  /**
   * Returns the {@link Production} for the program text within the document.
   * 
   * @return The {@link Production} for the program text.
   */
  public final Production getProduction ()
  {
    try
    {
      Production productionWord = ( Production ) getParsedObject ();
      return checkProduction ( productionWord );
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final Production parse ()
  {
    Production productionWord = ( Production ) super.parse ();
    return checkProduction ( productionWord );
  }


  /**
   * Removes the given {@link ProductionChangedListener}.
   * 
   * @param listener The {@link ProductionChangedListener}.
   */
  public final synchronized void removeProductionChangedListener (
      ProductionChangedListener listener )
  {
    this.listenerList.remove ( ProductionChangedListener.class, listener );
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. Every parsed symbol in this set is
   * a {@link NonterminalSymbol}.
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
    clearOverwrittenStyle ();
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      addOverwrittenStyle ( current.getName (), Style.NONTERMINAL_SYMBOL );
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
    clearOverwrittenStyle ();
    for ( TerminalSymbol current : this.terminalSymbolSet )
    {
      addOverwrittenStyle ( current.getName (), Style.TERMINAL_SYMBOL );
    }
  }


  /**
   * Sets the {@link Production} of the document.
   * 
   * @param production The input {@link Production}.
   */
  public final void setText ( Production production )
  {
    if ( production == null )
    {
      getEditor ().setText ( "" ); //$NON-NLS-1$
    }
    else
    {
      getEditor ().setText ( production.toString () );
    }
  }
}
