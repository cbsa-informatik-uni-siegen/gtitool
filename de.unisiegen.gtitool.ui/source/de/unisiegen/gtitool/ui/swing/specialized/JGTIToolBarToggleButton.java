package de.unisiegen.gtitool.ui.swing.specialized;


import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
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
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param action The {@link Action}.
   */
  public JGTIToolBarToggleButton ( Action action )
  {
    super ( action );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param icon The {@link Icon}.
   */
  public JGTIToolBarToggleButton ( Icon icon )
  {
    super ( icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTIToolBarToggleButton ( Icon icon, boolean selected )
  {
    super ( icon, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param text The text.
   */
  public JGTIToolBarToggleButton ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param text The text.
   * @param selected The selected value.
   */
  public JGTIToolBarToggleButton ( String text, boolean selected )
  {
    super ( text, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   */
  public JGTIToolBarToggleButton ( String text, Icon icon )
  {
    super ( text, icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToolBarToggleButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTIToolBarToggleButton ( String text, Icon icon, boolean selected )
  {
    super ( text, icon, selected );
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
