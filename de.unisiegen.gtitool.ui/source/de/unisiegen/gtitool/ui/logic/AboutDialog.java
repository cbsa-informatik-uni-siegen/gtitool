package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.Versions;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;


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
  private AboutDialogForm aboutDialogForm;


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
    this.aboutDialogForm = new AboutDialogForm ( this, pParent );
    this.aboutDialogForm.jLabelName.setText ( "GTI Tool " + Versions.UI ); //$NON-NLS-1$
  }


  /**
   * Closes the {@link AboutDialogForm}.
   */
  public void close ()
  {
    this.aboutDialogForm.dispose ();
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.aboutDialogForm.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.aboutDialogForm.getHeight () / 2 );
    this.aboutDialogForm.setBounds ( x, y, this.aboutDialogForm.getWidth (),
        this.aboutDialogForm.getHeight () );
    this.aboutDialogForm.setVisible ( true );
  }
}
