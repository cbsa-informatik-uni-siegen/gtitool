package de.unisiegen.gtitool.ui.convert;

import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a {@link CFG} to a {@link DefaultTDP}
 * 
 * {@author} Christian Uhrhan
 *
 */
public class ConvertToTDP extends ConvertContextFreeGrammar
{

  /**
   * Allocate a new {@link ConvertToTDP}.
   *
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param grammar The {@link Grammar}.
   */
  public ConvertToTDP ( MainWindowForm mainWindowForm, Grammar grammar )
  {
    super ( mainWindowForm, grammar );
  }


  /**
   * {@inheritDoc}
   *
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    Machine machine = new DefaultTDP ( getAlphabet (), getPushDownAlphabet (),
        true );
    createMachinePanel ( machine );
  }
}
