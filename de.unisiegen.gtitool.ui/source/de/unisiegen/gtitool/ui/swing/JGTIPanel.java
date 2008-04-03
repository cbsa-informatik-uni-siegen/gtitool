package de.unisiegen.gtitool.ui.swing;


import java.awt.GridBagLayout;

import javax.swing.JPanel;


/**
 * Special {@link JPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTIPanel extends JPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 9140440706538435476L;


  /**
   * Allocates a new {@link JGTIPanel}.
   */
  public JGTIPanel ()
  {
    super ();
    setLayout ( new GridBagLayout () );
  }
}
