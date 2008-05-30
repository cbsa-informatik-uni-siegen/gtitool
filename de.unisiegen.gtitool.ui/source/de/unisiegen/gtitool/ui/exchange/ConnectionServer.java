package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.net.ServerSocket;

import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link ConnectionServer} {@link Thread}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConnectionServer extends Connection
{

  /**
   * Allocates a new {@link ConnectionServer}.
   * 
   * @param network The {@link Network}.
   * @throws ExchangeException If the {@link Network} could not listen on the
   *           port.
   */
  public ConnectionServer ( Network network ) throws ExchangeException
  {
    super ( network );
    try
    {
      setServerSocket ( new ServerSocket ( getNetwork ().getPort () ) );
    }
    catch ( IOException exc )
    {
      close ();
      throw new ExchangeException ( Messages.getString (
          "ExchangeDialog.ExceptionListen", String.valueOf ( getNetwork () //$NON-NLS-1$
              .getPort () ) ) );
    }
  }


  /**
   * Executes the {@link ConnectionServer} {@link Thread}.
   * 
   * @see Thread#run()
   */
  @Override
  public final void run ()
  {
    // Accept the server socket connection
    try
    {
      setSocket ( getServerSocket ().accept () );
    }
    catch ( IOException exc )
    {
      close ();
      return;
    }

    // Create the streams
    createStreams ();

    // Send the public RSA key
    sendPublicKeyRSA ();

    // Receive the AES key encrypted with RSA
    receiveSecretKeyAES ();

    // Receive the file and fire the event
    Exchange exchange = receiveExchange ();
    fireExchangeReceived ( exchange );

    // Close the open connection
    close ();
  }
}
