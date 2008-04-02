package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


/**
 * Special {@link JScrollPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTIScrollPane extends JScrollPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7315286435055612251L;


  /**
   * Allocates a new {@link JGTIScrollPane}.
   */
  public JGTIScrollPane ()
  {
    super ();
    setBorder ( new LineBorder ( Color.BLACK, 1 ) );
  }
}
