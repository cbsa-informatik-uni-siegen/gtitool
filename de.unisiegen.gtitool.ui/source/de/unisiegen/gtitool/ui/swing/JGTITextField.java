package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import de.unisiegen.gtitool.core.util.Theme;


/**
 * Special {@link JTextField}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITextField extends JTextField
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8082101999864785450L;


  /**
   * The initial {@link Color}.
   */
  private Color initialColor;


  /**
   * Allocates a new {@link JGTITextField}.
   */
  public JGTITextField ()
  {
    super ();
    this.initialColor = getBackground ();
    setBorder ( new LineBorder ( Color.BLACK, 1 ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#setEnabled(boolean)
   */
  @Override
  public final void setEnabled ( boolean enabled )
  {
    super.setEnabled ( enabled );
    if ( enabled )
    {
      setBackground ( this.initialColor );
    }
    else
    {
      setBackground ( Theme.DISABLED_COMPONENT_COLOR );
    }
  }
}
