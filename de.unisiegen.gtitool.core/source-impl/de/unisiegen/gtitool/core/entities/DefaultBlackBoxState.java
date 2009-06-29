package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * {@link DefaultState} without a displayed name
 */
public class DefaultBlackBoxState extends DefaultState
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7052595983183170428L;


  /**
   * TODO
   */
  private boolean ready = false;


  /**
   * Creates a new of {@link DefaultBlackBoxState}
   * 
   * @param name The name of the state
   * @throws StateException When State creator has a problem
   */
  public DefaultBlackBoxState ( String name ) throws StateException
  {
    super ( name );
  }


  /**
   * TODO
   * 
   * @param r
   */
  public void setReady ( boolean r )
  {
    this.ready = r;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.DefaultState#toPrettyString()
   */
  @Override
  public PrettyString toPrettyString ()
  {
    if ( this.ready )
    {
      return super.toPrettyString ();
    }
    return new PrettyString ();
  }
}
