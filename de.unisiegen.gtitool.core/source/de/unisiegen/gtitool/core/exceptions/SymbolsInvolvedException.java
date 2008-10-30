package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * Involved {@link Symbol} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface SymbolsInvolvedException
{

  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public ArrayList < Symbol > getSymbol ();
}
