package de.unisiegen.gtitool.ui.swing;


import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


/**
 * Special {@link JComboBox}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIComboBox extends JComboBox
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -675392052849557328L;


  /**
   * Allocates a new {@link JGTIComboBox}.
   */
  public JGTIComboBox ()
  {
    super ();
    setFont ( new Font ( "Dialog", Font.PLAIN, 12 ) ); //$NON-NLS-1$
    setModel ( new DefaultComboBoxModel () );
  }
}
