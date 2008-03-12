package de.unisiegen.gtitool.ui.swing;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;


/**
 * Special {@link JButton} for the tool bar.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIToolBarButton extends JButton
{

  /**
   * The height of this {@link JGTIToolBarButton}.
   */
  private static final int FIX_HEIGHT = 36;


  /**
   * The width of this {@link JGTIToolBarButton}.
   */
  private static final int FIX_WIDTH = 36;


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1746095496850915318L;


  /**
   * Allocates a new {@link JGTIToolBarButton}.
   */
  public JGTIToolBarButton ()
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
