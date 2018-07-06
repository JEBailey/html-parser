/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tagstream.util;

import java.util.function.Consumer;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.ext.LexicalHandler;

import com.github.tagstream.api.Element;
import com.github.tagstream.api.Tag;

public class HtmlSAXSupport implements Consumer<Element> {
    
    private static final DefaultHandler2 handler = new DefaultHandler2();
    
    private ContentHandler contentHandler = handler;
    private LexicalHandler lexicalHandler = handler;
    private boolean initialized;

    public HtmlSAXSupport(ContentHandler ch, final LexicalHandler lh) {
        if (ch != null) {
            contentHandler = ch;
        }
        if (lh != null ) {
            lexicalHandler = lh;
        }
    }

    @Override
    public void accept(Element element) {
        try {
            if (!initialized) {
                contentHandler.startDocument();
                initialized = true;
            }
            String value = element.toString();
            switch (element.getType()) {
            case COMMENT:
                lexicalHandler.comment(value.toCharArray(), 0, value.length());
                break;
            case DECLARATION:
                break;
            case END_TAG:
                lexicalHandler.endEntity(value);
                contentHandler.endElement("", value, value);
                break;
            case EOF:
                contentHandler.endDocument();
                break;
            case START_TAG:
                lexicalHandler.startEntity(value);
                contentHandler.startElement("", value, value, SaxAttributes.convert(((Tag)element).getAttributes()));
                break;
            case TEXT:
                contentHandler.characters(value.toCharArray(), 0, value.toCharArray().length);
                break;
            default:
                break;
            }
        } catch (SAXException se) {

        }

    }

}
