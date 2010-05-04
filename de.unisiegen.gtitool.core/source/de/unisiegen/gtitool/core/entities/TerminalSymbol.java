package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link TerminalSymbol} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface TerminalSymbol extends Entity < TerminalSymbol >,
    ProductionWordMember, Storable
{

  /**
   * Returns the name of this {@link TerminalSymbol}.
   * 
   * @return The name of this {@link TerminalSymbol}.
   */
  public String getName ();


  /**
   * Returns true if this {@link TerminalSymbol} is a error
   * {@link TerminalSymbol}, otherwise false.
   * 
   * @return True if this {@link TerminalSymbol} is a error
   *         {@link TerminalSymbol}, otherwise false.
   */
  public boolean isError ();


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   */
  public void setError ( boolean error );
  
  
  /**
   * 
   * Sets this {@link TerminalSymbol} as highlighted
   *
   * @param highlighted The highlighted status
   */
  public void setHighlighted(boolean highlighted);
  
  
  /**
   * 
   * Returns if this {@link TerminalSymbol} is highlighted
   * or not
   *
   * @return true if this {@link TerminalSymbol} is highlighted
   */
  public boolean isHighlighted();
}
