package de.unisiegen.gtitool.ui.model;


import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * blub
 */
public class FollowSetStepByStepTableModel extends AbstractTableModel
{

  /**
   * generated serial
   */
  private static final long serialVersionUID = 392056323779056359L;


  /**
   * follow sets
   */
  private HashMap < NonterminalSymbol, TerminalSymbolSet > followSets;


  /**
   * The {@link CFG}
   */
  private CFG cfg;


  /**
   * ctor
   * 
   * @param followSets parameter
   * @param cfg cfg
   */
  public FollowSetStepByStepTableModel (
      final HashMap < NonterminalSymbol, TerminalSymbolSet > followSets,
      final CFG cfg )
  {
    this.followSets = followSets;
    this.cfg = cfg;
  }
  
  
  /**
   * 
   * resets the follow sets
   *
   * @param followSets The follow sets
   */
  public void setFollowSet(final HashMap<NonterminalSymbol,TerminalSymbolSet> followSets)
  {
    this.followSets = followSets;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return 2;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.followSets.size ();
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
      case 0 :
        return this.cfg.getNonterminalSymbolSet ().get ( rowIndex );
      case 1 :
        return this.followSets.get ( this.cfg.getNonterminalSymbolSet ().get (
            rowIndex ) );
    }
    return new PrettyString ();
  }

}
