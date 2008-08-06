package de.unisiegen.gtitool.ui.swing;


import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JSlider;


/**
 * Special {@link JSlider}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTISlider extends JSlider
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8865465889290483169L;


  /**
   * Allocates a new {@link JGTISlider}.
   */
  public JGTISlider ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTISlider}.
   * 
   * @param brm The {@link BoundedRangeModel}.
   */
  public JGTISlider ( BoundedRangeModel brm )
  {
    super ( brm );
    init ();
  }


  /**
   * Allocates a new {@link JGTISlider}.
   * 
   * @param orientation The orientation.
   */
  public JGTISlider ( int orientation )
  {
    super ( orientation );
    init ();
  }


  /**
   * Allocates a new {@link JGTISlider}.
   * 
   * @param min The min value.
   * @param max The max value.
   */
  public JGTISlider ( int min, int max )
  {
    super ( min, max );
    init ();
  }


  /**
   * Allocates a new {@link JGTISlider}.
   * 
   * @param min The min value.
   * @param max The max value.
   * @param value The value.
   */
  public JGTISlider ( int min, int max, int value )
  {
    super ( min, max, value );
    init ();
  }


  /**
   * Allocates a new {@link JGTISlider}.
   * 
   * @param orientation The orientation.
   * @param min The min value.
   * @param max The max value.
   * @param value The value.
   */
  public JGTISlider ( int orientation, int min, int max, int value )
  {
    super ( orientation, min, max, value );
    init ();
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setPaintLabels ( true );
    setPaintTicks ( true );
    setPaintTrack ( true );
    setSnapToTicks ( true );
  }
}
