package de.unisiegen.gtitool.ui.netbeans.interfaces;


import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;


/**
 * The gui class interface which is implemented from every gui class.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link LogicClass}.
 */
public interface GUIClass < E extends LogicClass < ? extends GUIClass < E >>>
{

  /**
   * Return the logic class.
   * 
   * @return The logic class.
   */
  public E getLogic ();
}
