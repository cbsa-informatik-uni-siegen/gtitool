package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.netbeans.ConfirmDialogForm;
import de.unisiegen.gtitool.ui.swing.JGTIButton;


/**
 * The {@link ConfirmDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConfirmDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( ConfirmDialog.class );


  /**
   * The {@link ConfirmDialogForm}.
   */
  private ConfirmDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The dialog is confirmed.
   */
  private boolean confirmed = false;


  /**
   * The dialog is not confirmed.
   */
  private boolean notConfirmed = false;


  /**
   * The dialog is canceled.
   */
  private boolean canceled = false;


  /**
   * Allocates a new {@link ConfirmDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param text The text of the {@link ConfirmDialog}.
   * @param title The title of the {@link ConfirmDialog}.
   * @param yesButtonVisible The yes button visible flag.
   * @param noButtonVisible The no button visible flag.
   * @param cancelButtonVisible The cancel button visible flag.
   */
  public ConfirmDialog ( JFrame parent, String text, String title,
      boolean yesButtonVisible, boolean noButtonVisible,
      boolean cancelButtonVisible )
  {
    logger.debug ( "allocate a new confirm dialog" ); //$NON-NLS-1$
    this.parent = parent;
    this.gui = new ConfirmDialogForm ( this, parent );
    this.gui.jTextAreaInfo.setCursor ( null );
    this.gui.jTextAreaInfo.setText ( text );
    this.gui.setTitle ( title );

    ArrayList < JGTIButton > buttonList = new ArrayList < JGTIButton > ();
    if ( yesButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonYes );
    }
    if ( noButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonNo );
    }
    if ( cancelButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonCancel );
    }
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonYes );
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonNo );
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonCancel );

    GridBagConstraints gridBagConstraints;

    if ( buttonList.size () == 1 )
    {
      gridBagConstraints = new GridBagConstraints ();
      gridBagConstraints.gridx = 1;
      gridBagConstraints.gridy = 1;
      gridBagConstraints.insets = new Insets ( 5, 16, 16, 16 );
      this.gui.getContentPane ()
          .add ( buttonList.get ( 0 ), gridBagConstraints );
    }
    else
    {
      for ( int i = 0 ; i < buttonList.size () ; i++ )
      {
        // The first button
        if ( i == 0 )
        {

          gridBagConstraints = new GridBagConstraints ();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = GridBagConstraints.EAST;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new Insets ( 5, 16, 16, 5 );
          this.gui.getContentPane ().add ( buttonList.get ( i ),
              gridBagConstraints );
        }
        // The last button
        else if ( i == buttonList.size () - 1 )
        {
          gridBagConstraints = new GridBagConstraints ();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = GridBagConstraints.WEST;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new Insets ( 5, 5, 16, 16 );
          this.gui.getContentPane ().add ( buttonList.get ( i ),
              gridBagConstraints );
        }
        // The button in the middle
        else
        {
          gridBagConstraints = new GridBagConstraints ();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.insets = new Insets ( 5, 5, 16, 5 );
          this.gui.getContentPane ().add ( buttonList.get ( i ),
              gridBagConstraints );
        }
      }
    }
    this.gui.pack ();
  }


  /**
   * Cancels the {@link ConfirmDialogForm}.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handle cancel" ); //$NON-NLS-1$
    this.canceled = true;
    this.gui.dispose ();
  }


  /**
   * Corfims the {@link ConfirmDialogForm}.
   */
  public final void handleConfirm ()
  {
    logger.debug ( "handle confirm" ); //$NON-NLS-1$
    this.confirmed = true;
    this.gui.dispose ();
  }


  /**
   * Cancels the {@link ConfirmDialogForm}.
   */
  public final void handleNotConfirm ()
  {
    logger.debug ( "handle not confirm" ); //$NON-NLS-1$
    this.notConfirmed = true;
    this.gui.dispose ();
  }


  /**
   * Returns the canceled value.
   * 
   * @return The canceled value.
   * @see #canceled
   */
  public final boolean isCanceled ()
  {
    return this.canceled;
  }


  /**
   * Returns the confirmed value.
   * 
   * @return The confirmed value.
   * @see #confirmed
   */
  public final boolean isConfirmed ()
  {
    return this.confirmed;
  }


  /**
   * Returns the not confirmed value.
   * 
   * @return The not confirmed value.
   * @see #notConfirmed
   */
  public final boolean isNotConfirmed ()
  {
    return this.notConfirmed;
  }


  /**
   * Shows the {@link ConfirmDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the confirm dialog" ); //$NON-NLS-1$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
