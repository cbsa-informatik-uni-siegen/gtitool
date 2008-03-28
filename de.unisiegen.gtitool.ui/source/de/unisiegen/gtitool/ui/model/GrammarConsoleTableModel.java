package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarDuplicateProductionException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The table model for the warning and error tables.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id:DefaultTableModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class GrammarConsoleTableModel extends AbstractTableModel
{

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
    public String message;


    /**
     * The description.
     */
    public PrettyString description;


    /**
     * The {@link Production}s.
     */
    public ArrayList < Production > productions = new ArrayList < Production > ();


    /**
     * Allocates a new {@link ConsoleTableEntry}.
     * 
     * @param message The message.
     * @param descrition The description.
     * @param productions The {@link Production}s
     */
    public ConsoleTableEntry ( String message, PrettyString descrition,
        ArrayList < Production > productions )
    {
      this.message = message;
      this.description = descrition;
      this.productions = productions;
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7684497578193097976L;


  /**
   * The message column
   */
  public final static int MESSAGE_COLUMN = 0;


  /**
   * The description column
   */
  public final static int DESCRIPTION_COLUMN = 1;


  /**
   * The {@link Production} column
   */
  public final static int PRODUCTION_COLUMN = 2;


  /**
   * The column count
   */
  public final static int COLUMN_COUNT = 3;


  /**
   * The data of this table model
   */
  private ArrayList < ConsoleTableEntry > data;


  /**
   * Allocates a new {@link GrammarConsoleTableModel}.
   */
  public GrammarConsoleTableModel ()
  {
    this.data = new ArrayList < ConsoleTableEntry > ();
  }


  /**
   * Add a row to this data model
   * 
   * @param grammarException the GrammarException containing the data for the
   *          new row
   */
  public final void addRow ( GrammarException grammarException )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();

    if ( grammarException instanceof GrammarDuplicateProductionException )
    {
      productions
          .addAll ( ( ( GrammarDuplicateProductionException ) grammarException )
              .getProductions () );
    }

    this.data.add ( new ConsoleTableEntry ( grammarException.getMessage (),
        grammarException.getDescription (), productions ) );
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
        return String.class;
      }
      case DESCRIPTION_COLUMN :
      {
        return PrettyString.class;
      }
      case PRODUCTION_COLUMN :
      {
        return ArrayList.class;
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
      case PRODUCTION_COLUMN :
      {
        return "Productions"; //$NON-NLS-1$
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
   * Returns the {@link State}s of the given row index.
   * 
   * @param rowIndex The given row index.
   * @return The {@link State}s of the given row index.
   */
  public final ArrayList < Production > getProductions ( int rowIndex )
  {
    return this.data.get ( rowIndex ).productions;
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
      case PRODUCTION_COLUMN :
      {
        return this.data.get ( rowIndex ).productions;
      }
      default :
      {
        return null;
      }
    }
  }
}
