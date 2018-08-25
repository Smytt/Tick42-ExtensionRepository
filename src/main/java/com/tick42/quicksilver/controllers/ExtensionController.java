package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Spec.ExtensionSpec;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.services.base.ExtensionService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/extension")
public class ExtensionController {

    private final ExtensionService extensionService;
    private HttpServletRequest request;
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

    @GetMapping(value = "/featured/{count}")
    public List<ExtensionDTO> featured(@PathVariable(name = "count") int count) {
        return extensionService.findFeatured(count);
    }

    @PatchMapping(value = "/approveExtension/{id}")
    void acceptExtension(@PathVariable(name = "id") int id) {
        extensionService.approveExtension(id);
    }

    @PatchMapping(value = "/changeFeaturedState/{id}/{newState}")
    void changeFeaturedState(@PathVariable("id") int id, @PathVariable("newState") String newState){
        extensionService.changeFeaturedState(id, newState);
    }

    @PostMapping(value = "/add")
    public @ResponseBody
    ExtensionDTO addExtension(@RequestBody ExtensionSpec extension) {
        return extensionService.create(extension);
    }


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
