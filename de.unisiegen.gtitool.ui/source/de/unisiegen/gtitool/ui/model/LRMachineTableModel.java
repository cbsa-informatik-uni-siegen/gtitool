package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.DefaultLRStateStack;
import de.unisiegen.gtitool.core.entities.LRStateStack;
import de.unisiegen.gtitool.core.entities.RejectAction;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * The table model for the lr parsers
 * 
 * @author Christian Uhrhan
 */
public class LRMachineTableModel extends StatelessMachineTableModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = 7634471767910162876L;


  /**
   * the column count
   */
  private final static int COLUMN_COUNT = 4;


  /**
   * the input column
   */
  private final static int INPUT_COLUMN = 0;


  /**
   * the stack column
   */
  private final static int STACK_COLUMN = 1;


  /**
   * the state stack column
   */
  private final static int STATE_STACK_COLUMN = 2;


  /**
   * the action column
   */
  private final static int ACTION_COLUMN = 3;


  /**
   * the lr state stack data
   */
  private ArrayList < LRStateStack > lrStateStackData = new ArrayList < LRStateStack > ();


  /**
   * Special case when the word has been accepted, no additional row will be
   * added, but "Accepted" will be put into the last row of the actions column
   * When this is undone, no row may be removed, but "Accepted" is erased.
   */
  private boolean isFinished = false;


  /**
   * Allocates a new {@link LRMachineTableModel}
   */
  public LRMachineTableModel ()
  {
    super ();
  }


  /**
   * adds a row to the table
   * 
   * @param stack the {@link Stack}
   * @param input the {@link Word}
   * @param action the {@link Action} (can be null), for example, if the machine
   *          has just been started
   * @param stateStack the state stack
   */
  public final void addRow ( final Stack stack, final Word input,
      final Action action, final LRStateStack stateStack )
  {
    super.addRow ( stack, input, action );

    this.lrStateStackData.add ( new DefaultLRStateStack ( stateStack ) );
  }


  /**
   * removes a row
   * 
   * @param rowIndex the index of the row which shall be removed
   */
  @Override
  public final void removeRow ( final int rowIndex )
  {
    super.removeRow ( rowIndex );
    this.lrStateStackData.remove ( rowIndex );
  }


  /**
   * removes the last row
   */
  @Override
  public final void removeLastRow ()
  {
    if ( this.isFinished )
      this.actionData.remove ( this.actionData.size () - 1 );
    else
    {
      super.removeLastRow ();
      this.lrStateStackData.remove ( this.lrStateStackData.size () - 1 );
    }

    this.isFinished = false;
  }


  /**
   * clears all the data
   */
  @Override
  public final void clearData ()
  {
    super.clearData ();
    this.lrStateStackData.clear ();
    this.isFinished = false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return LRMachineTableModel.COLUMN_COUNT;
  }


  /**
   * TODO
   * 
   * @param action
   * @see de.unisiegen.gtitool.ui.model.StatelessMachineTableModel#accept(de.unisiegen.gtitool.core.entities.AcceptAction)
   */
  @Override
  public void accept ( final AcceptAction action )
  {
    this.actionData.add ( action );
    this.isFinished = true;
  }


  /**
   * TODO
   * 
   * @param action
   * @see de.unisiegen.gtitool.ui.model.StatelessMachineTableModel#reject(de.unisiegen.gtitool.core.entities.RejectAction)
   */
  @Override
  public void reject ( final RejectAction action )
  {
    this.actionData.add ( action );
    this.isFinished = true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public PrettyString getValueAt ( int rowIndex, int columnIndex )
  {
    switch ( columnIndex )
    {
      case LRMachineTableModel.INPUT_COLUMN :
        return this.inputData.get ( rowIndex ).toPrettyString ();
      case LRMachineTableModel.STACK_COLUMN :
        return new PrettyString ( new PrettyToken ( this.stackData.get (
            rowIndex ).reverseElementString (), Style.NONE ) );
      case LRMachineTableModel.STATE_STACK_COLUMN :
        return new PrettyString ( new PrettyToken ( this.lrStateStackData.get (
            rowIndex ).toString (), Style.NONE ) );
      case LRMachineTableModel.ACTION_COLUMN :
        // the action should be displayed in the row where it
        // takes place and *not* a row after that
        // but the action can only be determined after the user
        // hits the 'next' button. (then the machine determines
        // the action corresponding to its internal state)
        if ( getRowCount () > rowIndex + 1 )
        {
          Action action = this.actionData.get ( rowIndex + 1 );
          if ( action != null )
            return action.toPrettyString ();
        }
        else if ( this.isFinished )
        {
          return this.actionData.get ( rowIndex + 1 ).toPrettyString ();
        }
    }
    return new PrettyString ();
  }
}
