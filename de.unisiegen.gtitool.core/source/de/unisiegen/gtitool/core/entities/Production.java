package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link Production} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Production extends Entity, Storable, Modifyable,
    Comparable < Production >
{

  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  public Production clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( Production other );


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object other );


  /**
   * Returns the {@link NonterminalSymbol} for this {@link Production}
   * 
   * @return the {@link NonterminalSymbol} for this {@link Production}
   */
  public NonterminalSymbol getNonterminalSymbol ();


  /**
   * Returns the {@link ProductionWord} of this {@link Production}
   * 
   * @return the {@link ProductionWord} of this {@link Production}
   */
  public ProductionWord getProductionWord ();


  /**
   * Set the {@link NonterminalSymbol} for this {@link Production}.
   * 
   * @param nonterminalSymbol the {@link NonterminalSymbol}.
   */
  public void setNonterminalSymbol ( NonterminalSymbol nonterminalSymbol );


  /**
   * Set the {@link ProductionWord} for this {@link Production}.
   * 
   * @param productionWord the {@link ProductionWord}.
   */
  public void setProductionWord ( ProductionWord productionWord );


  /**
   * Returns true if this {@link Production} contains this
   * {@link NonterminalSymbol}, else false.
   * 
   * @param symbol The {@link NonterminalSymbol}.
   * @return true if this {@link Production} contains this
   *         {@link NonterminalSymbol}, else false.
   */
  public boolean contains ( NonterminalSymbol symbol );


  /**
   * Returns true if this {@link Production} contains this
   * {@link TerminalSymbol}, else false.
   * 
   * @param symbol The {@link TerminalSymbol}.
   * @return true if this {@link Production} contains this
   *         {@link TerminalSymbol}, else false.
   */
  public boolean contains ( TerminalSymbol symbol );
}
