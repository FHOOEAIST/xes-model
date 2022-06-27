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
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * <p>Write an example XES stream</p>
 *
 * @author Andreas Pointner
 */
public class ExampleTest {

    private final XMLRepository<LogType> repository = new LogRepository();

    @SneakyThrows
    private static boolean isEqual(Path firstFile, Path secondFile) {
        if (Files.size(firstFile) != Files.size(secondFile)) {
            return false;
        }

        byte[] first = Files.readAllBytes(firstFile);
        byte[] second = Files.readAllBytes(secondFile);
        return Arrays.equals(first, second);
    }

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
        Assert.assertTrue(isEqual(Paths.get(Objects.requireNonNull(getClass().getResource("/compare-xes.xml")).toURI()), Path.of("target", "test.xes")));
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
