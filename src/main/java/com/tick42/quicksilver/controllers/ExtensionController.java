package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.security.JwtValidator;
import com.tick42.quicksilver.services.base.ExtensionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/extension")
public class ExtensionController {

    private final ExtensionService extensionService;
    private HttpServletRequest request;
    private JwtValidator validator;

    @Autowired
    public ExtensionController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping(value = "/all")
    public List<ExtensionDTO> findAll() {
        return extensionService.findAll();
    }

    @GetMapping(value = "/get/{id}")
    public ExtensionDTO findById(@PathVariable(name = "id") int id) {
        return extensionService.findById(id);
    }

    @GetMapping(value = "/search/{name}")
    public List<ExtensionDTO> findByName(@PathVariable(name = "name") String name) {
        return extensionService.findByName(name);
    }

    @GetMapping(value = "/mostDownloads/{count}")
    public List<ExtensionDTO> mostDownloaded(@PathVariable(name = "count") int count) {
        return extensionService.findTopMostDownloaded(count);
    }

    @GetMapping(value = "/mostRecentUploads/{count}")
    public List<ExtensionDTO> latestUploads(@PathVariable(name = "count") int count) {
        return extensionService.findMostRecentUploads(count);
    }

    @GetMapping(value = "/featured")
    public List<ExtensionDTO> featured() {
        return extensionService.findFeatured();
    }

    @GetMapping(value = "/userExtensions")
    public List<ExtensionDTO> featured(HttpServletRequest request, HttpServletResponse response){
        int id = validator.getUserIdFromToken(request, response);
        return extensionService.findUserExtensions(id);
    }

    @PatchMapping(value = "/approveExtension/{id}")
    void acceptExtension(@PathVariable(name = "id") int id) {
        extensionService.approveExtension(id);
    }

    @PatchMapping(value = "/changeFeaturedState/{id}/{newState}")
    void changeFeaturedState(@PathVariable("id") int id, @PathVariable("newState") String newState) {
        extensionService.changeFeaturedState(id, newState);
    }

    @GetMapping(value = "/findByTag/{tag}")
    public List<ExtensionDTO> findByTag(@PathVariable String tag) {
        return extensionService.findByTag(tag);
    }

    @PostMapping(value = "/add")
    public @ResponseBody
    ExtensionDTO addExtension(@RequestBody ExtensionSpec extension) {
        return extensionService.create(extension);
    }


//    @GetMapping(value = "/httpRequest")
//    public @ResponseBody
//    void generateReport(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println(request.getHeader("Authorization"));
//        System.out.println(response.getHeader("Authorization"));
//    }

//    @GetMapping(value = "/sortByUploadDate")
//    public List<Extension> sortByUploadDate() {
//        return extensionService.sortByUploadDate();
//    }
//
//    @GetMapping(value = "/sortByMostDownloads")
//    public List<Extension> sortByMostDownloads() {
//        return extensionService.sortByMostDownloads();
//    }
//
//    @GetMapping(value = "/sortByCommitDate")
//    public List<Extension> sortByCommitDate() {
//        return extensionService.sortByCommitDate();
//    }

    //    @DeleteMapping(value = "/delete/{id}")
//    void deleteExtension(@PathVariable int id) {
//        extensionService.delete(id);
//    }
}
