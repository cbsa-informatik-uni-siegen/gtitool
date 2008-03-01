package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingUtilities;


/**
 * The {@link Connection} {@link Thread}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class Connection extends Thread
{

  /**
   * The {@link Network}.
   */
  private Network network;


  /**
   * The {@link ObjectInputStream}.
   */
  private ObjectInputStream input;


  /**
   * The {@link ObjectOutputStream}.
   */
  private ObjectOutputStream output;


  /**
   * The {@link Socket}.
   */
  private Socket socket;


  /**
   * The {@link ServerSocket}.
   */
  private ServerSocket serverSocket;


  /**
   * Allocates a new {@link Connection}.
   * 
   * @param network The {@link Network}.
   * @param socket The {@link Socket}.
   */
  public Connection ( Network network, Socket socket )
  {
    super ( "Connection-Client" ); //$NON-NLS-1$
    this.network = network;
    this.socket = socket;
  }


  /**
   * Allocates a new {@link Connection}.
   * 
   * @param network The {@link Network}.
   * @param serverSocket The {@link ServerSocket}.
   */
  public Connection ( Network network, ServerSocket serverSocket )
  {
    super ( "Connection-Server" ); //$NON-NLS-1$
    this.network = network;
    this.serverSocket = serverSocket;
  }


  /**
   * Closes the {@link Connection}.
   */
  public final void close ()
  {
    try
    {
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
      System.exit ( 1 );
    }
  }


  /**
   * Create the streams.
   */
  private final void createStreams ()
  {
    boolean first = false;
    try
    {
      int port1 = this.socket.getLocalPort ();
      int port2 = this.socket.getPort ();
      int address1 = this.socket.getLocalAddress ().hashCode ();
      int address2 = this.socket.getInetAddress ().hashCode ();
      if ( port1 < port2 )
      {
        first = true;
      }
      else if ( port1 > port2 )
      {
        first = false;
      }
      else if ( address1 < address2 )
      {
        first = true;
      }
      else if ( address1 > address2 )
      {
        first = false;
      }
      if ( first )
      {
        this.input = new ObjectInputStream ( this.socket.getInputStream () );
        this.output = new ObjectOutputStream ( this.socket.getOutputStream () );
      }
      else
      {
        this.output = new ObjectOutputStream ( this.socket.getOutputStream () );
        this.input = new ObjectInputStream ( this.socket.getInputStream () );
      }
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
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
    if ( this.socket == null )
    {
      try
      {
        this.socket = this.serverSocket.accept ();
      }
      catch ( IOException exc )
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void run ()
          {
            Connection.this.network.close ();
          }
        } );
        return;
      }
    }
    createStreams ();
    SwingUtilities.invokeLater ( new Runnable ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void run ()
      {
        Connection.this.network.fireNetworkConnected ();
      }
    } );
    while ( true )
    {
      Exchange tmpExchange = null;
      try
      {
        tmpExchange = ( Exchange ) Connection.this.input.readObject ();
      }
      catch ( IOException exc )
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void run ()
          {
            Connection.this.network.close ();
          }
        } );
        return;
      }
      catch ( ClassNotFoundException exc )
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void run ()
          {
            Connection.this.network.close ();
          }
        } );
        return;
      }
      final Exchange exchange = tmpExchange;

      SwingUtilities.invokeLater ( new Runnable ()
      {

        @SuppressWarnings ( "synthetic-access" )
        public void run ()
        {
          Connection.this.network.fireExchangeReceived ( exchange );
        }
      } );
    }
  }


  /**
   * Sends the given {@link Exchange} to the {@link ObjectOutputStream}.
   * 
   * @param exchange The {@link Exchange} to send.
   */
  public final void send ( Exchange exchange )
  {
    try
    {
      this.output.writeObject ( exchange );
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
