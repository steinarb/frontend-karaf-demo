package no.priv.bang.demos.frontendkarafdemo;

import static no.priv.bang.demos.frontendkarafdemo.ApplicationConstants.APPLICATION_PATH;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import no.priv.bang.servlet.frontend.FrontendServlet;


@Component(service={Servlet.class}, property={"alias=" + APPLICATION_PATH} )
public class ReactServlet extends FrontendServlet {
    private static final long serialVersionUID = 250817058831319271L;

    public ReactServlet() {
        super();
        setRoutes("/", "/counter", "/about");
    }

    @Override
    @Reference
    public void setLogService(LogService logservice) {
        super.setLogService(logservice);
    }

}
