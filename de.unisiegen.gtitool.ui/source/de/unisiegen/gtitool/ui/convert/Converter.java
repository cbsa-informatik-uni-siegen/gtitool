package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * This interface represents the converter classes.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public interface Converter
{

  /**
   * Performs the convert action.
   * 
   * @param fromEntityType The from {@link EntityType}.
   * @param toEntityType The to {@link EntityType}.
   * @param complete If the the complete powerset {@link Machine} is used.
   */
  public void convert ( EntityType fromEntityType, EntityType toEntityType,
      boolean complete );
}
