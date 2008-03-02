package de.unisiegen.gtitool.core.grammars.csg;


import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The class for context sensitiv grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultCSG extends AbstractGrammar implements CSG
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2216491414956042158L;

  
  /**
   * Allocate a new {@link DefaultCSG}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   */
  public DefaultCSG ( NonterminalSymbolSet nonterminalSymbolSet, TerminalSymbolSet terminalSymbolSet ){
    super(nonterminalSymbolSet, terminalSymbolSet);
  }

  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  @Override
  public final String getGrammarType ()
  {
    return "CSG"; //$NON-NLS-1$
  }
}
