package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * the table model for the TDP (parser movements)
 * 
 * @author Christian Uhrhan
 */
public class TDPMachineTableModel extends StatelessMachineTableModel
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
   * allocates a new {@link TDPMachineTableModel}
   */
  public TDPMachineTableModel ()
  {
    super ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return TDPMachineTableModel.COLUMN_COUNT;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void accept ( final AcceptAction action )
  {
    addRow ( this.stackData.get ( getRowCount () - 1), this.inputData
        .get ( getRowCount () - 1), action );
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
      case TDPMachineTableModel.INPUT_COLUMN :
        return this.inputData.get ( rowIndex );
      case TDPMachineTableModel.STACK_COLUMN :
        return this.stackData.get ( rowIndex );
      case TDPMachineTableModel.ACTION_COLUMN :
        Action action = this.actionData.get ( rowIndex );
        if ( action != null )
          return action;
    }
    return new PrettyString ();
  }
}
