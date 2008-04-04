package de.unisiegen.gtitool.ui.swing;


import java.awt.Insets;

import javax.swing.JCheckBox;


/**
 * Special {@link JCheckBox}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTICheckBox extends JCheckBox
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6277062079474033608L;


  /**
   * Allocates a new {@link JGTICheckBox}.
   */
  public JGTICheckBox ()
  {
    super ();
    setFocusPainted ( false );
    setBorder ( null );
    setMargin ( new Insets ( 0, 0, 0, 0 ) );
  }
}
