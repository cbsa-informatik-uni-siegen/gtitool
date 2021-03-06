package de.unisiegen.gtitool.ui.swing;


import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;


/**
 * Special {@link JCheckBox}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTICheckBox extends JCheckBox
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6277062079474033608L;


  /**
   * Allocates a new {@link JGTICheckBox}.
   */
  public JGTICheckBox ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param action The {@link Action}.
   */
  public JGTICheckBox ( Action action )
  {
    super ( action );
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param icon The {@link Icon}.
   */
  public JGTICheckBox ( Icon icon )
  {
    super ( icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTICheckBox ( Icon icon, boolean selected )
  {
    super ( icon, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param text The text.
   */
  public JGTICheckBox ( String text )
  {
    super ( text );
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param text The text.
   * @param selected The selected value.
   */
  public JGTICheckBox ( String text, boolean selected )
  {
    super ( text, selected );
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   */
  public JGTICheckBox ( String text, Icon icon )
  {
    super ( text, icon );
    init ();
  }


  /**
   * Allocates a new {@link JGTICheckBox}.
   * 
   * @param text The text.
   * @param icon The {@link Icon}.
   * @param selected The selected value.
   */
  public JGTICheckBox ( String text, Icon icon, boolean selected )
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
