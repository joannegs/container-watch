package com.joannegs.container_watch.controllers;

import com.github.dockerjava.api.model.Container;
import com.joannegs.container_watch.services.DockerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DockerContainersControllerTest {

    @Mock
    private DockerService dockerService;

    @InjectMocks
    private DockerContainersController dockerContainersController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListContainers() {
        List<Container> mockContainers = Collections.emptyList();
        when(dockerService.listContainers(true)).thenReturn(mockContainers);

        List<Container> result = dockerContainersController.listContainers(true);

        assertNotNull(result);
        assertEquals(mockContainers, result);
        verify(dockerService, times(1)).listContainers(true);
    }

    @Test
    void testStartContainer() {
        String containerId = "abc123";
        dockerContainersController.startContainer(containerId);
        verify(dockerService, times(1)).startContainer(containerId);
    }

    @Test
    void testStopContainer() {
        String containerId = "abc123";
        dockerContainersController.stopContainer(containerId);
        verify(dockerService, times(1)).stopContainer(containerId);
    }

    @Test
    void testDeleteContainer() {
        String containerId = "abc123";
        dockerContainersController.deleteContainer(containerId);
        verify(dockerService, times(1)).deleteContainer(containerId);
    }

    @Test
    void testCreateContainer() {
        String imageName = "nginx:latest";
        dockerContainersController.createContainer(imageName);
        verify(dockerService, times(1)).createContainer(imageName);
    }
}