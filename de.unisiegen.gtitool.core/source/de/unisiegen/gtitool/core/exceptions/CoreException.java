package de.unisiegen.gtitool.core.exceptions;


/**
 * The <code>CoreException</code> is as parent class for all other exceptions
 * in the core project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class CoreException extends Exception
{

  /**
   * The error type of the exception.
   * 
   * @author Christian Fehler
   */
  public enum ErrorType
  {
    /**
     * The error type.
     */
    ERROR,

    /**
     * The warning type.
     */
    WARNING
  }


  /**
   * The detail message
   */
  private String message;


  /**
   * The detail description.
   */
  private String description;


  /**
   * Allocates a new <code>CoreException</code>.
   */
  public CoreException ()
  {
    super ();
  }


  /**
   * Allocates a new <code>CoreException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public CoreException ( String pMessage, String pDescription )
  {
    // Message
    if ( pMessage == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = pMessage;
    // Description
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = pDescription;
  }


  /**
   * Returns the detail description.
   * 
   * @return The detail description.
   * @see #description
   */
  public final String getDescription ()
  {
    return this.description;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#getLocalizedMessage()
   */
  @Override
  public final String getLocalizedMessage ()
  {
    return this.message;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#getMessage()
   */
  @Override
  public final String getMessage ()
  {
    return this.message;
  }


  /**
   * Returns the {@link ErrorType} of the exception.
   * 
   * @return The {@link ErrorType} of the exception.
   */
  public abstract ErrorType getType ();


  /**
   * Sets the detail description.
   * 
   * @param pDescription The description to set.
   */
  protected final void setDescription ( String pDescription )
  {
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = pDescription;
  }


  /**
   * Sets the detail message.
   * 
   * @param pMessage The message to set.
   */
  protected final void setMessage ( String pMessage )
  {
    if ( pMessage == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = pMessage;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Message:     " + getMessage () + lineBreak ); //$NON-NLS-1$
    result.append ( "Description: " + getDescription () ); //$NON-NLS-1$
    return result.toString ();
  }
}
