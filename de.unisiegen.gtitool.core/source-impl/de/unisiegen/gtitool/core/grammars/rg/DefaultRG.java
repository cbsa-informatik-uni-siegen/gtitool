package de.unisiegen.gtitool.core.grammars.rg;


import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The class for regular grammars.
 * 
 * @author Christian Fehler
 * @version $Id: DefaultDFA.java 394 2007-12-31 12:55:46Z fehler $
 */
public final class DefaultRG extends AbstractGrammar implements RG
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5814924266068717426L;


  /**
   * Returns the {@link Grammar} type.
   * 
   * @return The {@link Grammar} type.
   */
  @Override
  public final String getGrammarType ()
  {
    return "RG"; //$NON-NLS-1$
  }
}
