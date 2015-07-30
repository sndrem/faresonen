package sim.tv2.no.tippeligaen.fotballxtra.HTMLParser;

import java.io.IOException;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class HtmlConverter extends HTMLEditorKit.ParserCallback {

	private StringBuffer s;
	
	public HtmlConverter() {
		// TODO Auto-generated constructor stub
	}
	    
    public void parse(Reader in) throws IOException {
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        delegator.parse(in, this, Boolean.TRUE);
    }
    
    public void handleText(char[] text, int pos) {
        s.append(text);
        s.append("\n");
    }
    
    public String getText() {
        return s.toString();
    }

}
