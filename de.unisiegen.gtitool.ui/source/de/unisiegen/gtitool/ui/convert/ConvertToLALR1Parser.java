package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.machines.lr.LR1Parser;


/**
 * TODO
 */
public class ConvertToLALR1Parser implements Converter
{

  public ConvertToLALR1Parser ( final LR1Parser parser )
  {
    this.parser = parser;
    this.result = null;
  }


  public void convert ( EntityType fromEntityType, EntityType toEntityType,
      boolean complete, boolean cb )
  {

    // this.result = new DefaultLR1Parser ();
  }


  private LR1Parser parser;


  private LR1Parser result;
}
