package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * Involved {@link Symbol} interface.
 * 
 * @author Christian Fehler
 * @version $Id: ProductionWordMembersInvolvedException.java 1372 2008-10-30
 *          08:36:20Z fehler $
 */
public interface ProductionWordMembersInvolvedException
{

  /**
   * Returns the {@link ProductionWordMember}s.
   * 
   * @return The {@link ProductionWordMember}s.
   */
  public ArrayList < ProductionWordMember > getProductionWordMember ();
}
