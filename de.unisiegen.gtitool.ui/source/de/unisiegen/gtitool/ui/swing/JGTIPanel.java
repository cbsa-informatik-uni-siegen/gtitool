package de.unisiegen.gtitool.ui.swing;


import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;


/**
 * Special {@link JPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIPanel extends JPanel
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
    init ();
  }


  /**
   * Allocates a new {@link JGTIPanel}.
   * 
   * @param isDoubleBuffered The is double buffered value.
   */
  public JGTIPanel ( boolean isDoubleBuffered )
  {
    super ( isDoubleBuffered );
    init ();
  }


  /**
   * Allocates a new {@link JGTIPanel}.
   * 
   * @param layout The {@link LayoutManager}.
   */
  public JGTIPanel ( LayoutManager layout )
  {
    super ( layout );
    init ();
  }


  /**
   * Allocates a new {@link JGTIPanel}.
   * 
   * @param layout The {@link LayoutManager}.
   * @param isDoubleBuffered The is double buffered value.
   */
  public JGTIPanel ( LayoutManager layout, boolean isDoubleBuffered )
  {
    super ( layout, isDoubleBuffered );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setLayout ( new GridBagLayout () );
  }
}
