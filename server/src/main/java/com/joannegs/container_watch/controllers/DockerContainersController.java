package com.joannegs.container_watch.controllers;


import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.joannegs.container_watch.services.DockerService;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class DockerContainersController {

    private final DockerService dockerService;

    public DockerContainersController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping("")
    public List<Container> listContainers(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return this.dockerService.listContainers(showAll);
    }

    @GetMapping("/{id}/start")
    public void startContainer (@PathVariable String id) {
        dockerService.startContainer(id);
    }

    @GetMapping("/{id}/stop")
    public void stopContainer (@PathVariable String id) {
        dockerService.stopContainer(id);
    }

    @DeleteMapping("/{id}")
    public void deleteContainer(@PathVariable String id) {
        dockerService.deleteContainer(id);
    }

    @PostMapping("")
    public void createContainer(@RequestParam String imageName) {
        dockerService.createContainer(imageName);
    }
}
