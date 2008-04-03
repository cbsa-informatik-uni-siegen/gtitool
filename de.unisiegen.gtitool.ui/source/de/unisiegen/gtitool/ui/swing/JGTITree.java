package de.unisiegen.gtitool.ui.swing;


import javax.swing.JTree;


/**
 * Special {@link JTree}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTITree extends JTree
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6067980178941266840L;


  /**
   * Allocates a new {@link JGTITree}.
   */
  public JGTITree ()
  {
    super ();
    setModel ( null );
  }
}
