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
  public DefaultRG ( NonterminalSymbolSet nonterminalSymbolSet, TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol ){
    super(nonterminalSymbolSet, terminalSymbolSet, startSymbol);
  }

  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  @Override
  public final String getGrammarType ()
  {
    return "RG"; //$NON-NLS-1$
  }
}
