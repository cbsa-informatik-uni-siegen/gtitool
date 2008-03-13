package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.SwingUtilities;

import sun.security.rsa.RSAPublicKeyImpl;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.ui.storage.Storage;


/**
 * The {@link Connection} {@link Thread}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class Connection extends Thread
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
   * The {@link Network}.
   */
  private Network network;


  /**
   * The {@link InputStream}.
   */
  private InputStream input;


  /**
   * The {@link OutputStream}.
   */
  private OutputStream output;


  /**
   * The {@link Socket}.
   */
  private Socket socket = null;


  /**
   * The {@link ServerSocket}.
   */
  private ServerSocket serverSocket = null;


  /**
   * The rsa {@link PublicKey}.
   */
  private PublicKey publicKeyRSA = null;


  /**
   * The RSA {@link PrivateKey}.
   */
  private PrivateKey privateKeyRSA = null;


  /**
   * The AES {@link SecretKey}.
   */
  private SecretKey secretKeyAES = null;


  /**
   * Allocates a new {@link Connection}.
   * 
   * @param network The {@link Network}.
   */
  public Connection ( Network network )
  {
    super ( "Connection" ); //$NON-NLS-1$
    this.network = network;
  }


  /**
   * Closes the {@link Connection}.
   */
  public final void close ()
  {
    try
    {
      if ( this.serverSocket != null )
      {
        this.serverSocket.close ();
        this.serverSocket = null;
      }
      if ( this.socket != null )
      {
        this.socket.close ();
        this.socket = null;
      }
      if ( this.input != null )
      {
        this.input.close ();
        this.input = null;
      }
      if ( this.output != null )
      {
        this.output.close ();
        this.output = null;
      }
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Closes the {@link Network}.
   */
  protected final void closeNetwork ()
  {
    SwingUtilities.invokeLater ( new Runnable ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void run ()
      {
        Connection.this.network.close ();
      }
    } );
  }


  /**
   * Create the streams.
   */
  protected final void createStreams ()
  {
    try
    {
      this.input = this.socket.getInputStream ();
      this.output = this.socket.getOutputStream ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Decryptes a byte array with the {@link PrivateKey}.
   * 
   * @param cipherText The ciphertext.
   * @return The decrypted ciphertext.
   * @throws NoSuchPaddingException Padding wrong.
   * @throws NoSuchAlgorithmException Algorithm not found.
   * @throws InvalidKeyException Key wrong.
   * @throws BadPaddingException Padding wrong.
   * @throws IllegalBlockSizeException Block size is wrong.
   */
  private final byte [] decryptAES ( byte [] cipherText )
      throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {
    SecretKeySpec secretKeySpec = new SecretKeySpec ( this.secretKeyAES
        .getEncoded (), AES );
    Cipher cipher = Cipher.getInstance ( AES );
    cipher.init ( Cipher.DECRYPT_MODE, secretKeySpec );
    return cipher.doFinal ( cipherText );
  }


  /**
   * Decryptes a byte array with the {@link PrivateKey}.
   * 
   * @param cipherText The ciphertext.
   * @return The decrypted ciphertext.
   * @throws NoSuchPaddingException Padding wrong.
   * @throws NoSuchAlgorithmException Algorithm not found.
   * @throws InvalidKeyException Key wrong.
   * @throws BadPaddingException Padding wrong.
   * @throws IllegalBlockSizeException Block size is wrong.
   */
  private final byte [] decryptRSA ( byte [] cipherText )
      throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {
    Cipher cipher = Cipher.getInstance ( RSA );
    cipher.init ( Cipher.DECRYPT_MODE, this.privateKeyRSA );
    return cipher.doFinal ( cipherText );
  }


  /**
   * Encryptes a byte array with the {@link PublicKey}.
   * 
   * @param plainText The plaintext.
   * @return The encrypted plaintext.
   * @throws NoSuchPaddingException Padding wrong.
   * @throws NoSuchAlgorithmException Algorithm not found.
   * @throws InvalidKeyException Key wrong.
   * @throws BadPaddingException Padding wrong.
   * @throws IllegalBlockSizeException Block size is wrong.
   */
  private final byte [] encryptAES ( byte [] plainText )
      throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {
    SecretKeySpec secretKeySpec = new SecretKeySpec ( this.secretKeyAES
        .getEncoded (), AES );
    Cipher cipher = Cipher.getInstance ( AES );
    cipher.init ( Cipher.ENCRYPT_MODE, secretKeySpec );
    return cipher.doFinal ( plainText );
  }


  /**
   * Encryptes a byte array with the {@link PublicKey}.
   * 
   * @param plainText The plaintext.
   * @return The encrypted plaintext.
   * @throws NoSuchPaddingException Padding wrong.
   * @throws NoSuchAlgorithmException Algorithm not found.
   * @throws InvalidKeyException Key wrong.
   * @throws BadPaddingException Padding wrong.
   * @throws IllegalBlockSizeException Block size is wrong.
   */
  private final byte [] encryptRSA ( byte [] plainText )
      throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {
    Cipher cipher = Cipher.getInstance ( RSA );
    cipher.init ( Cipher.ENCRYPT_MODE, this.publicKeyRSA );
    return cipher.doFinal ( plainText );
  }


  /**
   * Let the listeners know that a {@link Exchange} was received.
   * 
   * @param exchange The received {@link Exchange}.
   */
  protected final void fireExchangeReceived ( final Exchange exchange )
  {
    SwingUtilities.invokeLater ( new Runnable ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void run ()
      {
        Connection.this.network.fireExchangeReceived ( exchange );
      }
    } );
  }


  /**
   * Let the listeners know that the {@link Network} is connected.
   */
  protected final void fireNetworkConnected ()
  {
    SwingUtilities.invokeLater ( new Runnable ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void run ()
      {
        Connection.this.network.fireNetworkConnected ();
      }
    } );
  }


  /**
   * Returns the byte value.
   * 
   * @param intValue The input int.
   * @return The byte value.
   */
  private final byte [] getByteValue ( int intValue )
  {
    byte [] bytes = new byte [ 4 ];
    bytes [ 0 ] = ( byte ) ( ( intValue >> 0 ) & 255 );
    bytes [ 1 ] = ( byte ) ( ( intValue >> 8 ) & 255 );
    bytes [ 2 ] = ( byte ) ( ( intValue >> 16 ) & 255 );
    bytes [ 3 ] = ( byte ) ( ( intValue >> 24 ) & 255 );
    return bytes;
  }


  /**
   * Returns the int value.
   * 
   * @param bytes The input bytes.
   * @return The byte value.
   */
  private final int getIntValue ( byte [] bytes )
  {
    if ( bytes.length != 4 )
    {
      throw new IllegalArgumentException ( "must have length 4" ); //$NON-NLS-1$
    }
    return ( bytes [ 0 ] & 255 ) | ( ( bytes [ 1 ] & 255 ) << 8 )
        | ( ( bytes [ 2 ] & 255 ) << 16 ) | ( ( bytes [ 3 ] & 255 ) << 24 );
  }


  /**
   * Returns the {@link Network}.
   * 
   * @return The {@link Network}.
   * @see #network
   */
  protected final Network getNetwork ()
  {
    return this.network;
  }


  /**
   * Returns the {@link ServerSocket}.
   * 
   * @return The {@link ServerSocket}.
   * @see #serverSocket
   */
  protected final ServerSocket getServerSocket ()
  {
    return this.serverSocket;
  }


  /**
   * Returns the {@link Socket}.
   * 
   * @return The {@link Socket}.
   * @see #socket
   */
  protected final Socket getSocket ()
  {
    return this.socket;
  }


  /**
   * Receives the {@link Exchange}.
   * 
   * @return The {@link Exchange}.
   */
  protected final Exchange receiveExchange ()
  {
    try
    {
      // Description length
      byte [] descriptionLengthBytes = new byte [ 4 ];
      this.input.read ( descriptionLengthBytes, 0, 4 );
      int descriptionLength = getIntValue ( descriptionLengthBytes );

      // Description
      byte [] descriptionBytes = new byte [ descriptionLength ];
      this.input.read ( descriptionBytes, 0, descriptionLength );
      descriptionBytes = decryptAES ( descriptionBytes );
      String description = new String ( descriptionBytes );

      // Element length
      byte [] elementLengthBytes = new byte [ 4 ];
      this.input.read ( elementLengthBytes, 0, 4 );
      int elementLength = getIntValue ( elementLengthBytes );

      // Element
      byte [] elementBytes = new byte [ elementLength ];
      this.input.read ( elementBytes, 0, elementLength );
      elementBytes = decryptAES ( elementBytes );
      Element element = Storage.getInstance ().load (
          new String ( elementBytes ) );

      return new Exchange ( element, description );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
      return null;
    }
  }


  /**
   * Receives the RSA {@link PublicKey}.
   */
  protected final void receivePublicKeyRSA ()
  {
    try
    {
      byte [] publicKeyLengthBytes = new byte [ 4 ];
      this.input.read ( publicKeyLengthBytes, 0, 4 );
      int publicKeyLength = getIntValue ( publicKeyLengthBytes );

      byte [] publicKeyBytes = new byte [ publicKeyLength ];
      Connection.this.input.read ( publicKeyBytes, 0, publicKeyLength );
      this.publicKeyRSA = new RSAPublicKeyImpl ( publicKeyBytes );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Receives the AES {@link SecretKey}.
   */
  protected final void receiveSecretKeyAES ()
  {
    try
    {
      byte [] secretKeyLengthBytes = new byte [ 4 ];
      this.input.read ( secretKeyLengthBytes, 0, 4 );
      int secretKeyLength = getIntValue ( secretKeyLengthBytes );

      byte [] secretKeyBytes = new byte [ secretKeyLength ];
      Connection.this.input.read ( secretKeyBytes, 0, secretKeyLength );
      secretKeyBytes = decryptRSA ( secretKeyBytes );
      this.secretKeyAES = new SecretKeySpec ( secretKeyBytes, AES );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Sends the given {@link Exchange} to the {@link OutputStream}.
   * 
   * @param exchange The {@link Exchange} to send.
   */
  public final void sendExchange ( Exchange exchange )
  {
    if ( this.secretKeyAES == null )
    {
      throw new RuntimeException ( "secret AES key not received" ); //$NON-NLS-1$
    }
    try
    {
      // Description
      String description = exchange.getDescription ();
      byte [] descriptionBytes = description.getBytes ( ENCODING );
      descriptionBytes = encryptAES ( descriptionBytes );
      this.output.write ( getByteValue ( descriptionBytes.length ), 0, 4 );
      this.output.write ( descriptionBytes, 0, descriptionBytes.length );

      // Element
      String element = exchange.getElement ().getStoreString ();
      byte [] elementBytes = element.getBytes ( ENCODING );
      elementBytes = encryptAES ( elementBytes );
      this.output.write ( getByteValue ( elementBytes.length ), 0, 4 );
      this.output.write ( elementBytes, 0, elementBytes.length );

      // Flush
      this.output.flush ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Sends the given RSA {@link PublicKey} to the {@link OutputStream}.
   */
  protected final void sendPublicKeyRSA ()
  {
    try
    {
      // Create keys
      KeyPair keyPair = null;
      try
      {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ( RSA );
        keyPairGenerator.initialize ( RSA_KEY_LENGTH );
        keyPair = keyPairGenerator.genKeyPair ();

        // Save the private key
        this.privateKeyRSA = keyPair.getPrivate ();
      }
      catch ( NoSuchAlgorithmException exc )
      {
        closeNetwork ();
        return;
      }

      // PublicKey
      byte [] publicKeyBytes = keyPair.getPublic ().getEncoded ();
      this.output.write ( getByteValue ( publicKeyBytes.length ), 0, 4 );
      this.output.write ( publicKeyBytes, 0, publicKeyBytes.length );

      // Flush
      this.output.flush ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Sends the given AES {@link SecretKey} to the {@link OutputStream}.
   */
  protected final void sendSecretKeyAES ()
  {
    try
    {
      // Create keys
      try
      {
        KeyGenerator keyGenerator = KeyGenerator.getInstance ( AES );
        keyGenerator.init ( AES_KEY_LENGTH );
        this.secretKeyAES = keyGenerator.generateKey ();
      }
      catch ( NoSuchAlgorithmException exc )
      {
        closeNetwork ();
        return;
      }

      // SecretKey
      byte [] secretKeyBytes = this.secretKeyAES.getEncoded ();
      secretKeyBytes = encryptRSA ( secretKeyBytes );
      this.output.write ( getByteValue ( secretKeyBytes.length ), 0, 4 );
      this.output.write ( secretKeyBytes, 0, secretKeyBytes.length );

      // Flush
      this.output.flush ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      closeNetwork ();
    }
  }


  /**
   * Sets the {@link ServerSocket}.
   * 
   * @param serverSocket The {@link ServerSocket} to set.
   * @see #serverSocket
   */
  protected final void setServerSocket ( ServerSocket serverSocket )
  {
    this.serverSocket = serverSocket;
  }


  /**
   * Sets the {@link Socket}.
   * 
   * @param socket The {@link Socket} to set.
   * @see #socket
   */
  protected final void setSocket ( Socket socket )
  {
    this.socket = socket;
  }
}
