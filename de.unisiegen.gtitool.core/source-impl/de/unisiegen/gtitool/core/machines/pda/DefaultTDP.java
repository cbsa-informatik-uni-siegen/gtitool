package de.unisiegen.gtitool.core.machines.pda;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;


/**
 * The class for the top down parser (pda)
 *
 *@author Christian Uhrhan
 */
public class DefaultTDP extends DefaultPDA
{
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1371164970141783189L;

  /**
   * Allocates a new {@link PDA}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link PDA}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this {@link PDA}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   */
  public DefaultTDP ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet )
  {
    super(alphabet, pushDownAlphabet, usePushDownAlphabet);
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getTableColumnModel()
   */
  @Override
  public TableColumnModel getTableColumnModel()
  {
    DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();
    
    /*
     * NonterminalSymbol column
     */
    TableColumn nonTerminalColumn = new TableColumn(NONTERMINAL_COLUMN);
    nonTerminalColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( "", //$NON-NLS-1$
        Style.NONE ) ) );
    nonTerminalColumn.setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    nonTerminalColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    columnModel.addColumn ( nonTerminalColumn );
    
    /*
     * TerminalSymbol columns
     */
    for ( int i = 0 ; i < getAlphabet ().size () ; i++ )
    {
      TableColumn symbolColumn = new TableColumn ( i + 1 );
      symbolColumn.setHeaderValue ( getAlphabet ().get ( i ) );
      symbolColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      columnModel.addColumn ( symbolColumn );
    }
    
    return columnModel;
  }
}
