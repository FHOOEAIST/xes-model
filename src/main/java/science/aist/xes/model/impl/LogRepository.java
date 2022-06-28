/*
 * Copyright (c) 2022 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.xes.model.impl;

import lombok.SneakyThrows;
import science.aist.xes.model.LogType;
import science.aist.xes.model.XMLRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>Repository for {@link LogType}</p>
 *
 * @author Andreas Pointner
 * @since 1.0
 */
public class LogRepository implements XMLRepository<LogType> {
    private final JAXBContext jaxbContext;

    @SneakyThrows
    public LogRepository() {
        jaxbContext = JAXBContext.newInstance(LogType.class);
    }

    @SneakyThrows
    @Override
    public void save(JAXBElement<LogType> jaxbElement, OutputStream os) {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(jaxbElement, os);
    }

    @SneakyThrows
    @Override
    public JAXBElement<LogType> load(InputStream is) {
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(is), LogType.class);
    }
}
