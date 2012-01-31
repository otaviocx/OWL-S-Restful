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
package br.ufg.inf.sws.rest.vocabulary;

import org.mindswap.owl.OWLClass;
import org.mindswap.owl.OWLDataProperty;
import org.mindswap.owl.OWLObjectProperty;
import org.mindswap.owl.OWLOntology;
import org.mindswap.owl.vocabulary.Vocabulary;
import org.mindswap.utils.URIUtils;

/**
 * A vocabulary that references the RESTfulGrounding properties, instances and classes.
 * @author Otávio Calaça Xavier <otaviocx@gmail.com>
 *
 */
public abstract class OWLSRestful extends Vocabulary {
	public static final String version = "v1.0";
	public static final String URI = "http://solutio.in/owlsrestful/";
	
	public static final String GROUNDING_NS = URI + "RESTfulGrounding.owl#";
	
	public static final OWLOntology ont = loadOntology(GROUNDING_NS);
		
	public static OWLClass WadlGrounding = ont.getClass(URIUtils.createURI(GROUNDING_NS + "WadlGrounding"));
	public static OWLClass WadlAtomicProcessGrounding = ont.getClass(URIUtils.createURI(GROUNDING_NS + "WadlAtomicProcessGrounding"));
	public static OWLClass WadlResourceMethodRef = ont.getClass(URIUtils.createURI(GROUNDING_NS + "WadlResourceMethodRef"));
	public static OWLClass WadlMessageParamMap = ont.getClass(URIUtils.createURI(GROUNDING_NS + "WadlMessageParamMap"));
	public static OWLClass WadlRequestParamMap = ont.getClass(URIUtils.createURI(GROUNDING_NS + "WadlRequestParamMap"));
	public static OWLClass WadlResponseParamMap = ont.getClass(URIUtils.createURI(GROUNDING_NS + "WadlResponseParamMap"));
	public static OWLClass TransformationFileMap = ont.getClass(URIUtils.createURI(GROUNDING_NS + "TransformationFileMap"));
	
	public static OWLDataProperty method = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "method"));
	public static OWLDataProperty resource = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "resource"));
	public static OWLDataProperty wadlMessageParam = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "wadlMessageParam"));
	public static OWLDataProperty wadlDocument = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "wadlDocument"));
	public static OWLDataProperty wadlVersion = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "wadlVersion"));
	public static OWLDataProperty transformationURI = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "transformationURI"));
	public static OWLDataProperty transformationMediaType = ont.getDataProperty(URIUtils.createURI(GROUNDING_NS + "transformationMediaType"));
	
	public static OWLObjectProperty wadlResourceMethod = ont.getObjectProperty(URIUtils.createURI(GROUNDING_NS + "wadlResourceMethod"));
	public static OWLObjectProperty wadlRequestParam = ont.getObjectProperty(URIUtils.createURI(GROUNDING_NS + "wadlRequestParam"));
	public static OWLObjectProperty wadlResponseParam = ont.getObjectProperty(URIUtils.createURI(GROUNDING_NS + "wadlResponseParam"));
	public static OWLObjectProperty transformationFile = ont.getObjectProperty(URIUtils.createURI(GROUNDING_NS + "transformationFile"));
		
}
