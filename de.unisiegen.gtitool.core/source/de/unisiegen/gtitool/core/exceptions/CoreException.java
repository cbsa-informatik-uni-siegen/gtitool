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
  private PrettyString message;


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
  public CoreException ( PrettyString message, PrettyString description )
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
   * {@inheritDoc}
   * 
   * @see Throwable#getLocalizedMessage()
   */
  @Override
  public final String getLocalizedMessage ()
  {
    throw new RuntimeException ( "do not use this method" ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#getMessage()
   */
  @Override
  public final String getMessage ()
  {
    throw new RuntimeException ( "do not use this method" ); //$NON-NLS-1$
  }


  /**
   * Returns the pretty description.
   * 
   * @return The pretty description.
   * @see #description
   */
  public final PrettyString getPrettyDescription ()
  {
    return this.description;
  }


  /**
   * Returns the pretty message.
   * 
   * @return The pretty message.
   * @see #message
   */
  public final PrettyString getPrettyMessage ()
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
   * Sets the pretty description.
   * 
   * @param description The pretty description to set.
   */
  protected final void setPrettyDescription ( PrettyString description )
  {
    if ( description == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = description;
  }


  /**
   * Sets the pretty message.
   * 
   * @param message The pretty message to set.
   */
  protected final void setPrettyMessage ( PrettyString message )
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
    result.append ( "Description: " + getPrettyDescription () ); //$NON-NLS-1$
    return result.toString ();
  }
}
