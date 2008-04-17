package de.unisiegen.gtitool.ui;


import java.math.BigInteger;

import de.unisiegen.gtitool.ui.exchange.encryption.RSAPublicKeyImpl;


/**
 * This class can be used for simple test.
 * 
 * @author Christian Fehler
 */
public final class MainTest
{

  /**
   * The main method.
   * 
   * @param arguments The command line arguments.
   */
  public static void main ( String [] arguments )
  {
    new MainTest ();
  }


  /**
   * Allocates a new {@link MainTest}.
   */
  public MainTest ()
  {
    testKeys ();
  }


  /**
   * Tests the keys.
   */
  public final void testKeys ()
  {
    RSAPublicKeyImpl key1 = new RSAPublicKeyImpl ( BigInteger
        .valueOf ( 263487692 ), BigInteger.valueOf ( 892647263 ) );
    System.out.println ( key1 );

    byte [] encoded = key1.getEncoded ();

    RSAPublicKeyImpl key2 = new RSAPublicKeyImpl ( encoded );
    System.out.println ( key2 );
  }
}
