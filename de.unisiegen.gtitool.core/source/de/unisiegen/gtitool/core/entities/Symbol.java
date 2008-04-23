package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link Symbol} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Symbol extends Entity < Symbol >, Storable
{

  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public Symbol clone ();


  /**
   * Returns the name of this {@link Symbol}.
   * 
   * @return The name of this {@link Symbol}.
   */
  public String getName ();


  /**
   * Returns true if this {@link Symbol} is a active {@link Symbol}, otherwise
   * false.
   * 
   * @return True if this {@link Symbol} is a active {@link Symbol}, otherwise
   *         false.
   */
  public boolean isActive ();


  /**
   * Returns true if this {@link Symbol} is a error {@link Symbol}, otherwise
   * false.
   * 
   * @return True if this {@link Symbol} is a error {@link Symbol}, otherwise
   *         false.
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
