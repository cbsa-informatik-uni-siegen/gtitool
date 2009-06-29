package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link NonterminalSymbol} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface NonterminalSymbol extends Entity < NonterminalSymbol >,
    ProductionWordMember, Storable
{

  /**
   * Returns the name of this {@link NonterminalSymbol}.
   * 
   * @return The name of this {@link NonterminalSymbol}.
   */
  public String getName ();


  /**
   * Returns true if this {@link NonterminalSymbol} is a error
   * {@link NonterminalSymbol}, otherwise false.
   * 
   * @return True if this {@link NonterminalSymbol} is a error
   *         {@link NonterminalSymbol}, otherwise false.
   */
  public boolean isError ();


  /**
   * Returns true if this {@link NonterminalSymbol} is a highlighted
   * {@link NonterminalSymbol}, otherwise false.
   * 
   * @return True if this {@link NonterminalSymbol} is a highlighted
   *         {@link NonterminalSymbol}, otherwise false.
   */
  public boolean isHighlighted ();


  /**
   * Returns true if this {@link NonterminalSymbol} is a start
   * {@link NonterminalSymbol}, otherwise false.
   * 
   * @return True if this {@link NonterminalSymbol} is a start
   *         {@link NonterminalSymbol}, otherwise false.
   */
  public boolean isStart ();


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   */
  public void setError ( boolean error );


  /**
   * Sets the start value.
   * 
   * @param start The start value to set.
   */
  public void setStart ( boolean start );


  /**
   * Sets the highlighted value.
   * 
   * @param highlighted The highlighted value to set.
   */
  public void setHighlighted ( boolean highlighted );
}
