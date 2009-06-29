package de.unisiegen.gtitool.ui.logic;


import java.awt.Window;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.InfoDialogForm;


/**
 * The {@link InfoDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class InfoDialog implements LogicClass < InfoDialogForm >
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( InfoDialog.class );


  /**
   * The {@link InfoDialogForm}.
   */
  protected InfoDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private Window parent;


  /**
   * The rows.
   */
  protected int rows = 4;


  /**
   * The columns.
   */
  protected int columns = 18;


  /**
   * Allocates a new {@link InfoDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param text The text of the {@link InfoDialog}.
   * @param title The title of the {@link InfoDialog}.
   */
  public InfoDialog ( JDialog parent, String text, String title )
  {
    logger.debug ( "InfoDialog", "allocate a new info dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.gui = new InfoDialogForm ( this, parent );
    this.gui.jGTITextAreaInfo.setCursor ( null );
    this.gui.jGTITextAreaInfo.setText ( text );
    this.gui.setTitle ( title );
    this.gui.pack ();
  }


  /**
   * Allocates a new {@link InfoDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param text The text of the {@link InfoDialog}.
   * @param title The title of the {@link InfoDialog}.
   */
  public InfoDialog ( JFrame parent, String text, String title )
  {
    logger.debug ( "InfoDialog", "allocate a new info dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.gui = new InfoDialogForm ( this, parent );
    this.gui.jGTITextAreaInfo.setCursor ( null );
    this.gui.jGTITextAreaInfo.setText ( text );
    this.gui.setTitle ( title );
    this.gui.pack ();
  }


  /**
   * Centers the dialog.
   */
  protected final void centerDialog ()
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
  public final InfoDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Closes the {@link InfoDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handleClose", "handle close" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Shows the {@link InfoDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the info dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.gui.jGTITextAreaInfo.setRows ( this.rows );
    this.gui.jGTITextAreaInfo.setColumns ( this.columns );
    this.gui.pack ();

    this.gui.jGTIScrollPaneInfo.getVerticalScrollBar ().addAdjustmentListener (
        new AdjustmentListener ()
        {

          public void adjustmentValueChanged (
              @SuppressWarnings ( "unused" ) AdjustmentEvent event )
          {
            while ( ( InfoDialog.this.rows < 10 )
                && ( InfoDialog.this.gui.jGTIScrollPaneInfo
                    .getVerticalScrollBar ().isVisible () ) )
            {
              InfoDialog.this.rows++ ;
              InfoDialog.this.columns = InfoDialog.this.columns + 2;
              InfoDialog.this.gui.jGTITextAreaInfo
                  .setRows ( InfoDialog.this.rows );
              InfoDialog.this.gui.jGTITextAreaInfo
                  .setColumns ( InfoDialog.this.columns );
              InfoDialog.this.gui.pack ();
              centerDialog ();
            }
          }
        } );

    centerDialog ();
    this.gui.setVisible ( true );
  }
}
