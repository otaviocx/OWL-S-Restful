
package org.jvnet.ws.wadl2java;

import com.sun.localization.Localizable;
import com.sun.localization.LocalizableMessageFactory;
import com.sun.localization.Localizer;


/**
 * Defines string formatting method for each constant in the resource file
 * 
 */
public final class Wadl2JavaMessages {

    private final static LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("org.jvnet.ws.wadl2java.Wadl2Java");
    private final static Localizer localizer = new Localizer();

    public static Localizable localizablePACKAGE_ATTRIBUTE_REQUIRED() {
        return messageFactory.getMessage("package.attribute.required");
    }

    /**
     * package attribute must be specified
     * 
     */
    public static String PACKAGE_ATTRIBUTE_REQUIRED() {
        return localizer.localize(localizablePACKAGE_ATTRIBUTE_REQUIRED());
    }

    public static Localizable localizableWADL_DESCRIPTION_FILE(Object arg0) {
        return messageFactory.getMessage("wadl.description.file", arg0);
    }

    /**
     * WADL description {0} must be a file
     * 
     */
    public static String WADL_DESCRIPTION_FILE(Object arg0) {
        return localizer.localize(localizableWADL_DESCRIPTION_FILE(arg0));
    }

    public static Localizable localizablePARAMETER_REQUIRED(Object arg0, Object arg1) {
        return messageFactory.getMessage("parameter.required", arg0, arg1);
    }

    /**
     * Parameter {0} of method {1} is required and must not be null
     * 
     */
    public static String PARAMETER_REQUIRED(Object arg0, Object arg1) {
        return localizer.localize(localizablePARAMETER_REQUIRED(arg0, arg1));
    }

    public static Localizable localizablePROCESSING_FAILED() {
        return messageFactory.getMessage("processing.failed");
    }

    /**
     * WADL file processing failed
     * 
     */
    public static String PROCESSING_FAILED() {
        return localizer.localize(localizablePROCESSING_FAILED());
    }

    public static Localizable localizableSKIPPING_REFERENCE_TYPE(Object arg0, Object arg1) {
        return messageFactory.getMessage("skipping.reference.type", arg0, arg1);
    }

    /**
     * Warning: reference {0} in {1} identifies the wrong kind of element, skipping
     * 
     */
    public static String SKIPPING_REFERENCE_TYPE(Object arg0, Object arg1) {
        return localizer.localize(localizableSKIPPING_REFERENCE_TYPE(arg0, arg1));
    }

    public static Localizable localizableERROR_FATAL(Object arg0) {
        return messageFactory.getMessage("error.fatal", arg0);
    }

    /**
     * "Fatal Error: {0}
     * 
     */
    public static String ERROR_FATAL(Object arg0) {
        return localizer.localize(localizableERROR_FATAL(arg0));
    }

    public static Localizable localizableSKIPPING_REFERENCE(Object arg0, Object arg1) {
        return messageFactory.getMessage("skipping.reference", arg0, arg1);
    }

    /**
     * Warning: reference {0} not found in {1}, skipping
     * 
     */
    public static String SKIPPING_REFERENCE(Object arg0, Object arg1) {
        return localizer.localize(localizableSKIPPING_REFERENCE(arg0, arg1));
    }

    public static Localizable localizableTARGET_ATTRIBUTE_REQUIRED() {
        return messageFactory.getMessage("target.attribute.required");
    }

    /**
     * target attribute must be specified
     * 
     */
    public static String TARGET_ATTRIBUTE_REQUIRED() {
        return localizer.localize(localizableTARGET_ATTRIBUTE_REQUIRED());
    }

    public static Localizable localizablePROCESSING(Object arg0) {
        return messageFactory.getMessage("processing", arg0);
    }

    /**
     * Processing: {0}
     * 
     */
    public static String PROCESSING(Object arg0) {
        return localizer.localize(localizablePROCESSING(arg0));
    }

    public static Localizable localizableFAULT_NO_ELEMENT() {
        return messageFactory.getMessage("fault.no.element");
    }

    /**
     * Warning: skipping fault with no XML element
     * 
     */
    public static String FAULT_NO_ELEMENT() {
        return localizer.localize(localizableFAULT_NO_ELEMENT());
    }

    public static Localizable localizableNOT_A_FILE(Object arg0) {
        return messageFactory.getMessage("not.a.file", arg0);
    }

    /**
     * {0} is not a file
     * 
     */
    public static String NOT_A_FILE(Object arg0) {
        return localizer.localize(localizableNOT_A_FILE(arg0));
    }

    public static Localizable localizableSKIPPING_COMPILATION() {
        return messageFactory.getMessage("skipping.compilation");
    }

