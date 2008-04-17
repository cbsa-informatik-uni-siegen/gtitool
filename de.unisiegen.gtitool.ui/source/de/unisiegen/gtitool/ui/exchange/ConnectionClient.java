package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import de.unisiegen.gtitool.ui.Messages;


/**
 * The {@link ConnectionClient} {@link Thread}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConnectionClient extends Connection
{

  /**
   * The timeout value in ms.
   */
  private static final int TIMEOUT = 3000;


  /**
   * Allocates a new {@link ConnectionClient}.
   * 
   * @param network The {@link Network}.
   * @param exchange The {@link Exchange}.
   * @throws ExchangeException If the {@link Connection} could not be
   *           established.
   */
  public ConnectionClient ( Network network, Exchange exchange )
      throws ExchangeException
  {
    super ( network, exchange );
    try
    {
      Socket socket = new Socket ();
      socket.connect ( new InetSocketAddress ( getNetwork ().getHost (),
          getNetwork ().getPort () ), TIMEOUT );
      setSocket ( socket );
    }
    catch ( UnknownHostException exc )
    {
      close ();
      throw new ExchangeException ( Messages.getString (
          "ExchangeDialog.ExceptionConnectUnknownHost", //$NON-NLS-1$
          getNetwork ().getHost () ) );
    }
    catch ( IOException exc )
    {
      close ();
      throw new ExchangeException ( Messages.getString (
          "ExchangeDialog.ExceptionConnectServer", getNetwork ().getHost (), //$NON-NLS-1$
          String.valueOf ( getNetwork ().getPort () ) ) );
    }
  }


  /**
   * Executes the {@link Connection} {@link Thread}.
   * 
   * @see Thread#run()
   */
  @Override
  public final void run ()
  {
    // Create the streams
    createStreams ();

    // Receive the public RSA key
    receivePublicKeyRSA ();

    // Send the AES key encrypted with RSA
    sendSecretKeyAES ();

    // Fire the network connected event
    fireNetworkConnected ();

    // Send the exchange object
    send ();

    // Fire the exchange finished event
    fireExchangeFinished ();

    // Close the open connection
    close ();
  }
}
