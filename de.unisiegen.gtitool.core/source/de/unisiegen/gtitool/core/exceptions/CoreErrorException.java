package de.unisiegen.gtitool.core.exceptions;


/**
 * The <code>CoreErrorException</code> is as parent class for all other error
 * exceptions in the core project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class CoreErrorException extends CoreException
{

  /**
   * Allocates a new <code>CoreErrorException</code>.
   */
  public CoreErrorException ()
  {
    super ();
  }


  /**
   * Allocates a new <code>CoreErrorException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public CoreErrorException ( String pMessage, String pDescription )
  {
    super ( pMessage, pDescription );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.ERROR;
  }
}
