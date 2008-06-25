package de.unisiegen.gtitool.core.exceptions.grammar;


import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link GrammarException} is used to be collected in a
 * {@link GrammarValidationException}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public abstract class GrammarException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4580002477138412539L;


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
  public GrammarException ( PrettyString message, PrettyString description )
  {
    super ( message, description );
  }
}
