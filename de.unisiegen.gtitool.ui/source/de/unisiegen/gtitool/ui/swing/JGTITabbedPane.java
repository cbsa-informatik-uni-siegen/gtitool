package de.unisiegen.gtitool.ui.swing;


import javax.swing.JTabbedPane;


/**
 * Special {@link JTabbedPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTITabbedPane extends JTabbedPane
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5744054037095918506L;


  /**
   * Allocates a new {@link JGTITabbedPane}.
   */
  public JGTITabbedPane ()
  {
    super ();
    setFocusable ( false );
  }
}
