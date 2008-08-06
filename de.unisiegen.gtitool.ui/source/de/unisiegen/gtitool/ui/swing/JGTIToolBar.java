package de.unisiegen.gtitool.ui.swing;


import javax.swing.JComponent;
import javax.swing.JToolBar;


/**
 * Special {@link JToolBar}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIToolBar extends JToolBar
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2802419904461779950L;


  /**
   * Allocates a new {@link JGTIToolBar}.
   */
  public JGTIToolBar ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBar}.
   * 
   * @param orientation The orientation.
   */
  public JGTIToolBar ( int orientation )
  {
    super ( orientation );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBar}.
   * 
   * @param name The name.
   */
  public JGTIToolBar ( String name )
  {
    super ( name );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBar}.
   * 
   * @param name The name.
   * @param orientation The orientation.
   */
  public JGTIToolBar ( String name, int orientation )
  {
    super ( name, orientation );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setBorder ( null );
    setFloatable ( false );
    setBorderPainted ( false );
  }
}
