package com.joannegs.container_watch.controllers;

import com.github.dockerjava.api.model.Image;
import com.joannegs.container_watch.controllers.DockerImagesController;
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

class DockerImagesControllerTest {

    @Mock
    private DockerService dockerService;

    @InjectMocks
    private DockerImagesController dockerImagesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListImages() {
        // Arrange
        List<Image> mockImages = Collections.emptyList();
        when(dockerService.listImages()).thenReturn(mockImages);

        // Act
        List<Image> result = dockerImagesController.listImages();

        // Assert
        assertNotNull(result);
        assertEquals(mockImages, result);
        verify(dockerService, times(1)).listImages();
    }

    @Test
    void testFilterImages() {
        // Arrange
        String filterName = "nginx";
        List<Image> mockImages = Collections.emptyList();
        when(dockerService.filterImages(filterName)).thenReturn(mockImages);

        // Act
        List<Image> result = dockerImagesController.filterImages(filterName);

        // Assert
        assertNotNull(result);
        assertEquals(mockImages, result);
        verify(dockerService, times(1)).filterImages(filterName);
    }
}
