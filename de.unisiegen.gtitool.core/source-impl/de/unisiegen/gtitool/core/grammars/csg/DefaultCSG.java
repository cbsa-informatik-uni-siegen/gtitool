package de.unisiegen.gtitool.core.grammars.csg;


import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The class for context sensitiv grammars.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultCSG extends AbstractGrammar implements CSG
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2216491414956042158L;


  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  @Override
  public final String getGrammarType ()
  {
    return "CSG"; //$NON-NLS-1$
  }
}
