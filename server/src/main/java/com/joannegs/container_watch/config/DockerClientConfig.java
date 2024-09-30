package com.joannegs.container_watch.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.RemoteApiVersion;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DockerClientConfig {

    @Value("${docker.socket.path}")
    private String dockerSocketPath;

    @Value("${docker.tcp.path}")
    private String tcpDockerHostPath;

    @Bean
    public DockerClient buildDockerClient(){
        DefaultDockerClientConfig.Builder dockerClientConfigBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder();

        if (isWindows()) {
            // Windows - TCP connection
            dockerClientConfigBuilder.withDockerHost(this.tcpDockerHostPath)
                    .withApiVersion(RemoteApiVersion.VERSION_1_24)
                    .withDockerTlsVerify(false);
        } else if (this.dockerSocketPath != null && this.dockerSocketPath.startsWith("unix://")) {
            // Unix - Unix socket
            dockerClientConfigBuilder.withDockerHost(dockerSocketPath)
                    .withApiVersion(RemoteApiVersion.VERSION_1_24)
                    .withDockerTlsVerify(false);
        }


        DefaultDockerClientConfig dockerClientConfig = dockerClientConfigBuilder
                .build();

        ApacheDockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .maxConnections(5)
                .connectionTimeout(Duration.ofMillis(300))
                .responseTimeout(Duration.ofSeconds(3))
                .build();

        DockerClient client = DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerHttpClient(dockerHttpClient)
                .build();

        client.pingCmd().exec();

        return client;

    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
