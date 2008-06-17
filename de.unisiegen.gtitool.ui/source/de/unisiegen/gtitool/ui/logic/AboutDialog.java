package de.unisiegen.gtitool.ui.logic;


import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Version;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.LicensePanel;
import de.unisiegen.gtitool.ui.utils.Clipboard;


/**
 * The {@link AboutDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AboutDialog implements LogicClass < AboutDialogForm >
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
    this.gui.jGTILabelName.setText ( "GTI Tool " + Version.VERSION ); //$NON-NLS-1$
    this.gui.jGTILabelVersionEntry.setText ( Version.FULL_VERSION );
    this.gui.jGTILabelWebpageEntry
        .setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );

    createLicenseTabs ();
  }


  /**
   * Creates the license tabs.
   */
  private final void createLicenseTabs ()
  {
    loadLicense ( "GTI Tool", "GTITool.txt" ); //$NON-NLS-1$ //$NON-NLS-2$
    loadLicense ( "JFlex", "JFlex.txt" ); //$NON-NLS-1$ //$NON-NLS-2$
    loadLicense ( "JavaCUP", "JavaCUP.txt" ); //$NON-NLS-1$ //$NON-NLS-2$
    loadLicense ( "JGraph", "JGraph.txt" ); //$NON-NLS-1$ //$NON-NLS-2$
    loadLicense ( "TinyLaF", "TinyLaF.txt" ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final AboutDialogForm getGUI ()
  {
    return this.gui;
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
   * Loads the license.
   * 
   * @param title The title.
   * @param fileName The file name.
   */
  private final void loadLicense ( String title, String fileName )
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder license = new StringBuilder ();
    try
    {
      BufferedReader reader = new BufferedReader ( new InputStreamReader (
          getClass ().getResourceAsStream (
              "/de/unisiegen/gtitool/ui/licenses/" + fileName ), "UTF8" ) ); //$NON-NLS-1$ //$NON-NLS-2$

      String input;
      boolean first = true;
      while ( ( input = reader.readLine () ) != null )
      {
        if ( !first )
        {
          license.append ( lineBreak );
        }
        first = false;
        license.append ( input );
      }

      LicensePanel panel = new LicensePanel ();
      panel.jGTITextAreaLicense.setText ( license.toString () );
      panel.jGTITextAreaLicense.setCaretPosition ( 0 );
      this.gui.jGTITabbedPaneLicenses.addTab ( title, panel );
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
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
