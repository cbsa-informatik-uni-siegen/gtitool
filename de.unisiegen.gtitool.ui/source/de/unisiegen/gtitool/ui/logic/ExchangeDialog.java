package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.event.ItemEvent;
import java.io.File;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.exchange.ExchangeException;
import de.unisiegen.gtitool.ui.exchange.Network;
import de.unisiegen.gtitool.ui.exchange.listener.ExchangeFinishedListener;
import de.unisiegen.gtitool.ui.exchange.listener.ExchangeReceivedListener;
import de.unisiegen.gtitool.ui.exchange.listener.NetworkConnectedListener;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ExchangeDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The {@link ExchangeDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ExchangeDialog implements LogicClass < ExchangeDialogForm >
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( ExchangeDialog.class );


  /**
   * The {@link ExchangeDialogForm}.
   */
  protected ExchangeDialogForm gui;


  /**
   * The {@link MainWindow}.
   */
  protected MainWindow mainWindow;


  /**
   * The server {@link Network}.
   */
  protected Network networkServer = null;


  /**
   * The client {@link Network}.
   */
  protected Network networkClient = null;


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
    logger.debug ( "ExchangeDialog", "allocate a new exchange dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.mainWindow = mainWindow;

    this.element = element;
    this.gui = new ExchangeDialogForm ( this, this.mainWindow.getGUI () );

    this.gui.jGTITextFieldPort.setText ( String.valueOf ( PreferenceManager
        .getInstance ().getPort () ) );
    this.gui.jGTITextFieldHost.setText ( PreferenceManager.getInstance ()
        .getHost () );

    if ( file != null )
    {
      this.gui.jGTITextAreaDescription.setText ( file.getName () );
    }

    this.gui.jGTITextPaneStatus.setEditorKit ( new StyledEditorKit () );

    setNormalMode ( true );

    if ( PreferenceManager.getInstance ().getReceiveModus ()
        || ( this.element == null ) )
    {
      this.gui.jGTIRadioButtonReceive.setSelected ( true );
    }
    else
    {
      this.gui.jGTIRadioButtonSend.setSelected ( true );
    }
  }


  /**
   * Appends the given message.
   * 
   * @param message The message to append.
   * @param error Flag that indicates if the message should be a error message.
   */
  protected final void appendMessage ( String message, boolean error )
  {
    SimpleAttributeSet set = new SimpleAttributeSet ();
    if ( error )
    {
      StyleConstants.setForeground ( set, Color.RED );
    }
    else
    {
      StyleConstants.setForeground ( set, Color.BLACK );
    }

    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    try
    {
      StyledDocument styledDocument = ( StyledDocument ) this.gui.jGTITextPaneStatus
          .getDocument ();
      styledDocument.insertString ( styledDocument.getLength (), message
          + lineBreak, set );
    }
    catch ( BadLocationException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final ExchangeDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Handles the cancel event.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
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
    logger.debug ( "handleClose", "handle close" ); //$NON-NLS-1$ //$NON-NLS-2$
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

    PreferenceManager.getInstance ().setReceiveModus (
        this.gui.jGTIRadioButtonReceive.isSelected () );

    this.gui.dispose ();
  }


  /**
   * Handles the execute.
   */
  public final void handleExecute ()
  {
    if ( this.gui.jGTIRadioButtonReceive.isSelected () )
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
    this.networkServer = new Network ( port );
    try
    {
      this.networkServer.listen ();
      this.networkServer
          .addExchangeReceivedListener ( new ExchangeReceivedListener ()
          {

            public void exchangeReceived ( Exchange exchange )
            {
              if ( exchange.getDescription ().equals ( "" ) ) //$NON-NLS-1$
              {
                appendMessage ( Messages
                    .getString ( "ExchangeDialog.ReceiveFile" ), false ); //$NON-NLS-1$
              }
              else
              {
                appendMessage ( Messages
                    .getString ( "ExchangeDialog.ReceiveFileDescription" ), //$NON-NLS-1$
                    false );
                appendMessage ( exchange.getDescription (), false );
              }
              ExchangeDialog.this.mainWindow.handleNew (
                  exchange.getElement (), false );

              // Close the network
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
        port, new Exchange ( this.element, this.gui.jGTITextAreaDescription
            .getText () ) );
    try
    {
      this.networkClient.connect ();
      this.networkClient
          .addNetworkConnectedListener ( new NetworkConnectedListener ()
          {

            public void networkConnected ()
            {
              appendMessage ( Messages.getString ( "ExchangeDialog.Sending", //$NON-NLS-1$
                  ExchangeDialog.this.gui.jGTITextFieldHost.getText () ), false );
            }
          } );
      this.networkClient
          .addExchangeFinishedListener ( new ExchangeFinishedListener ()
          {

            public void exchangeFinished ()
            {
              appendMessage ( Messages
                  .getString ( "ExchangeDialog.ExchangeFinished" ), false ); //$NON-NLS-1$

              // Close the network
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
  protected final void setNormalMode ( boolean enabled )
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
      this.gui.jGTILabelPort.setForeground ( Color.RED );
      this.gui.jGTILabelPort.setToolTipText ( Messages
          .getString ( "ExchangeDialog.PortException" ) ); //$NON-NLS-1$
      portOkay = false;
    }
    else
    {
      this.gui.jGTILabelPort.setForeground ( Color.BLACK );
      this.gui.jGTILabelPort.setToolTipText ( null );
    }

    // Host
    boolean hostOkay = true;
    if ( ( this.gui.jGTITextFieldHost.getText ().length () == 0 )
        && ( this.gui.jGTIRadioButtonSend.isSelected () ) )
    {
      this.gui.jGTILabelHost.setForeground ( Color.RED );
      this.gui.jGTILabelHost.setToolTipText ( Messages
          .getString ( "ExchangeDialog.HostException" ) ); //$NON-NLS-1$
      hostOkay = false;
    }
    else
    {
      this.gui.jGTILabelHost.setForeground ( Color.BLACK );
      this.gui.jGTILabelHost.setToolTipText ( null );
    }

    // Set status
    this.gui.jGTIRadioButtonReceive.setEnabled ( enabled );
    this.gui.jGTIRadioButtonSend.setEnabled ( enabled
        && ( this.element != null ) );
    this.gui.jGTITextFieldPort.setEnabled ( enabled );
    this.gui.jGTITextFieldHost.setEnabled ( enabled && ( this.element != null )
        && ( this.gui.jGTIRadioButtonSend.isSelected () ) );
    this.gui.jGTITextAreaDescription.setEnabled ( enabled
        && ( this.element != null )
        && ( this.gui.jGTIRadioButtonSend.isSelected () ) );
    this.gui.jGTIButtonExecute.setEnabled ( enabled && portOkay && hostOkay );
    this.gui.jGTIButtonCancel.setEnabled ( !enabled );
  }


  /**
   * Shows the {@link ExchangeDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the exchange dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.mainWindow.getGUI ().getBounds ().x
        + ( this.mainWindow.getGUI ().getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.mainWindow.getGUI ().getBounds ().y
        + ( this.mainWindow.getGUI ().getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
