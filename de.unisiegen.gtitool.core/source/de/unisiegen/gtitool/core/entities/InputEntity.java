package de.unisiegen.gtitool.core.entities;


import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;

import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The interface for the input entities {@link Machine} and {@link Grammar}.
 * 
 * @author Christian Fehler
 * @version $Id: Entity.java 846 2008-04-25 11:00:33Z fehler $
 */
public interface InputEntity
{

  /**
   * Signals the entity type.
   * 
   * @author Christian Fehler
   */
  public interface EntityType
  {
    // do nothing
  }
}
