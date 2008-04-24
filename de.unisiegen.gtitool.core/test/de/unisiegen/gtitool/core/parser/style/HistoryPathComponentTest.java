package de.unisiegen.gtitool.core.parser.style;


import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.machines.HistoryItem;
import de.unisiegen.gtitool.core.machines.HistoryPath;
import de.unisiegen.gtitool.core.parser.style.renderer.HistoryPathTableCellRenderer;


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

      // Alphabet
      Alphabet alphabet = new DefaultAlphabet ( symbol0, symbol1, symbol2 );

      // State
      State state0 = new DefaultState ( "z0" );//$NON-NLS-1$
      State state1 = new DefaultState ( "z1" );//$NON-NLS-1$
      State state2 = new DefaultState ( "z2" );//$NON-NLS-1$

      // Transition
      Transition transition0 = new DefaultTransition ( alphabet, alphabet,
          word, word, state0, state1, symbol0, symbol1 );
      Transition transition1 = new DefaultTransition ( alphabet, alphabet,
          word, word, state1, state2, symbol1, symbol2 );
      Transition transition2 = new DefaultTransition ( alphabet, alphabet,
          word, word, state2, state2, symbol0, symbol1, symbol2 );

      Stack stack = new DefaultStack ();

      // Item0
      TreeSet < State > item0States = new TreeSet < State > ();
      item0States.add ( state0 );
      TreeSet < Transition > item0Transitions = new TreeSet < Transition > ();
      item0Transitions.add ( transition0 );
      ArrayList < Symbol > item0Symbols = new ArrayList < Symbol > ();
      item0Symbols.add ( symbol0 );
      item0Symbols.add ( symbol1 );
      HistoryItem item0 = new HistoryItem ( item0States, item0Transitions,
          item0Symbols, stack );

      // Item1
      TreeSet < State > item1States = new TreeSet < State > ();
      item1States.add ( state1 );
      TreeSet < Transition > item1Transitions = new TreeSet < Transition > ();
      item1Transitions.add ( transition1 );
      ArrayList < Symbol > item1Symbols = new ArrayList < Symbol > ();
      item1Symbols.add ( symbol1 );
      HistoryItem item1 = new HistoryItem ( item1States, item1Transitions,
          item1Symbols, stack );

      // Item2
      TreeSet < State > item2States = new TreeSet < State > ();
      item2States.add ( state2 );
      TreeSet < Transition > item2Transitions = new TreeSet < Transition > ();
      item2Transitions.add ( transition2 );
      ArrayList < Symbol > item2Symbols = new ArrayList < Symbol > ();
      item2Symbols.add ( symbol1 );
      HistoryItem item2 = new HistoryItem ( item2States, item2Transitions,
          item2Symbols, stack );

      // History list
      ArrayList < HistoryItem > historyItemList = new ArrayList < HistoryItem > ();
      historyItemList.add ( item0 );
      historyItemList.add ( item1 );
      historyItemList.add ( item2 );

      // HistoryPath
      HistoryPath historyPath = new HistoryPath ();

      for ( int i = 0 ; i < historyItemList.size () - 1 ; i++ )
      {
        historyPath.add ( historyItemList.get ( i ).getStateSet ().first (),
            historyItemList.get ( i ).getTransitionSet ().first (),
            historyItemList.get ( i + 1 ).getStateSet ().first (),
            historyItemList.get ( i ).getSymbolSet ().get ( 0 ) );
      }

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

      jFrame.add ( jTable );
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
