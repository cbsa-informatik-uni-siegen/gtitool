package de.unisiegen.gtitool.core.grammars;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.InputEntity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.rg.RG;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The interface for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Grammar extends InputEntity, Serializable, TableModel,
    Modifyable
{

  /**
   * Signals the grammar type.
   * 
   * @author Christian Fehler
   */
  public enum GrammarType implements EntityType
  {
    /**
     * The {@link RG} grammar type.
     */
    RG,

    /**
     * The {@link CFG} grammar type.
     */
    CFG;

    /**
     * The file ending.
     * 
     * @return The file ending.
     */
    public final String getFileEnding ()
    {
      return toString ().toLowerCase ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case RG :
        {
          return "RG"; //$NON-NLS-1$
        }
        case CFG :
        {
          return "CFG"; //$NON-NLS-1$
        }
      }
      throw new IllegalArgumentException ( "unsupported grammar type" ); //$NON-NLS-1$
    }
  }


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

    /**
     * The grammar is not regular.
     */
    GRAMMAR_NOT_REGULAR;
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
   * Returns the {@link GrammarType}.
   * 
   * @return The {@link GrammarType}.
   */
  public GrammarType getGrammarType ();


  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return the {@link NonterminalSymbolSet}.
   */
  public NonterminalSymbolSet getNonterminalSymbolSet ();


  /**
   * Returns the not reachable {@link NonterminalSymbol} list.
   * 
   * @return The not reachable {@link NonterminalSymbol} list.
   */
  public ArrayList < NonterminalSymbol > getNotReachableNonterminalSymbols ();


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
   * Get all {@link Production}s of this grammar.
   * 
   * @return all {@link Production}s of this grammar.
   */
  public ProductionSet getProduction ();


  /**
   * Get a specified production.
   * 
   * @param index the index of the production.
   * @return the specified {@link Production}.
   */
  public Production getProductionAt ( int index );


  /**
   * Get all {@link Production}s for a given non terminal of this grammar.
   * 
   * @param s The non terminal symbol
   * @return all {@link Production}s for a given non terminal of this grammar.
   */
  public ProductionSet getProductionForNonTerminal ( NonterminalSymbol s );


  /**
   * Returns the reachable {@link NonterminalSymbol} list.
   * 
   * @return The reachable {@link NonterminalSymbol} list.
   */
  public ArrayList < NonterminalSymbol > getReachableNonterminalSymbols ();


  /**
   * Returns the start symbol for this grammar.
   * 
   * @return the start symbol for this grammar.
   */
  public NonterminalSymbol getStartSymbol ();


  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return the {@link TerminalSymbolSet}.
   */
  public TerminalSymbolSet getTerminalSymbolSet ();


  /**
   * Remove a new {@link Production} from this grammar.
   * 
   * @param index The index of the {@link Production}:
   */
  public void removeProduction ( int index );


  /**
   * Set the {@link Production}s.
   * 
   * @param productions The new {@link Production}s.
   */
  public void setProductions ( ProductionSet productions );


  /**
   * Returns the start symbol for this grammar.
   * 
   * @param startSymbol the new start symbol of this grammar.
   */
  public void setStartSymbol ( NonterminalSymbol startSymbol );


  /**
   * Updates the start symbol flags.
   */
  public void updateStartSymbol ();


  /**
   * Validates that everything in the {@link Grammar} is correct.
   * 
   * @throws GrammarValidationException If the validation fails.
   */
  public void validate () throws GrammarValidationException;


  /**
   * calculates the first set
   * 
   * @param pw the {@link ProductionWord}
   * @return the set of symbols the Production can begin with
   * @throws GrammarInvalidNonterminalException
   */
  public FirstSet first ( final ProductionWord pw )
      throws GrammarInvalidNonterminalException;


  /**
   * calculates the first set
   * 
   * @param ns the NonterminalSymbol
   * @return the set of symbols the Production can begin with
   * @throws GrammarInvalidNonterminalException
   */
  public FirstSet first ( final NonterminalSymbol ns )
      throws GrammarInvalidNonterminalException;


  /**
   * calculates the follow set
   * 
   * @param p the Production
   * @return set of symbols following directly to the Production p
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  public TerminalSymbolSet follow ( final NonterminalSymbol p )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException;


  /**
   * Creates an Alphabet out of the grammar's Terminals
   * 
   * @return the alphabet
   * @throws AlphabetException
   */
  public Alphabet getAlphabet () throws AlphabetException;
}
