package de.unisiegen.gtitool.ui.exchange.encryption;


import java.math.BigInteger;
import java.security.Key;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;


/**
 * An implementation of the {@link RSAPublicKey} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class RSAPublicKeyImpl extends AbstractKeyImpl implements
    RSAPublicKey
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2021692918171305129L;


  /**
   * The modulus.
   */
  private BigInteger modulus;


  /**
   * The public exponent.
   */
  private BigInteger publicExponent;


  /**
   * Allocates a new {@link RSAPublicKeyImpl}.
   * 
   * @param modulus The modulus.
   * @param publicExponent The public exponent.
   */
  public RSAPublicKeyImpl ( BigInteger modulus, BigInteger publicExponent )
  {
    this.modulus = modulus;
    this.publicExponent = publicExponent;
  }


  /**
   * Allocates a new {@link RSAPublicKeyImpl}.
   * 
   * @param encoded The encoded bytes.
   */
  public RSAPublicKeyImpl ( byte [] encoded )
  {
    byte [] lengthModulusByte = new byte [ 4 ];
    byte [] lengthPublicExponentByte = new byte [ 4 ];
    System.arraycopy ( encoded, 0, lengthModulusByte, 0, 4 );
    System.arraycopy ( encoded, 4, lengthPublicExponentByte, 0, 4 );

    int lengthModulus = getIntValue ( lengthModulusByte );
    int lengthPublicExponent = getIntValue ( lengthPublicExponentByte );

    byte [] modulusByte = new byte [ lengthModulus ];
    byte [] publicExponentByte = new byte [ lengthPublicExponent ];

    System.arraycopy ( encoded, 8, modulusByte, 0, lengthModulus );
    System.arraycopy ( encoded, 8 + lengthModulus, publicExponentByte, 0,
        lengthPublicExponent );

    this.modulus = new BigInteger ( modulusByte );
    this.publicExponent = new BigInteger ( publicExponentByte );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractKeyImpl#calculateEncoded()
   */
  @Override
  protected final byte [] calculateEncoded ()
  {
    byte [] modulusByte = this.modulus.toByteArray ();
    byte [] publicExponentByte = this.publicExponent.toByteArray ();
    byte [] result = new byte [ 8 + modulusByte.length
        + publicExponentByte.length ];

    System.arraycopy ( getByteValue ( modulusByte.length ), 0, result, 0, 4 );
    System.arraycopy ( getByteValue ( publicExponentByte.length ), 0, result,
        4, 4 );
    System.arraycopy ( modulusByte, 0, result, 8, modulusByte.length );
    System.arraycopy ( publicExponentByte, 0, result, 8 + modulusByte.length,
        publicExponentByte.length );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Key#getAlgorithm()
   */
  public final String getAlgorithm ()
  {
    return "RSA"; //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see RSAKey#getModulus()
   */
  public final BigInteger getModulus ()
  {
    return this.modulus;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RSAPublicKey#getPublicExponent()
   */
  public final BigInteger getPublicExponent ()
  {
    return this.publicExponent;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.modulus + " | " + this.publicExponent; //$NON-NLS-1$
  }
}
