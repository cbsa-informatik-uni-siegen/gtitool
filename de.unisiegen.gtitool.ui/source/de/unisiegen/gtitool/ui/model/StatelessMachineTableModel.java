package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.RejectAction;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Word;


/**
 * Base class for TDPMachineTableModel and LRMachineTableModel
 * 
 * @author Christian Uhrhan
 */
public abstract class StatelessMachineTableModel extends AbstractTableModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = 166016738882230378L;


  /**
   * the {@link Stack} data
   */
  protected ArrayList < Stack > stackData;


  /**
   * the {@link Word} input data
   */
  protected ArrayList < Word > inputData;


  /**
   * the {@link Action} data
   */
  protected ArrayList < Action > actionData;


  /**
   * Allocates a new {@link StatelessMachineTableModel}
   */
  public StatelessMachineTableModel ()
  {
    this.stackData = new ArrayList < Stack > ();
    this.inputData = new ArrayList < Word > ();
    this.actionData = new ArrayList < Action > ();
  }


  /**
   * adds a row to the table
   * 
   * @param stack the {@link Stack}
   * @param input the {@link Word}
   * @param action the {@link Action}
   */
  public void addRow ( final Stack stack, final Word input, final Action action )
  {
    this.stackData.add ( new DefaultStack ( stack ) );
    this.inputData.add ( new DefaultWord ( input ) );
    this.actionData.add ( action );
  }


  /**
   * removes a row
   * 
   * @param rowIndex the index of the row which shall be removed
   */
  public void removeRow ( final int rowIndex )
  {
    this.stackData.remove ( rowIndex );
    this.inputData.remove ( rowIndex );
    this.actionData.remove ( rowIndex );
  }


  /**
   * removes the last row
   */
  public void removeLastRow ()
  {
    this.stackData.remove ( this.stackData.size () - 1 );
    this.inputData.remove ( this.inputData.size () - 1 );
    this.actionData.remove ( this.actionData.size () - 1 );
  }


  /**
   * Sets the accept action in the last table entry
   * 
   * @param action The {@link AcceptAction}
   */
  public void accept ( final AcceptAction action )
  {
    this.actionData.set ( this.actionData.size () - 1, action );
  }


  /**
   * Input word is rejected
   * @param action The {@link RejectAction}
   */
  public void reject ( final RejectAction action )
  {
    this.actionData.set ( this.actionData.size () - 1, action );
  }


  /**
   * clears all the data
   */
  public void clearData ()
  {
    this.stackData.clear ();
    this.inputData.clear ();
    this.actionData.clear ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.stackData.size ();
  }
}
