package de.unisiegen.gtitool.core.machines;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;


/**
 * TODO
 *
 */
public class AbstractLRMachine implements LRMachine
{

  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#getAlphabet()
   */
  public Alphabet getAlphabet ()
  {
    return null;
  }

  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#isWordAccepted()
   */
  public boolean isWordAccepted ()
  {
    return false;
  }

  /**
   * TODO
   *
   * @param testWord
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#isWordAccepted(de.unisiegen.gtitool.core.entities.Word)
   */
  public boolean isWordAccepted ( Word testWord )
  {
    return false;
  }

  /**
   * TODO
   *
   * @param word
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  public void start ( Word word )
  {
  }

  /**
   * TODO
   *
   * @param transition
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#transit(de.unisiegen.gtitool.core.entities.LRAction)
   */
  public void transit ( LRAction transition )
  {
  }

}
