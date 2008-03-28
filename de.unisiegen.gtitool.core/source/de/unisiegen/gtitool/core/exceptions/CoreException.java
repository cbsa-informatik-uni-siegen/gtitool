package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link CoreException} is as parent class for all other exceptions in the
 * core project.
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
    WARNING,

    /**
     * The collection type.
     */
    COLLECTION;
  }


  /**
   * The detail message
   */
  private String message;


  /**
   * The detail description.
   */
  private PrettyString description;


  /**
   * Allocates a new {@link CoreException}.
   */
  public CoreException ()
  {
    super ();
  }


  /**
   * Allocates a new {@link CoreException}.
   * 
   * @param message The detail message.
   * @param description The detail description.
   */
  public CoreException ( String message, PrettyString description )
  {
    // Message
    if ( message == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = message;
    // Description
    if ( description == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = description;
  }


  /**
   * Returns the detail description.
   * 
   * @return The detail description.
   * @see #description
   */
  public final PrettyString getDescription ()
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
   * @param description The description to set.
   */
  protected final void setDescription ( PrettyString description )
  {
    if ( description == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = description;
  }


  /**
   * Sets the detail message.
   * 
   * @param message The message to set.
   */
  protected final void setMessage ( String message )
  {
    if ( message == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = message;
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
