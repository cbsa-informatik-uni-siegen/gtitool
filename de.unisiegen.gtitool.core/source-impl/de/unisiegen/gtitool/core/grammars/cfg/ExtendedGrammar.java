package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
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
 * TODO
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
   */
  public ExtendedGrammar ( final NonterminalSymbolSet nonterminalSymbolSet,
      final TerminalSymbolSet terminalSymbolSet,
      final NonterminalSymbol startSymbol )
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol,
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE );

    // TODO: find a unique symbol here!
    this.setStartSymbol ( new DefaultNonterminalSymbol ( startSymbol
        .toString ()
        + "'" ) );

    this.startProduction = new DefaultProduction ( this.getStartSymbol (),
        new DefaultProductionWord ( startSymbol ) );

    this.addProduction ( this.startProduction );
  }


  protected ExtendedGrammar ( final Grammar grammar )
  {
    super ( grammar.getNonterminalSymbolSet (),
        grammar.getTerminalSymbolSet (), grammar.getStartSymbol (),
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE ); // TODO!

    for ( Production prod : grammar.getProduction () )
    { 
      this.addProduction ( prod );
      
      if(prod.getNonterminalSymbol().equals ( this.getStartSymbol() ))
      {
        if(this.startProduction != null)
        {
          System.err.println("Multiple start productions!"); //$NON-NLS-1$
          System.exit ( 1 );
        }
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
   * TODO
   * 
   * @return
   */
  public Production getStartProduction ()
  {
    return this.startProduction;
  }


  /**
   * TODO
   * 
   * @return
   * @throws AlphabetException
   */
  public Alphabet getAlphabet () throws AlphabetException
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    for ( TerminalSymbol symbol : this.getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    for ( NonterminalSymbol symbol : this.getNonterminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    return new DefaultAlphabet ( symbols );
  }


  private Production startProduction;
}
