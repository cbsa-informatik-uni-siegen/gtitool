package de.unisiegen.gtitool.core.entities;


import java.io.Serializable;


/**
 * The interface for all entities.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Entity extends Cloneable, Serializable
{

  /**
   * Creates and returns a copy of this entity.
   * 
   * @see Object#clone()
   */
  public Entity clone ();


  /**
   * Returns a hash code value for this entity.
   * 
   * @see Object#hashCode()
   */
  public int hashCode ();
}
