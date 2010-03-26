package de.unisiegen.gtitool.core.grammars.cfg;


import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The class for context free grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultCFG extends AbstractGrammar implements CFG
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5466164968184903366L;


  /**
   * Allocate a new {@link DefaultCFG}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param startSymbol The start symbol of this grammar.
   */
  public DefaultCFG ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol )
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol,
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE );
  }


  /**
   * Copy constructor
   * 
   * @param other The {@link DefaultCFG}
   * @throws NonterminalSymbolSetException
   * @throws TerminalSymbolSetException
   */
  public DefaultCFG ( final DefaultCFG other )
      throws TerminalSymbolSetException, NonterminalSymbolSetException
  {
    super ( other );
  }


  /**
   * Returns the {@link Grammar.GrammarType}.
   * 
   * @return The {@link Grammar.GrammarType}.
   */
  @Override
  public final GrammarType getGrammarType ()
  {
    return GrammarType.CFG;
  }


  /**
   * {@inheritDoc}
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public final boolean isLL1 () throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    final ParsingTable pt = new DefaultParsingTable ( this );

    pt.create ();
    for ( NonterminalSymbol ns : getNonterminalSymbolSet () )
      for ( TerminalSymbol ts : getTerminalSymbolSet () )
        if ( pt.get ( ns, ts ).size () > 1 )
          return false;
    return true;
  }
}
