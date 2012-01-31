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

import org.mindswap.owl.OWLIndividual;

/**
 * Representation of the ontological class WadlResourceMethodRef.
 *
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 * 
 */
public interface WADLResourceMethodRef extends OWLIndividual {
	/**
	 * @param method
	 * 			The URI for the method definition in the WADL document.
	 */
	public void setMethod(URI method);
	/**
	 * @return The defined URI for the method definition in the WADL document.
	 */
	public URI getMethod();
	/**
	 * Removes the reference for the URI for the method definition in the WADL document.
	 */
	public void removeMethod();
	
	/**
	 * @param resource
	 * 			The URI for the resource definition in the WADL document.
	 */
	public void setResource(URI resource);
	/**
	 * @return The defined URI for the resource definition in the WADL document.
	 */
	public URI getResource();
	/**
	 * Removes the reference for the URI for the resource definition in the WADL document.
	 */
	public void removeResource();
}
