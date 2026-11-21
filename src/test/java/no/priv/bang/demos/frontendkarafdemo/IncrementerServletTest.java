package no.priv.bang.demos.frontendkarafdemo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

class IncrementerServletTest {
    private static Logger logger;
    private ListAppender<ILoggingEvent> logappender;

    @BeforeEach
    void setup() {
        logger = (Logger) LoggerFactory.getLogger(IncrementerServlet.class);
        logappender = new ListAppender<>();
        logappender.start();
        logger.addAppender(logappender);
    }

    @AfterEach
    void teardown() {
        logger.detachAppender(logappender);
    }

    @Test
    void doPostIncrement() throws Exception {
        var value = Counter.with().value(10).delta(1).build();
        var valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        var incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertThat(incrementedValue.value()).isGreaterThan(value.value());
    }

    @Test
    void doPostDecrement() throws Exception {
        var value = Counter.with().value(10).delta(-1).build();
        var valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        var incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertThat(incrementedValue.value()).isLessThan(value.value());
    }

    @Test
    void doPostNotJson() throws Exception {
        var valueNotJson = "<this>is <not>json</not></this>";
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueNotJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());

        var incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Error.class);
        assertEquals(500,incrementedValue.status());
        assertThat(incrementedValue.message()).startsWith("Unexpected character");
    }

    @Test
    void doPostIncrementWithWrongNameForValueProperty() throws Exception {
        var valueAsJson = "{ \"currentValue\": 0, \"delta\": -1 }";
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());
        assertThat(logappender.list).isNotEmpty();
    }

}
