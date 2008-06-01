package de.unisiegen.gtitool.core.grammars.rg;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The class for regular grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultRG extends AbstractGrammar implements RG
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5814924266068717426L;


  /**
   * Allocate a new {@link DefaultRG}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param startSymbol The start symbol of this grammar.
   */
  public DefaultRG ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol )
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol,
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE,
        ValidationElement.GRAMMAR_NOT_REGULAR );
  }


  /**
   * Returns the {@link Grammar.GrammarType}.
   * 
   * @return The {@link Grammar.GrammarType}.
   */
  @Override
  public final GrammarType getGrammarType ()
  {
    return GrammarType.RG;
  }
}
