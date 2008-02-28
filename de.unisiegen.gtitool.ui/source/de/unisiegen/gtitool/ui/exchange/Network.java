package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.event.EventListenerList;


/**
 * The {@link Network}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Network
{

  /**
   * The {@link Connection}.
   */
  private Connection connection;


  /**
   * The used host.
   */
  private String host = null;


  /**
   * The used port.
   */
  private int port;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link ServerSocket}.
   */
  private ServerSocket serverSocket;


  /**
   * Allocates a new {@link Network}.
   * 
   * @param host The used host.
   * @param port The used port.
   */
  public Network ( String host, int port )
  {
    this.host = host;
    this.port = port;
  }


  /**
   * Adds the given {@link ExchangeReceivedListener}.
   * 
   * @param listener The {@link ExchangeReceivedListener}.
   */
  public final synchronized void addExchangeReceivedListener (
      ExchangeReceivedListener listener )
  {
    this.listenerList.add ( ExchangeReceivedListener.class, listener );
  }


  /**
   * Adds the given {@link NetworkConnectedListener}.
   * 
   * @param listener The {@link NetworkConnectedListener}.
   */
  public final synchronized void addNetworkConnectedListener (
      NetworkConnectedListener listener )
  {
    this.listenerList.add ( NetworkConnectedListener.class, listener );
  }


  /**
   * Closes the {@link Network}.
   */
  public final void close ()
  {
    if ( this.connection != null )
    {
      this.connection.close ();
      this.connection = null;
    }
  }


  /**
   * Connect the {@link Network} to the server.
   */
  public final void connect ()
  {
    if ( this.host == null )
    {
      throw new RuntimeException ( "host is null" ); //$NON-NLS-1$
    }
    try
    {
      Socket socket = new Socket ( this.host, this.port );
      this.connection = new Connection ( this, socket );
      this.connection.start ();
    }
    catch ( UnknownHostException exc )
    {
      exc.printStackTrace ();
      close ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      close ();
    }
  }


  /**
   * Let the listeners know that a {@link Exchange} was received.
   * 
   * @param exchange The received {@link Exchange}.
   */
  public final void fireExchangeReceived ( Exchange exchange )
  {
    ExchangeReceivedListener [] listeners = this.listenerList
        .getListeners ( ExchangeReceivedListener.class );
    for ( ExchangeReceivedListener current : listeners )
    {
      current.exchangeReceived ( exchange );
    }
  }


  /**
   * Let the listeners know that the {@link Network} is connected.
   */
  public final void fireNetworkConnected ()
  {
    NetworkConnectedListener [] listeners = this.listenerList
        .getListeners ( NetworkConnectedListener.class );
    for ( NetworkConnectedListener current : listeners )
    {
      current.networkConnected ();
    }
  }


  /**
   * Returns the host.
   * 
   * @return The host.
   * @see #host
   */
  public final String getHost ()
  {
    return this.host;
  }


  /**
   * Returns the port.
   * 
   * @return The port.
   * @see #port
   */
  public final int getPort ()
  {
    return this.port;
  }


  /**
   * Listen for incoming {@link Connection}s.
   */
  public final void listen ()
  {
    try
    {
      this.serverSocket = new ServerSocket ( this.port );
      this.connection = new Connection ( this, this.serverSocket );
      this.connection.start ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Removes the given {@link ExchangeReceivedListener}.
   * 
   * @param listener The {@link ExchangeReceivedListener}.
   */
  public final synchronized void removeExchangeReceivedListener (
      ExchangeReceivedListener listener )
  {
    this.listenerList.remove ( ExchangeReceivedListener.class, listener );
  }


  /**
   * Removes the given {@link NetworkConnectedListener}.
   * 
   * @param listener The {@link NetworkConnectedListener}.
   */
  public final synchronized void removeNetworkConnectedListener (
      NetworkConnectedListener listener )
  {
    this.listenerList.remove ( NetworkConnectedListener.class, listener );
  }


  /**
   * Sends the given {@link Exchange}.
   * 
   * @param exchange The {@link Exchange} to send.
   */
  public final void send ( Exchange exchange )
  {
    if ( this.connection != null )
    {
      this.connection.send ( exchange );
    }
    else
    {
      throw new RuntimeException ( "not connected" ); //$NON-NLS-1$
    }
  }
}
