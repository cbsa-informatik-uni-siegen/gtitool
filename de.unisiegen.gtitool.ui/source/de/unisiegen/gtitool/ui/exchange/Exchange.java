package de.unisiegen.gtitool.ui.exchange;


import java.io.Serializable;

import de.unisiegen.gtitool.core.storage.Element;


/**
 * The {@link Exchange} object which is sended.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Exchange implements Serializable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3223674346666770228L;


  /**
   * The {@link Element}.
   */
  private Element element;


  /**
   * Allocates a new {@link Exchange}.
   * 
   * @param element The {@link Element}.
   */
  public Exchange ( Element element )
  {
    this.element = element;
  }


  /**
   * Returns the {@link Element}.
   * 
   * @return The {@link Element}.
   * @see #element
   */
  public final Element getElement ()
  {
    return this.element;
  }
}
