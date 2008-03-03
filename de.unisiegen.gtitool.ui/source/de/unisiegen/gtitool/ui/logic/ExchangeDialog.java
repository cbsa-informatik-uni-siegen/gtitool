package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.event.ItemEvent;
import java.io.File;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

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
   * @param file The file which is used for the description.
   */
  public ExchangeDialog ( MainWindow mainWindow, Element element, File file )
  {
    logger.debug ( "allocate a new exchange dialog" ); //$NON-NLS-1$
    this.mainWindow = mainWindow;

    this.element = element;
    this.gui = new ExchangeDialogForm ( this, this.mainWindow.getGui () );

    this.gui.jGTITextFieldPort.setText ( String.valueOf ( PreferenceManager
        .getInstance ().getPort () ) );
    this.gui.jGTITextFieldHost.setText ( PreferenceManager.getInstance ()
        .getHost () );

    if ( file != null )
    {
      this.gui.jGTITextFieldDescription.setText ( file.getName () );
    }

    this.gui.jTextPaneStatus.setEditorKit ( new StyledEditorKit () );
    this.document = new DefaultStyledDocument ();
    this.gui.jTextPaneStatus.setDocument ( this.document );

    setNormalMode ( true );
  }


  /**
   * The {@link DefaultStyledDocument}
   */
  private DefaultStyledDocument document;


  /**
   * Appends the given message.
   * 
   * @param message The message to append.
   * @param error Flag that indicates if the message should be a error message.
   */
  private final void appendMessage ( String message, boolean error )
  {
    SimpleAttributeSet set = new SimpleAttributeSet ();
    if ( error )
    {
      StyleConstants.setForeground ( set, Color.RED );
      StyleConstants.setBold ( set, true );
    }
    else
    {
      StyleConstants.setForeground ( set, Color.BLACK );
      StyleConstants.setBold ( set, true );
    }

    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    try
    {
      this.document.insertString ( this.document.getLength (), message
          + lineBreak, set );
    }
    catch ( BadLocationException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
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

    appendMessage (
        Messages.getString ( "ExchangeDialog.ExchangeCanceled" ), false ); //$NON-NLS-1$
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
   * Handles the host changed event.
   */
  public final void handleHostChanged ()
  {
    setNormalMode ( true );
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
   * Handles the port changed event.
   */
  public final void handlePortChanged ()
  {
    setNormalMode ( true );
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
    final int port = Integer.parseInt ( this.gui.jGTITextFieldPort.getText () );
    this.networkServer = new Network ( null, port );
    try
    {
      this.networkServer.listen ();
      this.networkServer
          .addExchangeReceivedListener ( new ExchangeReceivedListener ()
          {

            @SuppressWarnings ( "synthetic-access" )
            public void exchangeReceived ( Exchange exchange )
            {
              if ( exchange.getDescription ().equals ( "" ) ) //$NON-NLS-1$
              {
                appendMessage ( Messages
                    .getString ( "ExchangeDialog.ReceiveFile" ), false ); //$NON-NLS-1$
              }
              else
              {
                appendMessage ( Messages.getString (
                    "ExchangeDialog.ReceiveFileDescription", exchange //$NON-NLS-1$
                        .getDescription () ), false );
              }
              ExchangeDialog.this.mainWindow
                  .handleNew ( exchange.getElement () );

              // Close the network
              ExchangeDialog.this.networkServer.close ();
              ExchangeDialog.this.networkServer = null;
              setNormalMode ( true );

              PreferenceManager.getInstance ().setPort ( port );
            }
          } );

      appendMessage ( Messages.getString ( "ExchangeDialog.Listening", String //$NON-NLS-1$
          .valueOf ( port ) ), false );
    }
    catch ( ExchangeException exc )
    {
      appendMessage ( exc.getMessage (), true );

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
    final int port = Integer.parseInt ( this.gui.jGTITextFieldPort.getText () );
    this.networkClient = new Network ( this.gui.jGTITextFieldHost.getText (),
        port );
    try
    {
      this.networkClient.connect ();
      this.networkClient
          .addNetworkConnectedListener ( new NetworkConnectedListener ()
          {

            @SuppressWarnings ( "synthetic-access" )
            public void networkConnected ()
            {
              ExchangeDialog.this.networkClient
                  .send ( new Exchange ( ExchangeDialog.this.element,
                      ExchangeDialog.this.gui.jGTITextFieldDescription
                          .getText () ) );
              appendMessage ( Messages.getString ( "ExchangeDialog.Sending", //$NON-NLS-1$
                  ExchangeDialog.this.gui.jGTITextFieldHost.getText () ), false );

              // Close the network
              ExchangeDialog.this.networkClient.close ();
              ExchangeDialog.this.networkClient = null;
              setNormalMode ( true );

              PreferenceManager.getInstance ().setPort ( port );
              PreferenceManager.getInstance ().setHost (
                  ExchangeDialog.this.gui.jGTITextFieldHost.getText () );
            }
          } );
    }
    catch ( ExchangeException exc )
    {
      appendMessage ( exc.getMessage (), true );
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
    // Port
    boolean portOkay = true;
    int port = -1;
    try
    {
      port = Integer.parseInt ( this.gui.jGTITextFieldPort.getText () );
    }
    catch ( NumberFormatException exc )
    {
      // Do nothing
    }
    if ( ( port < 1024 ) || ( port > 65535 ) )
    {
      this.gui.jLabelPort.setForeground ( Color.RED );
      this.gui.jLabelPort.setToolTipText ( Messages
          .getString ( "ExchangeDialog.PortException" ) ); //$NON-NLS-1$
      portOkay = false;
    }
    else
    {
      this.gui.jLabelPort.setForeground ( Color.BLACK );
      this.gui.jLabelPort.setToolTipText ( null );
    }

    // Host
    boolean hostOkay = true;
    if ( ( this.gui.jGTITextFieldHost.getText ().length () == 0 )
        && ( this.gui.jRadioButtonSend.isSelected () ) )
    {
      this.gui.jLabelHost.setForeground ( Color.RED );
      this.gui.jLabelHost.setToolTipText ( Messages
          .getString ( "ExchangeDialog.HostException" ) ); //$NON-NLS-1$
      hostOkay = false;
    }
    else
    {
      this.gui.jLabelHost.setForeground ( Color.BLACK );
      this.gui.jLabelHost.setToolTipText ( null );
    }

    // Set status
    this.gui.jRadioButtonReceive.setEnabled ( enabled );
    this.gui.jRadioButtonSend.setEnabled ( enabled && ( this.element != null ) );
    this.gui.jGTITextFieldPort.setEnabled ( enabled );
    this.gui.jGTITextFieldHost.setEnabled ( enabled && ( this.element != null )
        && ( this.gui.jRadioButtonSend.isSelected () ) );
    this.gui.jGTITextFieldDescription.setEnabled ( enabled
        && ( this.element != null )
        && ( this.gui.jRadioButtonSend.isSelected () ) );
    this.gui.jGTIButtonExecute.setEnabled ( enabled && portOkay && hostOkay );
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
