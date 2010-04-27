package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultWord;
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
   * the accept status
   */
  private boolean wordAccepted;
  
  /**
   * the rejected status
   */
  private boolean rejected;


  /**
   * allocates a new {@link StatelessMachineHistoryItem}
   * 
   * @param input the {@link Word}
   * @param stack the {@link Stack}
   * @param wordAccepted the actual accepted status
   * @param rejected the actual rejected status
   */
  public StatelessMachineHistoryItem ( final Word input, final Stack stack,
      final boolean wordAccepted, final boolean rejected )
  {
    this.input = new DefaultWord ( input );
    this.stack = new DefaultStack ( stack );
    this.wordAccepted = wordAccepted;
    this.rejected = rejected;
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
   * retrieves the accepted status
   * 
   * @return the accepted status
   */
  public boolean getWordAccepted ()
  {
    return this.wordAccepted;
  }
  
  
  /**
   * 
   * retrieves the rejected status
   *
   * @return the rejected status
   */
  public boolean getRejected()
  {
    return this.rejected;
  }
}
