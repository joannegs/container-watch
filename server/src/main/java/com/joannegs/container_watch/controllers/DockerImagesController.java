package com.joannegs.container_watch.controllers;

import com.github.dockerjava.api.model.Image;
import com.joannegs.container_watch.services.DockerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class DockerImagesController {

    private final DockerService dockerService;

    public DockerImagesController(DockerService dockerService){
        this.dockerService = dockerService;
    }

    @GetMapping("")
    public List<Image> listImages(){
        return this.dockerService.listImages();
    }

    @GetMapping("/filter")
    public List<Image> filterImages(@RequestParam(required = true) String filterName){
        return this.dockerService.filterImages(filterName);
    }
}
