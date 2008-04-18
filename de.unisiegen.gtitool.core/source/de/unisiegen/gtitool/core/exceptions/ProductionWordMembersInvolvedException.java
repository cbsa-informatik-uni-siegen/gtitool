package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * Involved {@link Symbol} interface.
 * 
 * @author Christian Fehler
 * @version $Id: MachineEpsilonTransitionException.java 90 2007-11-04 16:20:27Z
 *          fehler $
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
