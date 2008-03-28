package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Production;


/**
 * Involved {@link Production} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ProductionInvolvedException
{

  /**
   * Returns the {@link Production}s.
   * 
   * @return The {@link Production}s.
   */
  public ArrayList < Production > getProductions ();

}
