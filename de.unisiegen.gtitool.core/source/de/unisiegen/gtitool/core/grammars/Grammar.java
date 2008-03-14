package de.unisiegen.gtitool.core.grammars;


import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
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
   * The available grammers.
   */
  public static final String [] AVAILABLE_GRAMMARS =
  { "RG", "CFG", "CSG" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$


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
   * 
   * @return the specified {@link Production}.
   */
  public Production getProductionAt ( int index );


  /**
   * Get all {@link Production}s of this grammar.
   * 
   * @return all {@link Production}s of this grammar.
   */
  public ArrayList < Production > getProductions ();
}
