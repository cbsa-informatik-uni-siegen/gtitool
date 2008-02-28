package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.exchange.ExchangeReceivedListener;
import de.unisiegen.gtitool.ui.exchange.Network;
import de.unisiegen.gtitool.ui.exchange.NetworkConnectedListener;
import de.unisiegen.gtitool.ui.netbeans.ExchangeDialogForm;


/**
 * The {@link ExchangeDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ExchangeDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( ExchangeDialog.class );


  /**
   * The used port.
   */
  private static final int PORT = 44444;


  /**
   * The {@link ExchangeDialogForm}.
   */
  private ExchangeDialogForm gui;


  /**
   * The {@link MainWindow}.
   */
  private MainWindow mainWindow;


  /**
   * The server {@link Network}.
   */
  private Network networkServer = null;


  /**
   * The client {@link Network}.
   */
  private Network networkClient = null;


  /**
   * The {@link Element}.
   */
  private Element element;


  /**
   * Allocates a new {@link ExchangeDialog}.
   * 
   * @param mainWindow The {@link MainWindow}.
   * @param element The {@link Element}.
   */
  public ExchangeDialog ( MainWindow mainWindow, Element element )
  {
    logger.debug ( "allocate a new exchange dialog" ); //$NON-NLS-1$
    this.mainWindow = mainWindow;
    this.element = element;
    this.gui = new ExchangeDialogForm ( this, this.mainWindow.getGui () );

    // No file to send is opened
    if ( element == null )
    {
      this.gui.jTextFieldHost.setEnabled ( false );
      this.gui.jTextFieldHost.setBackground ( new Color ( 240, 240, 240 ) );
      this.gui.jGTIButtonSend.setEnabled ( false );
    }
  }


  /**
   * Appends the given message.
   * 
   * @param message The message to append.
   */
  private final void appendMessage ( String message )
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    this.gui.jTextPaneStatus.setText ( this.gui.jTextPaneStatus.getText ()
        + message + lineBreak );
  }


  /**
   * Closes the {@link ExchangeDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handle close" ); //$NON-NLS-1$

    if ( this.networkServer != null )
    {
      this.networkServer.close ();
      this.networkServer = null;
    }
    if ( this.networkClient != null )
    {
      this.networkClient.close ();
      this.networkClient = null;
    }
    this.gui.dispose ();
  }


  /**
   * Handles the receive.
   */
  public final void handleReceive ()
  {
    if ( this.networkServer != null )
    {
      this.networkServer.close ();
    }
    this.networkServer = new Network ( null, PORT );
    this.networkServer.listen ();

    this.networkServer
        .addExchangeReceivedListener ( new ExchangeReceivedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void exchangeReceived ( Exchange exchange )
          {
            appendMessage ( "*Received*" ); //$NON-NLS-1$
            ExchangeDialog.this.mainWindow.handleNew ( exchange.getElement () );
          }
        } );
    this.gui.jGTIButtonReceive.setEnabled ( false );
    appendMessage ( "*Listening on port*: " + PORT ); //$NON-NLS-1$
  }


  /**
   * Handles the send.
   */
  public final void handleSend ()
  {
    if ( this.networkClient != null )
    {
      this.networkClient.close ();
    }
    this.networkClient = new Network ( this.gui.jTextFieldHost.getText (), PORT );
    this.networkClient.connect ();

    this.networkClient
        .addNetworkConnectedListener ( new NetworkConnectedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void networkConnected ()
          {
            ExchangeDialog.this.networkClient.send ( new Exchange (
                ExchangeDialog.this.element ) );
            appendMessage ( "*Sending to: *: " //$NON-NLS-1$
                + ExchangeDialog.this.gui.jTextFieldHost.getText () + "/" //$NON-NLS-1$
                + PORT );
          }
        } );
    this.gui.jGTIButtonSend.setEnabled ( false );
  }


  /**
   * Shows the {@link ExchangeDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the exchange dialog" ); //$NON-NLS-1$
    int x = this.mainWindow.getGui ().getBounds ().x
        + ( this.mainWindow.getGui ().getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.mainWindow.getGui ().getBounds ().y
        + ( this.mainWindow.getGui ().getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
