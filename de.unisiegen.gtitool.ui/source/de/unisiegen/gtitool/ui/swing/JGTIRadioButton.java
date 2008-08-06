package de.unisiegen.gtitool.ui.swing;


import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JRadioButton;


/**
 * Special {@link JRadioButton}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIRadioButton extends JRadioButton
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6506338508202501533L;


  /**
   * Allocates a new {@link JGTIRadioButton}.
   */
  public JGTIRadioButton ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param action The {@link Action}.
   */
  public JGTIRadioButton ( Action action )
  {
    super ( action );
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param icon The {@link Icon}.
   */
  public JGTIRadioButton ( Icon icon )
  {
    super ( icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTIRadioButton ( Icon icon, boolean selected )
  {
    super ( icon, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param text The text.
   */
  public JGTIRadioButton ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param text The text.
   * @param selected The selected value.
   */
  public JGTIRadioButton ( String text, boolean selected )
  {
    super ( text, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   */
  public JGTIRadioButton ( String text, Icon icon )
  {
    super ( text, icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTIRadioButton}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTIRadioButton ( String text, Icon icon, boolean selected )
  {
    super ( text, icon, selected );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setFocusPainted ( false );
    setBorder ( null );
    setMargin ( new Insets ( 0, 0, 0, 0 ) );
  }
}
