package de.unisiegen.gtitool.ui.history;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;


/**
 * The test class of the {@link HistoryPathComponent}.
 * 
 * @author Christian Fehler
 * @version $Id: PrettyStringHistoryComponentTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class HistoryPathComponentTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    try
    {
      // Word
      Word word = new DefaultWord ();

      // Symbol
      Symbol symbol0 = new DefaultSymbol ( "a" );//$NON-NLS-1$
      Symbol symbol1 = new DefaultSymbol ( "b" );//$NON-NLS-1$
      Symbol symbol2 = new DefaultSymbol ( "c" );//$NON-NLS-1$
      Symbol symbol3 = new DefaultSymbol ( "d" );//$NON-NLS-1$
      Symbol symbol4 = new DefaultSymbol ( "e" );//$NON-NLS-1$
      Symbol symbol5 = new DefaultSymbol ( "f" );//$NON-NLS-1$
      Symbol symbol6 = new DefaultSymbol ( "g" );//$NON-NLS-1$
      Symbol symbol7 = new DefaultSymbol ( "h" );//$NON-NLS-1$
      Symbol symbol8 = new DefaultSymbol ( "i" );//$NON-NLS-1$
      Symbol symbol9 = new DefaultSymbol ( "j" );//$NON-NLS-1$

      // Alphabet
      Alphabet alphabet = new DefaultAlphabet ( symbol0, symbol1, symbol2,
          symbol3, symbol4, symbol5, symbol6, symbol7, symbol8, symbol9 );

      // State
      State state0 = new DefaultState ( "z0" );//$NON-NLS-1$
      State state1 = new DefaultState ( "z1" );//$NON-NLS-1$
      State state2 = new DefaultState ( "z2" );//$NON-NLS-1$

      // Transition
      Transition transition0 = new DefaultTransition ( alphabet, alphabet,
          word, word, state0, state1, symbol0, symbol1, symbol2, symbol3,
          symbol4, symbol5, symbol6, symbol7, symbol8, symbol9 );
      Transition transition1 = new DefaultTransition ( alphabet, alphabet,
          word, word, state1, state2, symbol1, symbol2 );
      Transition transition2 = new DefaultTransition ( alphabet, alphabet,
          word, word, state2, state2, symbol0, symbol1, symbol2 );

      // HistoryPath
      HistoryPath historyPath = new HistoryPath ();
      historyPath.add ( transition0, symbol0 );
      historyPath.add ( transition1, symbol2 );
      historyPath.add ( transition2, symbol2 );

      // Model
      DefaultTableModel model = new DefaultTableModel ();
      model.addColumn ( "History" ); //$NON-NLS-1$
      model.addRow ( new Object []
      { historyPath } );

      // ColumnModel
      DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();
      TableColumn historyColumn = new TableColumn ( 0 );
      historyColumn.setHeaderValue ( "History" ); //$NON-NLS-1$
      historyColumn.setCellRenderer ( new HistoryPathTableCellRenderer () );
      columnModel.addColumn ( historyColumn );

      JFrame jFrame = new JFrame ( "PrettyStringHistoryComponentTest" ); //$NON-NLS-1$
      JTable jTable = new JTable ()
      {

        /**
         * The serial version uid.
         */
        private static final long serialVersionUID = -585804705346618616L;


        @Override
        public boolean isCellEditable ( @SuppressWarnings ( "unused" )
        int x, @SuppressWarnings ( "unused" )
        int y )
        {
          return false;
        }
      };
      jTable.setModel ( model );
      jTable.setColumnModel ( columnModel );

      JScrollPane jScrollPane = new JScrollPane ();
      jScrollPane.setViewportView ( jTable );

      jFrame.add ( jScrollPane );
      jFrame.setBounds ( 0, 0, 800, 600 );
      jFrame.setLocationRelativeTo ( null );
      jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
      jFrame.setVisible ( true );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
