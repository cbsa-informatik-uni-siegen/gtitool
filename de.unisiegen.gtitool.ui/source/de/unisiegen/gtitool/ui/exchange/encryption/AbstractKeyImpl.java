package de.unisiegen.gtitool.ui.exchange.encryption;


import java.security.Key;


/**
 * An abstract implementation of the {@link Key} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AbstractKeyImpl implements Key
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2125585173762329248L;


  /**
   * The encoded value.
   */
  private byte [] encoded = null;


  /**
   * Allocates a new {@link AbstractKeyImpl}.
   */
  public AbstractKeyImpl ()
  {
    // Do nothing
  }


  /**
   * Allocates a new {@link AbstractKeyImpl}.
   * 
   * @param encoded The encoded bytes.
   */
  public AbstractKeyImpl ( byte [] encoded )
  {
    this.encoded = encoded;
  }


  /**
   * Returns the calculated encoded value.
   * 
   * @return The calculated encoded value.
   */
  protected abstract byte [] calculateEncoded ();


  /**
   * Returns the byte value.
   * 
   * @param intValue The input int.
   * @return The byte value.
   */
  protected final byte [] getByteValue ( int intValue )
  {
    byte [] bytes = new byte [ 4 ];
    bytes [ 0 ] = ( byte ) ( ( intValue >> 0 ) & 255 );
    bytes [ 1 ] = ( byte ) ( ( intValue >> 8 ) & 255 );
    bytes [ 2 ] = ( byte ) ( ( intValue >> 16 ) & 255 );
    bytes [ 3 ] = ( byte ) ( ( intValue >> 24 ) & 255 );
    return bytes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Key#getEncoded()
   */
  public final byte [] getEncoded ()
  {
    if ( this.encoded == null )
    {
      this.encoded = calculateEncoded ();
    }
    return this.encoded;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Key#getFormat()
   */
  public final String getFormat ()
  {
    return "X.509"; //$NON-NLS-1$
  }


  /**
   * Returns the int value.
   * 
   * @param bytes The input bytes.
   * @return The byte value.
   */
  protected final int getIntValue ( byte [] bytes )
  {
    if ( bytes.length != 4 )
    {
      throw new IllegalArgumentException ( "must have length 4" ); //$NON-NLS-1$
    }
    return ( bytes [ 0 ] & 255 ) | ( ( bytes [ 1 ] & 255 ) << 8 )
        | ( ( bytes [ 2 ] & 255 ) << 16 ) | ( ( bytes [ 3 ] & 255 ) << 24 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    String result = ""; //$NON-NLS-1$

    boolean first = true;
    for ( byte current : this.encoded )
    {
      if ( !first )
      {
        result += "|"; //$NON-NLS-1$
      }
      first = false;
      result += String.valueOf ( current );
    }

    return result;
  }
}
