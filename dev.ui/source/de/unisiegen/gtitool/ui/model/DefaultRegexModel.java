package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * TODO
 */
public class DefaultRegexModel implements DefaultModel, Storable, Modifyable
{

  private DefaultRegex regex;


  /**
   * The {@link Regex} version.
   */
  private static final int REGEX_VERSION = 1;


  /**
   * TODO
   */
  public DefaultRegexModel ( DefaultRegex regex )
  {
    this.regex = regex;
  }


  
  /**
   * Returns the regex.
   *
   * @return The regex.
   * @see #regex
   */
  public DefaultRegex getRegex ()
  {
    return this.regex;
  }
  
  /**
   * TODO
   * 
   * @throws StoreException
   */
  public DefaultRegexModel ( Element element ) throws StoreException
  {
    boolean foundVersion = false;
    for ( Attribute attr : element.getAttribute () )
    {
      if ( attr.getName ().equals ( "regexVersion" ) ) //$NON-NLS-1$
      {
        foundVersion = true;
        if ( attr.getValueInt () != REGEX_VERSION )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.IncompatibleVersion" ) ); //$NON-NLS-1$
        }
      }
    }
    if ( !foundVersion )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.model.DefaultModel#getElement()
   */
  public Element getElement ()
  {
    Element newElement = new Element ( "RegexModel" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "regexVersion", //$NON-NLS-1$
        REGEX_VERSION ) );
    newElement.addElement ( this.regex.getAlphabet ().getElement () );
    newElement.addElement ( this.regex.getElement () );
    return newElement;
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.regex.addModifyStatusChangedListener ( listener );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.regex.isModified ();
  }


  /**
   * TODO
   * 
   * @param listener
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.regex.removeModifyStatusChangedListener ( listener );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.regex.resetModify ();
  }

}
