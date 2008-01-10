package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;
import javax.swing.JPanel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;


/**
 * TODO
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings ( "all" )
public class GrammarPanel implements EditorPanel
{

  GrammarPanelForm gui;


  /**
   * Allocate a new <code>GrammarPanel</code>
   * 
   * @param parent The parent frame
   * @param alphabet the {@link Alphabet}
   */
  public GrammarPanel ( JFrame parent, Alphabet alphabet )
  {
    this.gui = new GrammarPanelForm ();

  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getPanel()
   */
  public JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getAlphabet()
   */
  public Alphabet getAlphabet ()
  {
    return new DefaultAlphabet ();
  }
}
