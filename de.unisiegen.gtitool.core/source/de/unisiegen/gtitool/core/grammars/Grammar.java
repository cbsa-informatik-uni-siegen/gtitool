package de.unisiegen.gtitool.core.grammars;


import java.io.Serializable;

import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The interface for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id: Machine.java 398 2008-01-02 23:13:39Z fehler $
 */
public interface Grammar extends Serializable, Modifyable
{

  /**
   * The available grammers.
   */
  public static final String [] AVAILABLE_GRAMMARS =
  { "RG", "CFG", "CSG" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$


  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  public String getGrammarType ();
}
