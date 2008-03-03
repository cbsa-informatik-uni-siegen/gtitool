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
   * The description.
   */
  private String description;


  /**
   * Allocates a new {@link Exchange}.
   * 
   * @param element The {@link Element}.
   * @param description The description.
   */
  public Exchange ( Element element, String description )
  {
    // Element
    if ( element == null )
    {
      throw new IllegalArgumentException ( "element is null" ); //$NON-NLS-1$
    }
    this.element = element;

    // Description
    if ( description == null )
    {
      throw new IllegalArgumentException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = description;
  }


  /**
   * Returns the description.
   * 
   * @return The description.
   * @see #description
   */
  public final String getDescription ()
  {
    return this.description;
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
