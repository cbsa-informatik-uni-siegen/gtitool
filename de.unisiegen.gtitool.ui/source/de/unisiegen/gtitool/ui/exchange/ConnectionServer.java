package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.net.ServerSocket;

import de.unisiegen.gtitool.ui.Messages;


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
      this.serverSocket = new ServerSocket ( getNetwork ().getPort () );
    }
    catch ( IOException exc )
    {
      closeNetwork ();
      throw new ExchangeException ( Messages.getString (
          "ExchangeDialog.ExceptionListen", String.valueOf ( getNetwork () //$NON-NLS-1$
              .getPort () ) ) );
    }
  }


  /**
   * The {@link ServerSocket}.
   */
  private ServerSocket serverSocket = null;


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
      setSocket ( this.serverSocket.accept () );
    }
    catch ( IOException exc )
    {
      closeNetwork ();
      return;
    }

    // Create the streams
    createStreams ();

    // Receive the file and fire the event
    Exchange exchange = receive ();
    fireExchangeReceived ( exchange );
  }
}
