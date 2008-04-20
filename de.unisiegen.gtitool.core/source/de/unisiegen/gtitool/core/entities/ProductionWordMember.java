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


  /**
   * Returns true if this {@link NonterminalSymbol} is a error
   * {@link NonterminalSymbol}, otherwise false.
   * 
   * @return True if this {@link NonterminalSymbol} is a error
   *         {@link NonterminalSymbol}, otherwise false.
   */
  public boolean isError ();


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   */
  public void setError ( boolean error );
}
