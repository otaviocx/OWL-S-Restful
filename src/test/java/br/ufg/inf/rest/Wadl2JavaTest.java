package br.ufg.inf.rest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.jvnet.ws.wadl2java.Wadl2Java;

import com.sun.codemodel.JClassAlreadyExistsException;

public class Wadl2JavaTest {

	@Test
	public void process() throws IOException, JAXBException, JClassAlreadyExistsException, URISyntaxException {
		Wadl2Java w2j = new Wadl2Java(new File("./"), "facebook", true, new ArrayList<File>());		
		w2j.process(new URI("http://solutio.in/owlsrestful/facebook.wadl"));				
	}

}
