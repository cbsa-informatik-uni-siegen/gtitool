package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * The <code>AlphabetException</code> is used if the {@link Alphabet} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetException extends Exception
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8267857615201989774L;


  /**
   * The detail message
   */
  private String message;


  /**
   * The detail description.
   */
  private String description;


  /**
   * Allocates a new <code>AlphabetException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public AlphabetException ( String pMessage, String pDescription )
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
    result.append ( "Description: " + getDescription () ); //$NON-NLS-1$
    return result.toString ();
  }
}
