package org.smarterbalanced.itemviewerservice.web.backing;

import AIR.Common.Web.Session.BaseServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tds.itemrenderer.apip.APIPCsvLoader;
import javax.servlet.ServletContextEvent;


public class TDSApplication  extends BaseServletContextListener {
    private static final Logger _logger = LoggerFactory.getLogger (TDSApplication.class);

    public void contextInitialized (ServletContextEvent sce) {
        super.contextInitialized (sce);
        // log app start and list out assemblies
        StringBuilder logBuilder = new StringBuilder ("IVS application Started: ");
        // String path = sce.getServletContext ().getRealPath (".");
        setup();
        _logger.info (logBuilder.toString ());
    }

    private static void setup(){
        loadAPIP ();
    }

    private static void loadAPIP(){
        try {
            APIPCsvLoader.loadRules();
        } catch (Exception ex) {
            _logger.error("TDSApplication.loadAPIP: " + ex.getMessage (), "loadAPIP", null, ex);
        }
    }

}
