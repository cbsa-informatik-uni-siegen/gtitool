package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Symbol</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Symbol extends Entity, Storable, Comparable < Symbol >
{

  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public Symbol clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( Symbol other );


  /**
   * Returns the name of this symbol.
   * 
   * @return The name of this symbol.
   */
  public String getName ();


  /**
   * Returns true if this {@link Symbol} is a error {@link Symbol}, otherwise
   * false.
   * 
   * @return True if this {@link Symbol} is a error {@link Symbol}, otherwise
   *         false.
   */
  public boolean isError ();


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   */
  public void setError ( boolean error );

}
