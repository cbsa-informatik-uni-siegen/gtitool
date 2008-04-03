package de.unisiegen.gtitool.ui.swing;


import javax.swing.JSplitPane;


/**
 * Special {@link JSplitPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTISplitPane extends JSplitPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6298434809326827175L;


  /**
   * Allocates a new {@link JGTISplitPane}.
   */
  public JGTISplitPane ()
  {
    super ();
    setDividerSize ( 3 );
    setContinuousLayout ( false );
    setBorder ( null );
  }
}
