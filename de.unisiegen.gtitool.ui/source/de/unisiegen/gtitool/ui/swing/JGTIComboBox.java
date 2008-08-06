package de.unisiegen.gtitool.ui.swing;


import java.awt.Font;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;


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
    init ();
  }


  /**
   * Allocates a new {@link JGTIComboBox}.
   * 
   * @param model The {@link ComboBoxModel}.
   */
  public JGTIComboBox ( ComboBoxModel model )
  {
    super ( model );
    init ();
  }


  /**
   * Allocates a new {@link JGTIComboBox}.
   * 
   * @param items The items.
   */
  public JGTIComboBox ( final Object items[] )
  {
    super ( items );
    init ();
  }


  /**
   * Allocates a new {@link JGTIComboBox}.
   * 
   * @param items The items.
   */
  public JGTIComboBox ( Vector < ? > items )
  {
    super ( items );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setFont ( new Font ( "Dialog", Font.PLAIN, 12 ) ); //$NON-NLS-1$
    setModel ( new DefaultComboBoxModel () );
  }
}
