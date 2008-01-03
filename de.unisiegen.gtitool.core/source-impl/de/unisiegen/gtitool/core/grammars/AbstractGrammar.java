package de.unisiegen.gtitool.core.grammars;


/**
 * The abstract class for all grammars.
 * 
 * @author Christian Fehler
 * @version $Id: AbstractMachine.java 398 2008-01-02 23:13:39Z fehler $
 */
public abstract class AbstractGrammar implements Grammar
{

  /**
   * Returns the <code>Grammar</code> type.
   * 
   * @return The <code>Grammar</code> type.
   */
  public abstract String getGrammarType ();
}
