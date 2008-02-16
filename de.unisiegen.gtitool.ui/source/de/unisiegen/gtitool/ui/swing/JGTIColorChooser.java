package de.unisiegen.gtitool.ui.swing;


import javax.swing.JColorChooser;
import javax.swing.JPanel;


/**
 * Special {@link JColorChooser}.
 * 
 * @author Christian Fehler
 */
public class JGTIColorChooser extends JColorChooser
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3745141498172501423L;


  /**
   * Allocates a new {@link JGTIColorChooser}.
   */
  public JGTIColorChooser ()
  {
    setPreviewPanel ( new JPanel () );
  }
}
