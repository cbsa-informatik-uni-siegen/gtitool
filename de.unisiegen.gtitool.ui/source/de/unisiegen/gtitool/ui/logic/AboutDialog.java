package de.unisiegen.gtitool.ui.logic;


import java.util.ResourceBundle;

import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.Versions;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.preferences.LanguageChangedListener;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The <code>AboutDialog</code>.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class AboutDialog
{

  /**
   * The {@link AboutDialogForm}.
   */
  private AboutDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Creates a new <code>AboutDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public AboutDialog ( JFrame pParent )
  {
    this.parent = pParent;
    this.gui = new AboutDialogForm ( this, pParent );
    this.gui.jLabelName.setText ( "GTI Tool " + Versions.UI ); //$NON-NLS-1$

    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            ResourceBundle bundle = ResourceBundle
                .getBundle ( "de/unisiegen/gtitool/ui/messages" ); //$NON-NLS-1$
            AboutDialog.this.gui.setTitle ( bundle
                .getString ( "AboutDialog.Title" ) ); //$NON-NLS-1$
            AboutDialog.this.gui.jLabelCopyright
                .setText ( "AboutDialog.Copyright" ); //$NON-NLS-1$
            AboutDialog.this.gui.jLabelWebpage.setText ( "AboutDialog.Webpage" ); //$NON-NLS-1$
            AboutDialog.this.gui.jLabelDeveloper
                .setText ( "AboutDialog.Developer" ); //$NON-NLS-1$
            AboutDialog.this.gui.jButtonClose.setText ( "AboutDialog.Close" ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Closes the {@link AboutDialogForm}.
   */
  public void handleClose ()
  {
    this.gui.dispose ();
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
