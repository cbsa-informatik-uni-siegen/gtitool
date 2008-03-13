package de.unisiegen.gtitool.ui.exchange;


import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * The {@link Network} test class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NetworkTest
{

  /**
   * The RSA key length.
   */
  private static final int RSA_KEY_LENGTH = 1024;


  /**
   * The RSA algorithmus.
   */
  private static final String RSA = "RSA"; //$NON-NLS-1$


  /**
   * The AES key length.
   */
  private static final int AES_KEY_LENGTH = 128;


  /**
   * The AES algorithmus.
   */
  private static final String AES = "AES"; //$NON-NLS-1$


  /**
   * The encoding.
   */
  private static final String ENCODING = "UTF-8"; //$NON-NLS-1$


  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    try
    {
      if ( ( arguments.length > 0 ) && ( arguments [ 0 ].equals ( "--server" ) ) ) //$NON-NLS-1$
      {
        Network network = new Network ( "localhost", 44444 ); //$NON-NLS-1$
        network.listen ();
        System.out.println ( "Server is listening" ); //$NON-NLS-1$
      }
      else if ( ( arguments.length > 0 )
          && ( arguments [ 0 ].equals ( "--client" ) ) ) //$NON-NLS-1$
      {
        Network network = new Network ( "localhost", 44444 ); //$NON-NLS-1$
        network.connect ();
        System.out.println ( "Client is connected" ); //$NON-NLS-1$
      }
      else
      {
        System.out.println ( "Usage: --server | --client" ); //$NON-NLS-1$
      }
    }
    catch ( ExchangeException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * The AES test case.
   */
  public static void testAES ()
  {
    try
    {
      KeyGenerator keyGenerator = KeyGenerator.getInstance ( AES );
      keyGenerator.init ( AES_KEY_LENGTH );
      SecretKey secretKeyAES = keyGenerator.generateKey ();

      SecretKeySpec secretKeySpec = new SecretKeySpec ( secretKeyAES
          .getEncoded (), AES );
      Cipher cipher = Cipher.getInstance ( AES );
      cipher.init ( Cipher.ENCRYPT_MODE, secretKeySpec );
      String test = "this is a test"; //$NON-NLS-1$
      byte [] enc = cipher.doFinal ( test.getBytes ( ENCODING ) );

      SecretKeySpec secretKeySpec2 = new SecretKeySpec ( secretKeyAES
          .getEncoded (), AES );
      Cipher cipher2 = Cipher.getInstance ( AES );
      cipher2.init ( Cipher.DECRYPT_MODE, secretKeySpec2 );
      byte [] dec = cipher2.doFinal ( enc );

      System.out.println ( new String ( dec, ENCODING ) );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * The RSA test case.
   */
  public static void testRSA ()
  {
    try
    {
      KeyGenerator keyGenerator = KeyGenerator.getInstance ( AES );
      keyGenerator.init ( AES_KEY_LENGTH );
      SecretKey secretKeyAES = keyGenerator.generateKey ();

      for ( int i = 0 ; i < secretKeyAES.getEncoded ().length ; i++ )
      {
        System.out.print ( secretKeyAES.getEncoded () [ i ] + "|" ); //$NON-NLS-1$
      }
      System.out.println ();

      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ( RSA );
      keyPairGenerator.initialize ( RSA_KEY_LENGTH );
      KeyPair keyPair = keyPairGenerator.genKeyPair ();

      Cipher cipher = Cipher.getInstance ( RSA );
      cipher.init ( Cipher.ENCRYPT_MODE, keyPair.getPublic () );
      byte [] enc = cipher.doFinal ( secretKeyAES.getEncoded () );

      Cipher cipher2 = Cipher.getInstance ( RSA );
      cipher2.init ( Cipher.DECRYPT_MODE, keyPair.getPrivate () );
      byte [] dec = cipher2.doFinal ( enc );

      SecretKey key2 = new SecretKeySpec ( dec, AES );

      for ( int i = 0 ; i < key2.getEncoded ().length ; i++ )
      {
        System.out.print ( key2.getEncoded () [ i ] + "|" ); //$NON-NLS-1$
      }
      System.out.println ();
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
  }
}
