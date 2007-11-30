package de.unisiegen.gtitool.ui;


import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;


/**
 * The <code>EditorPanel</code> interface.
 * 
 * @author Benjamin Mies
 */
public interface EditorPanel
{

  /**
   * Returns the {@link JPanel}.
   * 
   * @return The {@link JPanel}.
   */
  public JPanel getPanel ();
  
  /** 
   * Returns the {@link Alphabet} of this panel
   *
   * @return the {@link Alphabet}
   */
  public Alphabet getAlphabet ();
}
