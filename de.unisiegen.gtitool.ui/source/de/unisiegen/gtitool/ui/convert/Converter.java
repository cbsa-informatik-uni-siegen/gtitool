package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;


/**
 * This interface represents the converter classes.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public interface Converter
{

  /**
   * Perform convert action.
   * 
   * @param machineType The {@link MachineType} to convert to. 
   */
  public void convert ( MachineType machineType );
}
