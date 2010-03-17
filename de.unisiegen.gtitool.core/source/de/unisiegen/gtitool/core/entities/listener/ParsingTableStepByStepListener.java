package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * The listener for receiving {@link ParsingTable} changes during the step by
 * step creation of the {@link ParsingTable}
 * 
 * @author Christian Uhrhan
 */
public interface ParsingTableStepByStepListener extends EventListener
{

  /**
   * Invoked when the check whether a {@link Production} is to be added as a
   * {@link ParsingTable} entry is evaluated to true
   * 
   * @param p The {@link Production} which is going to be added
   * @param ts The {@link TerminalSymbol}
   * @param cause The {@link ParsingTable.EntryCause} why {@code p} is going to
   *          be added
   */
  public void productionAddedAsEntry ( final Production p,
      final TerminalSymbol ts, final ParsingTable.EntryCause cause );
}
