
package org.jvnet.ws.wadl2java.ast;

import com.sun.localization.Localizable;
import com.sun.localization.LocalizableMessageFactory;
import com.sun.localization.Localizer;


/**
 * Defines string formatting method for each constant in the resource file
 * 
 */
public final class AstMessages {

    private final static LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("org.jvnet.ws.wadl2java.ast.ast");
    private final static Localizer localizer = new Localizer();

    public static Localizable localizableTEMPLATE_VALUE_MISSING(Object arg0) {
        return messageFactory.getMessage("template.value.missing", arg0);
    }

    /**
     * No value for required template parameter: {0}
     * 
     */
    public static String TEMPLATE_VALUE_MISSING(Object arg0) {
        return localizer.localize(localizableTEMPLATE_VALUE_MISSING(arg0));
    }

    public static Localizable localizableMATRIX_VALUE_MISSING(Object arg0) {
        return messageFactory.getMessage("matrix.value.missing", arg0);
    }

    /**
     * No value for required matrix parameter: {0}
     * 
     */
    public static String MATRIX_VALUE_MISSING(Object arg0) {
        return localizer.localize(localizableMATRIX_VALUE_MISSING(arg0));
    }

}
