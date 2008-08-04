package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ConfirmDialogForm;
import de.unisiegen.gtitool.ui.swing.JGTIButton;


/**
 * The {@link ConfirmDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConfirmDialog implements LogicClass < ConfirmDialogForm >
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
   * The dialog is confirmed all.
   */
  private boolean confirmedAll = false;


  /**
   * The dialog is not confirmed.
   */
  private boolean notConfirmed = false;


  /**
   * The dialog is not confirmed all.
   */
  private boolean notConfirmedAll = false;


  /**
   * The dialog is canceled.
   */
  private boolean canceled = false;


  /**
   * The rows.
   */
  private int rows = 4;


  /**
   * The columns.
   */
  private int columns = 18;


  /**
   * Allocates a new {@link ConfirmDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param text The text of the {@link ConfirmDialog}.
   * @param title The title of the {@link ConfirmDialog}.
   * @param yesButtonVisible The yes button visible flag.
   * @param yesToAllButtonVisible The yes to all button visible flag.
   * @param noButtonVisible The no button visible flag.
   * @param noToAllButtonVisible The no to all button visible flag.
   * @param cancelButtonVisible The cancel button visible flag.
   */
  public ConfirmDialog ( JFrame parent, String text, String title,
      boolean yesButtonVisible, boolean yesToAllButtonVisible,
      boolean noButtonVisible, boolean noToAllButtonVisible,
      boolean cancelButtonVisible )
  {
    logger.debug ( "ConfirmDialog", "allocate a new confirm dialog" ); //$NON-NLS-1$//$NON-NLS-2$
    this.parent = parent;
    this.gui = new ConfirmDialogForm ( this, parent );
    this.gui.jGTITextAreaInfo.setCursor ( null );
    this.gui.jGTITextAreaInfo.setText ( text );
    this.gui.setTitle ( title );

    ArrayList < JGTIButton > buttonList = new ArrayList < JGTIButton > ();
    if ( yesButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonYes );
    }
    if ( yesToAllButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonYesToAll );
    }
    if ( noButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonNo );
    }
    if ( noToAllButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonNoToAll );
    }
    if ( cancelButtonVisible )
    {
      buttonList.add ( this.gui.jGTIButtonCancel );
    }
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonYes );
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonYesToAll );
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonNo );
    this.gui.getContentPane ().remove ( this.gui.jGTIButtonNoToAll );
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
          gridBagConstraints.gridx = i;
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
          gridBagConstraints.gridx = i;
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
          gridBagConstraints.gridx = i;
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
   * Centers the dialog.
   */
  private final void centerDialog ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final ConfirmDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Cancels the {@link ConfirmDialogForm}.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$//$NON-NLS-2$
    this.canceled = true;
    this.gui.dispose ();
  }


  /**
   * Confirms the {@link ConfirmDialogForm}.
   */
  public final void handleConfirm ()
  {
    logger.debug ( "handleConfirm", "handle confirm" ); //$NON-NLS-1$//$NON-NLS-2$
    this.confirmed = true;
    this.gui.dispose ();
  }


  /**
   * Confirms all the {@link ConfirmDialogForm}.
   */
  public final void handleConfirmAll ()
  {
    logger.debug ( "handleConfirmAll", "handle confirm all" ); //$NON-NLS-1$//$NON-NLS-2$
    this.confirmedAll = true;
    this.gui.dispose ();
  }


  /**
   * Cancels the {@link ConfirmDialogForm}.
   */
  public final void handleNotConfirm ()
  {
    logger.debug ( "handleNotConfirm", "handle not confirm" ); //$NON-NLS-1$//$NON-NLS-2$
    this.notConfirmed = true;
    this.gui.dispose ();
  }


  /**
   * Cancels all the {@link ConfirmDialogForm}.
   */
  public final void handleNotConfirmAll ()
  {
    logger.debug ( "handleNotConfirmAll", "handle not confirm all" ); //$NON-NLS-1$//$NON-NLS-2$
    this.notConfirmedAll = true;
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
   * Returns the confirmed all value.
   * 
   * @return The confirmed all value.
   * @see #confirmedAll
   */
  public final boolean isConfirmedAll ()
  {
    return this.confirmedAll;
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
   * Returns the not confirmed all value.
   * 
   * @return The not confirmed all value.
   * @see #notConfirmedAll
   */
  public final boolean isNotConfirmedAll ()
  {
    return this.notConfirmedAll;
  }


  /**
   * Shows the {@link ConfirmDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the confirm dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.gui.jGTITextAreaInfo.setRows ( this.rows );
    this.gui.jGTITextAreaInfo.setColumns ( this.columns );

    this.gui.setResizable ( true );

    this.gui.pack ();

    this.gui.jGTIScrollPaneInfo.getVerticalScrollBar ().addAdjustmentListener (
        new AdjustmentListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void adjustmentValueChanged (
              @SuppressWarnings ( "unused" ) AdjustmentEvent event )
          {
            while ( ( ConfirmDialog.this.rows < 10 )
                && ( ConfirmDialog.this.gui.jGTIScrollPaneInfo
                    .getVerticalScrollBar ().isVisible () ) )
            {
              ConfirmDialog.this.rows++ ;
              ConfirmDialog.this.columns = ConfirmDialog.this.columns + 2;
              ConfirmDialog.this.gui.jGTITextAreaInfo
                  .setRows ( ConfirmDialog.this.rows );
              ConfirmDialog.this.gui.jGTITextAreaInfo
                  .setColumns ( ConfirmDialog.this.columns );
              ConfirmDialog.this.gui.pack ();
              centerDialog ();
            }
          }
        } );

    centerDialog ();
    this.gui.setVisible ( true );
  }
}
