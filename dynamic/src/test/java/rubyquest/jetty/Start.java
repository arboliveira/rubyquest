package rubyquest.jetty;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {

	private static final int PORT = 8084;

	private static final Logger instructions = new Instructions().getLogger();

	public static void main(final String[] args) throws Exception {
		final Server server = new Server();
		final SocketConnector connector = new SocketConnector();
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(PORT);
		server.setConnectors(new Connector[] { connector });

		final WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");

		server.addHandler(bb);

		instructions
				.info(">>> STARTING EMBEDDED JETTY SERVER, PRESS ENTER TO STOP");
		server.start();
		instructions
				.info(">>> STARTED EMBEDDED JETTY SERVER, PRESS ENTER TO STOP");
		instructions.info(">>> TO USE THE WEBSITE, NAVIGATE TO: "
				+ "http://localhost:" + PORT);

		while (System.in.available() == 0)
			Thread.sleep(5000);
		server.stop();
		server.join();
	}
}