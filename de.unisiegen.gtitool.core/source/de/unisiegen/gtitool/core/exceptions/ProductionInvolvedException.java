package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Production;


/**
 * Involved {@link Production} interface.
 * 
 * @author Christian Fehler
 * @version $Id: ProductionInvolvedException.java 1372 2008-10-30 08:36:20Z
 *          fehler $
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
