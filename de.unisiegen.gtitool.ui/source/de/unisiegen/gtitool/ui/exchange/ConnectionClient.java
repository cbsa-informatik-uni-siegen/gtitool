package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
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
   * Allocates a new {@link ConnectionClient}.
   * 
   * @param network The {@link Network}.
   * @throws ExchangeException If the {@link Connection} could not be
   *           established.
   */
  public ConnectionClient ( Network network ) throws ExchangeException
  {
    super ( network );
    try
    {
      setSocket ( new Socket ( getNetwork ().getHost (), getNetwork ()
          .getPort () ) );
    }
    catch ( UnknownHostException exc )
    {
      closeNetwork ();
      throw new ExchangeException ( Messages.getString (
          "ExchangeDialog.ExceptionConnectUnknownHost", //$NON-NLS-1$
          getNetwork ().getHost () ) );
    }
    catch ( IOException exc )
    {
      closeNetwork ();
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
    
    // Fire the event
    fireNetworkConnected ();
  }
}
