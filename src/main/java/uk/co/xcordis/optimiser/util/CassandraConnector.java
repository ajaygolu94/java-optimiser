/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;

/**
 * The <code>CassandraConnector</code> class is responsible for maintain the connection and session in <b>Optimiser</b>application.
 *
 * @author Rob Atkin
 */
@Repository
@PropertySource(ApplicationConstants.DATABASE_PROPERTY_FILE_CLASSPATH)
public class CassandraConnector {

	private static final Logger logger = LoggerFactory.getLogger(CassandraConnector.class);

	private Cluster cluster;
	private Session session;

	@Value(ApplicationConstants.CASSANDRA_CONTACTPOINTS_VALUE_LABEL)
	private String ip;

	@Value(ApplicationConstants.CASSANDRA_PORT_VALUE_LABEL)
	private Integer port;

	@Value(ApplicationConstants.CASSANDRA_KEYSPACE_VALUE_LABEL)
	private String keySpace;

	@Value(ApplicationConstants.CASSANDRA_USER_VALUE_LABEL)
	private String user;

	@Value(ApplicationConstants.CASSANDRA_PASSWORD_VALUE_LABEL)
	private String password;

	/**
	 * @return the keySpace
	 */
	public String getKeySpace() {

		return keySpace;
	}

	/**
	 * @param keySpace
	 *            the keySpace to set
	 */
	public void setKeySpace(final String keySpace) {

		this.keySpace = keySpace;
	}

	/**
	 * @return the cluster
	 */
	public Cluster getCluster() {

		return cluster;
	}

	/**
	 * @param cluster
	 *            the cluster to set
	 */
	public void setCluster(final Cluster cluster) {

		this.cluster = cluster;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(final Session session) {

		this.session = session;
	}

	/**
	 * This method <code>connect</code> is responsible to connect with the Cassandra database and creating the cluster.
	 */
	@PostConstruct
	public void connect() {

		logger.info(" ==> Method ==> connect ==> Enter");

		final PoolingOptions poolingOptions = new PoolingOptions();

		// Setting poolingOptions
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, 4, 10).setConnectionsPerHost(HostDistance.REMOTE, 2, 4);
		poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, 32768).setMaxRequestsPerConnection(HostDistance.REMOTE, 2000);
		poolingOptions.setPoolTimeoutMillis(10000);

		logger.info(" ==> Method ==> connect ==> ip ==> " + ip + " ==>port " + port + " ==>keySpace " + keySpace);
		cluster = Cluster.builder().addContactPoints(getInetAddreeListFormIPs()).withPort(port).withPoolingOptions(poolingOptions)
				.withProtocolVersion(ProtocolVersion.V4).withCredentials(user, password).build();

		session = cluster.connect(keySpace);
		setKeySpace(keySpace);
		setSession(session);
		setCluster(cluster);

		logger.info(" ==> Method ==> connect ==> Exit");
	}

	/**
	 * This method <code>getSession</code> is responsible to return the session object.
	 *
	 * @return Session
	 */
	public Session getSession() {

		if (session == null) {
			session = getCluster().connect(getKeySpace());
		}
		return session;
	}

	/**
	 * This method <code>close</code> is responsible to close the TCP connection with cassandra.
	 */
	public void close() {

		session.close();
		cluster.close();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {

		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * This method <code>getInetAddreeListFormIPs</code> is responsible to get the IP of all seeds nodes and convert into the InetAddress list.
	 *
	 * @return
	 */
	private List<InetAddress> getInetAddreeListFormIPs() {

		logger.info(" ==> Method ==> getInetAddreeListFormIPs ==> Enter");

		final List<InetAddress> ips = new ArrayList<>();

		if (!ApplicationUtils.isEmpty(ip)) {

			final String ipArray[] = ip.split(";");
			for (final String stringIP : ipArray) {

				if (!ApplicationUtils.isEmpty(stringIP)) {

					try {
						ips.add(InetAddress.getByName(stringIP));
					} catch (final UnknownHostException e) {
						logger.error(" ==> Method ==> getInetAddreeListFormIPs ==> UnknownHostException ==> " + e);
					}
				}
			}
		}

		logger.info(" ==> Method ==> getInetAddreeListFormIPs ==> Exit");
		return ips;
	}
}
