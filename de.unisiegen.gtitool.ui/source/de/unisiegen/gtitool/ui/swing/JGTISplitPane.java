package de.unisiegen.gtitool.ui.swing;


import java.awt.Component;

import javax.swing.JComponent;
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
    init ();
  }


  /**
   * Allocates a new {@link JGTISplitPane}.
   * 
   * @param newOrientation The new orientation.
   */
  public JGTISplitPane ( int newOrientation )
  {
    super ( newOrientation );
    init ();
  }


  /**
   * Allocates a new {@link JGTISplitPane}.
   * 
   * @param newOrientation The new orientation.
   * @param newContinuousLayout The new continuous layout.
   */
  public JGTISplitPane ( int newOrientation, boolean newContinuousLayout )
  {
    super ( newOrientation, newContinuousLayout );
    init ();
  }


  /**
   * Allocates a new {@link JGTISplitPane}.
   * 
   * @param newOrientation The new orientation.
   * @param newContinuousLayout The new continuous layout.
   * @param newLeftComponent The new left component.
   * @param newRightComponent The new right component.
   */
  public JGTISplitPane ( int newOrientation, boolean newContinuousLayout,
      Component newLeftComponent, Component newRightComponent )
  {
    super ( newOrientation, newContinuousLayout, newLeftComponent,
        newRightComponent );
    init ();
  }


  /**
   * Allocates a new {@link JGTISplitPane}.
   * 
   * @param newOrientation The new orientation.
   * @param newLeftComponent The new left component.
   * @param newRightComponent The new right component.
   */
  public JGTISplitPane ( int newOrientation, Component newLeftComponent,
      Component newRightComponent )
  {
    super ( newOrientation, newLeftComponent, newRightComponent );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setDividerSize ( 3 );
    setContinuousLayout ( false );
    setBorder ( null );
  }
}
