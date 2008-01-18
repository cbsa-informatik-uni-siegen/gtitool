package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Symbol</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Symbol extends ParseableEntity, Storable, Comparable < Symbol >
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

}
