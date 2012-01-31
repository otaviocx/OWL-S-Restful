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
package br.ufg.inf.sws.rest.impl;

import impl.owl.WrappedIndividual;

import java.net.URI;

import org.mindswap.owl.OWLIndividual;

import br.ufg.inf.sws.rest.WADLResourceMethodRef;
import br.ufg.inf.sws.rest.vocabulary.OWLSRestful;

/**
 * Default implementation of {@link WADLResourceMethodRef}.
 * 
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class WADLResourceMethodRefImpl extends WrappedIndividual implements WADLResourceMethodRef {
	/**
	 * @param ind
	 * 			Entity that represents the corresponding ontological class.
	 */
	public WADLResourceMethodRefImpl(OWLIndividual ind) {
		super(ind);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLResourceMethodRef#setMethod(java.net.URI)
	 */
	public void setMethod(URI method) {
		setProperty(OWLSRestful.method, method);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLResourceMethodRef#getMethod()
	 */
	public URI getMethod() {
		return getPropertyAsURI(OWLSRestful.method);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLResourceMethodRef#removeMethod()
	 */
	public void removeMethod() {
		if (hasProperty(OWLSRestful.method))
			removeProperty(OWLSRestful.method, null);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLResourceMethodRef#setResource(java.net.URI)
	 */
	public void setResource(URI resource) {
		setProperty(OWLSRestful.resource, resource);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLResourceMethodRef#getResource()
	 */
	public URI getResource() {
		return getPropertyAsURI(OWLSRestful.resource);
	}

	/* (non-Javadoc)
	 * @see br.ufg.inf.sws.rest.WADLResourceMethodRef#removeResource()
	 */
	public void removeResource() {
		if (hasProperty(OWLSRestful.resource))
			removeProperty(OWLSRestful.resource, null);
	}
	
	/* (non-Javadoc)
	 * @see impl.owl.WrappedIndividual#delete()
	 */
	public void delete() {
		removeMethod();
		removeResource();
		super.delete();
	}
}
