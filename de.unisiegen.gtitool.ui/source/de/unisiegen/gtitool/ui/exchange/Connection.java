package de.unisiegen.gtitool.ui.exchange;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.SwingUtilities;


/**
 * The {@link Connection} {@link Thread}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class Connection extends Thread
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
  private Socket socket = null;


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
  protected final Exchange receive ()
  {
    try
    {
      return ( Exchange ) Connection.this.input.readObject ();
    }
    catch ( Exception exc )
    {
      SwingUtilities.invokeLater ( new Runnable ()
      {

        @SuppressWarnings ( "synthetic-access" )
        public void run ()
        {
          Connection.this.network.close ();
        }
      } );
      return null;
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
