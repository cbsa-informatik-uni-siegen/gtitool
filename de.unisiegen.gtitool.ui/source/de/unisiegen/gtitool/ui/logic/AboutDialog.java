package de.unisiegen.gtitool.ui.logic;


import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
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
   * Create the license tabs.
   */
  private void createLicenseTabs ()
  {
    File file = new File ( "source/licenses" ); //$NON-NLS-1$

    if ( file.list ().length == 0 )
    {
      this.gui.jGTITabbedPaneMain.remove ( 1 );
      return;
    }
    for ( String current : file.list () )
    {
      String license = ""; //$NON-NLS-1$
      try
      {
        if (!current.endsWith ( ".txt" )){ //$NON-NLS-1$
          continue;
        }
        // Read the license from txt file
        BufferedReader reader = new BufferedReader ( new InputStreamReader (
            getClass ().getResourceAsStream ( "/licenses/" + current ), "UTF8" ) ); //$NON-NLS-1$ //$NON-NLS-2$

        String input = ""; //$NON-NLS-1$

        while ( ( input = reader.readLine () ) != null )
        {
          license += input + "\n"; //$NON-NLS-1$
        }
        
        LicensePanel panel = new LicensePanel();
        
        panel.jGTITextAreaLicense.setText ( license );
        
        // Goto the begin of the text area
        panel.jGTITextAreaLicense.setEditable ( true );
        panel.jGTITextAreaLicense.setCaretPosition ( 0 );
        panel.jGTITextAreaLicense.setEditable ( false );
        
        // Get the tab name from the file
        String name = current;
        name = name.replace ( ".txt", "" ); //$NON-NLS-1$ //$NON-NLS-2$
        
        // Add a new tab for the read license
        this.gui.jGTITabbedPaneLicenses.addTab ( name, panel );
      }
      catch ( IOException e )
      {
        e.printStackTrace ();
      }
      
    }
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
