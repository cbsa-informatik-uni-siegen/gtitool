package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringListCellRenderer;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableColumnModel;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableModel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertGrammarDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertRegexToMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.swing.JGTIList;
import de.unisiegen.gtitool.ui.swing.JGTITable;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRows;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListTransferHandler;
import de.unisiegen.gtitool.ui.utils.TextLoader;


/**
 * The LogicClass for the RegexToMachine Converter
 * 
 * @author Simon Meurer
 */
public class ConvertGrammarDialog implements
    LogicClass < ConvertGrammarDialogForm >
{

  /**
   * Does the next step after a delay.
   * 
   * @author Simon Meurer
   */
  private final class AutoStepTimerTask extends TimerTask
  {

    /**
     * {@inheritDoc}
     * 
     * @see TimerTask#run()
     */
    @Override
    public final void run ()
    {
      SwingUtilities.invokeLater ( new Runnable ()
      {

        @SuppressWarnings ( "synthetic-access" )
        public void run ()
        {
          if ( ConvertGrammarDialog.this.endReached )
          {
            handleStop ();
          }
          else
          {
            performNextStep ();
          }
        }
      } );
    }
  }


  /**
   * The Type of the conversion of the {@link Grammar}
   */
  public enum ConvertGrammarType
  {
    /**
     * The eliminate entity productions type
     */
    ELIMINATE_ENTITY_PRODUCTIONS,

    /**
     * The eliminate epsilon productions type
     */
    ELIMINATE_EPSILON_PRODUCTIONS,

    /**
     * The eliminate left recursion type
     */
    ELIMINATE_LEFT_RECURSION,

    /**
     * The left factoring type
     */
    LEFT_FACTORING;
  }


  /**
   * The {@link StepItem}.
   * 
   * @author Simon Meurer
   */
  private class StepItem
  {

    /**
     * The added epsilon {@link Production}
     */
    private Production addedEpsilonProduction;


    /**
     * True if a {@link Production} was found
     */
    private boolean foundProduction;


    /**
     * The current i
     */
    private int iNow;


    /**
     * The current j
     */
    private int jNow;


    /**
     * The current {@link CFG}
     */
    private CFG oldCFG;


    /**
     * The current nonterminals as {@link DefaultListModel}
     */
    private DefaultListModel oldNonterminalModel;


    /**
     * The old oldWorkingProductions
     */
    private ArrayList < Production > oldWorkingProductions;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param oldCFG The current {@link CFG}
     * @param oldNonterminalModel The current nonterminals
     * @param i The current i
     * @param j The current j
     * @param addedEpsilonProduction The added epsilon {@link Production}
     * @param foundProduction True if new Production was found
     * @param oldWorkingProductions The old working productions
     */
    public StepItem ( CFG oldCFG, DefaultListModel oldNonterminalModel, int i,
        int j, Production addedEpsilonProduction, boolean foundProduction,
        ArrayList < Production > oldWorkingProductions )
    {
      this.oldCFG = oldCFG;
      this.oldNonterminalModel = oldNonterminalModel;
      this.iNow = i;
      this.jNow = j;
      this.addedEpsilonProduction = addedEpsilonProduction;
      this.foundProduction = foundProduction;
      this.oldWorkingProductions = oldWorkingProductions;
    }


    /**
     * Returns the addedEpsilonProduction.
     * 
     * @return The addedEpsilonProduction.
     * @see #addedEpsilonProduction
     */
    public Production getAddedEpsilonProduction ()
    {
      return this.addedEpsilonProduction;
    }


    /**
     * Returns the iNow.
     * 
     * @return The iNow.
     * @see #iNow
     */
    public int getINow ()
    {
      return this.iNow;
    }


    /**
     * Returns the jNow.
     * 
     * @return The jNow.
     * @see #jNow
     */
    public int getJNow ()
    {
      return this.jNow;
    }


    /**
     * Returns the oldCFG.
     * 
     * @return The oldCFG.
     * @see #oldCFG
     */
    public CFG getOldCFG ()
    {
      return this.oldCFG;
    }


    /**
     * Returns the oldNonterminalModel.
     * 
     * @return The oldNonterminalModel.
     * @see #oldNonterminalModel
     */
    public DefaultListModel getOldNonterminalModel ()
    {
      return this.oldNonterminalModel;
    }


    /**
     * Returns the oldWorkingProductions.
     * 
     * @return The oldWorkingProductions.
     * @see #oldWorkingProductions
     */
    public ArrayList < Production > getWorkingProductions ()
    {
      return this.oldWorkingProductions;
    }


    /**
     * Returns the foundProduction.
     * 
     * @return The foundProduction.
     * @see #foundProduction
     */
    public boolean isFoundProduction ()
    {
      return this.foundProduction;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertGrammarDialog.class );


  /**
   * The algorithm
   */
  private String algorithm;


  /**
   * The algorithm window
   */
  private TextWindow algorithmWindow;


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * The {@link ConvertMachineTableModel}.
   */
  private ConvertMachineTableModel convertMachineTableModel;


  /**
   * The {@link ConvertGrammarType}
   */
  private ConvertGrammarType convertType;


  /**
   * Flag that indicates if the end is reached.
   */
  private boolean endReached = false;


  /**
   * The entity {@link Production}s
   */
  private ArrayList < Production > entityProductions;


  /**
   * The epsilon {@link Production}s
   */
  private ArrayList < Production > epsilonProductions;


  /**
   * The {@link GrammarColumnModel}.
   */
  private GrammarColumnModel grammarColumnModelConverted = new GrammarColumnModel ();


  /**
   * The {@link GrammarColumnModel}.
   */
  private GrammarColumnModel grammarColumnModelOriginal = new GrammarColumnModel ();


  /**
   * The {@link ConvertRegexToMachineDialogForm}
   */
  private ConvertGrammarDialogForm gui;


  /**
   * The variable i in the algorithm
   */
  private int i = 1;


  /**
   * Flag indicates if entity productions are eliminated
   */
  private boolean initialEliminateEntityProductionsDone = false;


  /**
   * Flag indicates if epsilon productions are eliminated
   */
  private boolean initialEliminateEpsilonProductionsDone = false;


  /**
   * The initial nonterminal size
   */
  private int initialNonterminalSize;


  /**
   * The variable j in the algorithm
   */
  private int j = 1;


  /**
   * The {@link DefaultGrammarModel} for the converted {@link CFG}
   */
  private DefaultGrammarModel modelConverted;


  /**
   * The {@link DefaultGrammarModel} for the original {@link CFG}
   */
  private DefaultGrammarModel modelOriginal;


  /**
   * The {@link NonterminalSymbol}s of the converted {@link CFG} as List
   */
  private DefaultListModel nonterminals = new DefaultListModel ();


  /**
   * The {@link GrammarPanel}
   */
  private GrammarPanel panel;


  /**
   * The parent {@link JFrame}
   */
  private JFrame parent;


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * The {@link ConvertMachineTableColumnModel}.
   */
  private ConvertMachineTableColumnModel tableColumnModel = new ConvertMachineTableColumnModel ();


  /**
   * The working productions
   */
  private ArrayList < Production > workingProductions;


  /**
   * Creates new from {@link ConvertGrammarDialog}
   * 
   * @param parent The parent {@link JFrame}
   * @param panel The {@link RegexPanel}
   */
  public ConvertGrammarDialog ( JFrame parent, GrammarPanel panel )
  {
    this.parent = parent;
    this.panel = panel;
  }


  /**
   * Adds a outline comment.
   * 
   * @param prettyString The {@link PrettyString} to add.
   */
  private final void addOutlineComment ( PrettyString prettyString )
  {
    this.convertMachineTableModel.addRow ( prettyString );
    this.gui.jGTITableOutline.changeSelection ( this.convertMachineTableModel
        .getRowCount () - 1, ConvertMachineTableModel.OUTLINE_COLUMN, false,
        false );
  }


  /**
   * Cancels the auto step timer.
   */
  private final void cancelAutoStepTimer ()
  {
    if ( this.autoStepTimer != null )
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
    }
  }


  /**
   * Clones the converted {@link CFG}
   * 
   * @return The cloned converted {@link CFG}
   */
  private CFG cloneConverted ()
  {
    NonterminalSymbol start = null;
    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();
    for ( NonterminalSymbol s : this.modelConverted.getGrammar ()
        .getNonterminalSymbolSet () )
    {
      NonterminalSymbol c = new DefaultNonterminalSymbol ( s.getName () );
      try
      {
        c.setHighlighted ( s.isHighlighted () );
        nonterminalSet.add ( c );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
      if ( s.equals ( this.modelConverted.getGrammar ().getStartSymbol () ) )
      {
        start = c;
      }
    }
    TerminalSymbolSet terminals = new DefaultTerminalSymbolSet ();
    for ( TerminalSymbol s : this.modelConverted.getGrammar ()
        .getTerminalSymbolSet () )
    {
      try
      {
        terminals.add ( new DefaultTerminalSymbol ( s.getName () ) );
      }
      catch ( TerminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
    }
    CFG newCFG = new DefaultCFG ( nonterminalSet, terminals, start );
    for ( Production p : this.modelConverted.getGrammar ().getProduction () )
    {
      NonterminalSymbol nonterminal = null;
      for ( NonterminalSymbol s : nonterminalSet )
      {
        if ( p.getNonterminalSymbol ().getName ().equals ( s.getName () ) )
        {
          nonterminal = s;
        }
      }
      ProductionWord word = new DefaultProductionWord ();
      for ( ProductionWordMember m : p.getProductionWord () )
      {
        for ( NonterminalSymbol n : nonterminalSet )
        {
          if ( m.getName ().equals ( n.getName () ) )
          {
            word.add ( n );
          }
        }
        for ( TerminalSymbol t : terminals )
        {
          if ( m.getName ().equals ( t.getName () ) )
          {
            word.add ( t );
          }
        }
      }
      Production newProd = new DefaultProduction ( nonterminal, word );
      newProd.setHighlighted ( p.isHighlighted () );
      newCFG.addProduction ( newProd );
    }
    return newCFG;
  }


  /**
   * Opens the convert dialog
   * 
   * @param convertGrammarType The {@link ConvertGrammarType}
   */
  public void convert ( ConvertGrammarType convertGrammarType )
  {
    this.convertType = convertGrammarType;
    this.gui = new ConvertGrammarDialogForm ( this, this.parent );
    if ( this.convertType.equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) )
    {
      this.gui.setTitle ( Messages
          .getString ( "ConvertGrammarDialog.TitleEliminateLeftRecursion" ) ); //$NON-NLS-1$
    }
    else if ( this.convertType.equals ( ConvertGrammarType.LEFT_FACTORING ) )
    {
      this.gui.setTitle ( Messages
          .getString ( "ConvertGrammarDialog.TitleFactoring" ) ); //$NON-NLS-1$
    }
    else if ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_ENTITY_PRODUCTIONS ) )
    {
      this.gui
          .setTitle ( Messages
              .getString ( "ConvertGrammarDialog.TitleEliminateEntityProductions" ) ); //$NON-NLS-1$
    }
    else if ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_EPSILON_PRODUCTIONS ) )
    {
      this.gui
          .setTitle ( Messages
              .getString ( "ConvertGrammarDialog.TitleEliminateEpsilonProductions" ) ); //$NON-NLS-1$
    }
    else
    {
      throw new IllegalArgumentException ( "illegal convert grammar type: " //$NON-NLS-1$
          + convertGrammarType );
    }
    this.gui.styledNonterminalSymbolSetParserPanelConverted
        .setVisible ( !this.convertType
            .equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) );
    this.gui.jScrollPaneNonterminalsConverted.setVisible ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) );
    this.gui.jGTIPanelPreferences.setVisible ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) );

    this.convertMachineTableModel = new ConvertMachineTableModel ();
    this.gui.jGTITableOutline.setModel ( this.convertMachineTableModel );
    this.modelOriginal = ( DefaultGrammarModel ) this.panel.getModel ();

    this.gui.jGTITableGrammarOriginal.setModel ( this.modelOriginal
        .getGrammar () );
    this.gui.jGTITableGrammarOriginal
        .setColumnModel ( this.grammarColumnModelOriginal );
    this.gui.jGTITableGrammarOriginal.getTableHeader ().setReorderingAllowed (
        false );

    updateOriginal ();
    createConverted ();

    this.gui.jGTITableGrammarConverted.setModel ( this.modelConverted
        .getGrammar () );
    this.gui.jGTITableGrammarConverted
        .setColumnModel ( this.grammarColumnModelConverted );
    this.gui.jGTITableGrammarConverted.getTableHeader ().setReorderingAllowed (
        false );
    if ( this.convertType.equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) )
    {
      this.gui.jGTIListNonterminalsConverted
          .setDndMode ( JGTIList.DROP_BETWEEN );
      this.gui.jGTIListNonterminalsConverted
          .setCellRenderer ( new PrettyStringListCellRenderer () );
      this.gui.jGTIListNonterminalsConverted
          .setTransferHandler ( new JGTIListTransferHandler (
              TransferHandler.MOVE )
          {

            /**
             * The serial version uid.
             */
            private static final long serialVersionUID = -6137236787378034581L;


            /**
             * {@inheritDoc}
             * 
             * @see de.unisiegen.gtitool.ui.swing.dnd.JGTIListTransferHandler#importListModelRows(de.unisiegen.gtitool.ui.swing.JGTIList,
             *      de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRows, int)
             */
            @Override
            protected boolean importListModelRows ( JGTIList list,
                JGTIListModelRows rows, int targetIndex )
            {
              moveNonterminals ( list, rows, targetIndex );
              return true;
            }
          } );
      this.gui.jGTIListNonterminalsConverted.setDragEnabled ( true );
      this.gui.jGTIListNonterminalsConverted
          .addAllowedDndSource ( this.gui.jGTIListNonterminalsConverted );
    }
    this.modelConverted
        .addModifyStatusChangedListener ( new ModifyStatusChangedListener ()
        {

          /**
           * {@inheritDoc}
           * 
           * @see de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener#modifyStatusChanged(boolean)
           */
          public void modifyStatusChanged (
              @SuppressWarnings ( "unused" ) boolean modified )
          {
            updateConverted ();
          }
        } );
    updateConverted ();
    setStatus ();
    show ();
  }


  /**
   * Creates the converted {@link CFG} initial
   */
  private void createConverted ()
  {
    NonterminalSymbol start = null;
    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();
    for ( NonterminalSymbol s : this.modelOriginal.getGrammar ()
        .getNonterminalSymbolSet () )
    {
      NonterminalSymbol c = new DefaultNonterminalSymbol ( new String ( s
          .getName () ) );
      try
      {
        nonterminalSet.add ( c );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
      if ( s.equals ( this.modelOriginal.getGrammar ().getStartSymbol () ) )
      {
        start = c;
      }
    }
    TerminalSymbolSet terminals = new DefaultTerminalSymbolSet ();
    for ( TerminalSymbol s : this.modelOriginal.getGrammar ()
        .getTerminalSymbolSet () )
    {
      try
      {
        terminals
            .add ( new DefaultTerminalSymbol ( new String ( s.getName () ) ) );
      }
      catch ( TerminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
    }
    CFG newCFG = new DefaultCFG ( nonterminalSet, terminals, start );
    for ( Production p : this.modelOriginal.getGrammar ().getProduction () )
    {
      NonterminalSymbol nonterminal = null;
      for ( NonterminalSymbol s : nonterminalSet )
      {
        if ( p.getNonterminalSymbol ().getName ().equals (
            new String ( s.getName () ) ) )
        {
          nonterminal = s;
        }
      }
      ProductionWord word = new DefaultProductionWord ();
      for ( ProductionWordMember m : p.getProductionWord () )
      {
        for ( NonterminalSymbol n : nonterminalSet )
        {
          if ( m.getName ().equals ( n.getName () ) )
          {
            word.add ( n );
          }
        }
        for ( TerminalSymbol t : terminals )
        {
          if ( m.getName ().equals ( t.getName () ) )
          {
            word.add ( t );
          }
        }
      }
      newCFG.addProduction ( new DefaultProduction ( nonterminal, word ) );
    }
    for ( NonterminalSymbol n : nonterminalSet.get () )
    {
      this.nonterminals.addElement ( n );
    }
    this.initialNonterminalSize = this.nonterminals.size ();
    this.gui.jGTIListNonterminalsConverted.setModel ( this.nonterminals );
    this.modelConverted = new DefaultGrammarModel ( newCFG );
  }


  /**
   * Eliminates the DirectLeftRecursion for a {@link NonterminalSymbol} in a
   * {@link CFG}
   * 
   * @param s The {@link NonterminalSymbol}
   * @param cfg The {@link CFG}
   * @throws NonterminalSymbolSetException When new {@link NonterminalSymbol}
   *           could not be created
   */
  private void eliminateDirectLeftRecursion ( NonterminalSymbol s, CFG cfg )
      throws NonterminalSymbolSetException
  {
    ArrayList < Production > leftRecursions = getDirectLeftRecursion ( s, cfg );

    if ( !leftRecursions.isEmpty () )
    {
      DefaultNonterminalSymbol otherS = new DefaultNonterminalSymbol ( s
          .getName ()
          + "'" ); //$NON-NLS-1$
      otherS.setHighlighted ( true );
      cfg.getNonterminalSymbolSet ().add ( otherS );
      this.nonterminals.addElement ( otherS );
      ArrayList < Production > otherProductions = new ArrayList < Production > ();
      otherProductions.addAll ( cfg.getProductionForNonTerminal ( s ) );
      otherProductions.removeAll ( leftRecursions );
      for ( Production p : otherProductions )
      {
        p.getProductionWord ().add ( otherS );
      }
      for ( Production p : leftRecursions )
      {
        p.setNonterminalSymbol ( otherS );
        p.getProductionWord ().get ().remove ( 0 );
        p.getProductionWord ().add ( otherS );
      }
      Production p = new DefaultProduction ( otherS,
          new DefaultProductionWord () );
      p.setHighlighted ( true );
      cfg.addProduction ( p );
    }
  }


  /**
   * Eliminates entity productions from a {@link CFG}
   * 
   * @param cfg The {@link CFG}
   */
  private void eliminateEntityProductions ( CFG cfg )
  {
    ArrayList < Production > tmpEntityProductions = getEntityProductions ( cfg );
    for ( Production p : tmpEntityProductions )
    {
      NonterminalSymbol a = p.getNonterminalSymbol ();
      NonterminalSymbol b = ( NonterminalSymbol ) p.getProductionWord ().get (
          0 );
      for ( Production p2 : cfg.getProductionForNonTerminal ( b ) )
      {
        Production newProd = new DefaultProduction ( a, p2.getProductionWord () );
        newProd.setHighlighted ( true );
        if ( !cfg.getProduction ().contains ( newProd ) )
        {
          cfg.addProduction ( newProd );
        }
      }
      cfg.getProduction ().remove ( p );
    }
    if ( !getEntityProductions ( cfg ).isEmpty () )
    {
      eliminateEntityProductions ( cfg );
    }
  }


  /**
   * Eliminates the EpsilonProductions from a {@link CFG}
   * 
   * @param cfg The {@link CFG}
   */
  private void eliminateEpsilonProductions ( CFG cfg )
  {
    ArrayList < NonterminalSymbol > symbolsToEpsilon = new ArrayList < NonterminalSymbol > ();
    for ( Production p : getEpsilonProductions ( cfg ) )
    {
      symbolsToEpsilon.add ( p.getNonterminalSymbol () );
      if ( !p.getNonterminalSymbol ().equals ( cfg.getStartSymbol () ) )
      {
        cfg.getProduction ().remove ( p );
      }
    }
    for ( NonterminalSymbol s : symbolsToEpsilon )
    {
      for ( Production p : getProductionsForNonterminal ( s, cfg ) )
      {
        DefaultProductionWord w = new DefaultProductionWord ();
        for ( ProductionWordMember m : p.getProductionWord ().get () )
        {
          if ( !m.equals ( s ) )
          {
            w.add ( m );
          }
        }
        Production newProd = new DefaultProduction ( p.getNonterminalSymbol (),
            w );
        newProd.setHighlighted ( true );
        cfg.addProduction ( newProd );
      }
    }

  }


  /**
   * Returns the convertMachineTableModel.
   * 
   * @return The convertMachineTableModel.
   * @see #convertMachineTableModel
   */
  public ConvertMachineTableModel getConvertMachineTableModel ()
  {
    return this.convertMachineTableModel;
  }


  /**
   * Gets the direct left recursions of a {@link NonterminalSymbol} in a
   * {@link CFG}
   * 
   * @param s The {@link NonterminalSymbol}
   * @param cfg The {@link CFG}
   * @return The direct left recursions of a {@link NonterminalSymbol} in a
   *         {@link CFG}
   */
  private ArrayList < Production > getDirectLeftRecursion (
      NonterminalSymbol s, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProductionForNonTerminal ( s ) )
    {
      if ( ( p.getProductionWord ().size () > 0 )
          && p.getProductionWord ().get ( 0 ).getName ().equals ( s.getName () ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * Gets the EntitiyProductions for a {@link CFG}
   * 
   * @param cfg The {@link CFG}
   * @return The EntityProductions
   */
  private ArrayList < Production > getEntityProductions ( CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( ( p.getProductionWord ().size () == 1 )
          && ( p.getProductionWord ().get ( 0 ) instanceof NonterminalSymbol ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * Gets the EpsilonProductions of a {@link CFG}
   * 
   * @param cfg The {@link CFG}
   * @return EpsilonProductions
   */
  private ArrayList < Production > getEpsilonProductions ( CFG cfg )
  {
    ArrayList < Production > symbols = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getProductionWord ().equals ( new DefaultProductionWord () ) )
      {
        symbols.add ( p );
      }
    }
    return symbols;
  }


  /**
   * Get the gui class
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public ConvertGrammarDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the {@link JGTITable} converted
   * 
   * @return The {@link JGTITable} converted
   */
  public JGTITable getJGTITableConverted ()
  {
    return this.gui.jGTITableGrammarConverted;
  }


  /**
   * Returns the {@link JGTITable} original
   * 
   * @return The {@link JGTITable} original
   */
  public JGTITable getJGTITableOriginal ()
  {
    return this.gui.jGTITableGrammarOriginal;
  }


  /**
   * Returns the modelConverted.
   * 
   * @return The modelConverted.
   * @see #modelConverted
   */
  public DefaultGrammarModel getModelConverted ()
  {
    return this.modelConverted;
  }


  /**
   * Returns the modelOriginal.
   * 
   * @return The modelOriginal.
   * @see #modelOriginal
   */
  public DefaultGrammarModel getModelOriginal ()
  {
    return this.modelOriginal;
  }


  /**
   * Returns the panel.
   * 
   * @return The panel.
   * @see #panel
   */
  public GrammarPanel getPanel ()
  {
    return this.panel;
  }


  /**
   * Gets Productions where the {@link NonterminalSymbol} is in the
   * {@link ProductionWord}
   * 
   * @param s The {@link NonterminalSymbol}
   * @param cfg The {@link CFG}
   * @return Productions where the {@link NonterminalSymbol} is in the
   *         {@link ProductionWord}
   */
  private ArrayList < Production > getProductionsForNonterminal (
      NonterminalSymbol s, CFG cfg )
  {
    ArrayList < Production > result = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getProductionWord ().get ().contains ( s ) )
      {
        result.add ( p );
      }
    }
    return result;
  }


  /**
   * Gets the productions for 2 {@link NonterminalSymbol}s
   * 
   * @param a The first {@link NonterminalSymbol}
   * @param b The second {@link NonterminalSymbol}
   * @param cfg The {@link CFG}
   * @return Productions for 2 {@link NonterminalSymbol}s
   */
  private ArrayList < Production > getProductionsForNonterminals (
      NonterminalSymbol a, NonterminalSymbol b, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getNonterminalSymbol ().getName ().equals ( a.getName () )
          && ( p.getProductionWord ().size () > 0 )
          && p.getProductionWord ().get ( 0 ).getName ().equals ( b.getName () ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * Gets the Productions with the prefix
   * 
   * @param productions The {@link Production}s
   * @param prefix The prefix as {@link ProductionWord}
   * @return The {@link Production}s with the prefix
   */
  private ArrayList < Production > getProductionsWithPrefix (
      ArrayList < Production > productions, ProductionWord prefix )
  {
    ArrayList < Production > result = new ArrayList < Production > ();
    for ( Production p : productions )
    {
      ProductionWord word = p.getProductionWord ();
      boolean fit = true;
      for ( int k = 0 ; k < prefix.get ().size () ; k++ )
      {

        if ( ( word.size () <= k )
            || !prefix.get ( k ).equals ( word.get ( k ) ) )
        {
          fit = false;
        }
      }
      if ( fit )
      {
        result.add ( p );
      }
    }
    return result;
  }


  /**
   * Gets the same prefixes of a {@link NonterminalSymbol} in a {@link CFG}
   * 
   * @param a The {@link NonterminalSymbol}
   * @param cfg The {@link CFG}
   * @return An {@link ObjectPair} with the prefix and the productions
   */
  private ObjectPair < ProductionWord, ArrayList < Production > > getSamePrefixes (
      NonterminalSymbol a, CFG cfg )
  {
    ProductionWord prefix = new DefaultProductionWord ();
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProductionForNonTerminal ( a ) )
    {
      productions.add ( p );
    }
    while ( !productions.isEmpty () )
    {
      Production p = productions.remove ( 0 );
      for ( ProductionWordMember m : p.getProductionWord () )
      {
        prefix.add ( m );
      }

      while ( !prefix.get ().isEmpty () )
      {
        ArrayList < Production > ps = getProductionsWithPrefix ( productions,
            prefix );
        if ( !ps.isEmpty () )
        {
          ps.add ( p );
          return new ObjectPair < ProductionWord, ArrayList < Production > > (
              prefix, ps );
        }
        prefix.get ().remove ( prefix.get ().size () - 1 );
      }
    }
    return new ObjectPair < ProductionWord, ArrayList < Production > > (
        new DefaultProductionWord (), new ArrayList < Production > () );
  }


  /**
   * Returns the tableColumnModel.
   * 
   * @return The tableColumnModel.
   * @see #tableColumnModel
   */
  public ConvertMachineTableColumnModel getTableColumnModel ()
  {
    return this.tableColumnModel;
  }


  /**
   * Shows or dispose the Algorithm window
   * 
   * @param show Show or dispose
   */
  public void handleAlgorithmWindowChanged ( boolean show )
  {
    if ( ( this.algorithm == null ) || ( this.algorithm.length () == 0 ) )
    {
      TextLoader loader = new TextLoader ();
      this.algorithm = loader.loadAlgorithm ( this.convertType );
    }

    if ( this.algorithmWindow == null )
    {
      this.algorithmWindow = new TextWindow ( this.gui, this.algorithm, true,
          this.gui.jGTIToggleButtonAlgorithm, this.convertType.toString () );
    }
    if ( show )
    {
      this.algorithmWindow.show ();
    }
    else
    {
      this.algorithmWindow.dispose ();
    }
  }


  /**
   * Handles the action on the auto step button.
   */
  public void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$

    setStatus ();

    startAutoStepTimer ();
  }


  /**
   * Handles the action on the begin step button.
   */
  public void handleBeginStep ()
  {
    logger.debug ( "handleBeginStep", "handle begin step" ); //$NON-NLS-1$ //$NON-NLS-2$
    while ( !this.stepItemList.isEmpty () )
    {
      handlePreviousStep ();
    }
  }


  /**
   * Handles the action on the cancel button.
   */
  public void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handles the action on the end step button.
   */
  public void handleEndStep ()
  {
    logger.debug ( "handleEndStep", "handle end step" ); //$NON-NLS-1$ //$NON-NLS-2$
    while ( !this.endReached )
    {
      handleNextStep ();
    }
  }


  /**
   * Handles the action on the next step button.
   */
  public void handleNextStep ()
  {
    logger.debug ( "handleNextStep", "handle next step" ); //$NON-NLS-1$ //$NON-NLS-2$
    performNextStep ();
    setStatus ();
  }


  /**
   * Handles the action on the ok button.
   */
  public void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();
    while ( !this.endReached )
    {
      performNextStep ();
    }
    this.gui.setVisible ( false );

    this.panel.getMainWindow ().handleNew ( this.modelConverted.getElement (),
        false );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the previous step button.
   */
  public void handlePreviousStep ()
  {
    logger.debug ( "handlePrevious", "handle previous" ); //$NON-NLS-1$ //$NON-NLS-2$
    performPreviousStep ();
  }


  /**
   * Handles the action on the print button.
   */
  public void handlePrint ()
  {
    PrintDialog dialog = new PrintDialog ( this.parent, this );
    dialog.show ();
  }


  /**
   * Handles the action on the stop button.
   */
  public void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
    setStatus ();
  }


  /**
   * Left factoring of a {@link NonterminalSymbol} in a {@link CFG}
   * 
   * @param a The {@link NonterminalSymbol}
   * @param cfg The {@link CFG}
   */
  private void leftFactore ( NonterminalSymbol a, CFG cfg )
  {
    ObjectPair < ProductionWord, ArrayList < Production > > objectPair = getSamePrefixes (
        a, cfg );
    ProductionWord prefix = objectPair.getFirst ();
    ArrayList < Production > productions = objectPair.getSecond ();

    if ( ! ( prefix.get ().size () == 0 ) && !productions.isEmpty () )
    {
      NonterminalSymbol newSymbol = new DefaultNonterminalSymbol ( a.getName ()
          + "'" ); //$NON-NLS-1$
      newSymbol.setHighlighted ( true );
      try
      {
        cfg.getNonterminalSymbolSet ().add ( newSymbol );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
      ProductionWord newWord = new DefaultProductionWord ( prefix.get () );
      newWord.add ( newSymbol );
      Production prod = new DefaultProduction ( a, newWord );
      prod.setHighlighted ( true );
      cfg.addProduction ( prod );
      for ( int k = 0 ; k < productions.size () ; k++ )
      {
        Production p = productions.get ( k );
        ProductionWord word = new DefaultProductionWord ();
        for ( int l = prefix.get ().size () ; l < p.getProductionWord ()
            .size () ; l++ )
        {
          word.add ( p.getProductionWord ().get ( l ) );
        }
        Production pr = new DefaultProduction ( newSymbol, word );
        pr.setHighlighted ( true );
        cfg.addProduction ( pr );
        cfg.getProduction ().remove ( p );
      }
    }
  }


  /**
   * Moves {@link NonterminalSymbol}s in the {@link JGTIList}
   * 
   * @param list The List
   * @param rows The rows to move
   * @param targetIndex The new index
   */
  protected void moveNonterminals (
      @SuppressWarnings ( "unused" ) JGTIList list, JGTIListModelRows rows,
      int targetIndex )
  {
    ArrayList < NonterminalSymbol > oldNonterminals = new ArrayList < NonterminalSymbol > ();
    for ( int n = 0 ; n < this.nonterminals.getSize () ; n++ )
    {
      oldNonterminals.add ( ( NonterminalSymbol ) this.nonterminals.get ( n ) );
    }

    ArrayList < NonterminalSymbol > nonterminalList = new ArrayList < NonterminalSymbol > ();

    int [] indeces = rows.getRowIndices ();

    int newTargetIndex = targetIndex;

    if ( ( indeces.length > 0 ) && ( indeces [ 0 ] < targetIndex ) )
    {
      newTargetIndex++ ;
    }

    for ( int index : indeces )
    {
      nonterminalList.add ( ( NonterminalSymbol ) this.nonterminals
          .getElementAt ( index ) );

      if ( index < targetIndex )
      {
        newTargetIndex-- ;
      }
    }

    for ( int n = indeces.length - 1 ; n > -1 ; n-- )
    {
      this.nonterminals.remove ( indeces [ n ] );
    }

    newTargetIndex = Math.min ( newTargetIndex, this.nonterminals.getSize () );
    Collections.reverse ( nonterminalList );
    for ( NonterminalSymbol s : nonterminalList )
    {
      this.nonterminals.add ( newTargetIndex, s );
    }
    this.gui.jGTIListNonterminalsConverted.getSelectionModel ()
        .setSelectionInterval ( newTargetIndex,
            newTargetIndex + indeces.length - 1 );
  }


  /**
   * Performs the next step.
   */
  private final void performNextStep ()
  {
    CFG oldCFG = cloneConverted ();
    CFG cfg = ( CFG ) this.modelConverted.getGrammar ();
    boolean foundProduction = false;
    ArrayList < Production > oldWorkingProductions = new ArrayList < Production > ();
    if ( this.workingProductions != null )
    {
      oldWorkingProductions.addAll ( this.workingProductions );
    }

    for ( Production p : cfg.getProduction () )
    {
      p.setHighlighted ( false );
    }
    for ( NonterminalSymbol s : cfg.getNonterminalSymbolSet () )
    {
      s.setHighlighted ( false );
    }

    if ( this.convertType.equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) )
    {
      DefaultListModel oldNonterminalModel = new DefaultListModel ();
      for ( int n = 0 ; n < this.nonterminals.size () ; n++ )
      {
        oldNonterminalModel.addElement ( this.nonterminals.getElementAt ( n ) );
      }
      int iNow = this.i;
      int jNow = this.j;

      ArrayList < Production > entitiyProductions = new ArrayList < Production > ();
      if ( this.gui.jGTICheckBoxEntityProductions.isSelected () )
      {
        entitiyProductions = getEntityProductions ( cfg );
      }
      ArrayList < Production > eProductions = new ArrayList < Production > ();
      if ( this.gui.jGTICheckBoxEpsilonProductions.isSelected () )
      {
        eProductions = getEpsilonProductions ( cfg );
      }
      if ( entitiyProductions.isEmpty () )
      {
        this.initialEliminateEntityProductionsDone = true;
      }
      if ( eProductions.isEmpty () )
      {
        this.initialEliminateEpsilonProductionsDone = true;
      }

      PrettyString line = new PrettyString ();
      if ( ( !this.initialEliminateEntityProductionsDone || !this.initialEliminateEpsilonProductionsDone ) )
      {
        if ( !entitiyProductions.isEmpty () )
        {
          eliminateEntityProductions ( cfg );
          line
              .add ( new PrettyToken (
                  Messages
                      .getString ( "ConvertGrammarDialog.EliminatedEntityProductions" ), Style.COMMENT ) ); //$NON-NLS-1$
          this.initialEliminateEntityProductionsDone = true;

        }
        else if ( !eProductions.isEmpty () )
        {
          line
              .add ( new PrettyToken (
                  Messages
                      .getString ( "ConvertGrammarDialog.EliminatedEpsilonProductions" ), Style.COMMENT ) ); //$NON-NLS-1$
          eliminateEpsilonProductions ( cfg );
          this.initialEliminateEpsilonProductionsDone = true;
        }
      }
      else
      {
        line.add ( new PrettyToken ( "i: ", Style.KEYWORD ) ); //$NON-NLS-1$
        line
            .add ( new PrettyToken ( Integer.toString ( this.i ), Style.SYMBOL ) );
        line.add ( new PrettyToken ( " j: ", Style.KEYWORD ) ); //$NON-NLS-1$
        line
            .add ( new PrettyToken ( Integer.toString ( this.j ), Style.SYMBOL ) );
        line.add ( new PrettyToken ( "; ", Style.KEYWORD ) ); //$NON-NLS-1$
        // for ( int i = 1 ; i <= nonterminals.size () ; i++ )

        if ( this.i <= this.initialNonterminalSize )
        {
          NonterminalSymbol ai = ( NonterminalSymbol ) this.nonterminals
              .getElementAt ( this.i - 1 );
          // for ( int j = 0 ; j < this.i - 1 ; j++ )

          line.add ( new PrettyToken ( "Ai: ", Style.KEYWORD ) ); //$NON-NLS-1$
          line.add ( ai.toPrettyString () );
          if ( this.j <= this.i - 1 )
          {
            NonterminalSymbol aj = ( NonterminalSymbol ) this.nonterminals
                .getElementAt ( this.j - 1 );

            line.add ( new PrettyToken ( "; Aj: ", Style.KEYWORD ) ); //$NON-NLS-1$
            line.add ( aj.toPrettyString () );
            this.j++ ;
            ArrayList < Production > productions = getProductionsForNonterminals (
                ai, aj, cfg );
            ArrayList < Production > productionsOfAj = cfg
                .getProductionForNonTerminal ( aj );

            for ( Production pi : productions )
            {
              cfg.getProduction ().remove ( pi );
              pi.getProductionWord ().get ().remove ( 0 );
              for ( Production pj : productionsOfAj )
              {
                ProductionWord word = new DefaultProductionWord ( pj
                    .getProductionWord ().get () );
                word.add ( pi.getProductionWord ().get () );
                Production p = new DefaultProduction ( ai, word );
                p.setHighlighted ( true );
                cfg.addProduction ( p );
              }
            }
          }
          else
          {
            try
            {
              eliminateDirectLeftRecursion ( ai, cfg );
            }
            catch ( NonterminalSymbolSetException exc )
            {
              exc.printStackTrace ();
            }
            this.j = 1;
            this.i++ ;
            if ( this.i > this.initialNonterminalSize )
            {
              this.endReached = true;
            }
          }

        }
        else
        {
          this.endReached = true;
        }
      }

      addOutlineComment ( line );
      this.stepItemList.add ( new StepItem ( oldCFG, oldNonterminalModel, iNow,
          jNow, null, foundProduction, oldWorkingProductions ) );
    }
    else if ( this.convertType.equals ( ConvertGrammarType.LEFT_FACTORING ) )
    {
      NonterminalSymbol a = null;
      ProductionWord prefix = null;
      for ( NonterminalSymbol s : cfg.getNonterminalSymbolSet () )
      {
        ObjectPair < ProductionWord, ArrayList < Production > > pair = getSamePrefixes (
            s, cfg );
        if ( pair.getFirst ().size () > 0 )
        {
          a = s;
          prefix = pair.getFirst ();
          break;
        }
      }
      if ( a != null )
      {
        leftFactore ( a, cfg );
      }
      this.stepItemList.add ( new StepItem ( oldCFG, null, 0, 0, null,
          foundProduction, oldWorkingProductions ) );
      this.endReached = true;
      for ( NonterminalSymbol s : cfg.getNonterminalSymbolSet () )
      {
        if ( getSamePrefixes ( s, cfg ).getFirst ().size () > 0 )
        {
          this.endReached = false;
          break;
        }
      }
      PrettyString line = new PrettyString ();
      if ( ( a != null ) && ( prefix != null ) )
      {
        line.add ( Messages.getPrettyString (
            "ConvertGrammarDialog.StepFactor", a.toPrettyString (), prefix //$NON-NLS-1$
                .toPrettyString () ) );
      }
      else
      {
        line.add ( Messages
            .getPrettyString ( "ConvertGrammarDialog.StepNothingToDo" ) ); //$NON-NLS-1$
      }
      addOutlineComment ( line );
    }
    // Eliminate Entity Productions
    else if ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_ENTITY_PRODUCTIONS ) )
    {
      PrettyString line = new PrettyString ();
      if ( this.entityProductions == null )
      {
        this.entityProductions = getEntityProductions ( cfg );
        this.workingProductions = new ArrayList < Production > ();
        this.i = 0;
        this.j = 0;
      }
      Production addedProduction = null;

      int iNow = this.i;
      int jNow = this.j;

      if ( !this.entityProductions.isEmpty () )
      {
        if ( this.i < this.entityProductions.size () )
        {
          // Right side of entity production must be a nonterminal

          NonterminalSymbol a = this.entityProductions.get ( this.i )
              .getNonterminalSymbol ();
          NonterminalSymbol b = ( NonterminalSymbol ) this.entityProductions
              .get ( this.i ).getProductionWord ().get ( 0 );

          if ( this.workingProductions.isEmpty () )
          {
            // Found new entity production
            this.workingProductions.addAll ( cfg
                .getProductionForNonTerminal ( b ) );
            this.j = 0;
            foundProduction = true;
            line.add ( Messages.getPrettyString (
                "ConvertGrammarDialog.StepEntityProductionFound",//$NON-NLS-1$
                this.entityProductions.get ( this.i ).toPrettyString () ) );
          }
          else
          {
            // Eliminate entity production
            Production p = this.workingProductions.get ( this.j++ );
            Production newProd = new DefaultProduction ( a, p
                .getProductionWord () );
            newProd.setHighlighted ( true );
            if ( !cfg.getProduction ().contains ( newProd ) )
            {
              cfg.addProduction ( newProd );
              if ( ( newProd.getProductionWord ().size () == 1 )
                  && ( newProd.getProductionWord ().get ( 0 ) instanceof NonterminalSymbol ) )
              {
                this.entityProductions.add ( newProd );
              }
            }
            line.add ( Messages.getPrettyString (
                "ConvertGrammarDialog.StepCreatedProduction", newProd //$NON-NLS-1$
                    .toPrettyString () ) );

          }

          if ( this.j >= this.workingProductions.size () )
          {
            this.workingProductions.clear ();
            this.i++ ;
          }
        }
        else
        {
          line
              .add ( Messages
                  .getPrettyString ( "ConvertGrammarDialog.EliminatedEntityProductions" ) ); //$NON-NLS-1$
          cfg.getProduction ().removeAll ( this.entityProductions );
          this.endReached = true;
        }
      }
      else
      {
        line
            .add ( Messages
                .getPrettyString ( "ConvertGrammarDialog.StepNoEntityProductions" ) ); //$NON-NLS-1$
        this.endReached = true;
      }

      this.stepItemList.add ( new StepItem ( oldCFG, null, iNow, jNow,
          addedProduction, foundProduction, oldWorkingProductions ) );
      addOutlineComment ( line );
    }
    // Eliminate Epsilon Productions
    else if ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_EPSILON_PRODUCTIONS ) )
    {

      PrettyString line = new PrettyString ();

      if ( this.epsilonProductions == null )
      {
        this.epsilonProductions = getEpsilonProductions ( cfg );
        this.workingProductions = new ArrayList < Production > ();
        this.i = 0;
        this.j = 0;
      }

      int iNow = this.i;
      int jNow = this.j;
      Production addedEpsilonProduction = null;

      if ( !this.epsilonProductions.isEmpty () )
      {
        if ( this.i < this.epsilonProductions.size () )
        {
          NonterminalSymbol s = this.epsilonProductions.get ( this.i )
              .getNonterminalSymbol ();
          if ( this.workingProductions.isEmpty () )
          {
            // New epsilon production found
            this.workingProductions.addAll ( getProductionsForNonterminal ( s,
                cfg ) );
            this.j = 0;
            foundProduction = true;
            line.add ( Messages.getPrettyString (
                "ConvertGrammarDialog.StepEpsilonProductionFound", s //$NON-NLS-1$
                    .toPrettyString () ) );
          }
          else
          {
            // Eliminate nonterminal in production word
            DefaultProductionWord w = new DefaultProductionWord ();
            Production p = this.workingProductions.get ( this.j++ );
            for ( ProductionWordMember m : p.getProductionWord ().get () )
            {
              if ( !m.equals ( s ) )
              {
                w.add ( m );
              }
            }
            Production newProduction = new DefaultProduction ( p
                .getNonterminalSymbol (), w );
            newProduction.setHighlighted ( true );
            if ( w.get ().isEmpty () )
            {
              // If created an epsilon production add it to the arraylist
              if ( !this.epsilonProductions.contains ( newProduction ) )
              {
                this.epsilonProductions.add ( newProduction );
                addedEpsilonProduction = newProduction;
              }
            }

            line.add ( Messages.getPrettyString (
                "ConvertGrammarDialog.StepCreatedProduction", newProduction //$NON-NLS-1$
                    .toPrettyString () ) );
            if ( !cfg.getProduction ().contains ( newProduction ) )
            {
              cfg.addProduction ( newProduction );
            }
          }
          if ( this.j >= this.workingProductions.size () )
          {
            this.workingProductions.clear ();
            this.i++ ;
          }
        }
        else
        {
          line
              .add ( Messages
                  .getPrettyString ( "ConvertGrammarDialog.EliminatedEpsilonProductions" ) ); //$NON-NLS-1$
          for ( Production p : this.epsilonProductions )
          {
            if ( !p.getNonterminalSymbol ().equals ( cfg.getStartSymbol () ) )
            {
              cfg.getProduction ().remove ( p );
            }
          }
          this.endReached = true;
        }
      }
      else
      {
        line
            .add ( Messages
                .getPrettyString ( "ConvertGrammarDialog.StepNoEpsilonProductions" ) ); //$NON-NLS-1$
        this.endReached = true;
      }

      this.stepItemList.add ( new StepItem ( oldCFG, null, iNow, jNow,
          addedEpsilonProduction, foundProduction, oldWorkingProductions ) );
      addOutlineComment ( line );
    }
    this.gui.repaint ();
    updateConverted ();
    setStatus ();
  }


  /**
   * Performs previous step
   */
  private void performPreviousStep ()
  {
    StepItem item = this.stepItemList.remove ( this.stepItemList.size () - 1 );
    CFG old = item.getOldCFG ();
    this.modelConverted = new DefaultGrammarModel ( old );

    this.gui.jGTITableGrammarConverted.setModel ( this.modelConverted
        .getGrammar () );

    this.gui.jGTITableGrammarConverted
        .setColumnModel ( this.grammarColumnModelConverted );
    this.gui.jGTITableGrammarConverted.getTableHeader ().setReorderingAllowed (
        false );
    if ( this.convertType.equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) )
    {
      this.i = item.getINow ();
      this.j = item.getJNow ();
      if ( ( this.i == 1 ) && ( this.j == 1 ) )
      {
        if ( this.initialEliminateEntityProductionsDone )
        {
          this.initialEliminateEntityProductionsDone = false;
        }
        if ( this.initialEliminateEpsilonProductionsDone )
        {
          this.initialEliminateEpsilonProductionsDone = false;
        }
      }

      this.nonterminals = item.getOldNonterminalModel ();
      this.gui.jGTIListNonterminalsConverted.setModel ( this.nonterminals );
    }
    if ( this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_EPSILON_PRODUCTIONS )
        || this.convertType
            .equals ( ConvertGrammarType.ELIMINATE_ENTITY_PRODUCTIONS ) )
    {
      this.i = item.getINow ();
      this.j = item.getJNow ();
      Production p = item.getAddedEpsilonProduction ();
      if ( p != null )
      {
        this.modelConverted.getGrammar ().getProduction ().remove ( p );
      }
    }
    this.workingProductions = new ArrayList < Production > ();
    this.workingProductions.addAll ( item.getWorkingProductions () );
    if ( item.isFoundProduction () )
    {
      this.workingProductions.clear ();
    }
    updateConverted ();
    this.convertMachineTableModel.removeLastRow ();
    this.endReached = false;
    setStatus ();
  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {
    if ( this.gui.jGTIToolBarToggleButtonAutoStep.isSelected () )
    {
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( false );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonStop.setEnabled ( true );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( false );
      this.gui.jGTIListNonterminalsConverted.setDragEnabled ( false );
      this.gui.jGTICheckBoxEntityProductions.setEnabled ( false );
      this.gui.jGTICheckBoxEpsilonProductions.setEnabled ( false );
    }
    else
    {
      boolean beginReached = this.stepItemList.isEmpty ();
      this.gui.jGTIListNonterminalsConverted.setDragEnabled ( beginReached );
      this.gui.jGTICheckBoxEntityProductions.setEnabled ( beginReached );
      this.gui.jGTICheckBoxEpsilonProductions.setEnabled ( beginReached );
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarButtonStop.setEnabled ( false );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( !this.endReached );
    }
  }


  /**
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    Rectangle rect = PreferenceManager.getInstance ()
        .getConvertMachineDialogBounds ();
    if ( ( rect.x == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y ) )
    {
      rect.x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
          - ( this.gui.getWidth () / 2 );
      rect.y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
          - ( this.gui.getHeight () / 2 );
    }
    this.gui.setBounds ( rect );
    this.gui.setVisible ( true );
  }


  /**
   * Starts the auto step timer.
   */
  @SuppressWarnings ( "synthetic-access" )
  private final void startAutoStepTimer ()
  {
    cancelAutoStepTimer ();

    this.autoStepTimer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.autoStepTimer.schedule ( new AutoStepTimerTask (), time, time );
  }


  /**
   * Updates the converted {@link CFG}
   */
  protected void updateConverted ()
  {
    if ( !this.convertType
        .equals ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION ) )
    {
      this.gui.styledNonterminalSymbolSetParserPanelConverted
          .setText ( this.modelConverted.getGrammar ()
              .getNonterminalSymbolSet () );
    }
    this.gui.styledStartNonterminalSymbolParserPanelConverted
        .setText ( this.modelConverted.getGrammar ().getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanelConverted
        .setText ( this.modelConverted.getGrammar ().getTerminalSymbolSet () );
  }


  /**
   * Updates the original {@link CFG}
   */
  private void updateOriginal ()
  {
    this.gui.styledNonterminalSymbolSetParserPanelOriginal
        .setText ( this.modelOriginal.getGrammar ().getNonterminalSymbolSet () );
    this.gui.styledStartNonterminalSymbolParserPanelOriginal
        .setText ( this.modelOriginal.getGrammar ().getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanelOriginal
        .setText ( this.modelOriginal.getGrammar ().getTerminalSymbolSet () );
  }

}
