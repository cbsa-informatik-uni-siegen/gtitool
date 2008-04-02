package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.netbeans.InfoDialogForm;


/**
 * The {@link InfoDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class InfoDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( InfoDialog.class );


  /**
   * The {@link InfoDialogForm}.
   */
  private InfoDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Allocates a new {@link InfoDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param text The text of the {@link InfoDialog}.
   * @param title The title of the {@link InfoDialog}.
   */
  public InfoDialog ( JFrame parent, String text, String title )
  {
    logger.debug ( "allocate a new info dialog" ); //$NON-NLS-1$
    this.parent = parent;
    this.gui = new InfoDialogForm ( this, parent );
    this.gui.jGTITextAreaInfo.setCursor ( null );
    this.gui.jGTITextAreaInfo.setText ( text );
    this.gui.setTitle ( title );
    this.gui.pack ();
  }


  /**
   * Closes the {@link InfoDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handle close" ); //$NON-NLS-1$
    this.gui.dispose ();
  }


  /**
   * Shows the {@link InfoDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the info dialog" ); //$NON-NLS-1$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
