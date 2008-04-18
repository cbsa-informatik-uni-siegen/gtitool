package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.entities.Production;


/**
 * Involved {@link Production} interface.
 * 
 * @author Christian Fehler
 * @version $Id: ProductionInvolvedException.java 695 2008-03-28 18:02:32Z
 *          fehler $
 */
public interface ProductionInvolvedException
{

  /**
   * Returns the {@link Production}s.
   * 
   * @return The {@link Production}s.
   */
  public Production getProduction ();

}
