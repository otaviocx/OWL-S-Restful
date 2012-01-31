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

import impl.owls.grounding.BaseMessageMap;

import java.net.URI;

import org.mindswap.owl.OWLDataProperty;
import org.mindswap.owl.OWLDataValue;
import org.mindswap.owl.OWLIndividual;
import org.mindswap.owl.OWLObjectProperty;
import org.mindswap.owls.grounding.MessageMap;
import org.mindswap.owls.vocabulary.OWLS;

import br.ufg.inf.sws.rest.vocabulary.OWLSRestful;

/**
 * Default implementation of {@link MessageMap} for the WADL RESTful Grounding.
 * This implementation uses the {@link TransformationFileMap} class to execute the transformation fot REST resources to ontological information.
 * 
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public class WADLMessageParamMap extends BaseMessageMap<TransformationFileMap> implements MessageMap<TransformationFileMap> {

	/**
	 * @param ind
	 * 			Entity that represents the corresponding ontological class.
	 */
	public WADLMessageParamMap(OWLIndividual ind) {
		super(ind);
	}
	
	/* (non-Javadoc)
	 * @see impl.owls.grounding.BaseMessageMap#groundingParameterProperty()
	 */
	protected OWLDataProperty groundingParameterProperty() { 
		return OWLSRestful.wadlMessageParam; 
	}
	/* (non-Javadoc)
	 * @see impl.owls.grounding.BaseMessageMap#groundingParameterValue(java.lang.String)
	 */
	protected OWLDataValue groundingParameterValue(final String value) { 
		return getOntology().createDataValue(URI.create(value)); 
	}
	/* (non-Javadoc)
	 * @see impl.owls.grounding.BaseMessageMap#owlsParameterProperty()
	 */
	protected OWLObjectProperty owlsParameterProperty() { 
		return OWLS.Grounding.owlsParameter; 
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.MessageMap#getTransformation()
	 */
	public TransformationFileMap getTransformation()
	{
	    return getPropertyAs(OWLSRestful.transformationFile, TransformationFileMap.class);
	}

	/* (non-Javadoc)
	 * @see org.mindswap.owls.grounding.MessageMap#setTransformation(java.lang.Object)
	 */
	public void setTransformation(final TransformationFileMap transformation)
	{
	    setProperty(OWLSRestful.transformationFile, transformation);
	}

}
