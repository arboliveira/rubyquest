/*
 * Created on 07/04/2010
 */
package rubyquest.downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;

import rubyquest.downloader.ConditionalRender.DoIt;
import rubyquest.downloader.ConditionalRender.IfTargetExists;
import rubyquest.downloader.ConditionalRender.Target;

public class Downloader {

	public Downloader(final IfTargetExists exists, final DoIt doIt) {
		this.exists = exists;
		this.doIt = doIt;
		prepareToHttps();
	}

	public void download(final URL source, final File destination) {
		new ConditionalRender(new Download(source, destination), this.exists, this.doIt).render();
	}

	class Download implements Target {

		private final URL source;
		private final File destination;

		Download(final URL source, final File destination) {
			this.source = source;
			this.destination = destination;
		}

		@Override
		public boolean exists() {
			return destination.exists();
		}

		@Override
		public void render() {
			logger.info("Downloading: " + destination);
			download();
			logger.info("Downloaded: " + destination);
		}

		@Override
		public void onSkip() {
			logger.info("Found, skipping download: " + destination);
		}

		@Override
		public void onDryRun() {
			logger.info("Dry run, skipping download: " + destination);
		}

		private void download() {
			try {
				FileUtils.copyURLToFile(source, destination);
			} catch (final IOException e) {
				throw new UnhandledException(e);
			}
		}
	}

	private static void prepareToHttps() {
		HttpsURLConnection.setDefaultSSLSocketFactory(newSSLSocketFactory());
	}

	private static SSLSocketFactory newSSLSocketFactory() {
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
				//
			}

			@Override
			public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
				//
			}
		} };

		try {
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			return sc.getSocketFactory();
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new RuntimeException(e);
		}
	}

	private final IfTargetExists exists;
	private final DoIt doIt;
	final Logger logger = Logger.getLogger(this.getClass());

}