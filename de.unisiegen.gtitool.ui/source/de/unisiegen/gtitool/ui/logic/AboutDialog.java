package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.Versions;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;


/**
 * The <code>AboutDialog</code>.
 * 
 * @author Christian Fehler
 * @version $Id: AboutDialog.java 28 2007-10-18 14:29:54Z fehler $
 */
public class AboutDialog
{

  /**
   * The {@link AboutDialogForm}.
   */
  private AboutDialogForm aboutDialogForm;


  /**
   * Creates a new <code>AboutDialog</code>.
   * 
   * @param pParent The parent window.
   */
  public AboutDialog ( JFrame pParent )
  {
    this.aboutDialogForm = new AboutDialogForm ( pParent, true );
    this.aboutDialogForm.jLabelName.setText ( "GTITool " + Versions.UI ); //$NON-NLS-1$
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    this.aboutDialogForm.setEnabled ( true );
  }
}
