package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link ProductionWordMember} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ProductionWordMember extends Entity, Storable
{

  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public ProductionWordMember clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object other );


  /**
   * Returns the name of this {@link ProductionWordMember}.
   * 
   * @return The name of this {@link ProductionWordMember}.
   */
  public String getName ();
}
