package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ItemEvent;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.exchange.ExchangeException;
import de.unisiegen.gtitool.ui.exchange.ExchangeReceivedListener;
import de.unisiegen.gtitool.ui.exchange.Network;
import de.unisiegen.gtitool.ui.exchange.NetworkConnectedListener;
import de.unisiegen.gtitool.ui.netbeans.ExchangeDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


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

    this.gui.jGTITextFieldPort.setText ( String.valueOf ( PreferenceManager
        .getInstance ().getPort () ) );
    this.gui.jGTITextFieldHost.setText ( PreferenceManager.getInstance ()
        .getHost () );

    setNormalMode ( true );
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
   * Returns the port.
   * 
   * @return The port.
   */
  private final int getPort ()
  {
    int port = -1;
    try
    {
      port = Integer.parseInt ( this.gui.jGTITextFieldPort.getText () );
    }
    catch ( NumberFormatException exc )
    {
      // Do nothing
    }
    return port;
  }


  /**
   * Handles the cancel event.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handle cancel" ); //$NON-NLS-1$
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

    setNormalMode ( true );

    appendMessage ( Messages.getString ( "ExchangeDialog.ExchangeCanceled" ) ); //$NON-NLS-1$
  }


  /**
   * Handles the close event.
   */
  public final void handleClose ()
  {
    logger.debug ( "handle close" ); //$NON-NLS-1$
    this.gui.setVisible ( false );

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

    PreferenceManager.getInstance ().setPort ( getPort () );
    PreferenceManager.getInstance ().setHost (
        this.gui.jGTITextFieldHost.getText () );

    this.gui.dispose ();
  }


  /**
   * Handles the execute.
   */
  public final void handleExecute ()
  {
    if ( this.gui.jRadioButtonReceive.isSelected () )
    {
      handleReceive ();
    }
    else
    {
      handleSend ();
    }
  }


  /**
   * Handles the item state changed.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handleItemStateChanged ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      setNormalMode ( true );
    }
  }


  /**
   * Handles the receive.
   */
  private final void handleReceive ()
  {
    setNormalMode ( false );

    if ( this.networkServer != null )
    {
      this.networkServer.close ();
    }
    this.networkServer = new Network ( null, getPort () );
    try
    {
      this.networkServer.listen ();
      this.networkServer
          .addExchangeReceivedListener ( new ExchangeReceivedListener ()
          {

            @SuppressWarnings ( "synthetic-access" )
            public void exchangeReceived ( Exchange exchange )
            {
              appendMessage ( Messages
                  .getString ( "ExchangeDialog.ReceiveFile" ) ); //$NON-NLS-1$
              ExchangeDialog.this.mainWindow
                  .handleNew ( exchange.getElement () );
              
              // Close the network
              ExchangeDialog.this.networkServer.close ();
              ExchangeDialog.this.networkServer = null;
              setNormalMode ( true );
            }
          } );

      appendMessage ( Messages.getString ( "ExchangeDialog.Listening", String //$NON-NLS-1$
          .valueOf ( getPort () ) ) );
    }
    catch ( ExchangeException exc )
    {
      appendMessage ( exc.getMessage () );

      setNormalMode ( true );
    }
  }


  /**
   * Handles the send.
   */
  private final void handleSend ()
  {
    setNormalMode ( false );

    if ( this.networkClient != null )
    {
      this.networkClient.close ();
    }
    this.networkClient = new Network ( this.gui.jGTITextFieldHost.getText (),
        getPort () );
    try
    {
      this.networkClient.connect ();
      this.networkClient
          .addNetworkConnectedListener ( new NetworkConnectedListener ()
          {

            @SuppressWarnings ( "synthetic-access" )
            public void networkConnected ()
            {
              ExchangeDialog.this.networkClient.send ( new Exchange (
                  ExchangeDialog.this.element ) );
              appendMessage ( Messages.getString ( "ExchangeDialog.Sending", //$NON-NLS-1$
                  ExchangeDialog.this.gui.jGTITextFieldHost.getText () ) );

              // Close the network
              ExchangeDialog.this.networkClient.close ();
              ExchangeDialog.this.networkClient = null;
              setNormalMode ( true );
            }
          } );
    }
    catch ( ExchangeException exc )
    {
      appendMessage ( exc.getMessage () );
      if ( this.networkClient != null )
      {
        this.networkClient.close ();
        this.networkClient = null;
      }

      setNormalMode ( true );
    }
  }


  /**
   * Sets the normal button mode.
   * 
   * @param enabled The enable flag.
   */
  private final void setNormalMode ( boolean enabled )
  {
    this.gui.jRadioButtonReceive.setEnabled ( enabled );
    this.gui.jRadioButtonSend.setEnabled ( enabled && ( this.element != null ) );
    this.gui.jGTITextFieldPort.setEnabled ( enabled );
    this.gui.jGTITextFieldHost.setEnabled ( enabled && ( this.element != null )
        && ( this.gui.jRadioButtonSend.isSelected () ) );
    this.gui.jGTIButtonExecute.setEnabled ( enabled );
    this.gui.jGTIButtonCancel.setEnabled ( !enabled );
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
