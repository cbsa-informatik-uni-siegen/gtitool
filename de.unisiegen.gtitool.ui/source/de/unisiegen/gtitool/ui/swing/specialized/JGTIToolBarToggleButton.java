package de.unisiegen.gtitool.ui.swing.specialized;


import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JToggleButton;


/**
 * Special {@link JToggleButton} for the tool bar.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIToolBarToggleButton extends JToggleButton
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1746095496850915318L;


  /**
   * The height of this {@link JGTIToolBarToggleButton}.
   */
  private static final int FIX_HEIGHT = 36;


  /**
   * The width of this {@link JGTIToolBarToggleButton}.
   */
  private static final int FIX_WIDTH = 36;


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   */
  public JGTIToolBarToggleButton ()
  {
    super ();
    setFocusPainted ( false );
    setFocusable ( false );
    setBorderPainted ( false );
    setOpaque ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#getMaximumSize()
   */
  @Override
  public final Dimension getMaximumSize ()
  {
    return new Dimension ( FIX_WIDTH, FIX_HEIGHT );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#getMinimumSize()
   */
  @Override
  public final Dimension getMinimumSize ()
  {
    return new Dimension ( FIX_WIDTH, FIX_HEIGHT );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#getPreferredSize()
   */
  @Override
  public final Dimension getPreferredSize ()
  {
    return new Dimension ( FIX_WIDTH, FIX_HEIGHT );
  }
}
