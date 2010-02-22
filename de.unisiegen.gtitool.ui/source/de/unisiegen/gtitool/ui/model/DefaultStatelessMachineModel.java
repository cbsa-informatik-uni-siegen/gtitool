package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The Model of the {@link StatelessMachine}s
 * 
 * @author Christian Uhrhan, Philipp Reh
 */
public class DefaultStatelessMachineModel implements DefaultModel, Storable,
    Modifyable
{

  /**
   * the machine
   */
  private StatelessMachine machine;


  /**
   * Allocates a new {@link DefaultStatelessMachineModel}
   *
   * @param machine the {@link StatelessMachine}
   */
  public DefaultStatelessMachineModel ( final StatelessMachine machine )
  {
    this.machine = machine;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.model.DefaultModel#getElement()
   */
  public Element getElement ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return false;
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
  }

}
