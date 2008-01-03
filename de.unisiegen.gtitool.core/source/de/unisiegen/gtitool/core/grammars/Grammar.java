package de.unisiegen.gtitool.core.grammars;


import java.io.Serializable;


/**
 * The interface for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id: Machine.java 398 2008-01-02 23:13:39Z fehler $
 */
public interface Grammar extends Serializable
{

  /**
   * The available grammers.
   */
  public static final String [] AVAILABLE_GRAMMARS =
  { "RG", "CFG", "CSG" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$


  /**
   * Returns the <code>Grammar</code> type.
   * 
   * @return The <code>Grammar</code> type.
   */
  public String getGrammarType ();
}
