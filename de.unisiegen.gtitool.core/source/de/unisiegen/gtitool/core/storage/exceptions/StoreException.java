package de.unisiegen.gtitool.core.storage.exceptions;


/**
 * The {@link StoreException} is used if the store or load fails.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StoreException extends Exception
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7430208490292655003L;


  /**
   * The detail message
   */
  private String message;


  /**
   * Allocates a new {@link StoreException}.
   * 
   * @param message The detail message.
   */
  public StoreException ( String message )
  {
    // Message
    if ( message == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = message;
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
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Message:     " + getMessage () + lineBreak ); //$NON-NLS-1$
    return result.toString ();
  }
}
