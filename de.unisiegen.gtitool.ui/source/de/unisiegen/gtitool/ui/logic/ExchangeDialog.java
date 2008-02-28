package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.event.ItemEvent;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.ui.exchange.Exchange;
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

    setComponentStatus ();
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
      // TODOChristian
    }
    return port;
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

    this.gui.jRadioButtonReceive.setEnabled ( false );
    this.gui.jRadioButtonSend.setEnabled ( false );
    this.gui.jGTITextFieldPort.setEnabled ( false );
    this.gui.jGTITextFieldHost.setEnabled ( false );
    this.gui.jGTIButtonExecute.setEnabled ( false );
  }


  /**
   * Closes the {@link ExchangeDialogForm}.
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
   * Handles the item state changed.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handleItemStateChanged ( ItemEvent event )
  {
    if ( event.getStateChange () == ItemEvent.SELECTED )
    {
      setComponentStatus ();
    }
  }


  /**
   * Handles the receive.
   */
  private final void handleReceive ()
  {
    if ( this.networkServer != null )
    {
      this.networkServer.close ();
    }
    this.networkServer = new Network ( null, getPort () );
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

    appendMessage ( "*Listening on port*: " + getPort () ); //$NON-NLS-1$
  }


  /**
   * Handles the send.
   */
  private final void handleSend ()
  {
    if ( this.networkClient != null )
    {
      this.networkClient.close ();
    }
    this.networkClient = new Network ( this.gui.jGTITextFieldHost.getText (),
        getPort () );
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
                + ExchangeDialog.this.gui.jGTITextFieldHost.getText () + "/" //$NON-NLS-1$
                + getPort () );
          }
        } );
  }


  /**
   * Sets the {@link Component} status.
   */
  private final void setComponentStatus ()
  {
    // No file to send is opened
    if ( this.element == null )
    {
      this.gui.jRadioButtonSend.setEnabled ( false );
      this.gui.jGTITextFieldHost.setEnabled ( false );
      return;
    }

    // Receive
    if ( this.gui.jRadioButtonReceive.isSelected () )
    {
      this.gui.jGTITextFieldHost.setEnabled ( false );
    }
    // Send
    else
    {
      this.gui.jGTITextFieldHost.setEnabled ( true );
    }
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
