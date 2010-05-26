package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The class for grammars that are extended by an extra start production to
 * implement LR parsers
 */
public class ExtendedGrammar extends AbstractGrammar implements CFG
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * TODO
   * 
   * @param nonterminalSymbolSet
   * @param terminalSymbolSet
   * @param startSymbol
   * @throws NonterminalSymbolSetException
   */
  public ExtendedGrammar ( final NonterminalSymbolSet nonterminalSymbolSet,
      final TerminalSymbolSet terminalSymbolSet,
      final NonterminalSymbol startSymbol )
      throws NonterminalSymbolSetException
  {
    super ( new DefaultNonterminalSymbolSet ( nonterminalSymbolSet ),
        new DefaultTerminalSymbolSet ( terminalSymbolSet ), startSymbol,
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE );

    System.out.println ( terminalSymbolSet + ", "
        + this.getTerminalSymbolSet () );

    NonterminalSymbol newSymbol = null;
    for ( newSymbol = new DefaultNonterminalSymbol ( startSymbol.toString ()
        + "'" ) ; getNonterminalSymbolSet ().contains ( newSymbol ) ; newSymbol = new DefaultNonterminalSymbol ( //$NON-NLS-1$
        newSymbol.toString () + "'" ) ) //$NON-NLS-1$
    {
      // no nothing
    }

    this.setStartSymbol ( newSymbol );

    this.getNonterminalSymbolSet ().add ( this.getStartSymbol () );

    this.startProduction = new DefaultProduction ( this.getStartSymbol (),
        new DefaultProductionWord ( startSymbol ) );

    this.addProduction ( this.startProduction );

    this.getTerminalSymbolSet ().addIfNonexistent (
        DefaultTerminalSymbol.EndMarker );
  }


  /**
   * Wrap an extended grammar from an already constructed grammar
   * 
   * @param grammar
   */
  protected ExtendedGrammar ( final Grammar grammar )
  {
    super ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol (),
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE );

    for ( Production prod : grammar.getProduction () )
    {
      this.addProduction ( prod );

      if ( prod.getNonterminalSymbol ().equals ( this.getStartSymbol () ) )
      {
        if ( this.startProduction != null )
          throw new RuntimeException (
              "Multiple start productions! Should not happen!" ); //$NON-NLS-1$

        this.startProduction = prod;
      }
    }
  }


  /**
   * TODO
   * 
   * @param element
   * @throws NonterminalSymbolSetException
   * @throws TerminalSymbolSetException
   * @throws StoreException
   */
  public ExtendedGrammar ( final Element element )
      throws NonterminalSymbolSetException, TerminalSymbolSetException,
      StoreException
  {
    super ( element, ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE ); // TODO:
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.grammars.AbstractGrammar#getGrammarType()
   */
  @Override
  public GrammarType getGrammarType ()
  {
    return GrammarType.CFG;
  }


  /**
   * Returns the new start production
   * 
   * @return the start production
   */
  public Production getStartProduction ()
  {
    return this.startProduction;
  }


  /**
   * Get the real start symbol
   * 
   * @return the real start symbol
   */
  public NonterminalSymbol getRealStartSymbol ()
  {
    final String symbolName = this.getStartSymbol ().getName ();

    final String newName = symbolName.substring ( 0, symbolName.length () - 1 );

    return this.getNonterminalSymbolSet ().get ( newName );
  }


  /**
   * {@inheritDoc}
   * 
   * @throws AlphabetException
   */
  @Override
  public Alphabet getAlphabet () throws AlphabetException
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    for ( TerminalSymbol symbol : this.getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    for ( NonterminalSymbol symbol : this.getNonterminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    symbols.remove ( new DefaultSymbol ( this.getStartSymbol () ) );

    return new DefaultAlphabet ( symbols );
  }


  /**
   * The extra start production
   */
  private Production startProduction;
}
