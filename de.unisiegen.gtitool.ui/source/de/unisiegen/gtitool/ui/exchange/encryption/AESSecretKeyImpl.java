package de.unisiegen.gtitool.ui.exchange.encryption;


import java.security.Key;

import javax.crypto.SecretKey;


/**
 * An implementation of the {@link SecretKey} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AESSecretKeyImpl extends AbstractKeyImpl implements
    SecretKey
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8979993542162905850L;


  /**
   * Allocates a new {@link AESSecretKeyImpl}.
   * 
   * @param encoded The encoded bytes.
   */
  public AESSecretKeyImpl ( byte [] encoded )
  {
    super ( encoded );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractKeyImpl#calculateEncoded()
   */
  @Override
  protected final byte [] calculateEncoded ()
  {
    return getEncoded ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Key#getAlgorithm()
   */
  public final String getAlgorithm ()
  {
    return "AES"; //$NON-NLS-1$
  }
}
