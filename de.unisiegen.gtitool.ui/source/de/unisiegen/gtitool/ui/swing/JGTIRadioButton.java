package de.unisiegen.gtitool.ui.swing;


import java.awt.Insets;

import javax.swing.JRadioButton;


/**
 * Special {@link JRadioButton}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIRadioButton extends JRadioButton
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6506338508202501533L;


  /**
   * Allocates a new {@link JGTIRadioButton}.
   */
  public JGTIRadioButton ()
  {
    super ();
    setFocusPainted ( false );
    setBorder ( null );
    setMargin ( new Insets ( 0, 0, 0, 0 ) );
  }
}
