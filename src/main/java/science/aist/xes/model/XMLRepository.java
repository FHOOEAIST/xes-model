/*
 * Copyright (c) 2022 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.xes.model;

import javax.xml.bind.JAXBElement;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>Generic Definition of a XML Repository</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public interface XMLRepository<T> {
    /**
     * Saves a given element to the given output stream
     *
     * @param jaxbElement the element
     * @param os          the output stream
     */
    void save(JAXBElement<T> jaxbElement, OutputStream os);

    /**
     * Loads an element from a given input stream
     *
     * @param is the input stream
     * @return the loaded element
     */
    JAXBElement<T> load(InputStream is);
}
