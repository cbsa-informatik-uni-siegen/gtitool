package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Interface representing a model managing the {@link Machine}s and
 * {@link Grammar}s.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public interface DefaultModel
{

  /**
   * Returns the {@link Element}.
   * 
   * @return the {@link Element}.
   */
  public Element getElement ();
}
