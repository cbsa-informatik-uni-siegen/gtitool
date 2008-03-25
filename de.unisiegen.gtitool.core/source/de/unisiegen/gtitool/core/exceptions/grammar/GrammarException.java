package de.unisiegen.gtitool.core.exceptions.grammar;


import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link GrammarException} is used to be collected in a
 * {@link GrammarValidationException}.
 * 
 * @author Benjamin Mies
 * @version $Id: MachineException.java 532 2008-02-04 23:54:55Z fehler $
 */
public abstract class GrammarException extends CoreException
{

  /**
   * Allocates a new {@link GrammarException}.
   */
  public GrammarException ()
  {
    super ();
  }


  /**
   * Allocates a new {@link GrammarException}.
   * 
   * @param message The detail message.
   * @param description The detail description.
   */
  public GrammarException ( String message, String description )
  {
    super ( message, description );
  }
}
