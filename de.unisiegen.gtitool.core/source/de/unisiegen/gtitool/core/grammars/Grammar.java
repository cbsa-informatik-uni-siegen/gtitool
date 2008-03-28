package de.unisiegen.gtitool.core.grammars;


import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The interface for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Grammar extends Serializable, TableModel, Modifyable
{
  
  /**
   * This enum is used to indicate which validation elements should be checked
   * during a validation.
   * 
   * @author Benjamin Mies
   */
  public enum ValidationElement
  {
    /**
     * There are duplicate {@link Production}s.
     */
    DUPLICATE_PRODUCTION,

    /**
     * There is a {@link NonterminalSymbol} which is not reachable.
     */
    NONTERMINAL_NOT_REACHABLE,
  }


  /**
   * The available grammers.
   */
  public static final String [] AVAILABLE_GRAMMARS =
  { "RG", "CFG" }; //$NON-NLS-1$//$NON-NLS-2$


  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  public String getGrammarType ();


  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return the {@link NonterminalSymbolSet}.
   */
  public NonterminalSymbolSet getNonterminalSymbolSet ();


  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return the {@link TerminalSymbolSet}.
   */
  public TerminalSymbolSet getTerminalSymbolSet ();


  /**
   * Add a new production to this grammar.
   * 
   * @param production The {@link Production}:
   */
  public void addProduction ( Production production );


  /**
   * Remove a new production from this grammar.
   * 
   * @param production The {@link Production}:
   */
  public void removeProduction ( Production production );


  /**
   * Get a specified production.
   * 
   * @param index the index of the production.
   * @return the specified {@link Production}.
   */
  public Production getProductionAt ( int index );


  /**
   * Get all {@link Production}s of this grammar.
   * 
   * @return all {@link Production}s of this grammar.
   */
  public ArrayList < Production > getProductions ();


  /**
   * Returns true if the given {@link NonterminalSymbol} can be removed from the
   * {@link NonterminalSymbolSet} of this {@link Grammar}, otherwise false.
   * 
   * @param symbol The {@link NonterminalSymbol} which should be checked.
   * @return True if the given {@link NonterminalSymbol} can be removed from the
   *         {@link NonterminalSymbolSet} of this {@link Grammar}, otherwise
   *         false.
   */
  public boolean isSymbolRemoveableFromNonterminalSymbols (
      NonterminalSymbol symbol );


  /**
   * Returns true if the given {@link TerminalSymbol} can be removed from the
   * {@link TerminalSymbolSet} of this {@link Grammar}, otherwise false.
   * 
   * @param symbol The {@link TerminalSymbol} which should be checked.
   * @return True if the given {@link TerminalSymbol} can be removed from the
   *         {@link TerminalSymbolSet} of this {@link Grammar}, otherwise
   *         false.
   */
  public boolean isSymbolRemoveableFromTerminalSymbols ( TerminalSymbol symbol );
  
  /**
   * Validates that everything in the {@link Grammar} is correct.
   * 
   * @throws GrammarValidationException If the validation fails.
   */
  public void validate () throws GrammarValidationException;
}
