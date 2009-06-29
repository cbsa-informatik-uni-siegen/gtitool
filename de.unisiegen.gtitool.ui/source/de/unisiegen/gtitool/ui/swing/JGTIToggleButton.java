package de.unisiegen.gtitool.ui.swing;


import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JToggleButton;


/**
 * Special {@link JToggleButton}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIToggleButton extends JToggleButton
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6035684262516422389L;


  /**
   * Allocates a new {@link JGTIToggleButton}.
   */
  public JGTIToggleButton ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param action The {@link Action}.
   */
  public JGTIToggleButton ( Action action )
  {
    super ( action );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param icon The {@link Icon}.
   */
  public JGTIToggleButton ( Icon icon )
  {
    super ( icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTIToggleButton ( Icon icon, boolean selected )
  {
    super ( icon, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param text The text.
   */
  public JGTIToggleButton ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param text The text.
   * @param selected The selected value.
   */
  public JGTIToggleButton ( String text, boolean selected )
  {
    super ( text, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   */
  public JGTIToggleButton ( String text, Icon icon )
  {
    super ( text, icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIToggleButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTIToggleButton ( String text, Icon icon, boolean selected )
  {
    super ( text, icon, selected );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setFocusPainted ( true );
    setBorderPainted ( true );
    setOpaque ( true );
  }
}
