package de.unisiegen.gtitool.ui.swing.specialized;


import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
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
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarButton}.
   * 
   * @param action The {@link Action}.
   */
  public JGTIToolBarButton ( Action action )
  {
    super ( action );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarButton}.
   * 
   * @param icon The {@link Icon}.
   */
  public JGTIToolBarButton ( Icon icon )
  {
    super ( icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarButton}.
   * 
   * @param text The text.
   */
  public JGTIToolBarButton ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   */
  public JGTIToolBarButton ( String text, Icon icon )
  {
    super ( text, icon );
    init ();
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


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setFocusPainted ( false );
    setBorderPainted ( false );
    setOpaque ( false );
  }
}
