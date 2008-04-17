package de.unisiegen.gtitool.ui.exchange.encryption;


import java.math.BigInteger;
import java.security.Key;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;


/**
 * An implementation of the {@link RSAPrivateKey} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class RSAPrivateKeyImpl extends AbstractKeyImpl implements
    RSAPrivateKey
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1374564797452932874L;


  /**
   * The modulus.
   */
  private BigInteger modulus;


  /**
   * The private exponent.
   */
  private BigInteger privateExponent;


  /**
   * Allocates a new {@link RSAPrivateKeyImpl}.
   * 
   * @param modulus The modulus.
   * @param privateExponent The private exponent.
   */
  public RSAPrivateKeyImpl ( BigInteger modulus, BigInteger privateExponent )
  {
    this.modulus = modulus;
    this.privateExponent = privateExponent;
  }


  /**
   * Allocates a new {@link RSAPrivateKeyImpl}.
   * 
   * @param encoded The encoded bytes.
   */
  public RSAPrivateKeyImpl ( byte [] encoded )
  {
    byte [] lengthModulusByte = new byte [ 4 ];
    byte [] lengthPrivateExponentByte = new byte [ 4 ];
    System.arraycopy ( encoded, 0, lengthModulusByte, 0, 4 );
    System.arraycopy ( encoded, 4, lengthPrivateExponentByte, 0, 4 );

    int lengthModulus = getIntValue ( lengthModulusByte );
    int lengthPrivateExponent = getIntValue ( lengthPrivateExponentByte );

    byte [] modulusByte = new byte [ lengthModulus ];
    byte [] privateExponentByte = new byte [ lengthPrivateExponent ];

    System.arraycopy ( encoded, 8, modulusByte, 0, lengthModulus );
    System.arraycopy ( encoded, 8 + lengthModulus, privateExponentByte, 0,
        lengthPrivateExponent );

    this.modulus = new BigInteger ( modulusByte );
    this.privateExponent = new BigInteger ( privateExponentByte );
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
    byte [] privateExponentByte = this.privateExponent.toByteArray ();
    byte [] result = new byte [ 8 + modulusByte.length
        + privateExponentByte.length ];

    System.arraycopy ( getByteValue ( modulusByte.length ), 0, result, 0, 4 );
    System.arraycopy ( getByteValue ( privateExponentByte.length ), 0, result,
        4, 4 );
    System.arraycopy ( modulusByte, 0, result, 8, modulusByte.length );
    System.arraycopy ( privateExponentByte, 0, result, 8 + modulusByte.length,
        privateExponentByte.length );
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
   * @see RSAPrivateKey#getPrivateExponent()
   */
  public final BigInteger getPrivateExponent ()
  {
    return this.privateExponent;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.modulus + " | " + this.privateExponent; //$NON-NLS-1$
  }
}
