package com.cotodel.hrms.web.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitemapController {
	
    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Resource> getSitemap() {
        Resource sitemap = new ClassPathResource("static/sitemap.xml");
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(sitemap);
    }

    @GetMapping(value = "/robots.txt", produces = "text/plain")
    public ResponseEntity<Resource> serveRobotsTxt() throws IOException {
        Resource sitemap = new ClassPathResource("static/robots.txt");
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(sitemap);
    }
    
}



