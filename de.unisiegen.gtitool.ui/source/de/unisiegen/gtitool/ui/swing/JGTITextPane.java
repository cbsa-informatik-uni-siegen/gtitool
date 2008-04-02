package de.unisiegen.gtitool.ui.swing;


import javax.swing.JTextPane;


/**
 * Special {@link JTextPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTITextPane extends JTextPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3335212157343560547L;


  /**
   * Allocates a new {@link JGTITextPane}.
   */
  public JGTITextPane ()
  {
    super ();
    setBorder ( null );
  }
}
