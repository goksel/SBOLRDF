package dissys.keele.ac.uk.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class RDFHandler {
	private Model model = null;
	private String baseUri = null;	
	
	public final static String OWL_SAME_AS_URI="http://www.w3.org/2002/07/owl#sameAs";

	public RDFHandler(InputStream stream, URI uri) {
		this(uri);
		if (stream != null) {
			this.model.read(stream, RDFS.getURI());
		}
	}
	
	public RDFHandler(URI uri)
    {      
       this.model = ModelFactory.createDefaultModel();
       setBaseURI(uri.toString());
    }
	

	public RDFHandler(Model model)
    {      
       this.model = model;
    }
	
	private void setBaseURI(String uri)
	{
		this.baseUri=uri;
		if (uri != null && uri.length() > 0) {
			model.setNsPrefix("", uri);
		}
	}
	
	public URI getBaseURI()
	{
		try
		{
			return new URI(this.baseUri);
		}
		catch (Exception exception)
		{
			return null;
		}
	}

	public void addNameSpace(String nameSpacePrefix, URI nameSpace)
	{
			this.model.setNsPrefix(nameSpacePrefix, nameSpace.toString());
		
	}

	public Resource createResource(URI resourceUri) {
		Resource resource = this.model.createResource(resourceUri.toString());
		return resource;
	}
	
	
	public String getRdfString(String format, Resource[] topLevelResources)
			throws Exception {
		String rdfData = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if (format == null || format.length() == 0) {
			format = getDefaultFormat();
		}
		try {
			RDFWriter writer = this.model.getWriter(format);
			// fasterWriter.setProperty("allowBadURIs","true");
			// fasterWriter.setProperty("relativeURIs","");
			writer.setProperty("tab", "3");
			if (topLevelResources != null && topLevelResources.length > 0) {
				writer.setProperty("prettyTypes", topLevelResources);
				//writer.setProperty("relativeURIs", "same-document, relative, parent, absolute");
				writer.setProperty("relativeURIs","same-document,relative");
			}
			writer.write(this.model, stream, this.baseUri);
			rdfData = new String(stream.toString());
		} finally {
			if (stream != null) {
				stream.close();
				stream = null;
			}
		}
		return rdfData;
	}
	
	public String getRdfString(Lang lang, Resource[] topLevelResources)
			throws Exception {
		String rdfData = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		try {
			
			RDFDataMgr.write(stream, model, lang);
			rdfData = new String(stream.toString());
		} finally {
			if (stream != null) {
				stream.close();
				stream = null;
			}
		}
		return rdfData;
	}

	public static void save(Model rdfModel, String filePath) throws IOException, FileNotFoundException {
		save(rdfModel, filePath,getDefaultFormat());
	}	
	  
	public static void save(Model rdfModel, String filePath, String format) throws IOException, FileNotFoundException {
		if (format == null || format.length() == 0) {
			format = getDefaultFormat();
		}
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(new File(filePath));
			rdfModel.write(stream, format);
		} 
		finally {
			if (stream != null) {
				stream.close();
				stream = null;
			}
		}
	}

	
	public static String getDefaultFormat() {
		return "RDF/XML-ABBREV";
	}
	
	public Model getModel()
    {
        return this.model;
    }
	
	

}
