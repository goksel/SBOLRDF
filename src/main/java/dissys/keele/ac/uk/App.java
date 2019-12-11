package dissys.keele.ac.uk;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFFormat;
import org.sbolstandard.core2.examples.ComponentDefinitionOutput;
import org.sbolstandard.core2.examples.CutExample;
import org.sbolstandard.core2.examples.GettingStartedExample;
import org.sbolstandard.core2.examples.ModuleDefinitionOutput;
import org.sbolstandard.core2.examples.Provenance_CodonOptimization;
import org.sbolstandard.core2.examples.SimpleComponentDefinitionExample;

import com.google.common.io.Resources;

import dissys.keele.ac.uk.util.FileHelper;
import dissys.keele.ac.uk.util.RDFHandler;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	new ComponentDefinitionOutput().createComponentDefinitionOutput();
    	new GettingStartedExample().main(null);
    	new CutExample().main(null);
    	new ModuleDefinitionOutput().main(null);
    	new Provenance_CodonOptimization().main(args);
    	new SimpleComponentDefinitionExample().main(args);
    	
    	
    	File folder = new File("./RDFExamples");
    	File[] listOfFiles = folder.listFiles();
    	for (File file:listOfFiles)
    	{
    		String name=file.getName();
    		if (name.endsWith(".sbol"))
    		{
    	    	InputStream is=new FileInputStream(new File("./RDFExamples/" + name));
    	    	
    	    	RDFHandler rdfHandler=new RDFHandler(is, URI.create("http://partsregistry.org/"));
    	    	rdfHandler.addNameSpace("pr", URI.create("http://partsregistry.org/"));
    	    	ArrayList<Resource> resources=new ArrayList<Resource>();
    	    	resources.add(rdfHandler.createResource(URI.create("http://sbols.org/v2#ComponentDefinition")));
    	    	resources.add(rdfHandler.createResource(URI.create("http://sbols.org/v2#Sequence")));
    	    	resources.add(rdfHandler.createResource(URI.create("http://sbols.org/v2#ModuleDefinition")));
    	    	
    	    	Resource[] topLevelResources=resources.toArray(new Resource[resources.size()]);
    	    	
    	    	FileHelper.WriteToFile(file + ".pretty", rdfHandler.getRdfString("RDF/XML-ABBREV",topLevelResources));
    	    	FileHelper.WriteToFile(file + ".rdfxml", rdfHandler.getRdfString("RDF/XML",topLevelResources));
    	    	FileHelper.WriteToFile(file + ".turtle", rdfHandler.getRdfString(Lang.TRIG,topLevelResources));
    	    	FileHelper.WriteToFile(file + ".rdfjson", rdfHandler.getRdfString("RDF/JSON",topLevelResources));
    	    	FileHelper.WriteToFile(file + ".n3", rdfHandler.getRdfString("N3",topLevelResources));
    	    	FileHelper.WriteToFile(file + ".ntriples", rdfHandler.getRdfString("NT",topLevelResources));
    	    	FileHelper.WriteToFile(file + ".jsonld", rdfHandler.getRdfString("JSON-LD",topLevelResources));
    		}    				
    	}
    
    

  
    	
    	
    	System.out.print("done!");
    	
    	
    	
    	
    	
    	
    	
    	/*
    	 * 
    	 * 	Resource dnaComponentResource=rdfHelper.CreateResource("http://sbols.org/v1#DnaComponent");
	Resource dnaSequenceResource=rdfHelper.CreateResource("http://sbols.org/v1#DnaSequence");
	Resource[] topLevelResources=new Resource[]{dnaComponentResource,dnaSequenceResource};
	
	//rdfHelper.Write(rdfHelper,RDFFormat.RDFXML_ABBREV,topLevelResources,"RDFExamples",true);
	rdfHelper.Write(rdfHelper,"RDF/XML-ABBREV",topLevelResources,"RDFExamples/pretty.xml",true);
	rdfHelper.Write(rdfHelper,"RDF/XML",topLevelResources,"RDFExamples/plain.xml",true);
	rdfHelper.Write(rdfHelper,"Turtle",topLevelResources,"RDFExamples/turtle.xml",true);
	rdfHelper.Write(rdfHelper, RDFFormat.TRIG_BLOCKS,"RDFExamples/turtleblocks.xml",true);
	
	//SBOLDocument sbolDocument= SBOLHelper.Read("RDFExamples/pretty.xml");
	
	/*Write (rdfHelper, RDFFormat.RDFXML_ABBREV,true);
	Write (rdfHelper, RDFFormat.RDFXML_PLAIN,true);
	Write (rdfHelper, RDFFormat.TRIG_BLOCKS,true);*/
    	 
    }
}
