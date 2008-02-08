package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link TerminalSymbol} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface TerminalSymbol extends Entity, Storable,
    Comparable < TerminalSymbol >
{

  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public TerminalSymbol clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( TerminalSymbol other );


  /**
   * Returns the name of this {@link TerminalSymbol}.
   * 
   * @return The name of this {@link TerminalSymbol}.
   */
  public String getName ();


  /**
   * Returns true if this {@link TerminalSymbol} is a active
   * {@link TerminalSymbol}, otherwise false.
   * 
   * @return True if this {@link TerminalSymbol} is a active
   *         {@link TerminalSymbol}, otherwise false.
   */
  public boolean isActive ();


  /**
   * Returns true if this {@link TerminalSymbol} is a error
   * {@link TerminalSymbol}, otherwise false.
   * 
   * @return True if this {@link TerminalSymbol} is a error
   *         {@link TerminalSymbol}, otherwise false.
   */
  public boolean isError ();


  /**
   * Sets the active value.
   * 
   * @param active The active value to set.
   */
  public void setActive ( boolean active );


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   */
  public void setError ( boolean error );
}
