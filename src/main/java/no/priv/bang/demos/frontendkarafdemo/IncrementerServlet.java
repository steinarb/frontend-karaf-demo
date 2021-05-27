package no.priv.bang.demos.frontendkarafdemo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.priv.bang.osgi.service.adapters.logservice.LoggerAdapter;

import static no.priv.bang.demos.frontendkarafdemo.ApplicationConstants.*;

@SuppressWarnings("serial")
@Component(service={Servlet.class}, property={"alias=" + APPLICATION_PATH + "/api/increment"} )
public class IncrementerServlet extends HttpServlet {
    static final ObjectMapper mapper = new ObjectMapper();
    final LoggerAdapter logger = new LoggerAdapter(getClass());

    @Reference
    public void setLogService(LogService logservice) {
        this.logger.setLogService(logservice);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            try(ServletInputStream postBody = request.getInputStream()) {
                Counter incrementedCounter = increment(mapper.readValue(postBody, Counter.class));
                response.setStatus(HttpServletResponse.SC_OK);
                try(PrintWriter responseBody = response.getWriter()) {
                    mapper.writeValue(responseBody, incrementedCounter);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to increment the counter value", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try(PrintWriter responseBody = response.getWriter()) {
                mapper.writeValue(responseBody, Error.with().status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message(e.getMessage()).build());
            } catch (Exception e2) {
                // Swallow exception quietly and just return the error code
            }
        }
    }

    Counter increment(Counter counter) {
        int delta = counter.getDelta();
        return Counter.with()
            .value(counter.getValue() + delta)
            .delta(delta)
            .build();
    }

}
