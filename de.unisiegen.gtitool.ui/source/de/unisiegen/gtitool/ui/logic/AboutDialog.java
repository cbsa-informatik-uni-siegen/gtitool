package de.unisiegen.gtitool.ui.logic;


import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.Versions;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.utils.Clipboard;


/**
 * The <code>AboutDialog</code>.
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
   * Allocates a new <code>AboutDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public AboutDialog ( JFrame pParent )
  {
    logger.debug ( "allocate a new about dialog" ); //$NON-NLS-1$
    this.parent = pParent;
    this.gui = new AboutDialogForm ( this, pParent );
    try
    {
      // TODO Find a way for java 1.5
      // this.gui.setIconImage ( ImageIO.read ( getClass ().getResource (
      // "/de/unisiegen/gtitool/ui/icon/gtitool.png" ) ) ); //$NON-NLS-1$
    }
    catch ( Exception e )
    {
      // Do nothing
    }
    this.gui.jLabelName.setText ( "GTI Tool " + Versions.VERSION ); //$NON-NLS-1$
    this.gui.jLabelWebpageEntry.setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );

    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            logger.debug ( "language changed listener" ); //$NON-NLS-1$
            AboutDialog.this.gui.setTitle ( Messages
                .getString ( "AboutDialog.Title" ) ); //$NON-NLS-1$

            AboutDialog.this.gui.jLabelCopyright.setText ( Messages
                .getString ( "AboutDialog.Copyright" ) ); //$NON-NLS-1$

            AboutDialog.this.gui.jLabelWebpage.setText ( Messages
                .getString ( "AboutDialog.Webpage" ) ); //$NON-NLS-1$

            AboutDialog.this.gui.jLabelWebpageEntry.setToolTipText ( Messages
                .getString ( "AboutDialog.WebpageEntryToolTip" ) ); //$NON-NLS-1$

            AboutDialog.this.gui.jLabelDeveloper.setText ( Messages
                .getString ( "AboutDialog.Developer" ) ); //$NON-NLS-1$

            AboutDialog.this.gui.jButtonClose.setText ( Messages
                .getString ( "AboutDialog.Close" ) ); //$NON-NLS-1$
            AboutDialog.this.gui.jButtonClose.setMnemonic ( Messages.getString (
                "AboutDialog.Close" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
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
