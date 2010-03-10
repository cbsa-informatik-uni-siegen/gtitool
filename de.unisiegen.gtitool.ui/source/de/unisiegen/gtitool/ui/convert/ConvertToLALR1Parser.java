package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.machines.lr.DefaultLR1Parser;
import de.unisiegen.gtitool.core.machines.lr.LR1Parser;


/**
 * Convert an LR1 parser to an LALR1 parser The result might contain ambigious
 * actions
 */
public class ConvertToLALR1Parser implements Converter
{

  /**
   * TODO
   * 
   * @param parser
   */
  public ConvertToLALR1Parser ( final LR1Parser parser )
  {
    this.parser = parser;
    this.result = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.Converter#convert(de.unisiegen.gtitool.core.entities.InputEntity.EntityType,
   *      de.unisiegen.gtitool.core.entities.InputEntity.EntityType, boolean,
   *      boolean)
   */
  public void convert ( EntityType fromEntityType, EntityType toEntityType,
      boolean complete, boolean cb )
  {
    try
    {
      this.result = new DefaultLR1Parser ( this.parser.getLR1 ().toLALR1 (),
          this.parser.getGrammar () );
    }
    catch ( Exception e )
    {
      e.printStackTrace ();

      System.exit ( 1 );
    }
  }


  private LR1Parser parser;


  private LR1Parser result;
}
