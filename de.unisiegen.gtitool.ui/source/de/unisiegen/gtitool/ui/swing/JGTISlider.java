package de.unisiegen.gtitool.ui.swing;


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
    setPaintLabels ( true );
    setPaintTicks ( true );
    setPaintTrack ( true );
    setSnapToTicks ( true );
  }
}
