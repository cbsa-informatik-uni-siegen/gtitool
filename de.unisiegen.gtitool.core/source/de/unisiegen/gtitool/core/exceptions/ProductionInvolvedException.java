package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Production;


/**
 * Involved {@link Production} interface.
 * 
 * @author Christian Fehler
 * @version $Id: MachineAllSymbolsException.java 639 2008-03-14 11:43:47Z fehler $
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
