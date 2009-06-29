package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link RegexException} is used to be collected in a
 * {@link RegexValidationException}.
 * 
 * @author Simon Meurer
 * @version
 */
public abstract class RegexException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4580002477138412539L;


  /**
   * Allocates a new {@link RegexException}.
   */
  public RegexException ()
  {
    super ();
  }


  /**
   * Allocates a new {@link RegexException}.
   * 
   * @param message The detail message.
   * @param description The detail description.
   */
  public RegexException ( PrettyString message, PrettyString description )
  {
    super ( message, description );
  }
}
