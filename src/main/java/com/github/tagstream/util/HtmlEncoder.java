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

import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Stream;

import com.github.tagstream.api.Element;
import com.github.tagstream.api.Tag;
import com.github.tagstream.api.impl.TagText;

public class HtmlEncoder implements Function<Element, Stream<Element>> {

    LinkedList<String> insideOf = new LinkedList<>();

    @Override
    public Stream<Element> apply(Element element) {
        switch (element.getType()) {

        case START_TAG:
            String name = ((Tag) element).getName();
            switch (name) {
            case "noframes":
            case "noscript":
            case "style":
            case "script":
                insideOf.push(name);
                break;
            default:
                break;
            }
            break;
        case END_TAG:
            String endName = ((Tag) element).getName();
            switch (endName) {
            case "noframes":
            case "noscript":
            case "style":
            case "script":
                while (!insideOf.isEmpty()) {
                    String tagName = insideOf.pop();
                    if (tagName.equals(endName)) {
                        break;
                    }
                }
                break;
            default:
                break;
            }
            break;
        case TEXT:
            if (!insideOf.isEmpty()) {
                TagText text = (TagText)element;
                text.setText(HtmlEntityTranslator.encodeHTML(text.getText()));
            }
            break;
        default:
            break;
        }
        return Stream.of(element);
    }

}
