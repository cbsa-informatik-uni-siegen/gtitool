package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The {@link DefaultMachineModel}
 */
public abstract class DefaultMachineModel implements DefaultModel, Modifyable
{

  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  abstract public Element getElement ();


  /**
   * Retrieves the {@link Machine}
   *
   * @return the {@link Machine}
   */
  abstract public Machine getMachine ();

}
