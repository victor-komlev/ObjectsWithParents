package com.vkomlev.app.controller;

import com.vkomlev.app.dto.ObjectTestArrayContainer;
import com.vkomlev.app.service.ObjectTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController @RequestMapping("/api") public class RestApiController {

    private static final int DEFAULT_DEPTH = 5;

    @Autowired private ObjectTestService objectTestService;

    @GetMapping("/testobject/{id}") @ResponseBody public ResponseEntity<Object> getObjectTest(
            @PathVariable("id") String id, @RequestParam(value = "hierarchy", required = false) String hierarchy,
            @RequestParam(value = "depth", required = false) Integer depth) {
        ResponseEntity<Object> responseEntity;
        hierarchy = !StringUtils.isEmpty(hierarchy) ? hierarchy : "DESC";
        switch (hierarchy) {
        case "ASC":
            responseEntity = new ResponseEntity<>(
                    objectTestService.findObjectWithParents(id, (depth != null ? depth : DEFAULT_DEPTH)),
                    HttpStatus.OK);
            break;
        case "DESC":
            responseEntity = new ResponseEntity<>(
                    objectTestService.findObjectWithChildren(id, (depth != null ? depth : DEFAULT_DEPTH)),
                    HttpStatus.OK);
            break;
        default:
                responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    @PostMapping("/testobject") @ResponseBody public ResponseEntity<Object> saveTestObjects (@RequestBody ObjectTestArrayContainer objects) {
        objectTestService.saveTestObjects(objects.getObject());
        return ResponseEntity.created(URI.create("")).build();
    }
}
