package de.unisiegen.gtitool.core.machines.pda;


import java.util.ArrayList;

import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.AbstractStatelessMachine;


/**
 * The class for the top down parser (pda)
 * 
 *@author Christian Uhrhan
 */
public class DefaultTDP extends AbstractStatelessMachine implements TDP
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1371164970141783189L;


  /**
   * the {@link CFG} for this push down automaton
   */
  private CFG cfg;


  /**
   * the {@link ParsingTable} for this top down parser
   */
  private ParsingTable parsingTable;


  /**
   * Allocates a new {@link PDA}.
   * 
   * @param cfg The {@link CFG} of this {@link PDA}.
   * @throws AlphabetException
   * @throws TerminalSymbolSetException 
   * @throws GrammarInvalidNonterminalException 
   */
  public DefaultTDP ( CFG cfg ) throws AlphabetException, GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    super ( cfg.getAlphabet () );
    this.cfg = cfg;
    this.parsingTable = new DefaultParsingTable ( this.cfg );
  }


  protected ArrayList < Production > getPossibleReductions ()
  {
    ArrayList < Production > prods = new ArrayList < Production > ();

    return prods;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public void autoTransit () throws MachineAmbigiousActionException
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#getTableModel()
   */
  public TableModel getTableModel ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getTableColumnModel()
   */
  public TableColumnModel getTableColumnModel ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public MachineType getMachineType ()
  {
    return MachineType.TDP;
  }
}
