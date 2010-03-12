package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR1Parser;
import de.unisiegen.gtitool.core.machines.lr.LR1Parser;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert an LR1 parser to an LALR1 parser The result might contain ambigious
 * actions
 */
public class ConvertToLALR1Parser extends ConvertToLRParser
{

  /**
   * TODO
   * 
   * @param mainWindow
   * @param parser
   * @throws AlphabetException
   */
  public ConvertToLALR1Parser ( final MainWindowForm mainWindow,
      final LR1Parser parser ) throws AlphabetException
  {
    super ( mainWindow, parser.getGrammar () );

    this.parser = parser;
  }


  /**
   * The source parser
   */
  private LR1Parser parser;


  /**
   * The resulting parser
   */
  private DefaultLR1Parser result;


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    try
    {
      this.result = new DefaultLR1Parser ( this.parser.getLR1 ().toLALR1 (),
          this.parser.getGrammar () );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    createMachinePanel ( this.result );
  }
}
