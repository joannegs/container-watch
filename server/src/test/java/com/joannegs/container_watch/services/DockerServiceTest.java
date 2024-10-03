package com.joannegs.container_watch.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.joannegs.container_watch.services.DockerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DockerServiceTest {

    @Mock
    private DockerClient dockerClient;

    @Mock
    private StartContainerCmd startContainerCmd;

    @Mock
    private StopContainerCmd stopContainerCmd;

    @Mock
    private RemoveContainerCmd removeContainerCmd;

    @Mock
    private CreateContainerCmd createContainerCmd;

    @Mock
    private ListContainersCmd listContainersCmd;

    @Mock
    private ListImagesCmd listImagesCmd;

    @InjectMocks
    private DockerService dockerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListContainers() {
        List<Container> mockContainers = Collections.emptyList();
        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(true)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockContainers);

        List<Container> result = dockerService.listContainers(true);

        assertNotNull(result);
        assertEquals(mockContainers, result);
        verify(dockerClient, times(1)).listContainersCmd();
        verify(listContainersCmd, times(1)).withShowAll(true);
        verify(listContainersCmd, times(1)).exec();
    }

    @Test
    void testListImages() {
        List<Image> mockImages = Collections.emptyList();
        when(dockerClient.listImagesCmd()).thenReturn(listImagesCmd);
        when(listImagesCmd.exec()).thenReturn(mockImages);

        List<Image> result = dockerService.listImages();

        assertNotNull(result);
        assertEquals(mockImages, result);
        verify(dockerClient, times(1)).listImagesCmd();
        verify(listImagesCmd, times(1)).exec();
    }


    @Test
    void testFilterImages() {
        String imageName = "imageName";
        List<Image> mockImages = Collections.emptyList();
        when(dockerClient.listImagesCmd()).thenReturn(listImagesCmd);
        when(listImagesCmd.withImageNameFilter(imageName)).thenReturn(listImagesCmd);
        when(listImagesCmd.exec()).thenReturn(mockImages);

        List<Image> result = dockerService.filterImages(imageName);

        assertNotNull(result);
        assertEquals(mockImages, result);
        verify(dockerClient, times(1)).listImagesCmd();
        verify(listImagesCmd, times(1)).withImageNameFilter(imageName);
        verify(listImagesCmd, times(1)).exec();
    }

    @Test
    void testStartContainer() {
        String containerId = "abc123";
        when(dockerClient.startContainerCmd(containerId)).thenReturn(startContainerCmd);

        dockerService.startContainer(containerId);

        verify(dockerClient, times(1)).startContainerCmd(containerId);
        verify(startContainerCmd, times(1)).exec();
    }

    @Test
    void testStopContainer() {
        String containerId = "abc123";
        when(dockerClient.stopContainerCmd(containerId)).thenReturn(stopContainerCmd);

        dockerService.stopContainer(containerId);

        verify(dockerClient, times(1)).stopContainerCmd(containerId);
        verify(stopContainerCmd, times(1)).exec();
    }

    @Test
    void testDeleteContainer() {
        String containerId = "abc123";
        when(dockerClient.removeContainerCmd(containerId)).thenReturn(removeContainerCmd);

        dockerService.deleteContainer(containerId);

        verify(dockerClient, times(1)).removeContainerCmd(containerId);
        verify(removeContainerCmd, times(1)).exec();
    }

    @Test
    void testCreateContainer() {
        String imageName = "nginx:latest";
        when(dockerClient.createContainerCmd(imageName)).thenReturn(createContainerCmd);

        dockerService.createContainer(imageName);

        verify(dockerClient, times(1)).createContainerCmd(imageName);
        verify(createContainerCmd, times(1)).exec();
    }
}
