package science.aist.xes.model;

import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.xes.model.impl.LogRepository;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <p>Write an example XES stream</p>
 *
 * @author Andreas Pointner
 */
public class ExampleTest {

    private final XMLRepository<LogType> repository = new LogRepository();

    @Test
    @SneakyThrows
    public void testSaveXes() {
        // given
        var factory = new ObjectFactory();
        LogType logType = factory.createLogType();
        TraceType traceType = factory.createTraceType();
        EventType eventType = factory.createEventType();
        AttributeDateType attributeDateType = factory.createAttributeDateType();
        attributeDateType.setKey("time:timestamp");
        attributeDateType.setValue(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2022, Calendar.JUNE, 27)));
        eventType.getStringOrDateOrInt().add(attributeDateType);
        traceType.getEvent().add(eventType);
        logType.getTrace().add(traceType);

        // when
        repository.save(factory.createLog(logType), new FileOutputStream("target/test.xes"));

        // then
        Files.exists(Path.of("target", "test.xes"));
    }

    @Test
    public void testLoadXes() {
        // given
        InputStream xesIS = getClass().getResourceAsStream("/xes.xml");

        // when
        JAXBElement<LogType> elem = repository.load(xesIS);

        // then
        Assert.assertNotNull(elem);
        LogType value = elem.getValue();
        Assert.assertEquals(value.getExtension().size(), 4);
        Assert.assertEquals(value.getTrace().size(), 1000);
        Assert.assertEquals(value.getTrace().get(0).getEvent().size(), 6);
    }
}
