package de.unisiegen.gtitool.core.entities;


import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;

import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The interface for the input entities {@link Machine}, {@link Grammar} and {@link Regex}.
 * 
 * @author Christian Fehler
 * @version $Id: InputEntity.java 959 2008-06-03 21:39:09Z fehler $
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
