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

import br.ufg.inf.sws.rest.vocabulary.OWLSRestful;

/**
 * The transformation class for WADL RESTful Grounding. This class references the same name ontological class. 
 * 
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class TransformationFileMap extends WrappedIndividual {
	/**
	 * @param ind
	 * 			Entity that represents the corresponding ontological class.
	 */
	public TransformationFileMap(OWLIndividual ind) {
		super(ind);
	}

	/**
	 * @param uri
	 * 			The URI for the transformation file.
	 */
	public void setTransformationURI(URI uri) {
		setProperty(OWLSRestful.transformationURI, uri);
	}

	/**
	 * @return The URI for the transformation file.
	 */
	public URI getTransformationURI() {
		return getPropertyAsURI(OWLSRestful.transformationURI);
	}

	/**
	 * Removes the reference for the URI for the transformation file.
	 */
	public void removeTransformationURI() {
		if (hasProperty(OWLSRestful.transformationURI))
			removeProperty(OWLSRestful.transformationURI, null);
	}

	/**
	 * @param mediaType
	 * 			The media type of the transformation file. A resource can have more than one transformation file, depending of the representation format.
	 */
	public void setTransformationMediaType(String mediaType) {
		setProperty(OWLSRestful.transformationMediaType, mediaType);
	}

	/**
	 * @return The media type of the transformation file.
	 */
	public String getTransformationMediaType() {
		return getPropertyAsString(OWLSRestful.transformationMediaType);
	}

	/**
	 * Removes the reference for the media type of the transformation file.
	 */
	public void removeTransformationMediaType() {
		if (hasProperty(OWLSRestful.transformationMediaType))
			removeProperty(OWLSRestful.transformationMediaType, null);
	}

	/* (non-Javadoc)
	 * @see impl.owl.WrappedIndividual#delete()
	 */
	public void delete() {
		removeTransformationMediaType();
		removeTransformationURI();
		super.delete();
	}
}
