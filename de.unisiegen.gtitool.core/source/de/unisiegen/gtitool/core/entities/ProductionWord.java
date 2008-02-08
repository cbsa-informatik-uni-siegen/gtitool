package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link ProductionWord} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ProductionWord extends Entity, Storable, Modifyable,
    Iterable < Symbol >
{

  /**
   * Appends the specified {@link Symbol}s to the end of this
   * {@link ProductionWord}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link ProductionWord}.
   */
  public void add ( Iterable < Symbol > symbols );


  /**
   * Appends the specified {@link Symbol} to the end of this
   * {@link ProductionWord}.
   * 
   * @param symbol The {@link Symbol} to be appended to this
   *          {@link ProductionWord}.
   */
  public void add ( Symbol symbol );


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * {@link ProductionWord}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link ProductionWord}.
   */
  public void add ( Symbol ... symbols );


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public ProductionWord clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object other );
}
