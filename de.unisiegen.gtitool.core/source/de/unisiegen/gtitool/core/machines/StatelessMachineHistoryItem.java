package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Word;


/**
 * History item for the {@link StatelessMachine}
 */
public final class StatelessMachineHistoryItem
{

  /**
   * the input {@link Word}
   */
  private Word input;


  /**
   * the {@link Stack}
   */
  private Stack stack;


  /**
   * the parsing index
   */
  private int wordIndex;


  /**
   * the accept status
   */
  private boolean wordAccepted;


  /**
   * allocates a new {@link StatelessMachineHistoryItem}
   * 
   * @param input the {@link Word}
   * @param stack the {@link Stack}
   * @param wordIndex the actual parsing index
   * @param wordAccepted the actual accepted status
   */
  public StatelessMachineHistoryItem ( final Word input, final Stack stack,
      final int wordIndex, final boolean wordAccepted )
  {
    this.input = input;
    this.stack = stack;
    this.wordIndex = wordIndex;
    this.wordAccepted = wordAccepted;
  }


  /**
   * retrieves the input {@link Word}
   * 
   * @return the input {@link Word}
   */
  public Word getWord ()
  {
    return this.input;
  }


  /**
   * retrieves the {@link Stack}
   * 
   * @return the {@link Stack}
   */
  public Stack getStack ()
  {
    return this.stack;
  }


  /**
   * retrieves the parsing index
   * 
   * @return the parsing index
   */
  public int getWordIndex ()
  {
    return this.wordIndex;
  }


  /**
   * retrieves the accepted status
   * 
   * @return the accepted status
   */
  public boolean getWordAccepted ()
  {
    return this.wordAccepted;
  }
}
