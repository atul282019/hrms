package com.cotodel.hrms.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitemapController {

//	 @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
//	    public String getSitemap() {
//	        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//	               "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n" +
//	               "    <url>\n" +
//	               "        <loc>https://cotodel.com/</loc>\n" +
//	               "        <lastmod>2024-04-23</lastmod>\n" +
//	               "        <changefreq>monthly</changefreq>\n" +
//	               "        <priority>1.0</priority>\n" +
//	               "    </url>\n" +
//	               "</urlset>";
//	    }
	

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Resource> getSitemap() {
        Resource sitemap = new ClassPathResource("static/sitemap.xml");
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(sitemap);
    }
    
}



