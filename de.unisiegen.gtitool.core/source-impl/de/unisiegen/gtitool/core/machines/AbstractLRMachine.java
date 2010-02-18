package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;


/**
 * TODO
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
    return this.wordAccepted;
  }


  /**
   * TODO
   * 
   * @param word
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  public void start ( Word word )
  {
    this.word = word;
    this.wordIndex = 0;
    this.wordAccepted = false;
  }


  /**
   * TODO
   * 
   * @param transition
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#transit(de.unisiegen.gtitool.core.entities.LRAction)
   */
  public boolean transit ( LRAction transition )
  {
    return false;
  }


  protected TerminalSymbol currentTerminal ()
  {
    return this.wordIndex < this.word.size () ? new DefaultTerminalSymbol (
        this.word.get ( this.wordIndex ).toString () ) : null;
  }


  public void autoTransit ()
  {

  }


  protected void nextSymbol ()
  {
    ++this.wordIndex;
  }


  protected void accept ()
  {
    this.wordAccepted = true;
  }


  private Word word;


  private int wordIndex;


  private boolean wordAccepted;
}
