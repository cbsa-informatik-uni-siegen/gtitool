package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import de.unisiegen.gtitool.core.util.Theme;


/**
 * Special {@link JTextArea}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITextArea extends JTextArea
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6856520361707837256L;


  /**
   * The initial {@link Color}.
   */
  private Color initialColor;


  /**
   * Allocates a new {@link JGTITextArea}.
   */
  public JGTITextArea ()
  {
    super ();
    this.initialColor = getBackground ();
    setBorder ( null );
    setLineWrap ( true );
    setWrapStyleWord ( true );
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
