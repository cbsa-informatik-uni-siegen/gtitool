package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The interface for the input entities {@link Machine}, {@link Grammar} and
 * {@link Regex}.
 * 
 * @author Christian Fehler
 * @version $Id$
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
