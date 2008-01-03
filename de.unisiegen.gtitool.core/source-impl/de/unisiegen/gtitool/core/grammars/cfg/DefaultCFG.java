package de.unisiegen.gtitool.core.grammars.cfg;


import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The class for context free grammars.
 * 
 * @author Christian Fehler
 * @version $Id: DefaultDFA.java 394 2007-12-31 12:55:46Z fehler $
 */
public final class DefaultCFG extends AbstractGrammar implements CFG
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5466164968184903366L;


  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  @Override
  public final String getGrammarType ()
  {
    return "CFG"; //$NON-NLS-1$
  }
}
