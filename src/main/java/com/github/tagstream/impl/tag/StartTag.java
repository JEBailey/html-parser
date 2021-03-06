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
package com.github.tagstream.impl.tag;

import java.util.Collections;
import java.util.Map;

import com.github.tagstream.AttrValue;
import com.github.tagstream.Element;
import com.github.tagstream.ElementType;

public class StartTag implements Element {

    private String value;
    private Map<String, AttrValue> attributes = Collections.emptyMap();

    public StartTag(String value, Map<String, AttrValue> attrList) {
        this.value = value;
        attributes = attrList;
    }

    @Override
    public ElementType getType() {
        return ElementType.START_TAG;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean supportsAttributes() {
        return true;
    }

    @Override
    public Map<String, AttrValue> getAttributes() {
        return attributes;
    }
}