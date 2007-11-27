package de.unisiegen.gtitool.core.exceptions;


/**
 * The <code>CoreWarningException</code> is as parent class for all other
 * warning exceptions in the core project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class CoreWarningException extends CoreException
{

  /**
   * Allocates a new <code>CoreWarningException</code>.
   */
  public CoreWarningException ()
  {
    super ();
  }


  /**
   * Allocates a new <code>CoreWarningException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public CoreWarningException ( String pMessage, String pDescription )
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
    return ErrorType.WARNING;
  }
}
