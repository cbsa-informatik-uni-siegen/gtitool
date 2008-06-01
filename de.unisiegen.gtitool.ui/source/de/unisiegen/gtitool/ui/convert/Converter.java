package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;


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
   */
  public void convert ( EntityType fromEntityType, EntityType toEntityType );
}
