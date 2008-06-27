package de.unisiegen.gtitool.core.exceptions.nonterminalsymbol;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link NonterminalSymbolException} is used if the
 * {@link NonterminalSymbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolException.java 1031 2008-06-25 16:57:06Z
 *          fehler $
 */
public abstract class NonterminalSymbolException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -9173410082582552529L;


  /**
   * Allocates a new {@link NonterminalSymbolException}.
   */
  public NonterminalSymbolException ()
  {
    super ();
  }
}
