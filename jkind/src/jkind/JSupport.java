package jkind;
 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashSet; 
import java.util.List;
import java.util.Set; 

import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.engines.MiniJKind;
import jkind.engines.ivcs.IvcUtil;
import jkind.engines.ivcs.MinimalIvcFinder; 
import jkind.lustre.Node;
import jkind.lustre.Program; 
import jkind.lustre.builders.NodeBuilder;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.RemoveEnumTypes;
import jkind.translation.Specification;
import jkind.translation.Translate; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class JSupport {
	private static Set<String> inputIVC;
	private static int TIMEOUT;
	public static void main(String args[]) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			Program program = Main.parseLustre(settings.filename);
			
			StaticAnalyzer.check(program, settings.solver);
			if (!LinearChecker.isLinear(program)) {
				throw new IllegalArgumentException("Non-linear not supported");
			}
			
			program = Translate.translate(program);
			program = RemoveEnumTypes.program(program);
			Node main = program.getMainNode();
			main = LustreSlicer.slice(main, new DependencyMap(main, main.properties, program.functions));
			main = new NodeBuilder(main).clearIvc().build();

			if (main.properties.size() != 1) {
				throw new IllegalArgumentException("Expected exactly one property, but found "
						+ main.properties.size());
			}
			
			inputIVC = getIVC(settings.useUnsatCore);

			MinimalIvcFinder minimalFinder = new MinimalIvcFinder(new Program(IvcUtil.overApproximateWithIvc(main, inputIVC, main.properties.get(0))),
					settings.filename);
			minimalFinder.minimizeIvc(inputIVC, new HashSet<>(), TIMEOUT);
			//-------------- computing MUST ---------------------
			//MinimalIvcFinder minimalFinder = new MinimalIvcFinder(main, settings.filename, main.properties.get(0));
			//minimalFinder.computeMust(inputIVC, true, TIMEOUT);
			System.exit(0);
		 	
			}catch (Throwable t) {
				t.printStackTrace();
				System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
			}
	}
	
	private static Set<String> getIVC(String file){  
		List<String> support = null;
		try{
			DocumentBuilder builder = (DocumentBuilderFactory.newInstance()).newDocumentBuilder();
			Document doc = builder.parse(new InputSource(file));
			Element progressElement =  doc.getDocumentElement();
			support = getStringList(getElements(progressElement, "IVC"));
			double d = Double.parseDouble(getStringList(getElements(progressElement, "Timeout")).get(0));
			TIMEOUT = (int)d;
        }

		catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" +  file + "'");                
        }
        catch(IOException e) {
            System.out.println("Error reading file '"  + file + "'");  
        } catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return new HashSet<>(support);
	}
	
	private static List<String> getStringList(List<Element> elements) {
		List<String> result = new ArrayList<>();
		for (Element e : elements) {
			result.add(e.getTextContent());
		}
		return result;
	}

	private static List<Element> getElements(Element element, String name) {
		List<Element> elements = new ArrayList<>();
		NodeList nodeList = element.getElementsByTagName(name);
		for (int i = 0; i < nodeList.getLength(); i++) {
			elements.add((Element) nodeList.item(i));
		}
		return elements;
	}
	
}

	