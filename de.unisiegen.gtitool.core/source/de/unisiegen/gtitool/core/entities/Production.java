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
}
