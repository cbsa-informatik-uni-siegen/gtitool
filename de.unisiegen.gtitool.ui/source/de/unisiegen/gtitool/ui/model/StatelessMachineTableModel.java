package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * the table model for the table (parser movements)
 * 
 * @author Christian Uhrhan
 */
public class StatelessMachineTableModel extends AbstractTableModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6693897333759433432L;


  /**
   * the column count
   */
  private final static int COLUMN_COUNT = 3;


  /**
   * the input column
   */
  private final static int INPUT_COLUMN = 0;


  /**
   * the stack column
   */
  private final static int STACK_COLUMN = 1;


  /**
   * the action column
   */
  private final static int ACTION_COLUMN = 2;


  /**
   * the {@link Stack} data
   */
  private ArrayList < Stack > stackData;


  /**
   * the {@link Word} input data
   */
  private ArrayList < Word > inputData;


  /**
   * the {@link Action} data
   */
  private ArrayList < Action > actionData;


  /**
   * allocates a new {@link StatelessMachineTableModel}
   */
  public StatelessMachineTableModel ()
  {
    initialize ();
  }


  /**
   * allocates a new {@link StatelessMachineTableModel}
   * 
   * @param stack the initial {@link Stack}
   * @param input the initial input {@link Word}
   * @param action the initial {@link Action}
   */
  public StatelessMachineTableModel ( final Stack stack, final Word input,
      final Action action )
  {
    initialize ();
    this.stackData.add ( stack );
    this.inputData.add ( input );
    this.actionData.add ( action );
  }


  /**
   * initializes the data fields
   */
  private final void initialize ()
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
  public final void addRow ( final Stack stack, final Word input,
      final Action action )
  {
    this.stackData.add ( stack );
    this.inputData.add ( input );
    this.actionData.add ( action );
  }


  /**
   * removes a row
   * 
   * @param rowIndex the index of the row which shall be removed
   */
  public final void removeRow ( final int rowIndex )
  {
    this.stackData.remove ( rowIndex );
    this.inputData.remove ( rowIndex );
    this.actionData.remove ( rowIndex );
  }


  /**
   * removes the last row
   */
  public final void removeLastRow ()
  {
    this.stackData.remove ( this.stackData.size () );
    this.inputData.remove ( this.inputData.size () );
    this.actionData.remove ( this.actionData.size () );
  }


  /**
   * clears all the data
   */
  public final void clearData ()
  {
    this.stackData.clear ();
    this.inputData.clear ();
    this.actionData.clear ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return StatelessMachineTableModel.COLUMN_COUNT;
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


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, int columnIndex )
  {
    switch ( columnIndex )
    {
      case StatelessMachineTableModel.INPUT_COLUMN :
        return this.inputData.get ( rowIndex );
      case StatelessMachineTableModel.STACK_COLUMN :
        return this.stackData.get ( rowIndex );
      case StatelessMachineTableModel.ACTION_COLUMN :
        return this.actionData.get ( rowIndex );
    }
    return new PrettyString ();
  }

}
