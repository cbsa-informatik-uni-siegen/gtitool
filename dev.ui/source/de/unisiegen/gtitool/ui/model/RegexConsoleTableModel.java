package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.exceptions.RegexException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The table model for the warning and error tables.
 * 
 * @author Simon Meurer
 * @version
 */
public final class RegexConsoleTableModel extends AbstractTableModel
{

  /**
   * The serial version id
   */
  private static final long serialVersionUID = -5683546628566227934L;


  /**
   * The console table entry.
   * 
   * @author Christian Fehler
   */
  private final class ConsoleTableEntry
  {

    /**
     * The message.
     */
    public PrettyString message;


    /**
     * The description.
     */
    public PrettyString description;


    /**
     * The symbol
     */
    public Symbol symbol;


    /**
     * Allocates a new {@link ConsoleTableEntry}.
     * 
     * @param message The message.
     * @param description The description.
     * @param symbol The {@link Symbol}
     */
    public ConsoleTableEntry ( PrettyString message, PrettyString description,
        Symbol symbol )
    {
      this.message = message;
      this.description = description;
      this.symbol = symbol;
    }
  }


  /**
   * The message column
   */
  public final static int MESSAGE_COLUMN = 0;


  /**
   * The description column
   */
  public final static int DESCRIPTION_COLUMN = 1;


  /**
   * The column count
   */
  public final static int COLUMN_COUNT = 2;


  /**
   * The data of this table model
   */
  private ArrayList < ConsoleTableEntry > data;


  /**
   * Allocates a new {@link RegexConsoleTableModel}.
   */
  public RegexConsoleTableModel ()
  {
    this.data = new ArrayList < ConsoleTableEntry > ();
  }


  /**
   * Add a row to this data model
   * 
   * @param regexException the RegexException containing the data for the new
   *          row
   *          TODO
   */
  public final void addRow ( RegexException regexException )
  {
    this.data.add ( new ConsoleTableEntry ( regexException.getPrettyMessage (),
        regexException.getPrettyDescription (), null ) );
    fireTableRowsInserted ( this.data.size () - 1, this.data.size () - 1 );
  }


  /**
   * Clear the data of this table model
   */
  public final void clearData ()
  {
    this.data.clear ();
    fireTableDataChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractTableModel#getColumnClass(int)
   */
  @Override
  public final Class < ? > getColumnClass ( int columnIndex )
  {
    switch ( columnIndex )
    {
      case MESSAGE_COLUMN :
      {
        return PrettyString.class;
      }
      case DESCRIPTION_COLUMN :
      {
        return PrettyString.class;
      }
      default :
      {
        return Object.class;
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnCount()
   */
  public final int getColumnCount ()
  {
    return COLUMN_COUNT;
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractTableModel#getColumnName(int)
   */
  @Override
  public final String getColumnName ( int columnIndex )
  {
    switch ( columnIndex )
    {
      case MESSAGE_COLUMN :
      {
        return "Message"; //$NON-NLS-1$
      }
      case DESCRIPTION_COLUMN :
      {
        return "Description"; //$NON-NLS-1$
      }
      default :
      {
        return ""; //$NON-NLS-1$
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getRowCount()
   */
  public final int getRowCount ()
  {
    return this.data.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public final Object getValueAt ( int rowIndex, int columnIndex )
  {
    switch ( columnIndex )
    {
      case MESSAGE_COLUMN :
      {
        return this.data.get ( rowIndex ).message;
      }
      case DESCRIPTION_COLUMN :
      {
        return this.data.get ( rowIndex ).description;
      }
      default :
      {
        return null;
      }
    }
  }
}
