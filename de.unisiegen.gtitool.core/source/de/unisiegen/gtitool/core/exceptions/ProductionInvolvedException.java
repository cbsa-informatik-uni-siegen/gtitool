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
   * Returns the {@link Production} list.
   * 
   * @return The {@link Production} list.
   */
  public ArrayList < Production > getProduction ();
}