    /**
     * Generated code is up to date, skipping compilation
     * 
     */
    public static String SKIPPING_COMPILATION() {
        return localizer.localize(localizableSKIPPING_COMPILATION());
    }

    public static Localizable localizableTARGET_ATTRIBUTE_DIRECTORY(Object arg0) {
        return messageFactory.getMessage("target.attribute.directory", arg0);
    }

    /**
     * target attribute {0} must specify a directory
     * 
     */
    public static String TARGET_ATTRIBUTE_DIRECTORY(Object arg0) {
        return localizer.localize(localizableTARGET_ATTRIBUTE_DIRECTORY(arg0));
    }

    public static Localizable localizableDESCRIPTION_REQUIRED() {
        return messageFactory.getMessage("description.required");
    }

    /**
     * description attribute must be specified
     * 
     */
    public static String DESCRIPTION_REQUIRED() {
        return localizer.localize(localizableDESCRIPTION_REQUIRED());
    }

    public static Localizable localizableWADL_DESCRIPTION_MUST_EXIST(Object arg0) {
        return messageFactory.getMessage("wadl.description.must.exist", arg0);
    }

    /**
     * WADL description {0} must exist
     * 
     */
    public static String WADL_DESCRIPTION_MUST_EXIST(Object arg0) {
        return localizer.localize(localizableWADL_DESCRIPTION_MUST_EXIST(arg0));
    }

    public static Localizable localizableCREATE_INSTANCE() {
        return messageFactory.getMessage("create.instance");
    }

    /**
     * Create new instance
     * 
     */
    public static String CREATE_INSTANCE() {
        return localizer.localize(localizableCREATE_INSTANCE());
    }

    public static Localizable localizableERROR(Object arg0) {
        return messageFactory.getMessage("error", arg0);
    }

    /**
     * Error: {0}
     * 
     */
    public static String ERROR(Object arg0) {
        return localizer.localize(localizableERROR(arg0));
    }

    public static Localizable localizableWARNING(Object arg0) {
        return messageFactory.getMessage("warning", arg0);
    }

    /**
     * Warning: {0}
     * 
     */
    public static String WARNING(Object arg0) {
        return localizer.localize(localizableWARNING(arg0));
    }

    public static Localizable localizableINVOCATION_FAILED() {
        return messageFactory.getMessage("invocation.failed");
    }

    /**
     * Invocation failed, see FaultInfo property for details
     * 
     */
    public static String INVOCATION_FAILED() {
        return localizer.localize(localizableINVOCATION_FAILED());
    }

    public static Localizable localizableINFO(Object arg0) {
        return messageFactory.getMessage("info", arg0);
    }

    /**
     * Info: {0}
     * 
     */
    public static String INFO(Object arg0) {
        return localizer.localize(localizableINFO(arg0));
    }

    public static Localizable localizableUNKNOWN_OPTION(Object arg0) {
        return messageFactory.getMessage("unknown.option", arg0);
    }

    /**
     * Unknown option: {0}
     * 
     */
    public static String UNKNOWN_OPTION(Object arg0) {
        return localizer.localize(localizableUNKNOWN_OPTION(arg0));
    }

    public static Localizable localizableUSAGE() {
        return messageFactory.getMessage("usage");
    }

    /**
     * Usage: wadl2java -o outputDir -p package [-a] [-c customizationFile]* file.wadl
     * 
     */
    public static String USAGE() {
        return localizer.localize(localizableUSAGE());
    }

    public static Localizable localizableELEMENT_NOT_FOUND(Object arg0) {
        return messageFactory.getMessage("element.not.found", arg0);
    }

    /**
     * Warning: element {0} not found
     * 
     */
    public static String ELEMENT_NOT_FOUND(Object arg0) {
        return localizer.localize(localizableELEMENT_NOT_FOUND(arg0));
    }

    public static Localizable localizableNOT_A_DIRECTORY(Object arg0) {
        return messageFactory.getMessage("not.a.directory", arg0);
    }

    /**
     * {0} is not a directory
     * 
     */
    public static String NOT_A_DIRECTORY(Object arg0) {
        return localizer.localize(localizableNOT_A_DIRECTORY(arg0));
    }

    public static Localizable localizableTARGET_DIRECTORY_MUST_EXIST(Object arg0) {
        return messageFactory.getMessage("target.directory.must.exist", arg0);
    }

    /**
     * target directory {0} must exist
     * 
     */
    public static String TARGET_DIRECTORY_MUST_EXIST(Object arg0) {
        return localizer.localize(localizableTARGET_DIRECTORY_MUST_EXIST(arg0));
    }

}
