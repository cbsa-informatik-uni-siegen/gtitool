package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.StatelessMachinePanel;
import de.unisiegen.gtitool.ui.model.DefaultStatelessMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * the abstract converter to {@link StatelessMachine}s
 */
public abstract class AbstractConvertGrammarStatelessMachine extends
    AbstractConvertGrammar
{

  /**
   * the {@link DefaultStatelessMachineModel}
   */
  private DefaultStatelessMachineModel model;


  /**
   * the {@link StatelessMachinePanel}
   */
  private StatelessMachinePanel panel;


  /**
   * Allocates a new {@link AbstractConvertGrammarStatelessMachine}
   * 
   * @param mwf the {@link MainWindowForm}
   * @param grammar the {@link Grammar}
   * @throws AlphabetException 
   */
  public AbstractConvertGrammarStatelessMachine ( MainWindowForm mwf,
      Grammar grammar ) throws AlphabetException
  {
    super ( mwf, grammar, grammar.getAlphabet () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachinePanel(de.unisiegen.gtitool.core.machines.Machine)
   */
  @Override
  protected void createMachinePanel ( Machine machine )
  {
    this.model = new DefaultStatelessMachineModel (
        ( StatelessMachine ) machine );
    this.model.setGrammar ( getGrammar () );
    setNewPanel ( new StatelessMachinePanel ( getMainWindowForm (), this.model,
        null ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#setNewPanel(de.unisiegen.gtitool.ui.logic.MachinePanel)
   */
  @Override
  protected void setNewPanel ( final MachinePanel panel )
  {
    this.panel = ( StatelessMachinePanel ) panel;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#getNewPanel()
   */
  @Override
  public MachinePanel getNewPanel ()
  {
    return this.panel;
  }
}
