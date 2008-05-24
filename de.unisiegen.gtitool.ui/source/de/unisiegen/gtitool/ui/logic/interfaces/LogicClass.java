package de.unisiegen.gtitool.ui.logic.interfaces;


import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The logic class interface which is implemented from every logic class.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link GUIClass}.
 */
public interface LogicClass < E extends GUIClass < ? extends LogicClass < E >> >
{

  /**
   * Return the gui class.
   * 
   * @return The gui class.
   */
  public E getGUI ();
}
