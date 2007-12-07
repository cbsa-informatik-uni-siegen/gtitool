package de.unisiegen.gtitool.core.storage;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.storage.exceptions.StoreWarningException;


/**
 * The <code>Storable</code> interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Storable
{

  /**
   * Returns the {@link Element}.
   * 
   * @return The {@link Element}.
   */
  public Element getElement ();


  /**
   * Returns the warnings.
   * 
   * @return The warning.
   */
  public ArrayList < StoreWarningException > getWarning ();


  /**
   * Returns the warning with the given index.
   * 
   * @param pIndex The index to return.
   * @return The warning with the given index.
   */
  public ArrayList < StoreWarningException > getWarning ( int pIndex );
}
