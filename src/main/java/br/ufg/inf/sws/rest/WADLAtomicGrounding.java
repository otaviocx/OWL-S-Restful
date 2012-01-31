/*
 * Created 2011-07-25
 *
 * (c) 2011 Otávio Calaça Xavier
 *
 * The MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package br.ufg.inf.sws.rest;

import java.net.URI;

import org.mindswap.owls.grounding.AtomicGrounding;
import org.mindswap.owls.process.variable.Parameter;

import br.ufg.inf.sws.rest.impl.TransformationFileMap;

/**
 * This interface represents the ontological class WADLAtomicProcessGrounding. Each instance 
 * of WADLAtomicGrounding must have exactly one value for owlsProcess and exactly one 
 * for wadlResourceMethod, which sets up a one-to-one correspondence between an atomic 
 * process and a WADL Resource-Method combination.
 *
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 * 
 */
public interface WADLAtomicGrounding extends AtomicGrounding<TransformationFileMap> {
	public final static String GROUNDING_WADL = "WADL";
	
	/**
	 * Method to set the URI for WADL Document. This method gives 
	 * a value to the ontological property wadlDocument.
	 * @param wadlLoc
	 * 			The URI for WADL Document.
	 */
	public void setWADL(URI wadlLoc);
	
	/**
	 * Method to get the URI for WADL Document. This method returns 
	 * the value of the ontological property wadlDocument.
	 * @return The URI for WADL Document.
	 */
	public URI getWADL();

	/**
	 * Method to set the URI for the HTTP Request Method definition 
	 * in the WADL Document. This method gives a value to the 
	 * ontological property method of the ontological class
	 * WadlResourceMethodRef.
	 * @param method
	 * 			The URI for the method in WADL Document.
	 */
	public void setMethod(URI method);
	/**
	 * Method to get the URI for the HTTP Request Method definition 
	 * in the WADL Document. This method returns the value of the 
	 * ontological property method of the ontological class
	 * WadlResourceMethodRef.
	 * @return The URI for the method in WADL Document.
	 */
	public URI getMethod();
	
	/**
	 * Method to set the URI for the Resource definition 
	 * in the WADL Document. This method gives a value to the 
	 * ontological property resource of the ontological class
	 * WadlResourceMethodRef.
	 * @param resource
	 * 			The URI for the resource in WADL Document.
	 */
	public void setResource(URI resource);
	/**
	 * Method to get the URI for the Resource definition 
	 * in the WADL Document. This method returns the value of the 
	 * ontological property resource of the ontological class
	 * WadlResourceMethodRef.
	 * @return The URI for the resource in WADL Document.
	 */
	public URI getResource();
	
	/**
	 * Method to set the Resource-Method combination of an
	 * REST representation. This method gives a value to 
	 * the ontological property wadlResourceMethod.
	 * @param rmRef
	 * 			A instance of the class WADLResourceMethodRef
	 * that represents a combination of a resource and a method
	 * in the WADL grounding. 
	 */
	public void setResourceMethodRef(WADLResourceMethodRef rmRef);	
	/**
	 * Method to get the Resource-Method combination of an
	 * REST representation. This method returns the value of 
	 * the ontological property wadlResourceMethod.
	 * 
	 * @return A instance of the class WADLResourceMethodRef
	 * that represents a combination of a resource and a method
	 * in the WADL grounding. 
	 */
	public WADLResourceMethodRef getResourceMethodRef();
	
	/**
	 * Method that gives a URI for a given parameter.
	 *   
	 * @param parameter
	 * 			A input or output parameter. This parameter
	 * may represents some value of the ontological property
	 * wadlRequestParam or the wadlResponseParam.
	 * @return The URI for the given parameter.
	 */
	public URI getWADLParameter(Parameter parameter);
}