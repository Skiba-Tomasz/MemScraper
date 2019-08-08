package memscraper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MemScraper {
	private String domainURL = "https://jbzdy.net/str/1";
	private WebClient client;
	
	
	public static void main(String[] args) {
		MemScraper memScraper = new MemScraper();
	}
	
	public MemScraper(){
		client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(true);
		client.getOptions().setUseInsecureSSL(true);
		
		try {
			HtmlPage pageContent = client.getPage(domainURL);	
			List<HtmlElement> memlist = pageContent.getByXPath("//article[@class=\"resource-object \"]");
			if(memlist.isEmpty())System.out.println("NO MEMES LOL");
			else {
				System.out.println(getAllChildContent(memlist));
				
				
				/*for(HtmlElement mem :memlist) {
					System.out.println();
					System.out.println();
					System.out.println("Name: " +mem.getAttribute("title"));
					 DomNodeList<DomNode> contentInfo =  mem.getChildNodes();
					for(DomNode c: contentInfo) {
						System.out.println(c.asText());
						if(c.hasChildNodes()) {
							for(DomNode cc: c.getChildNodes()) {
								System.out.println(cc.asText());
							}
						}
					}
				}*/
			}
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAllChildContent(List<HtmlElement> elements) {
		String content = new String();
		List<DomNode> allChildren = new ArrayList<DomNode>();
		for(HtmlElement mem :elements) {
			DomNodeList<DomNode> contentInfo =  mem.getChildNodes();
			allChildren.addAll(getAllDomNodes(contentInfo));
		}
		for(DomNode line: allChildren) {
			content += line.asText() +"\n";
		}
		return content;
	}
	
	public List<DomNode> getAllDomNodes(DomNodeList<DomNode> domNodeInputList) {
		List<DomNode> domNodeOutputList = new ArrayList<DomNode>();
		for(DomNode c: domNodeInputList) {
			System.out.println(c.asText());
			if(c.hasChildNodes()) {
				domNodeOutputList.addAll(getAllDomNodes(c.getChildNodes()));
			}
		}
		return domNodeOutputList;
	}
}


/*			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
DocumentBuilder documentBuilder = null;
try {
	documentBuilder = documentFactory.newDocumentBuilder();
} catch (ParserConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
Document document = null;
try {
	document = documentBuilder.parse(pageContent.asText());
} catch (SAXException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

XPath xpath = XPathFactory.newInstance().newXPath();

String expression = "/article[@class=\"resource-object \"]";

try {
	Node title = (Node) xpath.compile(expression).evaluate(document, XPathConstants.NODE);
	System.out.println(title);
} catch (XPathExpressionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	*/		
