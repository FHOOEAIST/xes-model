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
