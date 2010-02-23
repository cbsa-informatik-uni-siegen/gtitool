package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * The Model of the {@link StatelessMachine}s
 * 
 * @author Christian Uhrhan, Philipp Reh
 */
public class DefaultStatelessMachineModel extends DefaultMachineModel
{

  /**
   * the {@link StatelessMachine}
   */
  private StatelessMachine machine;


  /**
   * the {@link Grammar}
   */
  private Grammar grammar;


  /**
   * Allocates a new {@link DefaultStatelessMachineModel}
   * 
   * @param machine the {@link StatelessMachine}
   */
  public DefaultStatelessMachineModel ( final StatelessMachine machine )
  {
    this.machine = machine;
  }


  public void setGrammar ( final Grammar grammar )
  {
    this.grammar = grammar;
  }


  public Grammar getGrammar ()
  {
    return this.grammar;
  }


  @Override
  public StatelessMachine getMachine ()
  {
    return this.machine;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.model.DefaultModel#getElement()
   */
  @Override
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
