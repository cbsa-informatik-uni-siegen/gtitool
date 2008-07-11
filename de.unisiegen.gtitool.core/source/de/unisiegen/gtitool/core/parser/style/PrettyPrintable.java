package de.unisiegen.gtitool.core.parser.style;


import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;


/**
 * The {@link PrettyPrintable} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface PrettyPrintable
{

  /**
   * Adds the given {@link PrettyStringChangedListener}.
   * 
   * @param listener The {@link PrettyStringChangedListener}.
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener );


  /**
   * Removes the given {@link PrettyStringChangedListener}.
   * 
   * @param listener The {@link PrettyStringChangedListener}.
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener );


  /**
   * Returns the {@link PrettyString}.
   * 
   * @return The {@link PrettyString}.
   */
  public PrettyString toPrettyString ();
}
