package de.unisiegen.gtitool.ui.logic;


import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Version;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.utils.Clipboard;


/**
 * The {@link AboutDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AboutDialog
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( AboutDialog.class );


  /**
   * The {@link AboutDialogForm}.
   */
  private AboutDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Allocates a new {@link AboutDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   */
  public AboutDialog ( JFrame parent )
  {
    logger.debug ( "AboutDialog", "allocate a new about dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.gui = new AboutDialogForm ( this, parent );
    this.gui.jGTILabelName.setText ( "GTI Tool " + Version.MAJOR + "." //$NON-NLS-1$//$NON-NLS-2$
        + Version.MINOR + "." + Version.MICRO ); //$NON-NLS-1$
    this.gui.jGTILabelVersionEntry.setText ( Version.MAJOR
        + "." + Version.MINOR //$NON-NLS-1$
        + "." + Version.MICRO + "." + Version.BUILD ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.jGTILabelWebpageEntry
        .setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
  }


  /**
   * Closes the {@link AboutDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handleClose", "handle close" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handles the {@link MouseEvent} on the webpage entry.
   */
  public final void handleWebpageEntry ()
  {
    logger.debug ( "handleWebpageEntry", "handle web page entry" ); //$NON-NLS-1$ //$NON-NLS-2$
    Clipboard.getInstance ().copy ( this.gui.jGTILabelWebpageEntry.getText () );
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the about dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
