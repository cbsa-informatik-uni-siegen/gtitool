package de.unisiegen.gtitool.ui.logic;


import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

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
   * @param pParent The parent {@link JFrame}.
   */
  public AboutDialog ( JFrame pParent )
  {
    logger.debug ( "allocate a new about dialog" ); //$NON-NLS-1$
    this.parent = pParent;
    this.gui = new AboutDialogForm ( this, pParent );
    this.gui.jLabelName.setText ( "GTI Tool " + Version.VERSION ); //$NON-NLS-1$
    this.gui.jLabelWebpageEntry.setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
  }


  /**
   * Closes the {@link AboutDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handle close" ); //$NON-NLS-1$
    this.gui.dispose ();
  }


  /**
   * Handles the {@link MouseEvent} on the webpage entry.
   */
  public final void handleWebpageEntry ()
  {
    logger.debug ( "handle web page entry" ); //$NON-NLS-1$
    Clipboard.getInstance ().copy ( this.gui.jLabelWebpageEntry.getText () );
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the about dialog" ); //$NON-NLS-1$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
