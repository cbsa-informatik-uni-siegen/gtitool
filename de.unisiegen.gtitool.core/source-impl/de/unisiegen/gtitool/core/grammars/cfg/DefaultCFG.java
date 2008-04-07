package de.unisiegen.gtitool.core.grammars.cfg;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
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
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  @Override
  public final String getGrammarType ()
  {
    return "CFG"; //$NON-NLS-1$
  }
}
