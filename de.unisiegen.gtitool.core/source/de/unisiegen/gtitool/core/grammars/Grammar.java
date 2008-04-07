package de.unisiegen.gtitool.core.grammars;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

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
   * Add a new production to this grammar.
   * 
   * @param production The {@link Production}:
   */
  public void addProduction ( Production production );


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
   * Returns the {@link NonterminalSymbol}s which are not removeable from the
   * {@link NonterminalSymbolSet}.
   * 
   * @return The {@link NonterminalSymbol}s which are not removeable from the
   *         {@link NonterminalSymbolSet}.
   */
  public TreeSet < NonterminalSymbol > getNotRemoveableNonterminalSymbolsFromNonterminalSymbol ();


  /**
   * Returns the {@link TerminalSymbol}s which are not removeable from the
   * {@link TerminalSymbolSet}.
   * 
   * @return The {@link TerminalSymbol}s which are not removeable from the
   *         {@link TerminalSymbolSet}.
   */
  public TreeSet < TerminalSymbol > getNotRemoveableTerminalSymbolsFromTerminalSymbol ();


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
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return the {@link TerminalSymbolSet}.
   */
  public TerminalSymbolSet getTerminalSymbolSet ();


  /**
   * Remove a new production from this grammar.
   * 
   * @param production The {@link Production}:
   */
  public void removeProduction ( Production production );


  /**
   * Validates that everything in the {@link Grammar} is correct.
   * 
   * @throws GrammarValidationException If the validation fails.
   */
  public void validate () throws GrammarValidationException;


  /**
   * Returns the start symbol for this grammar.
   *
   * @return the start symbol for this grammar.
   */
  public NonterminalSymbol getStartSymbol ();
  
  /**
   * Returns the start symbol for this grammar.
   *
   * @param startSymbol the new start symbol of this grammar.
   */
  public void setStartSymbol (NonterminalSymbol startSymbol);
}
